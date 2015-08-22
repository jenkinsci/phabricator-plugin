// Copyright (c) 2015 Uber
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.uber.jenkins.phabricator.unit;

import hudson.tasks.junit.TestResult;
import hudson.util.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class JUnitTestProviderTest {
    @Test
    public void testConvertJUnit() throws Exception {
        JUnitTestProvider provider = new JUnitTestProvider();
        UnitResults results = provider.convertJUnit(getTestResult());

        assertNotNull(results);
        assertEquals(35, results.getResults().size());
        for (UnitResult result : results.getResults()) {
            assertEquals("pass", result.getHarbormasterResult());
        }
    }

    @Test
    public void testConvertNullProvider() {
        assertNull(new JUnitTestProvider().convertJUnit(null));
    }

    private TestResult getTestResult() throws IOException {
        File temp = File.createTempFile("anything", "xml");
        temp.deleteOnExit();
        InputStream junit = getClass().getResourceAsStream("go-torch-junit.xml");

        IOUtils.copy(junit, temp);
        TestResult result = new TestResult();
        result.parse(temp);
        return result;
    }
}
