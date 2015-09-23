package com.github.yu55.gog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
public class Controller {

    private Repositories repositories;

    private GrepService grepService;

    @Autowired
    public Controller(GrepService grepService, Repositories repositories) {
        this.grepService = grepService;
        this.repositories = repositories;
    }

    @RequestMapping(value = "/repositories")
    public List<String> repositories() {
        return repositories.getDirectories().stream().map(d -> d.getName()).collect(Collectors.toList());
    }

    @RequestMapping(value = "/grep", method = RequestMethod.POST)
    public List<String> grep(@RequestBody String wanted) {
        return grepService.grep(wanted);
    }

}
