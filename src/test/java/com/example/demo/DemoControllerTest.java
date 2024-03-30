package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
public class DemoControllerTest {

    @Test
    public void shouldTestAPI(){
        DemoController demoController = new DemoController();
        Assert.notNull(demoController.home());
    }
}
