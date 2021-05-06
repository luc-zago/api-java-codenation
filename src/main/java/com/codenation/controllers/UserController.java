package com.codenation.controllers;

import com.codenation.dtos.UserDTO;
import com.codenation.dtos.UserDTOWithId;
import com.codenation.dtos.UserEmailDTO;
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
    @ApiOperation(value = "Retorna todos os usuários ativos. O retorno pode ser filtrado e ordenado de " +
            "acordo com os seguintes parâmetros que podem ser passados via url: a) email: email do usuário; " +
            "b) firstname: primeiro nome do usuário; c) lastname: sobrenome do usuário; d) status: status do " +
            "usuário (active ou inactive); Para retornar os usuários inativos, usar o parâmetro 'status' com o " +
            "valor 'inactive'. Os usuários também podem ser ordenados por qualquer um dos atributos " +
            "mencionados através do parâmetro 'order', que também é passado via url e recebe como valor o " +
            "nome do atributo que deve ser tomado como referência (Ex: email, firstname, lastname). Se " +
            "nenhum parâmetro é passado, o retorno é ordenado pelo atributo 'id' em ordem ascendente. Para " +
            "alterar o retorno para ordem descendente, deve ser utilizado o parâmetro 'sort' via url com o " +
            "valor 'desc'.")
    public ResponseEntity<List<UserDTOWithId>> getAll(
            @RequestParam(value = "email", required = false, defaultValue = "") String email,
            @RequestParam(value = "firstname", required = false, defaultValue = "") String firstName,
            @RequestParam(value = "lastname", required = false, defaultValue = "") String lastName,
            @RequestParam(value = "status", required = false, defaultValue = "active") String status,
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
    @ApiOperation(value = "Retorna o email de todos os usuários ativos. O retorno pode ser filtrado e " +
            "ordenado de acordo com os seguintes parâmetros que podem ser passados via url: a) email: email " +
            "do usuário; b) firstname: primeiro nome do usuário; c) lastname: sobrenome do usuário; Para " +
            "retornar os usuários inativos, usar o parâmetro 'status' com o valor 'inactive'. Os " +
            "usuários também podem ser ordenados por qualquer um dos atributos mencionados através do " +
            "parâmetro 'order', que também é passado via url e recebe como valor o nome do atributo que " +
            "deve ser tomado como referência (Ex: email, firstname, lastname). Se nenhum parâmetro é passado," +
            " o retorno é ordenado pelo atributo 'id' em ordem ascendente. Para alterar o retorno para ordem " +
            "descendente, deve ser utilizado o parâmetro 'sort' via url com o valor 'desc'.")
    public ResponseEntity<List<UserEmailDTO>> getAllEmail(
            @RequestParam(value = "email", required = false, defaultValue = "") String email,
            @RequestParam(value = "status", required = false, defaultValue = "active") String status,
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
    @ApiOperation(value = "Altera a autoridade de um usuário identificado pelo 'id' passado via url " +
            " com base no 'body' enviado na requisição com a chave 'authority' e os valores 'USER' ou 'ADMIN'. " +
            "Os valores obrigatoriamente devem estar em caixa alta.")
    public ResponseEntity<UserDTO> changeAuthority(@PathVariable("id") Long id,
                                                   @RequestBody User user) {
        User tUser = userService.changeAuthority(id, user.getAuthority());
        return ResponseEntity.status(HttpStatus.OK).body(toUserDTO(tUser));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Transforma um usuário em 'inativo' com base no 'id' passado pela url")
    public ResponseEntity<Response> inactiveById(@PathVariable("id") Long id) {
        userService.inactiveById(id);
        Response response = new Response("Usuário apagado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
