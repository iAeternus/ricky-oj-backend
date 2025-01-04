# **Architecture 架构**

## user-server 用户服务

### user 用户

### verification 验证码

### login 登录



## judge-server 评测服务

### judge 评测

### sand-box 安全沙箱

### remote-judge 远程评测

### format 赛制

### language 语言



## problem-server 题目服务

### problem 题目

### category 分类



## training-server 训练服务



## group-server 团队服务



## discussion-server 讨论服务



## contest-server 比赛服务



## common-server 通用服务



## plugins-server 插件服务（optional）

# **Core Use Case 核心用例**

## judge 评测

### 基本事件流

1. 评测记录落库
2. 查询题目
3. 评测
    1. 获取评测模式
    2. 调用安全沙箱编译，若评测模式为`spj`或`interactive`则可能需要编译额外的文件
    3. 加载测试数据
    4. 调用安全沙箱运行每个测试点，并比对结果（MD5）
    5. 评判最终结果，调用赛制处理器
4. 评测结果落库
5. 后续操作
    1. 变更用户数据
    2. 变更比赛数据



