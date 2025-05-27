package com.tbr.lettura.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tbr.lettura.model.User;
import com.tbr.lettura.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegisterUserWithMock() {
        User user = new User();
        user.setEmail("mock@email.com");
        user.setPassword("PasswordSicura!1");

        when(userRepository.save(any(User.class))).thenReturn(user);

        boolean result = userService.registerUser(user);
        assertTrue(result);
    }
}