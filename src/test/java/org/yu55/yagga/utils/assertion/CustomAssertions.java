package org.yu55.yagga.utils.assertion;

import org.yu55.yagga.api.grep.model.GrepResponseLine;
import org.yu55.yagga.api.grep.model.GrepResponseLineAssert;

public class CustomAssertions extends org.assertj.core.api.Assertions {

    public static GrepResponseLineAssert assertThat(GrepResponseLine actual) {
        return GrepResponseLineAssert.assertThat(actual);
    }
}

