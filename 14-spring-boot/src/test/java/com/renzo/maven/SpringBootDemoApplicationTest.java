package com.renzo.maven;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SpringBootDemoApplicationTest {

    @Test
    void helloReturnsExpectedMessage() {
       SpringBootDemoApplication application = new SpringBootDemoApplication();
       String result = application.hello();
       assertEquals("Spring Boot build verified!", result);
    }
}