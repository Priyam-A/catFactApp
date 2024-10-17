package com.example.myapplication;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CatFactsAPITest extends TestCase {
    private MockWebServer mockWebServer;
    private CatFactsAPI catFactApi;

    @Before
    public void setUp() throws Exception {
        // Initialize MockWebServer
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        // Initialize Retrofit with the MockWebServer URL
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/")) // Use the MockWebServer URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        catFactApi = retrofit.create(CatFactsAPI.class);
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown(); // Shut down MockWebServer
    }

    @org.junit.jupiter.api.Test
    public void testGetCatFacts_success() throws IOException {
        // Create a mock response
        String mockResponse = "[\n" +
                "  {\n" +
                "    \"text\": \"Cats are awesome!\",\n" +
                "    \"user\": \"5a9ac18c7478810ea6c06381\",\n" +
                "    \"createdAt\": \"2018-03-06T21:20:03.505Z\"\n" +
                "  }\n" +
                "]";

        // Enqueue the mock response
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mockResponse));

        // Execute the API call
        Call<List<Fact>> call = catFactApi.getCatFacts();
        Response<List<Fact>> response = call.execute();

        // Verify the response
        assertEquals(200, response.code()); // Ensure response is successful

        // Get the list of cat facts from the response
        List<Fact> catFacts = response.body();
        assertEquals(1, catFacts.size()); // Ensure only one cat fact is returned
        assertEquals("Cats are awesome!", catFacts.get(0).getText()); // Check the content of the fact
    }

    @Test
    public void testGetCatFacts_failure() throws IOException {
        // Enqueue a mock error response
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody("{ \"error\": \"Not Found\" }"));

        // Execute the API call
        Call<List<Fact>> call = catFactApi.getCatFacts();
        Response<List<Fact>> response = call.execute();

        // Verify the response
        assertEquals(404, response.code()); // Ensure the response code is 404
        assertEquals(null, response.body()); // Ensure the response body is null
    }
}