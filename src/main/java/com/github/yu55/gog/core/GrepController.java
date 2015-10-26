package com.github.yu55.gog.core;

import com.github.yu55.gog.core.model.AnnotateRequest;
import com.github.yu55.gog.core.model.AnnotateResponse;
import com.github.yu55.gog.core.model.GrepRequest;
import com.github.yu55.gog.core.model.GrepResponseLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class GrepController {

    private GrepService grepService;

    private AnnotateService annotateService;

    @Autowired
    public GrepController(GrepService grepService, AnnotateService annotateService) {
        this.grepService = grepService;
        this.annotateService = annotateService;
    }

    @RequestMapping(value = "/repositories")
    public List<String> repositories() {
        return grepService.getAvailableRepositories();
    }

    @RequestMapping(value = "/grep", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<GrepResponseLine> grep(@RequestBody GrepRequest grepRequest) {
        return grepService.grep(grepRequest).getGrepResponseLines();
    }

    @RequestMapping(value = "/annotate", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public AnnotateResponse annotate(@RequestBody AnnotateRequest annotateRequest) {
        return annotateService.annotate(annotateRequest);
    }
}
