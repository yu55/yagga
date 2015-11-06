package org.yu55.yagga.core.grep;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yu55.yagga.core.grep.model.GrepRequest;
import org.yu55.yagga.core.grep.model.GrepResponseLine;

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

    @RequestMapping(value = "/grep", method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<GrepResponseLine> grep(@RequestBody GrepRequest grepRequest) {
        return grepService.grep(grepRequest).getGrepResponseLines();
    }
}
