package br.senac.tads.dsw.dadospessoais;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class PessoaService {

    private AtomicInteger contador = new AtomicInteger(0);

    private Map<String, PessoaDto> mapPessoas = new ConcurrentHashMap<>();

	@PostConstruct
	public void init() {
		var mapTemp = Map.ofEntries(
        Map.entry("fulano", new PessoaDto(contador.incrementAndGet(),
            "fulano", "Fulano da Silva",
            "fulano@email.com", LocalDate.parse("2000-10-20"))),
        Map.entry("ciclano", new PessoaDto(contador.incrementAndGet(),
            "ciclano", "Ciclano de Souza",
            "ciclano@email.com", LocalDate.parse("1999-05-10"))),
        Map.entry("beltrana", new PessoaDto(contador.incrementAndGet(),
            "beltrana", "Beltrana dos Santos",
            "beltrana@email.com", LocalDate.parse("2001-02-23")))
    	);
		mapPessoas.putAll(mapTemp);
	}

    public List<PessoaDto> obterPessoas() {
        return new ArrayList<>(mapPessoas.values());
    }

    public Optional<PessoaDto> obterPessoa(String username) {
        return Optional.ofNullable(mapPessoas.get(username));
    }

	public PessoaDto incluirNovaPessoa(PessoaDto pessoa) {
		pessoa.setId(contador.incrementAndGet());
		mapPessoas.put(pessoa.getUsername(), pessoa);
		return pessoa;
	}

	public PessoaDto alterarPessoa(String username, PessoaAlteracaoDto pessoaAlteracao) {
		if (!mapPessoas.containsKey(username)) {
			// Lanço erro
			throw new NaoEncontradoException("Pessoa " + username + " não encontrada");
		}
		PessoaDto pessoaOriginal = mapPessoas.get(username);
		pessoaOriginal.setNome(pessoaAlteracao.getNome());
		pessoaOriginal.setEmail(pessoaAlteracao.getEmail());
		pessoaOriginal.setDataNascimento(pessoaAlteracao.getDataNascimento());
		pessoaOriginal.setConhecimentos(pessoaAlteracao.getConhecimentos());
		return pessoaOriginal;
	}

	public void removerPessoa(String username) {
		if (!mapPessoas.containsKey(username)) {
			// Lanço erro
			throw new NaoEncontradoException("Pessoa " + username + " não encontrada");
		}
		mapPessoas.remove(username);
	}
}
