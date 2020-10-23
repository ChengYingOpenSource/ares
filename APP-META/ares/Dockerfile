FROM 192.168.3.192/base/centos7-jdk8-base-zh_cn:1.1

ENV APP_NAME ares

COPY environment/* /cy/server/$APP_NAME/bin/


COPY $APP_NAME.jar /cy/server/$APP_NAME/lib/

RUN chmod +x /cy/server/$APP_NAME/bin/*.sh

RUN echo "Asia/Shanghai" > /etc/timezone


VOLUME /cy/logs/$APP_NAME/

WORKDIR /cy/server/$APP_NAME/

EXPOSE 30115

ENTRYPOINT ["/cy/server/ares/bin/start.sh"]