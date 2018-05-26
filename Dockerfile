FROM ubuntu:14.04

MAINTAINER jyunchyou “jyunchyou@gmail.com”
RUN apt-get update
RUN touch /usr/local/bin/start-agent.sh
RUN touch /usr/local/bin/docker-entrypoint.sh
ENTRYPOINT ["docker-entrypoint.sh"]
