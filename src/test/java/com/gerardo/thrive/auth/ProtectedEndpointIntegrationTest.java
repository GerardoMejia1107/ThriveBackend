package com.gerardo.thrive.auth;

import com.gerardo.thrive.AbstractIntegrationTest;
import com.gerardo.thrive.auth.security.UserPrincipal;
import com.gerardo.thrive.auth.services.JwtService;
import com.gerardo.thrive.common.enums.Role;
import com.gerardo.thrive.testsupport.TestUserFactory;
import com.gerardo.thrive.user.entities.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
public class ProtectedEndpointIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    TestUserFactory testUserFactory;
    @Autowired
    JwtService jwtService;

    @Test
    void protectedEndpoint_noToken_returns401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/security/user/test/ping"))
                .andExpect(MockMvcResultMatchers.status()
                        .isUnauthorized());

    }

    @Test
    void protectedEndpoint_wrongRole_return403() throws Exception {
        UserPrincipal principal = new UserPrincipal(
                testUserFactory.createUserTest("Gerardo", "00104923@uca.edu.sv", "portgas", "c5pE6d-CSl",
                        Role.CLIENT));
        String token = jwtService.generateToken(principal);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/security/resource/test/data")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status()
                        .isForbidden());
    }

    @Test
    void protectedEndpoint_rightRole_return200() throws Exception {
        UserPrincipal principal = new UserPrincipal(
                testUserFactory.createUserTest("Dulce", "dulceeee@gmail.com", "dulceeeee", "quicksilver", Role.ADMIN));
        String token = jwtService.generateToken(principal);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/security/resource/test/data")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());

    }
}
