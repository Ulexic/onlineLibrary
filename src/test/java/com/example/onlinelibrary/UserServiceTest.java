package com.example.onlinelibrary;

import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.model.UserDTO;
import com.example.onlinelibrary.repository.UserRepository;
import com.example.onlinelibrary.service.UserService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class UserServiceTest {
    @Resource
    private UserRepository repository;
    @Resource
    private UserService service;

    @Test
    void testRegister() {
        repository.deleteAll();
        UserDTO userDTO = new UserDTO("test", "Test@huq.fr", 1L, "test");
        UserDTO user = service.register(userDTO);
        assertNotNull(user);
    }

    @Test
    void testRegisterInvalidEmail() {
        repository.deleteAll();
        UserDTO userDTO = new UserDTO("test", "Test", 1L, "test");
        assertThrows(IllegalArgumentException.class, () -> {service.register(userDTO);});
    }

    @Test
    void testLogin() {
        repository.deleteAll();
        UserDTO userDTO = new UserDTO("test", "Test@huq.fr", 1L, "test");
        service.register(userDTO);
        UserDTO user = service.login(userDTO);
        assertNotNull(user);
    }

    @Test
    void testLoginInvalidEmail() {
        repository.deleteAll();
        service.register(new UserDTO("pass", "teswqwt@hwdqwds.fd", 1L, "test"));
        UserDTO userDTO = new UserDTO("pass", "test@hwdqwds.fd", 1L, "test");
        assertThrows(IllegalArgumentException.class, () -> {service.login(userDTO);});
    }

    @Test
    void testLoginInvalidPassword() {
        repository.deleteAll();
        service.register(new UserDTO("pass", "test@hwdqwds.fd", 1L, "test"));
        UserDTO userDTO = new UserDTO("test", "test@hwdqwds.fd", 1L, "test");
        assertThrows(IllegalArgumentException.class, () -> {service.login(userDTO);});

    }

    @Test
    void testUpdate() {
        repository.deleteAll();
        UserDTO userDTO = new UserDTO("test", "test@hws.fd", 1L, "test");
        UserDTO registeredUser = service.register(userDTO);
        userDTO.setUsername("test2");
        UserDTO user = service.update(userDTO, registeredUser.getId());
        assertEquals("test2", user.getUsername());
    }

    @Test
    void testUpdateInvalidEmail() {
        repository.deleteAll();
        UserDTO userDTO = new UserDTO("test", "test@hws.fd", 1L, "test");
        userDTO = service.register(userDTO);
        userDTO.setEmail("test2");
        UserDTO finalUserDTO = userDTO;
        assertThrows(IllegalArgumentException.class, () -> {service.update(finalUserDTO, finalUserDTO.getId());});
    }

    @Test
    void testUpdateNoUser() {
        repository.deleteAll();
        UserDTO userDTO = new UserDTO("test", "test@hws.fd", 1L, "test");
        assertThrows(IllegalArgumentException.class, () -> {service.update(userDTO, 1L);});

    }

    @Test
    void testGet() {
        repository.deleteAll();
        UserDTO userDTO = new UserDTO("test", "test@hws.fd", 1L, "test");
        userDTO = service.register(userDTO);
        UserDTO user = service.get(userDTO.getId());
        assertNotNull(user);
    }

    @Test
    void testGetNoUser() {
        repository.deleteAll();
        assertThrows(IllegalArgumentException.class, () -> {service.get(1L);});
    }

    @Test
    void testDelete() {
        repository.deleteAll();
        UserDTO userDTO = new UserDTO("test", "test@hws.fd", 1L, "test");
        userDTO = service.register(userDTO);
        service.delete(userDTO.getId());
        UserDTO finalUserDTO = userDTO;
        assertThrows(IllegalArgumentException.class, () -> {service.get(finalUserDTO.getId());});
    }

    @Test
    void testDeleteNoUser() {
        repository.deleteAll();
        assertThrows(IllegalArgumentException.class, () -> {service.delete(1L);});
    }
}