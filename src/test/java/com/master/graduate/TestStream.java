package com.master.graduate;

import com.master.graduate.customer.dao.OrderDAO;
import com.master.graduate.customer.dao.ProductsDAO;
import com.master.graduate.customer.entities.Kind;
import com.master.graduate.customer.entities.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Master_Joe lutong99
 * @since 3/3/2020 5:18 PM
 */
@SpringBootTest
public class TestStream {

    @Autowired
    ProductsDAO productsDAO;

    @Autowired
    OrderDAO orderDAO;


    @Test
    public void testStream() {

        List<Kind> kinds = productsDAO.getKinds();
        List<String> collect = kinds.stream().map(Kind::getkName).collect(Collectors.toList());
        collect.forEach(System.out::println);
        System.out.println("collect.contains(\"hello\") = " + collect.contains("hello"));
    }

    @Test
    public void testStream2() {
        Optional<Double> reduce = orderDAO.getAll().stream().map(Order::getMoney).reduce(Double::sum);
        Double aDouble = reduce.get();
        System.out.println("aDouble = " + aDouble);
    }

}
