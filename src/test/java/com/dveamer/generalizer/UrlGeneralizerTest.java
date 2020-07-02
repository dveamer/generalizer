package com.dveamer.generalizer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UrlGeneralizerTest {

    Generalizer generalizer;

    @BeforeAll
    void setup() {
        generalizer = new GeneralizerFactory().createUrlGeneralizer(PathFixture.wholeListeningPath());
    }

    @Test
    void testSimplify() {
        Collection<ExpectedInOut> testCases = PathFixture.expectedInOutList();
        for(ExpectedInOut expectedInOut : testCases) {
            assertThat(expectedInOut.getInput(), generalizer.generalize(expectedInOut.getInput()), equalTo(expectedInOut.getOutput()));
        }
    }

    @Test
    void testSimplifyParallel() {
        Collection<ExpectedInOut> testCases = PathFixture.expectedInOutList();
        testCases.parallelStream()
                .forEach(expectedInOut->{
                    assertThat(expectedInOut.getInput(), generalizer.generalize(expectedInOut.getInput()), equalTo(expectedInOut.getOutput()));
                });
    }

}
