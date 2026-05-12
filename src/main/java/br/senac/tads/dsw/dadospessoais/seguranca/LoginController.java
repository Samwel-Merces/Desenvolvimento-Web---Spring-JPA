package br.senac.tads.dsw.dadospessoais.seguranca;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            // 1. Delega a autenticação ao Spring Security
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), loginDto.getSenha()
                )
            );

            // 2. Extrai o UsuarioSistema do objeto de autenticação.
            //    getPrincipal() retorna o objeto carregado por loadUserByUsername().
            UsuarioSistema usuario = (UsuarioSistema) auth.getPrincipal();

            // 3. Gera o JWT e retorna ao cliente
            String token = jwtService.gerarToken(usuario);
            return ResponseEntity.ok(Map.of("token", token));

        } catch (BadCredentialsException e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("erro", "Usuário ou senha inválidos"));
        }
    }
}