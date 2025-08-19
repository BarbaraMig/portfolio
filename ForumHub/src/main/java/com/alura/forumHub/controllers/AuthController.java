package com.alura.forumHub.controllers;

import com.alura.forumHub.dtos.DadosAuth;
import com.alura.forumHub.dtos.DadosTokenJWT;
import com.alura.forumHub.entities.Usuario;
import com.alura.forumHub.infra.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DadosAuth dadosAuth){
        var AuthToken = new UsernamePasswordAuthenticationToken(dadosAuth.email(),dadosAuth.senha());

        var authentication = manager.authenticate(AuthToken);
        var tokenJWT= tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
