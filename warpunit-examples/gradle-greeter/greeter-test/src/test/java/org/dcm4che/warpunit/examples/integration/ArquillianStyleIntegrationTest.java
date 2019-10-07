package org.dcm4che.warpunit.examples.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.dcm4che.test.remote.Warp;
import org.dcm4che.warpunit.examples.greeter.control.PoliteGreeter;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Example for a Arquillian-IntegrationTest-style of usage.
 * 
 *  The whole test-method is run in the container/server.
 *  This requires all the types used in the test-method to be present at server-side;
 *  This includes JUnit-types like assertThat.
 *
 */
@RunWith(Warp.class)
public class ArquillianStyleIntegrationTest {

    @Inject
    PoliteGreeter greeter;

    @Test
    //@Ignore("The server should be running for this test to work")
    public void testGreeter() {
        System.out.println("This is printed in the server log");

        String result =  greeter.greet("Bob");

        assertThat(result, is("Greetings, Bob !"));
    }
}