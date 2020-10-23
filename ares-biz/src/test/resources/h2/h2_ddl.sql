-- 动态配置表结构设计
CREATE DATABASE ares;

USE `ares`;

-- 配置表（核心）
-- (app_name, profile, group, data_id) 唯一，程序中保证其唯一性
CREATE TABLE `ares_config`(
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `app_name` VARCHAR(128) NOT NULL COMMENT '配置所属应用名',
  `profile` VARCHAR(24) NOT NULL COMMENT '环境',
  `group` VARCHAR(24) NOT NULL DEFAULT 'default' COMMENT '分组',
  `data_id` VARCHAR(128) NOT NULL COMMENT '配置的id',
  `content` TEXT COMMENT '配置的内容',
  `content_type` VARCHAR(8) NOT NULL DEFAULT 'text' COMMENT '配置内容的格式。text-(文本), json, yaml, properties, xml',
  `digest` TEXT COMMENT '配置内容的摘要，判断配置是否变更的依据之一',
  `cur_version` INT NOT NULL DEFAULT 0 COMMENT '配置的版本，每次修改都自增1',
  `version_change_type` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '版本变化的类型。0-NewAdd(新增)，1-Update(修改)，2-Rollback(回滚，回滚时，version也会自增1)',
  `rollback_from_version` INT COMMENT '若 version_change_type = 2, 则此需要记录，由哪个版本回滚而来',
  `create_user` BIGINT DEFAULT NULL COMMENT '创建人',
  `gmt_create` DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
  `modify_user` BIGINT DEFAULT NULL COMMENT '修改人',
  `gmt_modified` DATETIME NOT NULL DEFAULT now() COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 配置历史表
-- （config_id, version）是唯一键
CREATE TABLE `ares_config_history`(
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `config_id` BIGINT NOT NULL COMMENT '配置id',
  `app_name` VARCHAR(128) NOT NULL COMMENT '配置所属应用名',
  `profile` VARCHAR(24) NOT NULL COMMENT '环境',
  `group` VARCHAR(24) NOT NULL DEFAULT 'default' COMMENT '分组',
  `data_id` VARCHAR(128) NOT NULL COMMENT '配置的id',
  `content` TEXT COMMENT '配置的内容',
  `content_type` VARCHAR(8) NOT NULL COMMENT '配置内容的格式。text-(文本), json, yaml, properties, xml',
  `digest` TEXT COMMENT '配置内容的摘要，判断配置是否变更的依据之一',
  `version` INT NOT NULL COMMENT '配置的版本',
  `version_change_type` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '版本变化的类型。0-NewAdd(新增)，1-Update(修改)，2-Rollback(回滚，回滚时，version也会自增1)',
  `rollback_from_version` INT COMMENT '若 version_change_type = 2, 则此需要记录，由哪个版本回滚而来',
  `record_create_user` BIGINT COMMENT '原纪录的创建人',
  `record_gmt_create` DATETIME COMMENT '原记录的创建时间',
  `record_modify_user` BIGINT COMMENT '原纪录的最后一次修改人',
  `record_gmt_modified` DATETIME COMMENT '原纪录的修改时间',
  `create_user` BIGINT COMMENT '此历史记录的创建人',
  `gmt_create` DATETIME NOT NULL DEFAULT now() COMMENT '历史记录创建时间',
  `gmt_modified` DATETIME NOT NULL DEFAULT now() COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `CONFIG_HISTORY_UK` (`config_id`, `version`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 客户端信息表
-- 客户端唯一性标识：(app, client_ip)
CREATE TABLE `ares_client`(
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `app` VARCHAR(128) NOT NULL COMMENT '客户端所属的应用',
  `client_ip` VARCHAR(16) NOT NULL COMMENT '客户端ip',
  `client_hostname` VARCHAR(64) COMMENT '客户端的hostname',
  `client_request_ip` VARCHAR(16) COMMENT '从request请求中获取的ip',
  `client_request_port` VARCHAR(8) COMMENT '从request请求中获取的port',
  `monitor_config_num` INT NOT NULL COMMENT '客户端监听的配置的个数',
  `polling_server_addr` VARCHAR(20) COMMENT '本次轮询的server地址',
  `polling_time` DATETIME NOT NULL DEFAULT now() COMMENT '本次轮询的时间',
  `last_polling_server_addr` VARCHAR(20) COMMENT '上一次轮询的server地址',
  `last_polling_time` DATETIME COMMENT '上一次轮询的时间',
  `long_polling_count` BIGINT NOT NULL DEFAULT 0 COMMENT '客户端自启动开始发起的长轮询次数',
  `client_status` TINYINT NOT NULL DEFAULT 1 COMMENT '客户端状态。1->正常，0->异常挂起（超过一定的时间没有接收到Polling Request，则挂起），
        -1->正常下线（客户端在关闭JVM进程时，向Server发出一条下线消息），-2->异常下线(挂起超过一定的时间，则下线), -3->强制下线(人为强制下线)',
  `gmt_create` DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
  `gmt_modified` DATETIME NOT NULL DEFAULT now() COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 客户端监听的配置表 （客户端：配置 ==> 一对多）
-- 唯一键：(client_id, app_name, profile, group, data_id) 由程序保证
CREATE TABLE `ares_client_monitor_config`(
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `client_id` BIGINT NOT NULL COMMENT '客户端id',
  `app_name` VARCHAR(128) NOT NULL COMMENT '监听的配置所属的应用名',
  `profile` VARCHAR(24) NOT NULL COMMENT '监听的配置所属的环境',
  `group` VARCHAR(24) NOT NULL COMMENT '监听的配置所属的group',
  `data_id` VARCHAR(128) NOT NULL COMMENT '监听的配置dataId',
  `config_id` BIGINT COMMENT '配置的id（根据app_name, profile, group, data_id查询得出，也可能为NULL，为了保证config_id的正确性，启动一个Task定时的check&update）',
  `client_config_version` INT COMMENT '客户端该配置的版本（初次请求监听，客户端无该配置，为NULL）',
  `client_config_digest` TEXT COMMENT '客户端该配置的摘要',
  `last_update_config_time` DATETIME COMMENT '客户端上一次更新配置的时间',
  `gmt_create` DATETIME NOT NULL DEFAULT now() COMMENT '历史记录创建时间',
  `gmt_modified` DATETIME NOT NULL DEFAULT now() COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 客户端表更日志表
CREATE TABLE `ares_client_change_log`(
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `client_id` BIGINT NOT NULL COMMENT '客户端id',
  `app` VARCHAR(128) NOT NULL COMMENT '客户端所属的应用',
  `client_ip` VARCHAR(16) NOT NULL COMMENT '客户端ip',
  `client_hostname` VARCHAR(64) COMMENT '客户端的hostname',
  `client_request_ip` VARCHAR(16) COMMENT '从request请求中获取的ip',
  `client_request_port` VARCHAR(8) COMMENT '从request请求中获取的port',
  `change_type` TINYINT(4) NOT NULL COMMENT 'change类型，目前有两种变化记录到此表中。a. client_status变化; b. client监听的配置发生变化。
          二进制中，用最低位标识status变化，为1表示，status有变化，为0则无；用倒数第二位表示监听的配置的变化，1表示有，0则无。因此，
          01-状态有变化，监听的配置无变化，10-状态无变化，监听的配置有变化，11-状态、监听的配置都有变化',
  `client_status` TINYINT NOT NULL DEFAULT 1 COMMENT '客户端状态。1->正常，0->异常挂起（超过一定的时间没有接收到Polling Request，则挂起），
        -1->正常下线（客户端在关闭JVM进程时，向Server发出一条下线消息），-2->异常下线(挂起超过一定的时间，则下线), -3->强制下线(人为强制下线)',
  `monitor_config_num` INT NOT NULL COMMENT '客户端监听的配置的个数',
  `monitor_configs` TEXT NOT NULL COMMENT '监听的配置',
  `create_user` BIGINT COMMENT '创建人',
  `gmt_create` DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
  `gmt_modified` DATETIME NOT NULL DEFAULT now() COMMENT '最后一次修改时间',
   PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;


-- 发布信息表
CREATE TABLE `ares_config_publish_record`(
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `config_id` BIGINT NOT NULL COMMENT '发布的配置Id',
  `version` INT NOT NULL COMMENT '发布的配置版本',
  `is_ready` TINYINT(2) NOT NULL DEFAULT 0 COMMENT '是否准备好发布。0-未准备好，1-准备好',
  `pub_status` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '发布状态。0->发布中，1->发布成功，-1->发布失败, -2->部分发布失败',
  `client_count` INT COMMENT '监听此配置的客户端个数',
  `create_user` BIGINT COMMENT '创建人',
  `gmt_create` DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
  `modify_user` BIGINT COMMENT '修改人（修改由程序自动完成，不会有认为修改，此字段是为了保持表结构一致）',
  `gmt_modified` DATETIME NOT NULL DEFAULT now() COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 配置发布，客户端状态表 (发布记录与当时的client映射表，一对多)
CREATE TABLE `ares_pub_client_state`(
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `pub_id` BIGINT NOT NULL COMMENT '配置发布id',
  `client_id` BIGINT NOT NULL COMMENT '客户端id',
  `monitor_id` BIGINT NOT NULL COMMENT '配置监听记录id',
  `app_name` VARCHAR(128) NOT NULL COMMENT '配置所属的应用名',
  `profile` VARCHAR(24) NOT NULL COMMENT '配置所属的环境',
  `group` VARCHAR(24) NOT NULL COMMENT '配置所属的group',
  `data_id` VARCHAR(128) NOT NULL COMMENT '配置的dataId',
  `config_id` BIGINT COMMENT '配置的id',
  `client_config_version` INT COMMENT '发布前客户端的配置版本',
  `pub_config_version` INT NOT NULL COMMENT '发布的配置版本(冗余)',
  `pub_status` VARCHAR(10) NOT NULL DEFAULT 'WAITING' COMMENT '发布状态。WAITING-生效中，SUCCESS-成功，FAILURE-失败',
  `gmt_create` DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
  `gmt_modified` DATETIME NOT NULL DEFAULT now() COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `PUB_CLIENT_STATE_UK`(`pub_id`, `monitor_id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 配置强制推送记录表
CREATE TABLE `ares_force_push_record`(
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `client_id` BIGINT NOT NULL COMMENT '客户端id',
  `monitor_id` BIGINT NOT NULL COMMENT '配置监听记录id',
  `app_name` VARCHAR(128) NOT NULL COMMENT '配置所属的应用名',
  `profile` VARCHAR(24) NOT NULL COMMENT '配置所属的环境',
  `group` VARCHAR(24) NOT NULL COMMENT '配置所属的group',
  `data_id` VARCHAR(128) NOT NULL COMMENT '配置的dataId',
  `config_id` BIGINT COMMENT '配置的id',
  `client_config_version` INT COMMENT '推送前客户端的配置版本',
  `push_config_version` INT COMMENT '推送的配置版本',
  `push_status` VARCHAR(10) NOT NULL DEFAULT 'WAITING' COMMENT '推送状态，WAITING-生效中，SUCCESS-成功，FAILURE-失败',
  `create_user` BIGINT COMMENT '创建人',
  `gmt_create` DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
  `modify_user` BIGINT COMMENT '修改人',
  `gmt_modified` DATETIME NOT NULL DEFAULT now() COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 锁表，
CREATE TABLE `ares_lock`(
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `biz_type` VARCHAR(64) NOT NULL COMMENT '业务类型',
  `biz_id` BIGINT NOT NULL COMMENT '业务id',
  `effective_time` BIGINT NOT NULL COMMENT '生效时间戳（unix-timestamp）,精确到ms',
  `expire_time` BIGINT DEFAULT NULL COMMENT '有效时间，时长。单位：ms。为NULL，则表示不会过期，一直有效',
  `create_user` BIGINT COMMENT '创建人',
  `gmt_create` DATETIME NOT NULL DEFAULT now() COMMENT '发布时间',
  `modify_user` BIGINT COMMENT '修改人',
  `gmt_modified` DATETIME NOT NULL DEFAULT now() COMMENT '最后一次修改时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `LOCK_UK`(`biz_type`, `biz_id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 删除记录表
CREATE TABLE `ares_trash_record`(
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `biz_type` VARCHAR(64) NOT NULL COMMENT '业务名称（表名/类型）',
  `biz_id` BIGINT NOT NULL COMMENT '业务主键（表主键）',
  `snapshot` TEXT NOT NULL COMMENT '镜像（JSON格式）',
  `create_user` BIGINT COMMENT '操作人',
  `gmt_create` DATETIME NOT NULL DEFAULT now() COMMENT '删除时间',
  `gmt_modified` DATETIME NOT NULL DEFAULT now() COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;



