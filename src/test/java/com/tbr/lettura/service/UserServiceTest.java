package com.tbr.lettura.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tbr.lettura.model.Users;
import com.tbr.lettura.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegisterUserWithMock() {
        Users user = new Users();
        user.setEmail("mock@email.com");
        user.setPassword_hash("PasswordSicura!1");

        when(userRepository.save(any(Users.class))).thenReturn(user);

        boolean result = userService.registerUser(user);
        assertTrue(result);
    }
}