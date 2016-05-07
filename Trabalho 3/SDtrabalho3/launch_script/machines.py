#!/usr/bin/env python
import os
import sys
import json
import paramiko
import ConfigParser
from scp import SCPClient
from subprocess import call
import time
import socket
import subprocess
from collections import OrderedDict
from colorama import Fore, Back, Style


def generate_config():
    global ssh

    with open('configs/mapping.json') as json_data:
        jars = json.load(json_data)
        json_data.close()

    with open('configs/hosts.json') as json_data:
        hosts = json.load(json_data)
        json_data.close()

    print Style.BRIGHT + Fore.GREEN + "See what hosts are up to calculate the architecture of the solution" + Style.RESET_ALL

    for host in hosts:
        if not is_up_host(host["host"]):
            hosts.remove(host)
            continue

        try:
            ssh.connect(host["host"], username=host["user"], password=host["password"])
            ssh.exec_command("echo \"Hello!\"")
        except Exception:
            hosts.remove(host)

    jar_i = 0

    jars_hosts = []

    if len(hosts) == 0:
        print Fore.RED + "There are no machines active!" + Style.RESET_ALL
        exit(1)
    elif len(jars) <= len(hosts):
        for host in hosts:
            jars_hosts += [{
                "class": jars[jar_i],
                "host": host
            }]

            jar_i += 1
            if jar_i == len(jars):
                break
    else:
        for host in hosts:
            jars_hosts += [{
                "class": jars[jar_i],
                "host": host
            }]
            jar_i += 1
            if jar_i == len(jars):
                break

        for i in range(len(hosts) - 1, len(jars)):
            jars_hosts += [{
                "class": jars[i],
                "host": hosts[len(hosts) - 1]
            }]

    print Fore.GREEN + "Save the hosts in a config file! OK :D" + Style.RESET_ALL

    config = ConfigParser.RawConfigParser()
    config.add_section("mapping")

    for jars_host in jars_hosts:
        config.set("mapping", jars_host["class"]["class"] + "_HOST", jars_host["host"]["host"])
        if jars_host["class"]["type"] != "client":
            config.set("mapping", jars_host["class"]["class"] + "_PORT", jars_host["class"]["port"])

    config.set("mapping", "RegistryObject", 22125)
    config.set("mapping", "group", "sd0102")

    with open('configs/config.ini', 'wb') as configfile:
        config.write(configfile)
        configfile.close()

    # to upload
    config_up = ConfigParser.RawConfigParser()
    config_up.add_section("mapping")

    for jars_host in jars_hosts:
        ip = socket.gethostbyname(jars_host["host"]["host"])

        config_up.set("mapping", jars_host["class"]["class"] + "_HOST", ip)
        if jars_host["class"]["type"] != "client":
            config_up.set("mapping", jars_host["class"]["class"] + "_PORT", jars_host["class"]["port"])

    config_up.set("mapping", "RegistryObject", 22125)
    config_up.set("mapping", "group", jars_hosts[0]["host"]["user"])

    with open('configs/config_up.ini', 'wb') as configfile:
        config_up.write(configfile)
        configfile.close()

    with open('configs/config_up.ini', 'r+') as configfile:
        data = configfile.read()
        configfile.close()

    with open('configs/config.bash', 'w+') as configfile:
        data = data.replace(" ", "")
        data = data.replace("[mapping]", "")
        configfile.write(data)
        configfile.close()


def upload():
    print Style.DIM + Fore.BLUE + "Build and get ready: " + Style.RESET_ALL

    call(["sh", "build.sh"])
    lst = parse_config()

    # clean
    kill_all()

    # Upload folders on the remote server
    print Style.DIM + Fore.BLUE + "Upload folders on the remote server" + Style.RESET_ALL

    for key, value in lst.iteritems():
        ssh.connect(value["host"]["host"], username=value["host"]["user"], password=value["host"]["password"])

        print Style.DIM + value["host"]["host"] + Style.RESET_ALL + " - " + Fore.LIGHTGREEN_EX + key + Style.RESET_ALL

        scp = SCPClient(ssh.get_transport())

        ssh.exec_command("mkdir -p Public/classes/"+value["class"]["path"]+"/")

        for file_up in [f for f in os.listdir("javas/"+value["class"]["path"])
                        if os.path.isfile(os.path.join("javas/"+value["class"]["path"], f))]:
            print Fore.LIGHTGREEN_EX + os.path.join("javas/"+value["class"]["path"], file_up) + Style.RESET_ALL
            scp.put(files=os.path.join("javas/"+value["class"]["path"], file_up),
                    remote_path="Public/classes/"+file_up,
                    recursive=True)

        print Fore.LIGHTGREEN_EX + "Public/classes/java.tar.gz" + Style.RESET_ALL
        scp.put(files="java.tar.gz",
                remote_path="Public/classes/",
                recursive=True)

        print Fore.LIGHTGREEN_EX + "cd Public/classes; tar -zxvf java.tar.gz -C .; mv java/* .; " \
                                   "rm -rf java; rm java.tar.gz" + Style.RESET_ALL
        ssh.exec_command("cd Public/classes; tar -zxvf java.tar.gz -C .; mv java/* .; rm -rf java; rm java.tar.gz")

        print Fore.LIGHTGREEN_EX + "Public/classes/config.ini" + Style.RESET_ALL
        scp.put(files="configs/config_up.ini", remote_path="Public/classes/config.ini")

        print Fore.LIGHTGREEN_EX + "Public/classes/config.bash" + Style.RESET_ALL
        scp.put(files="configs/config.bash", remote_path="Public/classes/")

        if value["class"]["class"] == "Registry":
            print Fore.LIGHTGREEN_EX + "Public/classes/set_rmiregistry.sh" + Style.RESET_ALL
            print Fore.LIGHTGREEN_EX + "Public/classes/set_rmiregistry_alt.sh" + Style.RESET_ALL
            scp.put(files=["set_rmiregistry.sh", "set_rmiregistry_alt.sh"],
                    remote_path="Public/classes/")

        ssh.close()
        scp.close()

    print Style.DIM + Fore.BLUE + "Executing . . . in the workstation" + Style.RESET_ALL

    for key, value in lst.iteritems():
        print Style.DIM + value["host"]["host"] + Style.RESET_ALL + " - " + Fore.LIGHTGREEN_EX + key + Style.RESET_ALL

        ssh.connect(value["host"]["host"],
                    username=value["host"]["user"],
                    password=value["host"]["password"])

        print Fore.LIGHTGREEN_EX + "find . -name '*.sh' | xargs chmod u+x" + Style.RESET_ALL
        ssh.exec_command("find . -name '*.sh' | xargs chmod u+x")

        for cmd in value["class"]["command"]:
            print Fore.LIGHTGREEN_EX + cmd + Style.RESET_ALL
            ssh.exec_command(cmd)
            print Fore.LIGHTGREEN_EX + 'time.sleep(value["class"]["sleep"])' + Style.RESET_ALL
            time.sleep(value["class"]["sleep"])

    print Fore.GREEN + Style.DIM + "DONE!" + Style.RESET_ALL


def get_log():
    lst = parse_config()

    if not os.path.exists("logs"):
        os.makedirs("logs")
    try:
        ssh.connect(lst["Log"]["host"]["host"], username=lst["Log"]["host"]["user"], password=lst["Log"]["host"]["password"])
        ssh.exec_command("echo \"Hello!\"")
    except Exception:
        print "Unable to connect to the host: %s" % lst["Log"]["host"]
        return

    sftp = ssh.open_sftp()
    dir = sftp.listdir("Public/classes")

    for f in dir:
        if str(f).endswith(".log"):
            log_file = str(f)
            print Fore.GREEN + Style.DIM + log_file + Style.RESET_ALL
            sftp.get("Public/classes/" + log_file, "logs/" + log_file)
            call(["open", "logs/" + log_file])


def kill_all():
    global ssh

    lst = parse_config()

    print Style.DIM + Fore.BLUE + "Cleaning the remote server" + Style.RESET_ALL

    for key, value in lst.iteritems():
        ssh.connect(value["host"]["host"], username=value["host"]["user"], password=value["host"]["password"])

        print Style.DIM + value["host"]["host"] + Style.RESET_ALL + " - " + Fore.LIGHTGREEN_EX + key + Style.RESET_ALL
        print Fore.GREEN + "rm -rf Public/*" + Style.RESET_ALL
        ssh.exec_command("rm -rf Public/*")

        print(Fore.GREEN + "ps aux | grep java | grep \"" + value["host"]["user"] +
              "\" | grep -v \"grep\" | awk '/ /{print $2}' | xargs kill -9" + Style.RESET_ALL)
        ssh.exec_command("ps aux | grep java | grep \"" + value["host"]["user"] +
                         "\" | grep -v \"grep\" | awk '/ /{print $2}' | xargs kill -9")
        ssh.close()


def show_logs(command="tail"):
    global ssh

    lst = parse_config()

    print Style.BRIGHT + "\nSHOW LOGS\n" + Style.RESET_ALL

    for key, value in lst.iteritems():
        try:
            ssh.connect(value["host"]["host"], username=value["host"]["user"], password=value["host"]["password"])

            if len(command) == 1:
                stdin, stdout, stderr = ssh.exec_command("find . -name 'output*' -exec " + command[0] + " {} \;")
            else:
                stdin, stdout, stderr = ssh.exec_command("find . -name 'output*' -exec " + command + " {} \;")
        except Exception:
            continue

        print Style.DIM + value["host"]["host"] + Style.RESET_ALL + " - " + Fore.LIGHTGREEN_EX + key + Style.RESET_ALL

        output = str(stdout.readlines())

        if "exception" in output.lower() or "not" in output.lower() or "no" in output.lower():
            print Fore.RED + output + Style.RESET_ALL
        else:
            print Fore.GREEN + output + Style.RESET_ALL


def command(command_to="tail"):
    if len(command_to) != 1:
        print "Please send the command that you want!"
        exit(1)

    global ssh

    for host in hosts:
        hostname = host["host"]
        response = os.system("ping -c 1 -W 1 " + hostname)

        if response != 0:
            hosts.remove(host)
            continue
        try:
            ssh.connect(host["host"], username=host["user"], password=host["password"])
            ssh.exec_command("echo \"Hello!\"")
        except Exception:
            hosts.remove(host)

    print "\nCOMMAND: " + command_to[0] + "\n"

    for host in hosts:
        try:
            ssh.connect(host["host"], username=host["user"], password=host["password"])
            ssh.exec_command("echo \"Hello!\"")
        except Exception:
            continue

        stdin, stdout, stderr = ssh.exec_command(command_to[0])

        print host["host"]
        print stdout.readlines()


def parse_config():
    settings = ConfigParser.ConfigParser()
    settings.read('configs/config.ini')

    lst = OrderedDict(sorted({
        "Coach": {
            "hostname": settings.get("mapping", "coach_host"),
            "order": 6
        },
        "Contestant": {
            "hostname": settings.get("mapping", "contestant_host"),
            "order": 7
        },
        "Referee": {
            "hostname": settings.get("mapping", "referee_host"),
            "order": 8
        },
        "Registry": {
            "hostname": settings.get("mapping", "registry_host"),
            "port": settings.get("mapping", "registry_port"),
            "order": 1
        },
        "Log": {
            "hostname": settings.get("mapping", "log_host"),
            "port": settings.get("mapping", "log_port"),
            "order": 2
        },
        "Bench": {
            "hostname": settings.get("mapping", "bench_host"),
            "port": settings.get("mapping", "bench_port"),
            "order": 3
        },
        "Playground": {
            "hostname": settings.get("mapping", "playground_host"),
            "port": settings.get("mapping", "playground_port"),
            "order": 4
        },
        "RefereeSite": {
            "hostname": settings.get("mapping", "refereesite_host"),
            "port": settings.get("mapping", "refereesite_port"),
            "order": 5
        }
    }.items(), key=lambda x: x[1]["order"]))

    with open('configs/mapping.json') as json_data:
        jars = json.load(json_data)
        json_data.close()

    with open('configs/hosts.json') as json_data:
        hosts = json.load(json_data)
        json_data.close()

    for key, value in lst.iteritems():
        for host in hosts:
            if value["hostname"] == host["host"]:
                value["host"] = host
                break

        for jar in jars:
            if key == jar["class"]:
                value["class"] = jar
                break

    return lst


def is_up_host(ip_address):
    with open(os.devnull, 'w') as DEVNULL:
        try:
            subprocess.check_call(

                ['ping', '-c', '1', '-W', '1', ip_address],
                stdout=DEVNULL,  # suppress output
                stderr=DEVNULL
            )
            is_up = True
        except subprocess.CalledProcessError:
            is_up = False

    return is_up


if __name__ == '__main__':
    functions = {'generate_config': generate_config,
                 'upload': upload,
                 'killall': kill_all,
                 'show_logs': show_logs,
                 'get_log': get_log,
                 'command': command}

    if len(sys.argv) <= 1:
        print('Available functions are:\n' + repr(functions.keys()))
        exit(1)

    ssh = paramiko.SSHClient()
    ssh.load_system_host_keys()
    ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())

    if sys.argv[1] in functions.keys():
        if len(sys.argv[2:]) == 0:
            functions[sys.argv[1]]()
        else:
            functions[sys.argv[1]](sys.argv[2:])
