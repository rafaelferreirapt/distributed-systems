#!/usr/bin/env python

import os
import sys
import json
import paramiko
import ConfigParser
from subprocess import call
from scp import SCPClient


def send_jar(host, jar):
    """
    print "Jar: " + jar["class"]
    print "Sending the proper jar to the workstation"

    ssh.connect(host["host"], username=host["user"], password=host["password"])
    sftp = ssh.open_sftp()
    ssh.exec_command("rm -rf *")

    while u'libs\n' not in ssh.exec_command("ls")[1].readlines():
        ssh.exec_command("mkdir libs")

    ssh.exec_command("killall java")
    sftp.put(os.getcwd() + "/dist/SDtrabalho3.jar", "SDtrabalho3.jar")
    sftp.put(os.getcwd() + "/libs/json-simple-1.1.jar", "libs/json-simple-1.1.jar")
    sftp.put(os.getcwd() + "/libs/org.json-20120521.jar", "libs/org.json-20120521.jar")

    ssh.exec_command("chmod -R 600 *")
    """


def generate_config():
    global ssh

    print "See what hosts are up to calculate the architecture of the solution"

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

    jar_i = 0

    jars_hosts = []

    if len(hosts) == 0:
        print "There are no machines active!"
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

    print "Save the hosts in a config file"

    config = ConfigParser.RawConfigParser()
    config.add_section("mapping")

    for jars_host in jars_hosts:
        config.set("mapping", jars_host["class"]["class"] + "_HOST", jars_host["host"]["host"])
        if jars_host["class"]["type"] != "client":
            config.set("mapping", jars_host["class"]["class"] + "_PORT", jars_host["class"]["port"])

    config.set("mapping", "RegistryObject", 22125)

    with open('configs/config.ini', 'wb') as configfile:
        config.write(configfile)


def upload(wait=None):
    call(["sh", "build.sh"])

    settings = ConfigParser.ConfigParser()
    settings.read('configs/config.ini')

    lst = {
        "Coach": {
            "hostname": settings.get("mapping", "coach_host"),
        },
        "Contestant": {
            "hostname": settings.get("mapping", "contestant_host"),
        },
        "Referee": {
            "hostname": settings.get("mapping", "referee_host"),
        },
        "Registry": {
            "hostname": settings.get("mapping", "registry_host"),
            "port": settings.get("mapping", "registry_port"),
        },
        "Log": {
            "hostname": settings.get("mapping", "log_host"),
            "port": settings.get("mapping", "log_port"),
        },
        "Bench": {
            "hostname": settings.get("mapping", "bench_host"),
            "port": settings.get("mapping", "bench_port"),
        },
        "Playground": {
            "hostname": settings.get("mapping", "playground_host"),
            "port": settings.get("mapping", "playground_port"),
        },
        "RefereeSite": {
            "hostname": settings.get("mapping", "refereesite_host"),
            "port": settings.get("mapping", "refereesite_port"),
        }
    }

    for key, value in lst.iteritems():
        for host in hosts:
            if value["hostname"] == host["host"]:
                value["host"] = host
                break

        for jar in jars:
            if key == jar["class"]:
                value["class"] = jar
                break

    # clean
    print "Cleaning the remote server"

    for key, value in lst.iteritems():
        ssh.connect(value["host"]["host"], username=value["host"]["user"], password=value["host"]["password"])

        print value["host"]["host"]
        print("rm -rf Public/*")
        ssh.exec_command("rm -rf Public/*")

        print("killall java")
        ssh.exec_command("killall java")
        ssh.close()

    # Upload folders on the remote server
    print "Upload folders on the remote server"

    for key, value in lst.iteritems():
        ssh.connect(value["host"]["host"], username=value["host"]["user"], password=value["host"]["password"])

        print value["host"]["host"]

        scp = SCPClient(ssh.get_transport())
        scp.put(files="javas/"+value["class"]["path"],
                remote_path="Public/classes/"+value["class"]["path"],
                recursive=True)

        ssh.close()
        scp.close()

    print "ok"
    """
    for json_host in jars_hosts:

        sftp = ssh.open_sftp()
        sftp.put(os.getcwd() + "/hosts.json", "hosts.json")

    jars_hosts = sorted(jars_hosts, key=lambda jar_host: jar_host["class"]["order"])

    print "Executing each jar file on the proper workstation"

    log_connection = None

    for jars_host in jars_hosts:
        print "[%s]" % (jars_host["host"]["host"])

        try:
            ssh.connect(jars_host["host"]["host"], username=jars_host["host"]["user"],
                        password=jars_host["host"]["password"])
            ssh.exec_command("echo \"Hello!\"")
        except Exception:
            continue

        print jars_host["class"]["command"] % (
            jars_host["class"]["package"] + "." + jars_host["class"]["class"] + "Run")
        stdin, stdout, stderr = ssh.exec_command(
            jars_host["class"]["command"] % (jars_host["class"]["package"] + "." + jars_host["class"]["class"] + "Run"))

        if jars_host["class"]["class"] == "Log":
            log_connection = stdout.channel

    if len(wait) == 1 and wait[0] == "not":
        print "Bye! Don't forget to call 'python machines.py show_logs'\n" + \
              " and then: 'python machines.py get_log " + log_host["host"]["host"] + "'"
        exit(1)

    print "Waiting for the simulation end!"

    if log_connection.recv_exit_status() == 0:
        print "Simulation ended, copying log file to the local machine"

        get_log(log_host["host"]["host"])
    else:
        print "UPS! Something went wrong..."
    """


def get_log(log_host_hostname):
    if len(log_host_hostname) == 1:
        log_host_hostname = log_host_hostname[0]

    for log_host in hosts:
        if log_host["host"] == log_host_hostname:
            break

    if not os.path.exists("logs"):
        os.makedirs("logs")
    try:
        ssh.connect(log_host["host"], username=log_host["user"], password=log_host["password"])
        ssh.exec_command("echo \"Hello!\"")
    except Exception:
        print "Unable to connect to the host: %s" % log_host["host"]
        return

    sftp = ssh.open_sftp()
    dir = sftp.listdir(".")

    for f in dir:
        if str(f).endswith(".log"):
            log_file = str(f)
            sftp.get(log_file, "logs/" + log_file)


def kill_all():
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

    for host in hosts:
        try:
            ssh.connect(host["host"], username=host["user"], password=host["password"])
            ssh.exec_command("echo \"Hello!\"")
        except Exception:
            continue

        print host["host"]

        print("rm -rf Public/*")
        ssh.exec_command("rm -rf Public/*")

        print("killall java")
        ssh.exec_command("killall java")


def show_logs(command="tail"):
    global ssh

    for host in hosts:
        hostname = host["host"]
        response = os.system("ping -c 1 -W 1 " + hostname)

        if response != 0:
            hosts.remove(host)
            continue
        try:
            ssh.connect(host["host"], username=host["user"], password=host["password"])
        except Exception:
            hosts.remove(host)

    print "\nSHOW LOGS\n"

    for host in hosts:
        try:
            ssh.connect(host["host"], username=host["user"], password=host["password"])
            ssh.exec_command("echo \"Hello!\"")
        except Exception:
            continue

        if len(command) == 1:
            stdin, stdout, stderr = ssh.exec_command(command[0] + " output")
        else:
            stdin, stdout, stderr = ssh.exec_command(command + " output")

        print host["host"]
        print stdout.readlines()


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

    with open('configs/hosts.json') as json_data:
        hosts = json.load(json_data)
        json_data.close()

    with open('configs/mapping.json') as json_data:
        jars = json.load(json_data)
        json_data.close()

    ssh = paramiko.SSHClient()
    ssh.load_system_host_keys()
    ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())

    if sys.argv[1] in functions.keys():
        if len(sys.argv[2:]) == 0:
            functions[sys.argv[1]]()
        else:
            functions[sys.argv[1]](sys.argv[2:])
