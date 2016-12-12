package com.endpoint;

import com.ws.GetBanksImplementation;

import javax.xml.ws.Endpoint;


public class GetBanksPublisher {
    public static void main(String[] args ) {

        Endpoint.publish("http://localhost:9998/ws/GetBanks", new GetBanksImplementation());

    }
}
