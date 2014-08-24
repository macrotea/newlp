Newlp Server
==============


技术组合
----------
* 协议：HTTP/WS
* 技术：JAVA/JSON/RESTFUL
* 基础框架：Spring MVC/Spring Security/Spring Data/Aspect
* 日志管理：log4j/logback
* 包依赖管理：Maven
* 代码版本控制：Git


目录结构
-------

    ├── src
    │   └── main
    │       ├── java
    │       │   └── com.lesso.newlp
    │       │       ├── api             restful api
    │       │       ├── auth            身份认证模块
    │       │       ├── config          spring配置模块
    │       │       ├── core            公共模块
    │       │       ├── credit          信用管理模块
    │       │       ├── home            用户主页模块
    │       │       ├── invoice         单据模块模块
    │       │       ├── log             操作日志模块
    │       │       ├── material        物料主数据模块
    │       │       └── pm              用户管理模块
    │       ├── resources               各种配置文件
    │       └── webapp                  页面文件
    ├── pom.xml                         Maven配置文件
    └── README.md                       说明文档
