# Builder container
FROM registry.cn-hangzhou.aliyuncs.com/aliware2018/services AS builder
# Runner container
FROM registry.cn-hangzhou.aliyuncs.com/aliware2018/debian-jdk8
FROM registry.cn-hangzhou.aliyuncs.com/aliware2018/agent-demo AS agent

COPY --from=builder /usr/local/bin/docker-entrypoint.sh /usr/local/bin
COPY --from=agent /usr/local/bin/start-agent.sh /usr/local/bin

RUN mkdir /root/dists
EXPOSE 8087

ENTRYPOINT ["docker-entrypoint.sh"]

