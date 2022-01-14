package de.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping({"/", "index"})
    public String gotoIndex() {
        return "index";
    }

    @GetMapping("protected")
    public String gotoProtected() {
        return "protected";
    }
}
