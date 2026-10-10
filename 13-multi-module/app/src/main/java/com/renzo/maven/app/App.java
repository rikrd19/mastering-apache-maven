package com.renzo.maven.app;

import com.renzo.maven.core.MessageService;

public class App {

    public static void main(String[] args) {
        MessageService messageService = new MessageService();

        System.out.println(messageService.getMessage());
    }
}