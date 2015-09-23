package com.github.yu55.gog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class Controller {

    private GrepService grepService;

    @Autowired
    public Controller(GrepService grepService) {
        this.grepService = grepService;
    }

    @RequestMapping(value = "/grep", method = RequestMethod.POST)
    public List<String> grep(@RequestBody String wanted) {
        return grepService.grep(wanted);
    }

}
