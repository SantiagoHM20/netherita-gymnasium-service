package eci.edu.cvds.ECIBienestarGym.controller;

import eci.edu.cvds.ECIBienestarGym.service.UserService;
import org.junit.jupiter.api.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag("User")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
