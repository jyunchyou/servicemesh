FROM ubuntu:14.04

MAINTAINER jyunchyou “jyunchyou@gmail.com”
RUN apt update
RUN apt install git 
RUN y
RUN cd /root
RUN git clone https://code.aliyun.com/middlewarerace2018/services.git
RUN git clone https://code.aliyun.com/middlewarerace2018/agent-demo.git
RUN cp /root/services/docker-entrypoint.sh /usr/bin
RUN cp /root/services/docker-entrypoint.sh /usr/local/bin
RUN cp /root/agent-demo/start-agent.sh /usr/bin
RUN cp /root/agent-demo/start-agent.sh /usr/local/bin
ENTRYPOINT ["docker-entrypoint.sh"]
