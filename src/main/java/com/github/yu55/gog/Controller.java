package com.github.yu55.gog;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController()
public class Controller {

    @RequestMapping(value = "/grep", method = RequestMethod.POST)
    public List<String> grep(@RequestBody String wanted) {
        System.out.println("wanted=" + wanted);
        return Arrays.asList(new String[]{
                "src/main/java/com/github/yu55/gog/Application.java:    public static void main(String[] args) {",
                "src/main/java/com/github/yu55/gog/Controller.java:    List<String> grep() {",
                "src/main/java/com/github/yu55/gog/Controller.java:        return Arrays.asList(new String[] {\"\", \"\", \"\"});"});
    }
}
