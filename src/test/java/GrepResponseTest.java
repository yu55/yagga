import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.github.yu55.gog.core.model.GrepResponse;
import com.github.yu55.gog.core.model.GrepResponseLine;

public class GrepResponseTest {

    @Test
    public void shouldAddAll() {
        // given
        GrepResponse grepResponse = new GrepResponse(2);

        // when
        grepResponse.addAll(Arrays.asList(new GrepResponseLine("line_1"), new GrepResponseLine("line_2")));

        // then
        assertThat(grepResponse.getResponseLines())
                .hasSize(2)
                .extracting(GrepResponseLine::getLine).containsExactly("line_1", "line_2");
    }

    @Test
    public void shouldLimitAddedLines() {

    }
}