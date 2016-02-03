package org.yu55.yagga.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void shoudlReplaceAllBlankSubstrings() {
        // given
        String source = "    Marcin P\t2015-09-17 \t 10)";

        // when
        String result = StringUtils.wrapBlankSubstrings(source);

        // then
        assertThat(result).isEqualTo(" Marcin P 2015-09-17 10)");
    }
}