package org.dcm4che.warpunit.examples.greeter.control;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PoliteGreeter {

	public String greet(String whomToGreet) {
        return "Greetings, " + whomToGreet + " !";
    }
}