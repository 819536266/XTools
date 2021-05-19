# XHttp

## XHttp能做什么？
IDEA自动检测Spring注解，生成测试简易PostMan，快速测试接口；快捷关闭端口，避免端口被占用；选择代码可编辑为MarkDown代码片段，快速生成MarkDown文档；代码转化工具

## 参考项目

[RestfulTool](https://gitee.com/zys981029/RestfulTool)

[Knife4j（一款非常好用的Swagger文档）](https://doc.xiaominfo.com/)

[XTools插件地址](https://plugins.jetbrains.com/plugin/14400-xtools)

## 使用环境
IntelliJ IDEA版（182+)

## 功能说明

1.  自动检测Spring注解接口
2.  支持@PostMapping,@GetMapping,@DeleteMapping,@PutMapping,@PatchMapping,@RequestMapping
3.  点击生成测试接口PostMan
4.  请求头可以动态添加和删除
5.  自定义结构生成自动过滤参数
6.  自动JSON格式化栏，响应栏，URL栏，标题栏
7.  窗口切换按钮,用于竖状或横状窗口
8.  端口关闭工具
9.  MD文档生成工具
10. yaml与propertis代码互转
11. 字段转大小写、驼峰、下滑线、加减引号等

## 安装说明

1. IDEA插件商店搜索XHttp(改名为XTools)，安装重启即可
2. 下载源码中XHttp.zip，IDEA导入插件安装包重启

## 使用说明

在拥有注解的方法左侧点击线性标签，选择对应请求发送方式即可。注：（首次打开IDEA需要先打开XHttp窗口）。

可在IDEA中设置默认前缀以及自动过滤的参数类型。file -> settings -> Other Settings -> XHttp。

## 使用截图
![代码转化工具](https://images.gitee.com/uploads/images/2021/0519/153426_055bb29c_4832857.png "屏幕截图.png")

![yaml-TO-pro](https://images.gitee.com/uploads/images/2021/0519/154312_d18af8c0_4832857.png "屏幕截图.png")
![方法截图](https://images.gitee.com/uploads/images/2020/0810/160353_c3de3bc7_4832857.jpeg "115232_04a6b145_4832857.jpeg")

![请求截图](https://images.gitee.com/uploads/images/2020/0810/161153_6a489557_4832857.jpeg "1597047068(1).jpg")
![端口关闭](https://images.gitee.com/uploads/images/2020/0810/161216_56328488_4832857.jpeg "1597046970(1).jpg")
![MD文档生成](https://images.gitee.com/uploads/images/2020/0810/161235_9c9cc1b0_4832857.jpeg "1597047034(1).jpg")
