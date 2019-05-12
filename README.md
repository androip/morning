# morning

## 技术方案

以DDD为指导思想尝试建模并纵向划分领域，做为拆分为服务基础。目前分为两个服务——配置系统和引擎系统
配置系统系元数据管理系统，负责应以流程/单据。
引擎系统负责实例化流程和单据流转
两个系统均为Spring-boot项目，并使用Spring-Cloud做为微服务解决方案。
数据库使用MongoDB和MySQL，其中MongoDB主要负责存储元数据以及系统运行时产生的各种事件流程/表单定义/事件均以JSON文档形式存放在MongoDB）；
Mysql负责存储流程运行时的实例话数据

### 尚未添加

缓存将采用Redis，用于缓存元数据
微服务之间如果发生异步通信，将采用RabbitMQ做为底层基础消息设施
本项目目前持久化尚未使用ORM框架，暂以Spring-data和JdbcTemplate为持久化手段。

### 架构一览

![Image text](https://github.com/androip/morning/blob/master/framework.png)
