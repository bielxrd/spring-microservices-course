package br.com.service.tasks;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Value("${message}")
    private String message;

    @GetMapping("/message")
    public String getMessage() {
        if (message.equalsIgnoreCase("Hello world")) {
            System.out.println("Default mesage detected");
        } else if (message.equalsIgnoreCase("Hello from main service")) {
            System.out.println("Main service message detected");
        } else {
            System.out.println("Custom message detected: " + message);
        }


        return message;
    }
}
