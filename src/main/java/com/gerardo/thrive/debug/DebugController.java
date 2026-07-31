package com.gerardo.thrive.debug;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class DebugController {

    @GetMapping("/public")
    public String publicMessage() {
        return "Hello World";
    }

    @GetMapping("/private")
    public String privateMessage() {
        return "This is confidential!!";
    }
}
