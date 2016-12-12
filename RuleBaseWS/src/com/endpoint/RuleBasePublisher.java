package com.endpoint;

import com.ws.RuleBaseImplementation;

import javax.xml.ws.Endpoint;


public class RuleBasePublisher {
    public static void main(String[] args ) {

        Endpoint.publish("http://localhost:9998/ws/GetBanks", new RuleBaseImplementation());

    }
}
