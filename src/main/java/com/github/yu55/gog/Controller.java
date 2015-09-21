package com.github.yu55.gog;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController()
public class Controller {

    @RequestMapping("/grep")
    public List<String> grep() {
        return Arrays.asList(new String[]{
                "src/main/java/com/github/yu55/gog/Application.java:    public static void main(String[] args) {",
                "src/main/java/com/github/yu55/gog/Controller.java:    List<String> grep() {",
                "src/main/java/com/github/yu55/gog/Controller.java:        return Arrays.asList(new String[] {\"\", \"\", \"\"});"});
    }
}
