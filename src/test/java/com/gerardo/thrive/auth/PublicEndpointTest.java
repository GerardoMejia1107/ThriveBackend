package com.gerardo.thrive.auth;

import com.gerardo.thrive.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class PublicEndpointTest extends AbstractIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void publicEndpoint_noToken_returns200() throws Exception {
        mockMvc.perform(get("/api/security/user/test/public/ping"))
                .andExpect(status().isOk());
    }
}
