package br.senac.tads.dsw.dadospessoais.seguranca;
import java.util.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;


public class UsuarioSistema implements UserDetails {
    
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public UsuarioSistema(String username, String password, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities; 
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }
    

    @Override public boolean isAccountNonExpired (){return true;}
    @Override public boolean isAccountNonLocked (){{return true;}}
    @Override public boolean isCredentialsNonExpired (){return true;}
    @Override public boolean isEnabled() {return true;}

    
}
