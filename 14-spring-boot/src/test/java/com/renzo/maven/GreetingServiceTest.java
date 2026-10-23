package com.renzo.maven;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GreetingServiceTest {

    @Test
    void greetReturnsPersonalizedMessage() {
        GreetingService service = new GreetingService();
        String result = service.greet("Ricardo");
        assertEquals("Hello, Ricardo!", result);
    }
}