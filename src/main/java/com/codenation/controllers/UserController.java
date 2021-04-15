package com.codenation.controllers;

import com.codenation.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Api(value = "Api Rest Error Manager")
@CrossOrigin(origins = "*")
public class UserController {

    final private UserService userService;

    @GetMapping("/teste")
    @ApiOperation(value = "faz um teste")
    public String teste() {
        return "Olá teste";
    }


}
