package com.example.CRUDapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CruDappApplicationTests {

	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void getAllUsers_ShouldReturnListOfUsers() throws Exception {
		// Arrange
		User user1 = new User();
		user1.setId(1L);
		user1.setName("John");
		user1.setAge(30);

		User user2 = new User();
		user2.setId(2L);
		user2.setName("Jane");
		user2.setAge(25);

		when(userService.GetAllUsers()).thenReturn(Arrays.asList(user1, user2));

		// Act & Assert
		mockMvc.perform(get("/api/users"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("John"))
				.andExpect(jsonPath("$[0].age").value(30))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].name").value("Jane"))
				.andExpect(jsonPath("$[1].age").value(25));
	}

	@Test
	public void getUserById_WhenUserExists_ShouldReturnUser() throws Exception {
		// Arrange
		User user = new User();
		user.setId(1L);
		user.setName("John");
		user.setAge(30);

		when(userService.GetUserById(1L)).thenReturn(user);

		// Act & Assert
		mockMvc.perform(get("/api/users/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("John"))
				.andExpect(jsonPath("$.age").value(30));
	}

	@Test
	public void getUserById_WhenUserDoesNotExist_ShouldReturn404() throws Exception {
		// Arrange
		when(userService.GetUserById(1L)).thenReturn(null);

		// Act & Assert
		mockMvc.perform(get("/api/users/1"))
				.andExpect(status().isNotFound());
	}
}