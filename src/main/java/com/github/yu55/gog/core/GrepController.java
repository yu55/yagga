package com.github.yu55.gog.core;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.yu55.gog.core.model.GrepRequest;

@RestController()
public class GrepController {

    private GrepService grepService;

    @Autowired
    public GrepController(GrepService grepService) {
        this.grepService = grepService;
    }

    @RequestMapping(value = "/repositories")
    public List<String> repositories() {
        return grepService.getAvailavleRepositories();
    }

    @RequestMapping(value = "/grep", method = RequestMethod.POST)
    public List<String> grep(@RequestBody GrepRequest grepRequest) {
        return grepService.grep(grepRequest).getResponseLines()
                .stream()
                .map(responseLine -> responseLine.getLine())
                .collect(Collectors.toList());
    }

}
