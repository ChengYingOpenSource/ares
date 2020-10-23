CREATE TABLE `ares2_app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `namespace_code` varchar(32) DEFAULT NULL,
  `app_name` varchar(64) NOT NULL COMMENT '应用名',
  `app_code` varchar(32) NOT NULL COMMENT '应用Code',
  `type` varchar(32) NOT NULL COMMENT '应用类型',
  `desc` varchar(128) DEFAULT NULL COMMENT '描述',
  `create_user` bigint(20) DEFAULT '-1' COMMENT '创建人',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_user` bigint(20) DEFAULT '-1' COMMENT '修改人',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `create_user_account` varchar(64) DEFAULT NULL,
  `modify_user_account` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_ares2_app1` (`app_code`,`namespace_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=299 DEFAULT CHARSET=utf8mb4 COMMENT='应用表';

CREATE TABLE `ares2_app_env` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `namespace_code` varchar(32) DEFAULT NULL,
  `app_code` varchar(32) NOT NULL COMMENT '应用Code',
  `env_code` varchar(32) NOT NULL COMMENT '环境Code',
  `create_user` bigint(20) DEFAULT '-1' COMMENT '创建人',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_user` bigint(20) DEFAULT '-1' COMMENT '修改人',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `create_user_account` varchar(64) DEFAULT NULL,
  `modify_user_account` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_ares2_env1` (`namespace_code`,`env_code`,`app_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=511 DEFAULT CHARSET=utf8mb4 COMMENT='环境';

CREATE TABLE `ares2_cluster` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `namespace_code` varchar(32) DEFAULT NULL,
  `app_code` varchar(32) NOT NULL COMMENT '应用Code',
  `env_code` varchar(32) NOT NULL COMMENT '环境Code',
  `cluster_code` varchar(32) NOT NULL COMMENT '集群Code',
  `create_user` bigint(20) DEFAULT '-1' COMMENT '创建人',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_user` bigint(20) DEFAULT '-1' COMMENT '修改人',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `create_user_account` varchar(64) DEFAULT NULL,
  `modify_user_account` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_ares2_cluster1` (`namespace_code`,`app_code`,`env_code`,`cluster_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=517 DEFAULT CHARSET=utf8mb4 COMMENT='应用集群';

CREATE TABLE `ares2_conf` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `namespace_code` varchar(20) DEFAULT 'public',
  `app_code` varchar(64) NOT NULL COMMENT '应用Code',
  `env_code` varchar(20) NOT NULL COMMENT '环境Code',
  `cluster_code` varchar(20) NOT NULL DEFAULT 'default' COMMENT '集群Code',
  `group` varchar(24) NOT NULL DEFAULT 'default' COMMENT '分组',
  `data_id` varchar(128) NOT NULL,
  `content` text COMMENT '配置的内容',
  `content_size` int(11) DEFAULT NULL,
  `content_type` varchar(20) NOT NULL DEFAULT 'text' COMMENT '配置内容的格式。text-(文本), json, yaml, properties, xml',
  `compress` tinyint(4) DEFAULT '0' COMMENT '0不压缩 1压缩 (TODO 没用,只在内存中压缩即可)',
  `digest` text COMMENT '配置原始内容的摘要，判断配置是否变更的依据之一',
  `encrypt` tinyint(4) DEFAULT '0' COMMENT '是否加密, 1加密 0 不加密',
  `desc` varchar(64) DEFAULT NULL COMMENT '描述',
  `trace_id` varchar(36) DEFAULT NULL,
  `rollback_from_huid` varchar(36) DEFAULT NULL,
  `create_user` bigint(20) DEFAULT '-1' COMMENT '创建人',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_user` bigint(20) DEFAULT '-1' COMMENT '修改人',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `create_user_account` varchar(64) DEFAULT NULL,
  `modify_user_account` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_ares2_conf1` (`namespace_code`,`app_code`,`env_code`,`cluster_code`,`group`,`data_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=386021 DEFAULT CHARSET=utf8mb4 COMMENT='配置表';

CREATE TABLE `ares2_conf_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `group_uid` varchar(36) DEFAULT NULL,
  `namespace_code` varchar(32) DEFAULT NULL,
  `app_code` varchar(32) NOT NULL COMMENT '应用Code',
  `env_code` varchar(32) NOT NULL COMMENT '环境Code',
  `cluster_code` varchar(32) NOT NULL COMMENT '集群Code',
  `group` varchar(24) NOT NULL DEFAULT 'default' COMMENT '分组, default是private trait',
  `trait` varchar(32) DEFAULT 'private' COMMENT '公共组(public)、关联组(link-public)、私有组(private)',
  `link_group_uid` varchar(36) DEFAULT NULL COMMENT 'trait=关联的时候使用, Note不能递归关联',
  `create_user` bigint(20) DEFAULT '-1' COMMENT '创建人',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_user` bigint(20) DEFAULT '-1' COMMENT '修改人',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `create_user_account` varchar(64) DEFAULT NULL,
  `modify_user_account` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_ares2_group2` (`group_uid`) USING BTREE,
  UNIQUE KEY `index_ares2_group1` (`namespace_code`,`app_code`,`env_code`,`cluster_code`,`group`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=571 DEFAULT CHARSET=utf8mb4 COMMENT='应用配置组';

CREATE TABLE `ares2_conf_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `conf_history_uid` varchar(36) DEFAULT NULL COMMENT '配置历史记录uid',
  `operation_type` varchar(32) NOT NULL COMMENT '操作类型, add delete modify rollback',
  `namespace_code` varchar(32) DEFAULT NULL,
  `app_code` varchar(64) NOT NULL COMMENT '应用Code',
  `env_code` varchar(32) NOT NULL COMMENT '环境Code',
  `cluster_code` varchar(32) NOT NULL COMMENT '集群Code',
  `group` varchar(32) NOT NULL DEFAULT 'default' COMMENT '分组',
  `data_id` varchar(256) NOT NULL,
  `content` text COMMENT '配置的内容',
  `content_size` int(11) DEFAULT NULL,
  `content_type` varchar(20) NOT NULL DEFAULT 'text' COMMENT '配置内容的格式。text-(文本), json, yaml, properties, xml',
  `compress` tinyint(4) DEFAULT NULL COMMENT '0不压缩 1压缩',
  `digest` text COMMENT '配置内容的摘要，判断配置是否变更的依据之一',
  `encrypt` tinyint(4) DEFAULT '0' COMMENT '是否加密, 1加密 0 不加密',
  `desc` varchar(64) DEFAULT NULL COMMENT '描述',
  `rollback_from_huid` varchar(36) DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `create_user_account` varchar(64) DEFAULT NULL,
  `modify_user_account` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=389953 DEFAULT CHARSET=utf8mb4 COMMENT='配置变更history表';

CREATE TABLE `ares2_conf_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `trace_id` varchar(36) NOT NULL COMMENT '配置历史记录uid',
  `instance_id` varchar(128) DEFAULT NULL,
  `ip_addr` varchar(32) DEFAULT NULL,
  `hostname` varchar(128) DEFAULT NULL,
  `app_code` varchar(32) DEFAULT NULL COMMENT '环境Code',
  `data_id` varchar(128) NOT NULL,
  `push_status` varchar(32) DEFAULT NULL COMMENT 'push状态',
  `push_info` text COMMENT '推送详情(失败原因 or 成功)',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `create_user_account` varchar(64) DEFAULT NULL,
  `modify_user_account` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ares2_conf_log_i1` (`trace_id`,`app_code`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=4516009 DEFAULT CHARSET=utf8mb4 COMMENT='配置push日志表';

CREATE TABLE `ares2_env` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `namespace_code` varchar(32) DEFAULT NULL,
  `env_name` varchar(64) NOT NULL COMMENT '环境名称',
  `env_code` varchar(32) NOT NULL COMMENT '环境Code',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '环境类型, 0 dev 1 test 2 pre 3 prod',
  `desc` varchar(128) DEFAULT NULL COMMENT '描述',
  `create_user` bigint(20) DEFAULT '-1' COMMENT '创建人',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_user` bigint(20) DEFAULT '-1' COMMENT '修改人',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `create_user_account` varchar(64) DEFAULT NULL,
  `modify_user_account` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_ares2_env1` (`namespace_code`,`env_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4 COMMENT='环境表';

CREATE TABLE `ares2_namespace` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id，主键',
  `namespace_name` varchar(64) NOT NULL COMMENT '空间名称',
  `namespace_code` varchar(32) NOT NULL COMMENT '空间Code',
  `desc` varchar(128) DEFAULT NULL COMMENT '描述',
  `create_user` bigint(20) DEFAULT '-1' COMMENT '创建人',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_user` bigint(20) DEFAULT '-1' COMMENT '修改人',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `create_user_account` varchar(64) DEFAULT NULL,
  `modify_user_account` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_ares2_namespace1` (`namespace_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COMMENT='空间表';

CREATE TABLE `ares2_mock_http` (
  `id` bigint(21) NOT NULL AUTO_INCREMENT,
  `app_code` varchar(32) DEFAULT NULL COMMENT '默认值为 default',
  `method` varchar(32) DEFAULT NULL,
  `content_type` varchar(32) DEFAULT NULL,
  `url` varchar(128) DEFAULT NULL,
  `script` text,
  `type` varchar(32) DEFAULT NULL COMMENT 'text json groovy , 默认是json',
  `dataset` text COMMENT '数据集，用于脚本执行时候的输入',
  `remark` varchar(128) DEFAULT NULL,
  `create_user` bigint(21) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `modify_user` bigint(21) DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mock_index001` (`app_code`,`method`,`content_type`,`url`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
