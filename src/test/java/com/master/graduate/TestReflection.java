package com.master.graduate;

import com.master.graduate.customer.dao.ProductsDAO;
import com.master.graduate.customer.entities.Products;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;

/**
 * @author Master_Joe lutong99
 * @since 3/3/2020 4:43 PM
 */
@SpringBootTest
public class TestReflection {

    @Autowired
    ProductsDAO productsDAO;

    @Test
    public void testReflect() {

        Products p1 = productsDAO.getProductsById(15);
        Products p2 = productsDAO.getProductsById(16);

        Class<Products> pClass = Products.class;
        Field[] fields = pClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object o = field.get(p1);
                if (!o.equals(field.get(p2))) {
                    field.set(p2, field.get(p1));
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);


    }


}
