# Builder container
FROM registry.cn-hangzhou.aliyuncs.com/aliware2018/services AS builder

# Runner container
FROM registry.cn-hangzhou.aliyuncs.com/aliware2018/debian-jdk8


COPY --from=builder /usr/local/bin/docker-entrypoint.sh /usr/local/bin


EXPOSE 8087

ENTRYPOINT ["docker-entrypoint.sh"]

