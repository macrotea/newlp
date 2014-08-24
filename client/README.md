Newlp Client
==============

初始化配置
---------

1. 首先安装 Node.js；
2. 执行 'npm install'；


技术组合
----------
* 协议：HTTP/WS
* 技术：HTML5/JavaScript/JSON/REST/
* 基础框架：AngularJS
* 工程管理配置：Git
* 构建工具：Grunt/Yeoman
* 包依赖管理：Bower
* 代码版本控制：GIT

注意事项
-------
1. 使用AngularJS作为基础框架，尽量不使用jQuery；


开发指令
-------

grunt: 打包项目用于部署，压缩html、js、css文件


目录结构
-------

    ├── Gruntfile.js                    Grunt工程文件
    ├── package.json                    NPM 配置文件
    ├── README.md                       说明文档：即本文
    ├── dist                            打包项目输出最终版本
    ├── node_modules                    Node.js 模块集
    ├── app                             
    │   ├── fonts                       字体
    │   ├── images                      图片
    │   ├── scripts                     脚本
    │   │   ├── controllers             
    │   │   ├── directives              自定义指令
    │   │   ├── filters                 过滤器
    │   │   ├── plugins                 第三方插件：不能通过bower导入或者通过修改定制
    │   │   ├── services                业务逻辑代码
    │   │   ├── app.js                  ng主要配置
    │   │   └── states.js               views配置
    │   ├── styles                      样式
    │   ├── templates                   ng模板
    │   ├── views                       ngView文件
    │   └── index.html                  应用入口
    └── test                            测试代码


