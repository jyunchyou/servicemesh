from ubuntu 14.04

RUN update apt

RUN mkdir /root/local/bin/docker-entrypoint.sh
RUN mkdir /root/local/bin/start-agent.sh
ENTRYPOINT ["docker-entrypoint.sh"]
