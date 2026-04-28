package br.senac.tads.dsw.dadospessoais;

// OBS: RuntimeException não obriga a tratar a exceção
// dentro de um try/catch
public class NaoEncontradoException extends RuntimeException {

	public NaoEncontradoException(String mensagem) {
		super(mensagem);
	}

}
