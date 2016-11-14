package org.yu55.yagga.utils.assertion;

import org.yu55.yagga.common.model.grep.GrepResponseLine;
import org.yu55.yagga.common.model.grep.GrepResponseLineAssert;

public class CustomAssertions extends org.assertj.core.api.Assertions {

    public static GrepResponseLineAssert assertThat(GrepResponseLine actual) {
        return GrepResponseLineAssert.assertThat(actual);
    }
}

