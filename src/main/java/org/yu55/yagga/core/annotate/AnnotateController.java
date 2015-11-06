package org.yu55.yagga.core.annotate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.yu55.yagga.core.annotate.model.AnnotateRequest;
import org.yu55.yagga.core.annotate.model.AnnotateResponse;

@RestController()
public class AnnotateController {

    private AnnotateService annotateService;

    @Autowired
    public AnnotateController(AnnotateService annotateService) {
        this.annotateService = annotateService;
    }

    @RequestMapping(value = "/annotate", method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public AnnotateResponse annotate(@RequestBody AnnotateRequest annotateRequest) {
        return annotateService.annotate(annotateRequest);
    }
}
