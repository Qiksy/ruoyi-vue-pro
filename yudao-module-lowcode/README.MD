### 代码结构
在模块中执行命令 ` tree -d -L 9 -I target* -n -I test*`

```
.
├── yudao-module-lowcode-api   //对外暴露的api，暂时没有其他的模块引用吗，暂时为空
│   └── src
│       └── main
│           ├── java
│           │   └── org
│           │       ├── jeecg
│           │       │   └── common
│           │       │       └── system
│           │       └── module
│           │           └── common
│           │               └── system
│           └── resources
└── yudao-module-lowcode-biz
    └── src
        └── main
            ├── java
            │   └── org
            │       └── jeecg
            │           ├── common
            │           │   ├── api
            │           │   │   └── dto
            │           │   ├── config
            │           │   ├── constant
            │           │   ├── handler
            │           │   ├── query
            │           │   ├── service
            │           │   ├── system
            │           │   │   └── vo
            │           │   └── util
            │           │       ├── dynamic
            │           │       ├── online
            │           │       └── sqlparse
            │           ├── interceptor
            │           └── modules
            │               ├── codegenerate
            │               │   ├── database
            │               │   ├── generate
            │               │   └── window
            │               └── online
            │                   ├── annotation
            │                   ├── auth
            │                   ├── cgform
            │                   ├── cgreport
            │                   ├── config
            │                   └── handler
            └── resources
                ├── lowcode
                └── static
```
