package org.yu55.yagga.common.handler.annotate;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.yu55.yagga.common.model.annotate.AnnotateResponseAssert.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.yu55.yagga.common.model.annotate.AnnotateRequest;
import org.yu55.yagga.common.model.annotate.AnnotateResponse;
import org.yu55.yagga.common.model.annotate.AnnotateResponseLine;
import org.yu55.yagga.common.repository.AnnotateParameters;
import org.yu55.yagga.common.repository.RepositoriesContainer;
import org.yu55.yagga.common.repository.RepositoriesFileVisitor;
import org.yu55.yagga.common.repository.Repository;
import org.yu55.yagga.common.repository.annotate.AnnotateHandler;

public class AnnotateHandlerTest {

    @Test
    public void shouldAnnotate() {
        // given
        Repository dcvsRepository = mock(Repository.class);
        AnnotateResponseLine annotateResponseLine = new AnnotateResponseLine("Some annotate line");
        when(dcvsRepository.isDirectoryNameEqual("test_dir")).thenReturn(true);
        when(dcvsRepository.annotate(any(AnnotateParameters.class))).thenReturn(
                new AnnotateResponse(Arrays.asList(annotateResponseLine)));

        RepositoriesFileVisitor repositoriesFileVisitor = mock(RepositoriesFileVisitor.class);
        when(repositoriesFileVisitor.getRepositories()).thenReturn(asList(dcvsRepository));

        RepositoriesContainer repositoriesContainer = new RepositoriesContainer(repositoriesFileVisitor);
        AnnotateHandler annotateHandler = new AnnotateHandler(repositoriesContainer);

        // when
        AnnotateRequest annotateRequest = new AnnotateRequest();
        annotateRequest.setRepository("test_dir");
        AnnotateResponse expectedAnnotateResponse = annotateHandler.annotate(annotateRequest);

        // then
        assertThat(expectedAnnotateResponse).hasOnlyAnnotationResponseLines(annotateResponseLine);
    }
}