package com.dev.userserver;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.dev.userserver.model.User;
import com.dev.userserver.repository.UserRepo;
import com.dev.userserver.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = UserServerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
class UserServerApplicationTests {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepo userRepo;

	@Test
	public void findAllUserTest() {
		List<User> users = new ArrayList<>();
		users.add(new User("johnny@gmail.com", "1234", "ADMIN"));
		users.add(new User("sam@gmail.com", "4321", "USER"));

		when(userRepo.findAll()).thenReturn(users);

		List<User> result = userService.findAllUser();

		assertEquals(users.size(), result.size());
		assertEquals(users.get(0), result.get(0));
		
	}

}
