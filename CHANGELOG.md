
# 🚀Changelog

-------------------------------------------------------------------------------------------------------------

# 2.1.1

### 🐣新特性
* 右键选项改为中文。
* 右键工具增加预览。
* 项目构建方式改为Gradle
### 🐞Bug修复
* 修复一些bug）。
* 
-------------------------------------------------------------------------------------------------------------

# 2.1.0

### 🐣新特性
* 增加生成对象无默认值的setter方法、有默认值的setter方法、构造器的setter方法、链式调用的setter方法。
* 增加右键工具生成Bean的JSON字符串。
* 增加窗口工具JSON格式化工具栏
* 增加请求缓存。缓存内容：请求地址、请求方式、参数名称、参数值、请求头。
* 增加请求方式下拉，可选请求方式。
### 🐞Bug修复
* 修复@RequestBody时，存在地址栏参数未拼接问题；（issue#I42VLN@Gitee）。
* 修复点击请求打开窗口非XHttp问题。

-------------------------------------------------------------------------------------------------------------

# 2.0.0

### 🐣新特性
* 修改工具窗口为XHttp、XTools；删除请求窗口；完善P2Y窗口 YAML与Properties 互转功能。
* 右键增加XTool功能:Y-TO-P - YAML转Properties。
* 右键增加XTool功能:P-TO-Y - Properties转YAML。
* 右键合并原UpperCase、LowerCase功能，现为：Upper OR Lower Case - 字符串转大小写。
* 右键合并原UpperFirst、LowerFirst功能，现为：Upper OR Lower First - 首字母转大小写。
* 添加以及修改右键功能中快捷键。
### 🐞Bug修复
* 修复首次需初始化工具窗口问题。

-------------------------------------------------------------------------------------------------------------

# 1.5.0

### 🐣新特性
* 修复请求URL因为注解未定义参数引起的错误
* 去除Spring依赖
* 增加开源YML与Properties互转工具--地址:<a href="https://github.com/chencn/ConvertYamlAndProperties">https://github.com/chencn/ConvertYamlAndProperties</a>
* 添加右键功能集合-XTools
* 转移创建Markdown文档功能到XTools
* 增加XTool功能:UnderlineAndUpperCase - 驼峰式命名的字符串转换为下划线方式并转大写
* 增加XTool功能:UnderlineCase - 驼峰式命名的字符串转换为下划线方式
* 增加XTool功能:CamelCase - 下划线方式命名的字符串转换为驼峰式
* 增加XTool功能:UpperCase - 字符串转大写
* 增加XTool功能:LowerCase - 字符串转小写
* 增加XTool功能:SwapCase - 换给定字符串中的大小写。大写转小写，小写转大写
* 增加XTool功能:UpperFirst - 大写首字母
* 增加XTool功能:LowerFirst - 小写首字母
* 增加XTool功能:QuotesCast - 单引号转双引号,双引号转单引号
* 增加XTool功能:QuotesChange - 去双引号 加 双引号

-------------------------------------------------------------------------------------------------------------
