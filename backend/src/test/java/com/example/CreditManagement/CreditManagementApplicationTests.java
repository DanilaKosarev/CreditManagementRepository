package com.example.CreditManagement;

import com.example.CreditManagement.security.JWTFilter;
import com.example.CreditManagement.security.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CreditManagementApplicationTests {

	@MockBean
	private JWTFilter jwtFilter;
	@MockBean
	private JWTUtil jwtUtil;

	@Test
	void contextLoads() {
	}

}
