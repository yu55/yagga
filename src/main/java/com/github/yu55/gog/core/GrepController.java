package com.github.yu55.gog.core;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
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
    public List<String> blame() {//@RequestBody BlameRequest blameRequest) {
        return Arrays.asList("bfc83e2a        (Lukasz Sawicki 2015-10-14 21:32:16 +0200       1)\n" +
                        "80dfaad5        (  Marcin P     2015-09-26 23:36:13 +0200       2)\n" +
                        "80dfaad5        (  Marcin P     2015-09-26 23:36:13 +0200       3)\n" +
                        "80dfaad5        (  Marcin P     2015-09-26 23:36:13 +0200       4)\n" +
                        "80dfaad5        (  Marcin P     2015-09-26 23:36:13 +0200       5)\n" +
                        "bfc83e2a        (Lukasz Sawicki 2015-10-14 21:32:16 +0200       6)\n" +
                        "80dfaad5        (  Marcin P     2015-09-26 23:36:13 +0200       7)\n" +
                        "bfc83e2a        (Lukasz Sawicki 2015-10-14 21:32:16 +0200       8)\n" +
                        "80dfaad5        (  Marcin P     2015-09-26 23:36:13 +0200       9)\n" +
                        "80dfaad5        (  Marcin P     2015-09-26 23:36:13 +0200       10)\n" +
                        "80dfaad5        (  Marcin P     2015-09-26 23:36:13 +0200       11)\n" +
                        "80dfaad5        (  Marcin P     2015-09-26 23:36:13 +0200       12)\n" +
                        "80dfaad5        (  Marcin P     2015-09-26 23:36:13 +0200       13)\n" +
                        "80dfaad5        (  Marcin P     2015-09-26 23:36:13 +0200       14)\n" +
                        "bfc83e2a        (Lukasz Sawicki 2015-10-14 21:32:16 +0200       15)\n" +
                        "bfc83e2a        (Lukasz Sawicki 2015-10-14 21:32:16 +0200       16)\n" +
                        "bfc83e2a        (Lukasz Sawicki 2015-10-14 21:32:16 +0200       17)\n" +
                        "bfc83e2a        (Lukasz Sawicki 2015-10-14 21:32:16 +0200       18)\n" +
                        "80dfaad5        (  Marcin P     2015-09-26 23:36:13 +0200       19)\n" +
                        "80dfaad5        (  Marcin P     2015-09-26 23:36:13 +0200       20)\n" +
                        "80dfaad5        (  Marcin P     2015-09-26 23:36:13 +0200       21)\n" +
                        "80dfaad5        (  Marcin P     2015-09-26 23:36:13 +0200       22)",
                "package com.github.yu55.gog.core.model;\n" +
                "\n" +
                "import java.util.List;\n" +
                "\n" +
                "public class GrepRequest {\n" +
                "\n" +
                "    private String wanted;\n" +
                "\n" +
                "    private List<String> repositories;\n" +
                "\n" +
                "    public String getWanted() {\n" +
                "        return wanted;\n" +
                "    }\n" +
                "\n" +
                "    public boolean hasRepository(String name) {\n" +
                "        return repositories.contains(name);\n" +
                "    }\n" +
                "\n" +
                "    public List<String> getRepositories() {\n" +
                "        return repositories;\n" +
                "    }\n" +
                "}");
//        return "public class BlameRequest {}";
    }

}
