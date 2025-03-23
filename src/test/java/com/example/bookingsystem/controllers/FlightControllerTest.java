package com.example.bookingsystem.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class FlightControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String flightDtoIn = """
            {
              "flightNumber": "yyiyui",
              "departureAirport": "tallinn",
              "arrivalAirport": "helsingi",
              "departureTime": "2025-04-01T15:00:00",
              "arrivalTime": "2025-04-01T18:00:00",
              "price": 50.0
            }
            """;

    @Test
    void addFlight() throws Exception {
        mvc.perform(post("/api/flights/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(flightDtoIn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value("yyiyui"))
                .andExpect(jsonPath("$.departureAirport").value("tallinn"))
                .andExpect(jsonPath("$.arrivalAirport").value("helsingi"))
                .andExpect(jsonPath("$.price").value(50.0));
    }

    @Test
    void getFlightById() throws Exception {
        String response = mvc.perform(post("/api/flights/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(flightDtoIn))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode responseJson = objectMapper.readTree(response);
        long flightId = responseJson.get("flightId").asLong();

        mvc.perform(get("/api/flights/" + flightId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightId").value(flightId))
                .andExpect(jsonPath("$.flightNumber").value("yyiyui"));
    }

    @Test
    void getFlightsWithFilters() throws Exception {
        mvc.perform(post("/api/flights/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(flightDtoIn))
                .andExpect(status().isOk());

        mvc.perform(get("/api/flights")
                        .param("departureAirport", "tallinn")
                        .param("arrivalAirport", "helsingi")
                        .param("departureTime", "2025-04-01T15:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flights").isArray())
                .andExpect(jsonPath("$.flights[0].flightNumber").value("yyiyui"));
    }
}
