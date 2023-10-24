package com.dev.userserver;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.dev.userserver.model.User;
import com.dev.userserver.repository.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = UserServerApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration
@TestPropertySource(locations = "classpath:application-test.yml")
public class UserControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void userInfoSavingTest() throws Exception {
    User userToSave = new User("demo321@email.com", "1234", "USER");
    mvc.perform(MockMvcRequestBuilders
        .post("/user/save")
        .content(objectMapper.writeValueAsString(userToSave))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("User is saved!"));
    User savedUser = userRepo.findByEmail(userToSave.getEmail()).orElse(null);

    assertEquals(userToSave.getEmail(), savedUser.getEmail());
    assertTrue(passwordEncoder.matches(userToSave.getPassword(), savedUser.getPassword()));
    assertEquals(userToSave.getRoles(), savedUser.getRoles());
  }

}
