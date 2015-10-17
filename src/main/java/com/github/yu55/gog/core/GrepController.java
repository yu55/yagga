package com.github.yu55.gog.core;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/grep", method = RequestMethod.POST)
    public List<String> grep(@RequestBody GrepRequest grepRequest) {
        return grepService.grep(grepRequest).getResponseLines()
                .stream()
                .map(GrepResponseLine::getLine)
                .collect(toList());
    }

    @RequestMapping(value = "/blame", method = RequestMethod.POST)
    public String blame() {//@RequestBody BlameRequest blameRequest) {
//        return "package com.github.yu55.gog.core.model;\n" +
//                "\n" +
//                "public class BlameRequest {\n" +
//                "\n" +
//                "    private String file;\n" +
//                "\n" +
//                "    private String repository;\n" +
//                "\n" +
//                "    public String getFile() {\n" +
//                "        return file;\n" +
//                "    }\n" +
//                "\n" +
//                "    public String getRepository() {\n" +
//                "        return repository;\n" +
//                "    }\n" +
//                "}";
        return "public class BlameRequest {}";
    }

}
