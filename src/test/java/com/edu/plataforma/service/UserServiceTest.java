package com.edu.plataforma.service;

import com.edu.plataforma.model.Role;
import com.edu.plataforma.model.User;
import com.edu.plataforma.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setName("Test User");
        user.setPassword("password");
        user.setRole(Role.USER);
    }

    @Test
    void findAll_ShouldReturnList() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<User> result = userService.findAll();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void findById_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> result = userService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("test@test.com", result.get().getEmail());
    }

    @Test
    void findByEmail_ShouldReturnUser() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        Optional<User> result = userService.findByEmail("test@test.com");
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void save_ShouldReturnSavedUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        User result = userService.save(user);
        assertNotNull(result);
        assertEquals("test@test.com", result.getEmail());
    }

    @Test
    void update_ShouldReturnUpdatedUser() {
        User details = new User();
        details.setName("Updated Name");
        details.setEmail("updated@test.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.update(1L, details);
        assertNotNull(result);
        assertEquals("Updated Name", user.getName());
    }

    @Test
    void delete_ShouldReturnTrueWhenExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        boolean result = userService.delete(1L);
        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
