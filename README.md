# morning

## 项目简介
本项目为一个简单的流程引擎，并没有严格按照BPMN协议规范实现流程。
该项目主要目的：

1·消磨业余时间；

2·使用DDD思想进行一次实践；

3·保持日常技术锻炼。



## 技术方案

以DDD为指导思想尝试建模并纵向划分领域，做为拆分微服务基础。目前分为两个服务——配置系统和引擎系统。

配置系统：元数据管理，负责定义流程/单据；

引擎系统：负责实例化流程和单据流转。

两个系统均为Spring-boot项目，并使用Spring-Cloud做为微服务解决方案。

数据库使用MongoDB和MySQL，其中MongoDB主要负责存储元数据以及系统运行时产生的各种事件流程/表单定义/事件均以JSON文档形式存放在MongoDB）；
Mysql负责存储流程运行时的实例话数据

### 尚未添加

缓存将采用Redis，用于缓存元数据；

微服务之间如果发生异步通信，将采用RabbitMQ做为底层基础消息设施；

本项目目前持久化尚未使用ORM框架，暂以Spring-data和JdbcTemplate为持久化手段。

### 架构一览

![Image text](https://github.com/androip/morning/blob/master/framework.png)
