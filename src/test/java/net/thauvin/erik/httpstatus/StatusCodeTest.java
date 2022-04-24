/*
 * StatusCodeTest.java
 *
 * Copyright (c) 2015-2022, Erik C. Thauvin (erik@thauvin.net)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *   Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *   Neither the name of this project nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.thauvin.erik.httpstatus;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

import static org.testng.Assert.*;

/**
 * StatusCode Tests.
 *
 * @author <a href="mailto:erik@thauvin.net">Erik C. Thauvin</a>
 */
@SuppressFBWarnings("CE_CLASS_ENVY")
public class StatusCodeTest {
    @Test
    void testStatusCode() {
        final ResourceBundle bundle = ResourceBundle.getBundle(Reasons.BUNDLE_BASENAME);
        StatusCode statusCode = new StatusCode();
        for (final String key : bundle.keySet()) {
            final int code = Integer.parseInt(key);
            statusCode.setCode(code);
            assertEquals(statusCode.getCode(), code, "is not " + code);
            assertEquals(statusCode.isInfo(), code >= 100 && code < 200, code + " is info");
            assertEquals(statusCode.isSuccess(), code >= 200 && code < 300, code + " is ok");
            assertEquals(statusCode.isRedirect(), code >= 300 && code < 400, code + " is redirect");
            assertEquals(statusCode.isClientError(), code >= 400 && code < 500, code + " is client error");
            assertEquals(statusCode.isServerError(), code >= 500 && code < 600, code + " is server error");
            assertEquals(statusCode.isError(), code >= 400 && code < 600, code + " is error");
            assertTrue(statusCode.isValid(), code + "is valid");

            assertEquals(statusCode.getReason(), Reasons.getReasonPhrase(code), code + "reason phrase is not valid");
        }

        final int[] unknowns = {0, 99, 600};
        for (final int code : unknowns) {
            statusCode.setCode(code);
            assertEquals(statusCode.getCode(), code, "is not " + code);
            assertFalse(statusCode.isInfo(), code + " is info");
            assertFalse(statusCode.isSuccess(), code + " is ok");
            assertFalse(statusCode.isRedirect(), code + " is redirect");
            assertFalse(statusCode.isClientError(), code + " is client error");
            assertFalse(statusCode.isServerError(), code + " is server error");
            assertFalse(statusCode.isError(), code + " is error");
            assertFalse(statusCode.isValid(), "600 is invalid");
            assertNull(statusCode.getReason(), code + "reason phrase is not null.");
        }

        statusCode = new StatusCode(900);
        assertEquals(statusCode.getCode(), 900, "is not 900");
    }
}
