package br.senac.tads.dsw.dadospessoais.seguranca;

public class LoginDto {
    private String username;
    private String senha;

    public LoginDto(){}



    public String getUsername() {
        return username;
    }

    public String getSenha() {
        return senha;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public void setSenha(String senha) {
        this.senha = senha;
    }

    
    
}
