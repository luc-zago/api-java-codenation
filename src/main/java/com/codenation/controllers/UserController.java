package com.codenation.controllers;

import com.codenation.dtos.UserDTO;
import com.codenation.dtos.UserDTOWithId;
import com.codenation.dtos.UserEmailDTO;
import com.codenation.enums.Authority;
import com.codenation.enums.UserStatus;
import com.codenation.services.UserServiceImpl;
import com.codenation.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codenation.models.User;
import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;

import javax.management.InstanceAlreadyExistsException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Api(value = "Api Rest Error Manager")
@CrossOrigin(origins = "*")
public class UserController {

    final private UserServiceImpl userService;

    private final ModelMapper modelMapper;

    private UserDTO toUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private UserDTOWithId toUserWithId(User user) {
        return modelMapper.map(user, UserDTOWithId.class);
    }

    private UserEmailDTO toUserEmail(User user) { return modelMapper.map(user, UserEmailDTO.class); }

    @PostMapping
    @ApiOperation(value = "Cria um novo usuário")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid User user) throws InstanceAlreadyExistsException {
        User userCreated = userService.register(user);
        if (userCreated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(toUserDTO(userCreated));
        }
    }
    
    @GetMapping
    @ApiOperation(value = "Retorna todos os usuários")
    public ResponseEntity<List<UserDTOWithId>> getAll(
            @RequestParam(value = "email", required = false, defaultValue = "") String email,
            @RequestParam(value = "firstname", required = false, defaultValue = "") String firstName,
            @RequestParam(value = "lastname", required = false, defaultValue = "") String lastName,
            @RequestParam(value = "status", required = false, defaultValue = "active") UserStatus status,
            @RequestParam(value = "order", required = false, defaultValue = "id") String order,
            @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            Pageable pageable
    ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll(email, firstName, lastName,
                status, order, sort, page, size, pageable)
            .stream().map(this::toUserWithId).collect(Collectors.toList()));
    }

    @GetMapping("/emails")
    @ApiOperation(value = "Retorna o email de todos os usuários")
    public ResponseEntity<List<UserEmailDTO>> getAllEmail(
            @RequestParam(value = "email", required = false, defaultValue = "") String email,
            @RequestParam(value = "status", required = false, defaultValue = "active") UserStatus status,
            @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            Pageable pageable
    ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll(email, "", "",
                status, "email", sort, page, size, pageable)
                .stream().map(this::toUserEmail).collect(Collectors.toList()));
    }

    @PutMapping
    @ApiOperation(value = "Atualiza nome, sobrenome e senha de um usuário")
    public ResponseEntity<UserDTO> update(@RequestBody @Valid User user) {
        User updatedUser = userService.update(user);
        return ResponseEntity.status(HttpStatus.OK).body(toUserDTO(updatedUser));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza a autoridade de um usuário pelo 'id' passado via url")
    public ResponseEntity<UserDTO> changeAuthority(@PathVariable("id") Long id,
                                                   @RequestBody Authority authority) {
        User user = userService.changeAuthority(id, authority);
        return ResponseEntity.status(HttpStatus.OK).body(toUserDTO(user));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Transforma um usuário em 'inativo' com base no 'id' passado pela url")
    public ResponseEntity<Response> inactiveById(@PathVariable("id") Long id) {
        userService.inactiveById(id);
        Response response = new Response("Usuário apagado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
