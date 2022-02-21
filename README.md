# 微服务前后端分离在线考试平台
## 使用技术：SpringBoot，SpringCloud Alibaba，Mybatis-Plus，Redis，MySQL，EasyExcel，SpringSecurity+JWT+Token，Swagger，Vue，Element-Ui
## 项目快速启动
### 前端启动
1. 打开vue-admin文件夹
2. 执行npm install命令,执行没报错的话会生成node_modules文件夹

![image](https://user-images.githubusercontent.com/46312799/154923301-08537ab3-2a2b-492f-b70e-5c4ab1111c79.png)

3. 执行npm run dev命令,启动项目
4. 前后台启动步骤一致，前台是vue-front文件夹
### 后端启动
1. 修改service_acl项目下的application.yml文件(数据库连接，nacos注册中心地址,redis配置)
2. 修改service_core项目下的application.yml文件(redis配置，数据库连接，注册中心地址，腾讯云NLP自然语言处理秘钥 **重点如果没配置腾讯云的秘钥的话无法使用自动打分** )
3. 修改service_msm项目下的application.yml文件(redis配置，注册中心地址，腾讯云短信服务秘钥 **重点如果没配置腾讯云的秘钥的话无法使用短信服务** )
4. 修改service_oss项目下的application.yml文件(注册中心地址，阿里云oss服务秘钥 **重点如果没配置阿里云的秘钥的话无法使用oss服务** )
5. 修改infrastructure文件夹下的api_gateway项目下的application.yml文件(nacos服务地址)
6. 启动上述5个项目的主程序
## 后台项目效果
- 登录页面

![image](https://user-images.githubusercontent.com/46312799/154927324-019b3d41-2eff-4397-bc6d-2324c28b340b.png)


- 滑块验证码

![image](https://user-images.githubusercontent.com/46312799/154927395-a180c425-092c-4b41-81d9-dd080d4b0cb2.png)

- 首页

![image](https://user-images.githubusercontent.com/46312799/154927814-9e4ad2c1-2aaa-47e9-8306-89601397915f.png)

- 字典管理

![image](https://user-images.githubusercontent.com/46312799/154927994-f3c1e864-5897-4f32-9f05-7df4ede57e22.png)

![image](https://user-images.githubusercontent.com/46312799/154928015-ba8728e5-d13f-472b-bb2f-d45c8f3104f9.png)


- 网站设置

![image](https://user-images.githubusercontent.com/46312799/154928097-77e1f002-d9dc-4d86-b89b-f9417a05405a.png)


- 日志管理

![image](https://user-images.githubusercontent.com/46312799/154928126-d8719e1e-c563-4995-ab97-3de2e211bb5c.png)

- 题目列表

![image](https://user-images.githubusercontent.com/46312799/154928222-a1c9fff7-6041-4837-a75c-612fb1125031.png)

- 添加题目

![image](https://user-images.githubusercontent.com/46312799/154928287-4dfc6c4a-ef5a-40c4-9882-08f3b684aeac.png)

- excel批量导入题目

![image](https://user-images.githubusercontent.com/46312799/154928337-bab40039-8c53-43f2-adcd-f266609951cc.png)


- 试卷列表

![image](https://user-images.githubusercontent.com/46312799/154928388-3cdd5885-c3bd-4994-a1e0-933aa4fa09b9.png)

- 添加试卷


![image](https://user-images.githubusercontent.com/46312799/154928425-a4d78944-81e1-4ed6-95f7-865792936aab.png)

- 会员列表

![image](https://user-images.githubusercontent.com/46312799/154928647-fae07d78-082c-43e5-b6c1-f958b0bb0805.png)

- 消息管理

![image](https://user-images.githubusercontent.com/46312799/154928688-809e8022-7a5d-49e1-be3a-8856ac8d1fe2.png)

## 前台项目效果

- 首页

![image](https://user-images.githubusercontent.com/46312799/154929529-cfec285b-b7e0-41c8-90a2-b00631744bf2.png)


- 登录

![image](https://user-images.githubusercontent.com/46312799/154929602-01e77a4b-7597-45a6-a7b3-2c0c3c464169.png)


- 注册

![image](https://user-images.githubusercontent.com/46312799/154929629-132cdb93-f5a9-4827-9662-a954bffee3e6.png)


- 用户信息

![image](https://user-images.githubusercontent.com/46312799/154929767-9960ae9e-f8b9-49ab-b4bc-77f6acc22c98.png)


- 试卷详情

![image](https://user-images.githubusercontent.com/46312799/154929719-a64d4100-556a-44e1-a6c1-b0072a8af8da.png)


- 考试

![image](https://user-images.githubusercontent.com/46312799/154929838-aee9477e-99cb-4704-8737-2d673f3749dd.png)

- 考试记录

![image](https://user-images.githubusercontent.com/46312799/154929876-4b355a47-6a06-4041-adbd-f039119a3945.png)

- 错题本

![image](https://user-images.githubusercontent.com/46312799/154929916-ee8fb711-dedf-4b42-af9f-87465958a880.png)


