package org.yu55.yagga.api.grep.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class GrepResponseTest {

    @Test
    public void shouldAddAll() {
        // given
        GrepResponse grepResponse = new GrepResponse(2);
        GrepResponseLine grepResponseLine1 = new GrepResponseLine("/my/repo_1", "my_file_1", 1, "line_1");
        GrepResponseLine grepResponseLine2 = new GrepResponseLine("/my/repo_2", "my_file_2", 12, "line_2");

        // when
        grepResponse.addAllGrepResponseLines(Arrays.asList(grepResponseLine1, grepResponseLine2));

        // then
        assertThat(grepResponse.getGrepResponseLines())
                .hasSize(2).containsExactly(grepResponseLine1, grepResponseLine2);
    }

    @Test
    public void shouldLimitAddedLines() {
        // given
        GrepResponse grepResponse = new GrepResponse(1);
        GrepResponseLine grepResponseLine1 = new GrepResponseLine("/my/repo_1", "my_file_1", 1, "line_1");
        GrepResponseLine grepResponseLine2 = new GrepResponseLine("/my/repo_2", "my_file_2", 12, "line_2");

        // when
        grepResponse.addAllGrepResponseLines(Arrays.asList(grepResponseLine1, grepResponseLine2));

        // then
        assertThat(grepResponse.getGrepResponseLines())
                .hasSize(1).containsExactly(grepResponseLine1);
    }
}
