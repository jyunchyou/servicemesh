FROM ubuntu:14.04

MAINTAINER jyunchyou “jyunchyou@gmail.com”
RUN apt-get update
RUN mkdir /root/local/bin/start-agent.sh
ENTRYPOINT ["docker-entrypoint.sh"]
