package me.zhengjie;

import me.zhengjie.modules.iptv.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EladminSystemApplicationTests {
    @Autowired
    public OrderService orderService;

    @Test
    public void contextLoads() {
    }

    public static void main(String[] args) {
    }
}

