package br.senac.tads.dsw.dadospessoais;
import java.util.*;


public interface PessoaService {

    List<PessoaDto> obterPessoas();

    Optional<PessoaDto> obterPessoa(String username);

    PessoaDto incluirNovaPessoa(PessoaDto pessoa);

    PessoaDto alterarPessoa(String username, PessoaAlteracaoDto pessoaAlteracao);

    void removerPessoa(String username);
    
    List<PessoaDto> buscarPessoas(String termo);

    
}
