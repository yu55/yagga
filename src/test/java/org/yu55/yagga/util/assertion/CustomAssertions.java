package org.yu55.yagga.util.assertion;

import org.yu55.yagga.core.grep.model.GrepResponseLine;
import org.yu55.yagga.core.grep.model.GrepResponseLineAssert;

public class CustomAssertions extends org.assertj.core.api.Assertions {

    public static GrepResponseLineAssert assertThat(GrepResponseLine actual) {
        return GrepResponseLineAssert.assertThat(actual);
    }
}

