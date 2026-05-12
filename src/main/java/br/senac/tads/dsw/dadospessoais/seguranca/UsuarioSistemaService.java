package br.senac.tads.dsw.dadospessoais.seguranca;
import java.util.*;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioSistemaService implements UserDetailsService{

private final Map<String, UserDetails> usuarios;

    public UsuarioSistemaService(PasswordEncoder passwordEncoder){
        this.usuarios = new HashMap<>();
    
        this.usuarios.put("Admin", new UsuarioSistema(
            "admin", passwordEncoder.encode("Abcd%12345"),
        List.of(
            new SimpleGrantedAuthority("ROLE_ADMIN"),
            new SimpleGrantedAuthority("ROLE_GERENTE"),
            new SimpleGrantedAuthority("ROLE_USER")
        )
    ));

        this.usuarios.put("user1",new UsuarioSistema(
            "user2",
            passwordEncoder.encode("Abcd%12345"),
            List.of(
                new SimpleGrantedAuthority("ROLE_USER")
            )
        ));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserDetails usuario = usuarios.get(username);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario não encontrado" + username);
        } 
        return usuario;
    }
    
}
