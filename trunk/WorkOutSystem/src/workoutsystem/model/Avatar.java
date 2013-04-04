
package workoutsystem.model;

import org.hibernate.validator.constraints.NotEmpty;




public class Avatar {
	@NotEmpty
	private String nomeAvatar;
	private int imagem;
	private String mensagem;
	private String nivel;
	
	
	public String getNomeAvatar() {
		return nomeAvatar;
	}
	public int getImagem() {
		return imagem;
	}
	public String getMensagem() {
		return mensagem;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNomeAvatar(String nomeAvatar) {
		this.nomeAvatar = nomeAvatar;
	}
	public void setImagem(int imagem) {
		this.imagem = imagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

}
