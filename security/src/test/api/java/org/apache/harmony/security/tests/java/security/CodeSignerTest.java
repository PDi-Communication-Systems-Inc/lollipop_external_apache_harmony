/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
 * @author Alexander V. Astapchuk
 */

package org.apache.harmony.security.tests.java.security;

import java.security.*;
import java.security.cert.CertPath;
import java.util.Date;

import org.apache.harmony.security.tests.support.TestCertUtils;

import junit.framework.TestCase;


/**
 * Unit test for CodeSigner.
 */

public class CodeSignerTest extends TestCase {

    private CertPath cpath = TestCertUtils.genCertPath(3, 0);
    private Date now = new Date();

    private Timestamp ts = new Timestamp(now, cpath);

    /**
     * must throw NPE if signerCertPath is null
     */
    public void testCodeSigner_00() {
        try {
            new CodeSigner(null, ts);
            fail("must not accept null");
        } catch (NullPointerException ex) {
            /* it's ok */
        }
    }

    /**
     * timestamp can be null
     */
    public final void testCodeSigner_01() {
        new CodeSigner(cpath, null);
    }

    /**
     * Test various assertions about equals()
     */
    public final void testEqualsObject() {

        CodeSigner one = new CodeSigner(cpath, ts);
        CodeSigner two = new CodeSigner(cpath, ts);
        CodeSigner three = new CodeSigner(cpath, null);

        CertPath cpath2 = TestCertUtils.genCertPath(5, 3);
        CodeSigner four = new CodeSigner(cpath2, null);

        assertTrue(one.equals(one));
        assertTrue(one.equals(two));
        assertTrue(two.equals(one));
        assertFalse(one.equals(three));
        assertFalse(three.equals(one));
        assertTrue(three.equals(three));
        // different CertPaths
        assertFalse(three.equals(four));
        // special cases
        assertFalse(one.equals(null));
        assertFalse(one.equals(new Object()));
    }

    /**
     * Tests CodeSigner.getSignerCertPath()
     */
    public void testGetSignerCertPath() {
        assertSame(new CodeSigner(cpath, null).getSignerCertPath(), cpath);
    }

    /**
     * Tests CodeSigner.getTimeStamp()
     */
    public void testGetTimestamp() {
        assertNull(new CodeSigner(cpath, null).getTimestamp());
        assertSame(new CodeSigner(cpath, ts).getTimestamp(), ts);
    }

    /**
     * Tests CodeSigner.toString()
     */
    public void testToString() {
        new CodeSigner(cpath, null).toString();
        new CodeSigner(cpath, ts).toString();
    }

}
