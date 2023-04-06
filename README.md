# Graduate 

> This project, maybe not project, is just a demo what I did when I learnt the SpringBoot


## Structure 
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

## Installation 

1. create a database, and select a backup sql file from the `resources/backup` randomly, for example, `a.sql`

```sql
mysql> create databsae graduate;
mysql> use graduate;
```

2. import the sql file content into the database you created 

```shell
mysql> source /path/of/yours/a.sql
```

3. update the proxy and address in the `application.properties` which is the spring boot properties.

4. open a java IDE like VsCode, Eclipse or IntelliJ IDEA who supports maven, and import the project used by `pom.xml`

5. start Graduate Applicaion

## My Environment

- Java 1.8
- maven 3.8
- Spring Boot 2.2.4.RELEASE
- MySQL 8.0

# Good Luck

If it helps you, it's my pleasure