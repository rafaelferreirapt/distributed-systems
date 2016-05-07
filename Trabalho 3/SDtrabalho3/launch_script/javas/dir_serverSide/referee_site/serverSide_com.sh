#!/usr/bin/env bash
java -Djava.rmi.server.codebase="http://$registry_host/$group/classes/"\
     -Djava.rmi.server.useCodebaseOnly=true\
     -Djava.security.policy=java.policy\
     referee_site.RefereeSiteServer