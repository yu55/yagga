package org.yu55.yagga.handler.api.command.annotate;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.yu55.yagga.core.annotate.model.AnnotateResponseAssert.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.yu55.yagga.core.annotate.model.AnnotateRequest;
import org.yu55.yagga.core.annotate.model.AnnotateResponse;
import org.yu55.yagga.core.annotate.model.AnnotateResponseLine;
import org.yu55.yagga.handler.api.DvcsRepository;
import org.yu55.yagga.handler.generic.Repositories;
import org.yu55.yagga.handler.generic.RepositoriesFileVisitor;

public class AnnotateHandlerTest {

    @Test
    public void shouldAnnotate() {
        // given
        DvcsRepository dcvsRepository = mock(DvcsRepository.class);
        AnnotateResponseLine annotateResponseLine = new AnnotateResponseLine("Some annotate line");
        when(dcvsRepository.isDirectoryNameEqual("test_dir")).thenReturn(true);
        when(dcvsRepository.annotate(any(AnnotateParameters.class))).thenReturn(
                new AnnotateResponse(Arrays.asList(annotateResponseLine)));

        RepositoriesFileVisitor repositoriesFileVisitor = mock(RepositoriesFileVisitor.class);
        when(repositoriesFileVisitor.getRepositories()).thenReturn(asList(dcvsRepository));

        Repositories repositories = new Repositories(repositoriesFileVisitor);
        AnnotateHandler annotateHandler = new AnnotateHandler(repositories);

        // when
        AnnotateRequest annotateRequest = new AnnotateRequest();
        annotateRequest.setRepository("test_dir");
        AnnotateResponse expectedAnnotateResponse = annotateHandler.annotate(annotateRequest);

        // then
        assertThat(expectedAnnotateResponse).hasOnlyAnnotationResponseLines(annotateResponseLine);
    }
}