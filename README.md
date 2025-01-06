# **Ricky OJ Backend**

## Quick Start

```shell
# 启动mongo
mongod --replSet rs0 --logpath "F:\application\develop\MongoDB\mongodb\log\mongod.log" --dbpath "F:\application\develop\MongoDB\data\db" --port 27017

# 启动redis
cd F:\application\develop\redis\Redis-x64-5.0.14.1 & f: & redis-server.exe redis.windows.conf
```

```shell
# 启动nacos
systemctl restart NetworkManager docker
docker restart mysql
docker restart nacos
```

`nacos`客户端：

* http://192.168.137.128:8848/nacos
* `nacos`用户名：`nacos`
* `nacos`密码：`nacos`

## Nacos Dynamic Router

```json
[
  {
    "id": "user-server",
    "predicates": [{
        "name": "Path",
        "args": {
          "_genkey_0": "/user/**",
          "_genkey_1": "/registration/**",
          "_genkey_2": "/verification-code/**",
          "_genkey_3": "/login",
          "_genkey_4": "/verification-code-login",
          "_genkey_5": "/logout",
          "_genkey_6": "/refresh-token"
        }
    }],
    "filters": [],
    "uri": "lb://user-server",
    "order": 1
  },
  {
    "id": "judge-server",
    "predicates": [{
        "name": "Path",
        "args": {
          "_genkey_0": "/judge/**"
        }
    }],
    "filters": [],
    "uri": "lb://judge-server",
    "order": 1
  },
  {
    "id": "problem-server",
    "predicates": [{
        "name": "Path",
        "args": {
          "_genkey_0": "/problem/**",
          "_genkey_1": "/tag/**"
        }
    }],
    "filters": [],
    "uri": "lb://problem-server",
    "order": 1
  }
]
```

