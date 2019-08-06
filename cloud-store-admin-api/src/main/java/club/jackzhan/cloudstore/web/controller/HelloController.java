package club.jackzhan.cloudstore.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @GetMapping(value = "/hi")
    public String hi() {
        return "Hi! I'm admin service.";
    }
}
