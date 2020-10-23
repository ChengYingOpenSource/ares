## 公共接口

### 统一参数:

参数名: _aresCrypt 

格式: 

_aresCrypt = "key;time"

key = {"user":"web","pwd":"admin123"} key需要被加密

time = 服务器时间的毫秒数



### 服务器时间获取



url:  /cluster/api/time/acquire

type: post / get

请求参数: 无

返回结果:

{
​    "total": 0,
​    "code": 0,
​    "msg": null,
​    "data": 1559054271778,
​    "params": null,
​    "success": true,
​    "time": 0
}





## 命名空间管理



### 空间列表

url:  /ares/namespace/list

type: post / get

请求参数:

返回结果:

{
​    "total": 0,
​    "code": 0,
​    "msg": null,
​    "data": [
​        {
​            "createUser": -1,
​            "gmtCreate": "2019-05-27 02:41:55",
​            "modifyUser": -1,
​            "gmtModified": "2019-05-27 02:41:55",
​            "id": 1,
​            "namespaceCode": "public",
​            "namespaceName": "共享空间",
​            "desc": null
​        }
​    ],
​    "params": null,
​    "success": true,
​    "time": 0
}



### 空间添加

url:  /ares/namespace/add

type: post  <=> application/x-www-form-urlencoded

请求参数:

  namespaceCode

  namespaceName

  desc

返回结果:

{
​    "total": 0,
​    "code": 0,
​    "msg": null,
​    "data": null,
​    "params": null,
​    "success": true,    // true 代表成功!
​    "time": 0
}



{
​    "code": -1,
​    "success": false,
​    "msg": "系统错误",
​    "data": null,
​    "pagination": null
}



### 空间删除

url:  /ares/namespace/del

type: post / get

请求参数:

  namespaceCode

返回结果:

{
​    "total": 0,
​    "code": 0,
​    "msg": null,
​    "data": null,
​    "params": null,
​    "success": true,
​    "time": 0
}



## 环境管理



### 环境列表

url:  /ares/env/list

type: post / get

请求参数:

​    namespaceCode

返回结果:

{
​    "total": 0,
​    "code": 0,
​    "msg": null,
​    "data": [
​        {
​            "createUser": 0,
​            "gmtCreate": "2019-05-28 14:56:30",
​            "modifyUser": 0,
​            "gmtModified": "2019-05-28 14:56:30",
​            "id": 1,
​            "namespaceCode": "public",
​            "envName": "开发001",
​            "envCode": "dev001",
​            "type": 0,
​            "desc": "xxxx"
​        }
​    ],
​    "params": null,
​    "success": true,
​    "time": 0
}



### 环境添加

url:  /ares/env/add

type: post 

请求参数:

  namespaceCode

  envName

  envCode

   type  (int类型) (0 =开发 1=测试 2=预发 3 =生产)

   desc

返回结果:

{
​    "total": 0,
​    "code": 0,
​    "msg": null,
​    "data": null,
​    "params": null,
​    "success": true,
​    "time": 0
}



### 环境删除

url:  /ares/env/del

type: post / get

请求参数:

  namespaceCode

  envCode

返回结果:

{
​    "total": 0,
​    "code": 0,
​    "msg": null,
​    "data": null,
​    "params": null,
​    "success": true,
​    "time": 0
}

## 应用管理



### 应用列表

url:  /ares/app/list

type: post 

请求参数:

​    namespaceCode

​    pageNum

​    pageSize

返回结果:

{
​    "total": 3,
​    "code": 0,
​    "msg": null,
​    "data": [
​        {
​            "createUser": -1,
​            "gmtCreate": "2019-05-20 07:34:58",
​            "modifyUser": -1,
​            "gmtModified": "2019-05-20 07:34:58",
​            "id": 1,
​            "namespaceCode": "public",
​            "appName": "appm01",
​            "appCode": "appm01",
​            "type": "backend",
​            "desc": "1111"
​        },
​        {
​            "createUser": -1,
​            "gmtCreate": "2019-05-20 07:35:33",
​            "modifyUser": 1,
​            "gmtModified": "2019-05-20 07:35:33",
​            "id": 2,
​            "namespaceCode": "public",
​            "appName": "appm02",
​            "appCode": "appm02",
​            "type": "backend",
​            "desc": "2222"
​        },
​        {
​            "createUser": 0,
​            "gmtCreate": "2019-05-28 15:10:11",
​            "modifyUser": 0,
​            "gmtModified": "2019-05-28 15:10:11",
​            "id": 5,
​            "namespaceCode": "public",
​            "appName": "应用test",
​            "appCode": "app001",
​            "type": "backend",
​            "desc": "xx"
​        }
​    ],
​    "params": null,
​    "success": true,
​    "time": 0
}



### 应用添加

url:  /ares/app/add

type: post / get

请求参数:

  namespaceCode

  appName

  appCode

   type  (string类型) (fronted=前端 backend=后端)

   desc

返回结果:

{
​    "total": 0,
​    "code": 0,
​    "msg": null,
​    "data": null,
​    "params": null,
​    "success": true,
​    "time": 0
}



### 应用删除

url:  /ares/app/del

type: post / get

请求参数:

  namespaceCode

  appCode

返回结果:

{
​    "total": 0,
​    "code": 0,
​    "msg": null,
​    "data": null,
​    "params": null,
​    "success": true,
​    "time": 0
}



### 应用绑定环境

url:  	/ares/app/bind/env

type: post 

请求参数:

  namespaceCode

  appCode

  envCode

   clusterCode   不传默认值是 default

返回结果:

{
​    "total": 0,
​    "code": 0,
​    "msg": null,
​    "data": null,
​    "params": null,
​    "success": true,
​    "time": 0
}



## 配置管理





### 配置运行环境信息

url:  /ares/conf/runEnv/info

type: post / get

请求参数:

  namespaceCode

  appCode

返回结果:

{
​    "total": 0,
​    "code": 0,
​    "msg": null,
​    "data": {
​        "namespaceCode": "public",
​        "appCode": "appm01",
​        "envList": [
​            {
​                "createUser": 0,
​                "gmtCreate": "2019-05-28 14:57:47",
​                "modifyUser": 0,
​                "gmtModified": "2019-05-28 14:57:47",
​                "id": 3,
​                "namespaceCode": "public",
​                "envName": "开发001",
​                "envCode": "dev001",
​                "type": 0,
​                "desc": "xxxx"
​            },
​            {
​                "createUser": -1,
​                "gmtCreate": "2019-05-28 15:30:15",
​                "modifyUser": -1,
​                "gmtModified": "2019-05-28 15:30:15",
​                "id": 7,
​                "namespaceCode": "public",
​                "envName": "默认开发",
​                "envCode": "dev",
​                "type": 0,
​                "desc": "dev"
​            }
​        ],
​        "envCluster": {
​            "dev": [
​                "default"
​            ],
​            "dev001": [
​                "default"
​            ]
​        }
​    },
​    "params": null,
​    "success": true,
​    "time": 0
}



### 配置项list信息

url:  /ares/conf/list/info

type: post 

请求参数:

  namespaceCode

  appCode

  envCode

  clusterCode

返回结果:

{
​    "total": 0,
​    "code": 0,
​    "msg": null,
​    "data": [
​        {
​            "createUser": -1,
​            "gmtCreate": "2019-05-21 06:33:26",
​            "modifyUser": -1,
​            "gmtModified": "2019-05-23 07:49:07",
​            "id": 4,
​            "namespaceCode": "public",
​            "appCode": "appm01",
​            "envCode": "dev",
​            "clusterCode": "default",
​            "group": "default",
​            "dataId": "thread.test001",
​            "content": null,
​            "contentSize": 300,
​            "contentType": "text",
​            "digest": "$1$NjhUuHz1$VqAIyRg4qm/ISZ71mGRf9.",
​            "compress": 0,
​            "encrypt": 0,
​            "desc": "xxxx",
​            "rollbackFromHuid": null
​        },
​        {
​            "createUser": -1,
​            "gmtCreate": "2019-05-21 05:41:02",
​            "modifyUser": -1,
​            "gmtModified": "2019-05-21 05:41:02",
​            "id": 3,
​            "namespaceCode": "public",
​            "appCode": "appm01",
​            "envCode": "dev",
​            "clusterCode": "default",
​            "group": "default",
​            "dataId": "cahce.name",
​            "content": null,
​            "contentSize": 2,
​            "contentType": "text",
​            "digest": null,
​            "compress": 0,
​            "encrypt": 0,
​            "desc": null,
​            "rollbackFromHuid": null
​        },
​        {
​            "createUser": 0,
​            "gmtCreate": "2019-05-20 07:39:32",
​            "modifyUser": 0,
​            "gmtModified": "2019-05-20 07:39:32",
​            "id": 2,
​            "namespaceCode": "public",
​            "appCode": "appm01",
​            "envCode": "dev",
​            "clusterCode": "default",
​            "group": "default",
​            "dataId": "http.timeout",
​            "content": null,
​            "contentSize": 1,
​            "contentType": "text",
​            "digest": null,
​            "compress": 0,
​            "encrypt": 0,
​            "desc": null,
​            "rollbackFromHuid": null
​        },
​        {
​            "createUser": 0,
​            "gmtCreate": "2019-05-20 07:37:57",
​            "modifyUser": 0,
​            "gmtModified": "2019-05-20 07:37:57",
​            **"id": 1,**
​            "namespaceCode": "public",
​            "appCode": "appm01",
​            "envCode": "dev",
​            "clusterCode": "default",
​            "group": "default",
​            **"dataId": "spring.app.name",**
**​            "content": null,**
**​            "contentSize": 2,**
**​            "contentType": "text",**
​            "digest": null,
​            "compress": 0,
​            "encrypt": 0,
​            **"desc": null,**
​            "rollbackFromHuid": null
​        }
​    ],
​    "params": null,
​    "success": true,
​    "time": 0
}



### 配置项详情信息

url:  /ares/conf/dataItem/info

type: post / get

请求参数:

  id

返回结果:

{
​    "total": 0,
​    "code": 0,
​    "msg": null,
​    "data": {
​        "createUser": -1,
​        "gmtCreate": "2019-05-21 06:33:26",
​        "modifyUser": -1,
​        "gmtModified": "2019-05-23 07:49:07",
​        "id": 4,
​        "namespaceCode": "public",
​        "appCode": "appm01",
​        "envCode": "dev",
​        "clusterCode": "default",
​        "group": "default",
​        "dataId": "thread.test001",
​        "content": "ttt",
​        "contentSize": 300,
​        "contentType": "text",
​        "digest": "$1$NjhUuHz1$VqAIyRg4qm/ISZ71mGRf9.",
​        "compress": 0,
​        "encrypt": 0,
​        "desc": "xxxx",
​        "rollbackFromHuid": null
​    },
​    "params": null,
​    "success": true,
​    "time": 0
}



### 配置项删除

url:  /ares/conf/dataItem/del

type:  get

请求参数:

  id

返回结果:

{
​    "total": 0,
​    "code": 0,
​    "msg": null,
​    "data": 1,
​    "params": null,
​    "success": true,
​    "time": 0
}



### 配置项添加

url:  /ares/conf/dataItem/add

type: post

请求参数:

 namespaceCode

  appCode

  envCode

  clusterCode

  group

  dataId  (即输入的key)

  content (即输入的value)

  contentType  （text  json xml ....)

返回结果:

{
​    "total": 0,
​    "code": 0,
​    "msg": null,
​    "data": true,
​    "params": null,
​    "success": true,
​    "time": 0
}



### 配置项更新

url:  /ares/conf/dataItem/update

type: post / get

请求参数:

 namespaceCode

  appCode

  envCode

  clusterCode

  group

  dataId  (即输入的key)

  **content (即输入的value)**

  **contentType  （text  json xml ....)**

返回结果:

{
​    "total": 0,
​    "code": 0,
​    "msg": null,
​    "data": 1,
​    "params": null,
​    "success": true,
​    "time": 0
}