package br.senac.tads.dsw.dadospessoais.seguranca;

import java.util.*;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.Instant;
import java.util.jar.JarException;
import javax.management.RuntimeErrorException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiracao-segundos:3600}")
    private long expiracaoSegundos;

    public String gerarToken(UsuarioSistema usuario){
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : usuario.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        Instant agora = Instant.now();
        Instant expiracao = agora.plusSeconds(expiracaoSegundos);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
        .subject(usuario.getUsername())
        .issuer("dados-pessoais-api")
        .issueTime(Date.from(agora))
        .expirationTime(Date.from(expiracao))
        .claim("roles", roles)
        .build();
        return jwtEncode(claims);
    }

    private String jwtEncode(JWTClaimsSet claims) {
        try {
            byte[] keyBytes = MessageDigest.getInstance("SHA-256")
                .digest(jwtSecret.getBytes(StandardCharsets.UTF_8));
                MACSigner signer = new MACSigner(keyBytes);

                SignedJWT jwt  = new SignedJWT(
                    new com.nimbusds.jose.JWSHeader(JWSAlgorithm.HS256),
                    claims
                );

                jwt.sign(signer);
                return jwt.serialize();


        }   catch (JOSEException | NoSuchAlgorithmException e){
                throw new RuntimeException("Erro ao Assinar token JWT", e);
        }
    }
}
