package com.gerardo.thrive.exercise;

import com.gerardo.thrive.AbstractIntegrationTest;
import com.gerardo.thrive.auth.security.UserPrincipal;
import com.gerardo.thrive.auth.services.JwtService;
import com.gerardo.thrive.common.enums.Role;
import com.gerardo.thrive.exercise.entities.ExerciseModel;
import com.gerardo.thrive.exercise.enums.MuscleGroup;
import com.gerardo.thrive.exercise.repositories.ExerciseRepository;
import com.gerardo.thrive.testsupport.TestUserFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureMockMvc
public class ExerciseControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ExerciseRepository exerciseRepository;

    private static String token;

    @BeforeAll
    static void setUp(@Autowired TestUserFactory testUserFactory, @Autowired JwtService jwtService) {
        UserPrincipal principal = new UserPrincipal(
                testUserFactory.createUserTest("Marco", "marco.test@gmail.com", "marcotest", "quicksilver",
                        Role.ADMIN));
        token = jwtService.generateToken(principal);
    }

    @Test
    void createExercise_happyPath_return201() throws Exception {
        String request = """
                {
                  "name": "Bench Press",
                  "description": "A compound chest exercise performed with a barbell.",
                  "imageUri": "https://example.com/images/bench-press.png",
                  "muscleGroup": "CHEST"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/thrive/exercises/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status()
                        .isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name")
                        .value("Bench Press"));

        assertTrue(exerciseRepository.existsByName("Bench Press"));
    }

    @Test
    void createExercise_fieldViolation_return400() throws Exception {
        String nameViolation = """
                {
                  "name": "",
                  "description": "A compound chest exercise performed with a barbell.",
                  "imageUri": "https://example.com/images/bench-press.png",
                  "muscleGroup": "CHEST"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/thrive/exercises/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(nameViolation))
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());
    }

    @Test
    void getExercises_happyPath_return200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/thrive/exercises/all")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
    }

    @Test
    void getExerciseById_happyPath_return200() throws Exception {
        ExerciseModel exercise = new ExerciseModel();
        exercise.setName("Squat");
        exercise.setDescription("A compound leg exercise performed with a barbell.");
        exercise.setImageUri("https://example.com/images/squat.png");
        exercise.setMuscleGroup(MuscleGroup.ABS);
        ExerciseModel saved = exerciseRepository.save(exercise);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/thrive/exercises/searchBy/" + saved.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name")
                        .value("Squat"));
    }

    @Test
    void getExerciseById_notFound_return404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/thrive/exercises/searchBy/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status()
                        .isNotFound());
    }
}
