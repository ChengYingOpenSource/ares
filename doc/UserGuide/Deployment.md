#部署文档

## 创建数据库 
### 2.1 创建mysql数据库和用户


### 2.2 创建表信息，在工程中的sql-script文件夹中是数据库创建脚本
            

## 打包部署应用 
### 2.3 生成应用的jar包
         通过maven命令打包成ares.jar ，并上传到应用服务器
mvn clean package

           
### 2.4 修改配置信息application.properties 
application.properties放在和ares.jar同级目录下

spring.application.name=ares
server.port=30115

spring.datasource.url=jdbc:mysql://192.168.201.46:3306/ares?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false
spring.datasource.username=cy_ares
spring.datasource.password=qXkxWtKSCrUM2CWI

management.port=30114
cluster.config.clusterInnerInfo.seedNodes=30.14.200.61:25510
cluster.config.clusterInnerInfo.clusterName=ares2-system-opensource
spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
spring.jackson.time-zone=GMT+8
spring.main.allow-bean-definition-overriding=true
ares.ctx=ares
ares.cluster.ctx=cluster/api


配置说明： 
(1).数据库信息
(2).配置集群节点信息cluster.config.clusterInnerInfo.seedNodes,如果配置多个节点用逗号分隔，集群基地几点。配置的集群节点不能同时死亡，否则客户端连接可能会出现异常
(3).应用端口号server.port，默认端口号为30115


### 2.5启动

启动命令： 

nohup java -Xms256m -Xmx512m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/cy/logs/ares/ares_heapdump.hprop -Xloggc:/cy/logs/ares/gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Dlog_path=/cy/logs/ares/ -Dspring.config.location=/cy/server/application.properties -jar /cy/server/ares.jar > /cy/logs/ares/ares_console.log 2>&1 &


-Dlog_path  日志目录

/cy/server/ares.jar 为应用jar的路径

/cy/logs/ares/ares_console.log 为控制台输出的日志文件



### 2.6 检查启动情况
如果打印出如下日志，恭喜你应用成功启动啦!



### 2.7 一键部署脚本

进入192.168.4.1

执行/cy/server/下的release.sh脚本

root@/cy/server$ cd /cy/server/
root@/cy/server$ ls -lrt
total 101196
-rw-r--r--  1 admin admin 103498473 Oct 13 22:49 ares.jar
-rw-r--r--  1 root  root        602 Oct 16 15:03 application.properties
-rwxr-xr-x  1 root  root        386 Oct 16 15:23 start.sh
-rw-r--r--  1 root  root       1173 Oct 16 15:24 release.sh
drwxr-xr-x 15 root  root       4096 Oct 16 15:25 ares
root@/cy/server$ sh release.sh develop_merging
注意：192.168.4.1  是部署Master，脚本执行后，会同时将192.168.4.251 和 192.168.4.252进行jar更新&java进程重启
同时，start.sh脚本和application.properties文件也是通过192.168.4.1 同步过去的，如果对start.sh或application.properties进行编辑时，务必更新192.168.4.1:/cy/server/下的种子文件！！
