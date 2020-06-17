package com.dveamer.generalizer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SimplifierTest {

    Simplifier simplifier;

    @BeforeAll
    void setup() {
        simplifier = new SimplifierFactory().create(PathFixture.wholeListeningPath());
    }

    @Test
    void testSimplify() {
        Collection<ExpectedInOut> testCases = PathFixture.expectedInOutList();
        for(ExpectedInOut expectedInOut : testCases) {
            assertThat(expectedInOut.getInput(), simplifier.simplify(expectedInOut.getInput()), equalTo(expectedInOut.getOutput()));
        }
    }

    @Test
    void testSimplifyParallel() {
        Collection<ExpectedInOut> testCases = PathFixture.expectedInOutList();
        testCases.parallelStream()
                .forEach(expectedInOut->{
                    assertThat(expectedInOut.getInput(), simplifier.simplify(expectedInOut.getInput()), equalTo(expectedInOut.getOutput()));
                });
    }

}
