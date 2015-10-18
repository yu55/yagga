package com.github.yu55.gog.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.yu55.gog.core.model.GrepRequest;
import com.github.yu55.gog.core.model.GrepResponseLine;

@RestController()
public class GrepController {

    private GrepService grepService;

    @Autowired
    public GrepController(GrepService grepService) {
        this.grepService = grepService;
    }

    @RequestMapping(value = "/repositories")
    public List<String> repositories() {
        return grepService.getAvailableRepositories();
    }

    @RequestMapping(value = "/grep", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE})
    public List<GrepResponseLine> grep(@RequestBody GrepRequest grepRequest) {
        return grepService.grep(grepRequest).getGrepResponseLines();
    }

}
