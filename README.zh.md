# Graduate 

> 这个Demo(也可以叫做项目，因为太拉了不好意思，所以叫Demo)，当时学SpringBoot的时候顺手写来着，给一位技术有限的玩家参考做毕业设计了


## 项目结构（大概）

```
Graduate:
    |---src
        |---main
            |---java
                |---com.master.graduate
                    |---admin
                    |---config
                    |---customer
                    |---utils
                    |---GraduateApplication.java
            |---resources
                |---public
                |---resources
                    |---backup
                        |---*.sql
                |---static
                |---tmplates
                |---application.properties
        |---test
    |---pom.xml
```

## 安装使用，跑跑

1.  创建一个数据库（MySQL）,随便从`resources/backup`中选择一个文件,如, `a.sql`

```sql
mysql> create databsae graduate;
mysql> use graduate;
```

2. 把这个SQL文件中的内容导入到上一步创建的库中

```shell
mysql> source /path/of/yours/a.sql
```

3. 更新SpringBoot中`application.properties`配置文件中的MySQL的协议地址。

4. 打开一个java的集成开发环境要支持Maven的那种才行，比如如 VsCode, Eclipse 或者 IntelliJ IDEA，将下载的这个仓库导入到其中，用`pom.xml`导入。

5. 运行 `GraduateApplication.java`，就可以了

## 环境

- Java 1.8
- maven 3.8
- Spring Boot 2.2.4.RELEASE
- MySQL 8.0

# 祝好

如果帮到你了，点个星星吧，大哥大姐。