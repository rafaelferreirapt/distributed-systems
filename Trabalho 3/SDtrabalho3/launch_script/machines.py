#!/usr/bin/env python

import os
import sys
import json
import paramiko

hosts = [
    {
        "host": "l040101-ws01.ua.pt",
        "user": "sd0102",
        "password": "8OP22hFN2K570806411tzL388090k437u68N290d61y7100sxSl68w69M779HSI2oW7D6415g4X2c510u984CO299i5T05xiTU7q87c08XbMx8604Mp16T0S"
    },
    {
        "host": "l040101-ws02.ua.pt",
        "user": "sd0102",
        "password": "KuwAU1oEfbeyAaKxOFeEyTPSdQUihxxmJbDOSzeBoHbxzUppSdVitEPqAaRlhpNgHvlffOAHTSkCjOftLlUsjl8tiDgaDgqHOTVypxBlNOrnocdUaNgUaLbp"
    },
    {
        "host": "l040101-ws03.ua.pt",
        "user": "sd0102",
        "password": "462o9vX0x18O7CW871s727967pmL58510s26I779Do6gr2o31423020UAV3H9F1iz7r5C196Z0NhYcw3Vws1w5r36yY773W54936L804Fa941yZ1493bc71W"
    },
    {
        "host": "l040101-ws04.ua.pt",
        "user": "sd0102",
        "password": "c6wSS1b97Y0287PA9N53S08069AdIn9387aHC515OcFP39xN315DlH9k3Fr798378Nq2o5KodxkG16C15Qd2f8484Mrh2M2xC439fU24c856RA5p995Iz3RJ"
    },
    {
        "host": "l040101-ws05.ua.pt",
        "user": "sd0102",
        "password": "8g0cRpI3J5UjEc18CzKtKwdLKy428aQrNh146tJufdK7vaUGL8697h2116z9V1zYiYcemuxqUpsk4innr8lhq1ytsWu92TJO7Q03djRGDir8A7p5e0CiD7tA"
    },
    {
        "host": "l040101-ws06.ua.pt",
        "user": "sd0102",
        "password": "8W0dUd0I16E18115HS0260QWoK1743X3736V60zS12H01Qb9XW515qG27a9A16w9elVs1676l36N6g17055954gv8O5l9Y2y25kU0F9Bq8327Mw43f25v7Df"
    },
    {
        "host": "l040101-ws07.ua.pt",
        "user": "sd0102",
        "password": "atRyWsqE1Qu1t902ioAhgPnmb9x84VFO6dTje6DZ20Nr81WMh5m3GyB85oh0Es94YwMO1R3zH7No8fJp2x2z5n3gOoDPhFPlWi4R9yY7S126FpF41Z35t0Pv"
    },
    {
        "host": "l040101-ws09.ua.pt",
        "user": "sd0102",
        "password": "28wT04L4FjV04kuR25x3qi8D1o1KY975A1R3y5l24xi76x86652Y39rO6T5yU5Fu82u30389008a5N3igip24p75847RqD486mj03854Vuh503135B81kMey"
    },
    {
        "host": "l040101-ws10.ua.pt",
        "user": "sd0102",
        "password": "R91qV402bu1F1M6YMBaMQ4VABAvLO0MRPL09oSUcp1IcUiuXtV30se80A1YJGy5yKhE56p23793t9031467W8lZvEi4n7atB7cCQ435cOmz204DE430102kJ"
    }
]

jars = [
    {
        "class": "Bench",
        "package": "bench",
        "type": "server",
        "order": 5,
        "command": "java -cp 'SDtrabalho3.jar:libs/*' %s &> output"
    },
    {
        "class": "Coach",
        "package": "entities",
        "type": "client",
        "order": 7,
        "command": "java -cp 'SDtrabalho3.jar:libs/*' %s &> output"
    },
    {
        "class": "Contestant",
        "package": "entities",
        "type": "client",
        "order": 6,
        "command": "java -cp 'SDtrabalho3.jar:libs/*' %s &> output"
    },
    {
        "class": "Referee",
        "package": "entities",
        "type": "client",
        "order": 8,
        "command": "java -cp 'SDtrabalho3.jar:libs/*' %s &> output"
    },
    {
        "class": "Log",
        "package": "general_info_repo",
        "type": "server",
        "order": 2,
        "command": "java -cp 'SDtrabalho3.jar:libs/*' %s &> output"
    },
    {
        "class": "Playground",
        "package": "playground",
        "type": "server",
        "order": 4,
        "command": "java -cp 'SDtrabalho3.jar:libs/*' %s &> output"
    },
    {
        "class": "RefereeSite",
        "package": "referee_site",
        "type": "server",
        "order": 3,
        "command": "java -cp 'SDtrabalho3.jar:libs/*' %s &> output"
    },
    {
        "class": "NodeSetts",
        "package": "settings",
        "type": "server",
        "order": 1,
        "command": "java -cp 'SDtrabalho3.jar:libs/*' %s hosts.json &> output"
    },
]

ssh = paramiko.SSHClient()
ssh.load_system_host_keys()
ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())


def send_jar(host, jar):
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

    return [{
        "class": jar,
        "host": host
    }]


def upload(wait):
    global ssh

    print "Creating directory jars if it doesn't exist"

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

    print "and sending them to the workstations"

    jar_i = 0

    jars_hosts = []

    if len(hosts) == 0:
        print "There are no machines active!"
        exit(1)
    elif len(jars) <= len(hosts):
        for host in hosts:
            jars_hosts += send_jar(host, jars[jar_i])

            jar_i += 1
            if jar_i == len(jars):
                break
    else:
        for host in hosts:
            jars_hosts += send_jar(host, jars[jar_i])

            jar_i += 1
            if jar_i == len(jars):
                break

        for i in range(len(hosts) - 1, len(jars)):
            jars_hosts += send_jar(hosts[len(hosts) - 1], jars[i])

    print "Save the hosts in a JSON file to send it to the NodeSettsServer"

    to_file = []

    for host in jars_hosts:
        to_file += [{
            host["class"]["class"]: host["host"]["host"]
        }]

    with open('hosts.json', 'w') as outfile:
        json.dump(to_file, outfile)

    print "Upload hosts.json to all machines"

    for log_host in jars_hosts:
        if log_host["class"]["class"] == "Log":
            break

    for json_host in jars_hosts:
        try:
            ssh.connect(json_host["host"]["host"], username=json_host["host"]["user"],
                        password=json_host["host"]["password"])
            ssh.exec_command("echo \"Hello!\"")
        except Exception:
            continue

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
        ssh.exec_command("rm -rf *")
        ssh.exec_command("killall java")
        print host["host"] + ": killall java"


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
            stdin, stdout, stderr = ssh.exec_command(command[0]+" output")
        else:
            stdin, stdout, stderr = ssh.exec_command(command+" output")

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
    functions = {'upload': upload,
                 'killall': kill_all,
                 'show_logs': show_logs,
                 'get_log': get_log,
                 'command': command}

    if len(sys.argv) <= 1:
        print('Available functions are:\n' + repr(functions.keys()))
        exit(1)

    if sys.argv[1] in functions.keys():
        if len(sys.argv[2:]) == 0:
            functions[sys.argv[1]]()
        else:
            functions[sys.argv[1]](sys.argv[2:])
