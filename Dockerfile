FROM java:8-alpine
MAINTAINER stephenAlan@163.com

# Install base packages
RUN apk update && apk add curl bash tree tzdata \
    && cp -r -f /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone \
    && echo -ne "Alpine Linux 3.4 image. (`uname -rsv`)\n" >> /root/.built

RUN apk add --update coreutils && rm -rf /var/cache/apk/*

ARG JAR_FILE
COPY target/${JAR_FILE} /usr/local/app/app.jar
COPY docker-entrypoint.sh /usr/local/app/
RUN chmod a+x /usr/local/app/docker-entrypoint.sh
WORKDIR /usr/local/app
EXPOSE 8080

ENTRYPOINT ["./docker-entrypoint.sh"]