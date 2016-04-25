import os
import sys
import json
from subprocess import call
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
        "path": "build/classes/bench/BenchRun",
        "type": "server"
    },
    {
        "class": "Coach",
        "path": "build/classes/entities/CoachRun",
        "type": "client"
    },
    {
        "class": "Contestant",
        "path": "build/classes/entities/ContestantRun",
        "type": "client"
    },
    {
        "class": "Referee",
        "path": "build/classes/entities/RefereeRun",
        "type": "client"
    },
    {
        "class": "Log",
        "path": "build/classes/general_info_repo/LogRun",
        "type": "server"
    },
    {
        "class": "Playground",
        "path": "build/classes/playground/PlaygroundRun",
        "type": "server"
    },
    {
        "class": "RefereeSite",
        "path": "build/classes/referee_site/RefereeSiteRun",
        "type": "server"
    },
    {
        "class": "NodeSetts",
        "path": "build/classes/settings/NodeSettsRun",
        "type": "server"
    },
]


def send_jar(host, jar):
    print "Jar: " + jar["class"]
    print "Sending the proper jar to the workstation"

    ssh = paramiko.SSHClient()
    ssh.load_system_host_keys()
    ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    ssh.connect(host["host"], username=host["user"], password=host["password"])
    ssh.exec_command("rm -rf *")
    sftp = ssh.open_sftp()
    sftp.put(os.getcwd() + "/jars/" + jar["class"] + ".jar", jar["class"] + ".jar")

    return [{
        "class": jar,
        "host": host
    }]


def upload():
    print "Creating directory jars if it doesn't exist"

    if not os.path.exists("jars"):
        os.makedirs("jars")

    print "Generating all different jars (Total: %d)" % (len(jars))

    for jar in jars:
        call(["jar", "cfv", jar["class"] + ".jar", jar["path"] + ".class"])
        os.rename(jar["class"] + ".jar", "jars/" + jar["class"] + ".jar")

    print "See what hosts are up to calculate the architecture of the solution"

    for host in hosts:
        hostname = host["host"]
        response = os.system("ping -c 1 -W 1 " + hostname)

        if response != 0:
            hosts.remove(host)
            continue
        try:
            ssh = paramiko.SSHClient()
            ssh.load_system_host_keys()
            ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
            ssh.connect(host["host"], username=host["user"], password=host["password"])
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

    print "Upload hosts.json to the proper machine"
    

    """
    print "Executing each jar file on the proper workstation"

    for jars_host in jars_hosts:
        ssh = paramiko.SSHClient()
        ssh.load_system_host_keys()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        ssh.connect(jars_host["host"]["host"], username=jars_host["host"]["user"],
                    password=jars_host["host"]["password"])
        print "java -jar " + jars_host["class"]["class"] + ".jar"
        ssh.exec_command("java -jar " + jars_host["class"]["class"] + ".jar")

    # http://stackoverflow.com/questions/28485647/wait-until-task-is-completed-on-remote-machine-through-python

    print "Simulation ended, copying log file to the local machine"

    if not os.path.exists("logs"):
        os.makedirs("logs")
    """


def killall():
    for host in hosts:
        hostname = host["host"]
        response = os.system("ping -c 1 -W 1 " + hostname)

        if response != 0:
            hosts.remove(host)
            continue
        try:
            ssh = paramiko.SSHClient()
            ssh.load_system_host_keys()
            ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
            ssh.connect(host["host"], username=host["user"], password=host["password"])
        except Exception:
            hosts.remove(host)

    for host in hosts:
        ssh = paramiko.SSHClient()
        ssh.load_system_host_keys()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        ssh.connect(host["host"], username=host["user"], password=host["password"])
        ssh.exec_command("killall java")
        print host["host"] + ": killall java"


if __name__ == '__main__':
    functions = {'upload': upload, 'killall': killall}

    if len(sys.argv) <= 1:
        print('Available functions are:\n' + repr(functions.keys()))
        exit(1)

    if sys.argv[1] in functions.keys():
        functions[sys.argv[1]]()
