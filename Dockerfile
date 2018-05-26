FROM ubuntu:14.04

MAINTAINER jyunchyou “jyunchyou@gmail.com”
RUN apt-get update
RUN touch /root/local/bin/start-agent.sh
RUN touch /root/local/bin/docker-entrypoint.sh
ENTRYPOINT ["docker-entrypoint.sh"]
