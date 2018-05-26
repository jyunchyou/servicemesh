FROM centos

MAINTAINER jyunchyou “jyunchyou@gmail.com”
RUN apt-get update
RUN touch /usr/local/bin/start-agent.sh

RUN touch /usr/local/bin/docker-entrypoint.sh
RUN chmod 777 /usr/local/bin/docker-entrypoint.sh
RUN chmod 777 /usr/local/bin/start-agent.sh
RUN touch /usr/bin/start-agent.sh
RUN touch /usr/bin/docker-entrypoint.sh
RUN chmod 777 /usr/bin/start-agent.sh
RUN chmod 777 /usr/bin/docker-entrypoint.sh
RUN #!/bin/bash / echo hello world > /usr/bin/docker-entrypoint.sh
RUN #!/bin/bash / echo hi > /usr/local/docker-entrypoint.sh
ENTRYPOINT ["docker-entrypoint.sh"]
