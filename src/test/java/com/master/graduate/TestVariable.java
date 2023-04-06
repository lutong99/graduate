package com.master.graduate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Master_Joe lutong99
 * @since 3/3/2020 10:48 PM
 */

@SpringBootTest
public class TestVariable {

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.url}")
    String url;

    @Test
    public void testVariable1() {

        System.out.println("username = " + username);
        System.out.println("password = " + password);
        System.out.println("url = " + url);
        String database = null;
        if (url.contains("?")) {
            database = url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));
        } else {
            database = url.substring(url.lastIndexOf("/") + 1);
        }
        System.out.println("database = " + database);

    }

}
