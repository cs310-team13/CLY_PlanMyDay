package com.example.planmyday;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HttpHandlerTest {

    private WireMockServer wireMockServer;

    @Before
    public void setup() {
        wireMockServer = new WireMockServer(8089); // Port number for the mock server
        wireMockServer.start();

        configureFor("localhost", 8089); // Configure WireMock

        // Stubbing WireMock to provide a mock response for a specific URL
        stubFor(get(urlEqualTo("/testUrl"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Mock response")));
    }

    @After
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void testMakeServiceCallWithValidUrl() {
        HttpHandler handler = new HttpHandler();
        String testUrl = "http://localhost:8089/testUrl"; // Use the mock server URL

        String response = handler.makeServiceCall(testUrl);
        assertEquals("Mock response", response.trim());
    }

    @Test
    public void testMakeServiceCallWithInvalidUrl() {
        HttpHandler handler = new HttpHandler();
        String invalidUrl = "http://invalidurl";

        String response = handler.makeServiceCall(invalidUrl);
        assertNull("Response should be null for an invalid URL", response);
    }

}
