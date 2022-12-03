# XTools

## XTools能做什么？

IDEA自动检测Spring注解，生成测试简易PostMan，快速测试接口；快捷关闭端口，避免端口被占用；选择代码可编辑为MarkDown代码片段，快速生成MarkDown文档；代码转化工具；代码预览工具；

## 参考项目

[RestfulTool](https://gitee.com/zys981029/RestfulTool)

[Knife4j（一款非常好用的Swagger文档）](https://doc.xiaominfo.com/)

[XTools插件地址](https://plugins.jetbrains.com/plugin/14400-xtools)

[ConvertYamlAndProperties](https://github.com/chencn/ConvertYamlAndProperties)

[intellij-generateAllSetMethod](https://github.com/gejun123456/intellij-generateAllSetMethod)

## 使用环境

IntelliJ IDEA版（182+)

## 功能说明

1. 自动检测Spring注解接口
2. 支持@PostMapping,@GetMapping,@DeleteMapping,@PutMapping,@PatchMapping,@RequestMapping
3. 点击生成测试接口PostMan
4. 请求头可以动态添加和删除
5. 自定义结构生成自动过滤参数
6. 自动JSON格式化栏，响应栏，URL栏，标题栏
7. 窗口切换按钮,用于竖状或横状窗口
8. 端口关闭工具
9. Markdown文档生成工具
10. yaml与properties代码互转
11. 字段转大小写、驼峰、下滑线、加减引号等
12. 生成对象无默认值的setter方法、有默认值的setter方法、构造器的setter方法、链式调用的setter方法
13. 选择Bean生成Bean的JSON字符串
14. 预览实体类，字段等

## 安装说明

1. IDEA插件商店搜索改名为XTools(原插件名为XHttp)，安装重启即可
2. XTools-version.zip，IDEA导入插件安装包重启

## XHttp使用说明

```  
推荐使用Restful Fast Request，对比XHttp功能更完善，但Restful Fast Request使用收费，如果简单测试也可使用XHttp
```

在拥有注解的方法左侧点击线性标签，选择对应请求发送方式即可。

可在IDEA中设置默认前缀以及自动过滤的参数类型。file -> settings -> Other Settings -> XTools。

## XTools工具使用说明

除XHttp工具和下方按钮 + 快捷键功能外，还提供以下功能

1. 生成对象无默认值的setter方法、有默认值的setter方法、构造器的setter方法、链式调用的setter方法（如实体添加@Accessors、@Builder等注解，点击对象触发提示快捷键Alt+Enter，在候选中生成即可）
2. yaml与properties代码互转（选中文件右键点击转化或XTools窗口P2Y转化）
3. 端口关闭工具

### 快捷键

| 功能              | win快捷键           | 右键点击                              |
|-----------------|------------------|-----------------------------------|
| 预览              | Alt + P          | XTools -> 预览                      |
| 实体类生成JSON字符串    | Alt + I          | XTools -> Bean TO Json            |
| yaml转properties | Alt + Y          | XTools -> Y-TO-P                  |
| properties转yaml | Alt + Shift + Y  | XTools -> P-TO-Y                  |
| 字符串大小写互转        | Alt + U          | XTools -> Upper OR Lower Case     |
| 首字母大小写互转        | Alt + Shift + U  | XTools -> Upper OR Lower First    |
| 单引号转双引号,双引号转单引号 | Alt + 引号         | XTools -> Quotes Cast             |
| 去双引号,加双引号       | Alt + Shift + 引号 | XTools -> Quotes Change           |
| 驼峰命名转下划线并大写     | Alt + Shift + K  | XTools -> Underline And UpperCase |
| 驼峰命名转下划线        | Alt + K          | XTools -> Underline Case          |
| 下划线命名转驼峰        | 无                | XTools -> Camel Case              |
| 创建Markdown笔记    | Alt+M            | XTools -> Create Markdown         |

### 使用截图

- RequestMapping
  ![RequestMapping](https://images.gitee.com/uploads/images/2021/0524/162324_fe6774c8_4832857.gif "requestmapping.gif")
- GetMapping
  ![GetMapping](https://images.gitee.com/uploads/images/2021/0524/162515_aeadf176_4832857.gif "getmapping.gif")
- GetMappingJson
  ![GetMappingJson](https://images.gitee.com/uploads/images/2021/0524/162526_e6c1b49c_4832857.gif "getmappingjson.gif")
- PostMapping
  ![PostMapping](https://images.gitee.com/uploads/images/2021/0524/162542_1197f1ae_4832857.gif "postmapping.gif")
- PostMappingBody
  ![PostMappingBody](https://images.gitee.com/uploads/images/2021/0524/162552_197413a3_4832857.gif "postmappingbody.gif")
- 创建Markdown文档
  ![创建Markdown文档](https://images.gitee.com/uploads/images/2021/0524/162608_b3171a38_4832857.gif "md.gif")
- yaml与properties互转
  ![yaml与properties互转](https://images.gitee.com/uploads/images/2021/0524/162623_1a19cb3c_4832857.gif "p2y.gif")
- 关闭端口
  ![关闭端口](https://images.gitee.com/uploads/images/2021/0524/162635_2c27e136_4832857.gif "port.gif")
