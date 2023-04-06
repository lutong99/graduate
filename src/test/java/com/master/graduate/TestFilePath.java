package com.master.graduate;

import com.sun.org.apache.bcel.internal.util.ClassPath;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

/**
 * @author Master_Joe lutong99
 * @since 3/3/2020 9:25 PM
 */
@SpringBootTest
public class TestFilePath {

    @Test
    public void testPath() {
        File f = new File("resources/public/library/upload");
        System.out.println("f.getAbsolutePath() = " + f.getAbsolutePath());
    }

    @Test
    public void testPath1() {

        String path = System.getProperty("user.dir");
        System.out.println("path = " + path);

    }

    @Test
    public void testPath2() {
        String path = System.getProperty("user.dir");
        path = path + "src/main/resources";
        System.out.println("path = " + path);
    }

    @Test
    public void testPath3() {
        ClassPath classPath = ClassPath.SYSTEM_CLASS_PATH;
        System.out.println("classPath = " + classPath);
    }

    @Test
    public void testPath4() {
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/public/library");
        String absolutePath = file.getAbsolutePath();
        System.out.println(absolutePath);
    }


}
