package workoutsystem.model;

import java.util.Date;
import java.util.List;

public class Medida {

	private String nome;
	private List<Medicao> medicao;
	private Perfil perfil;
	
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setMedicao(List<Medicao> medicao) {
		this.medicao = medicao;
	}
	public List<Medicao> getMedicao() {
		return medicao;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public Perfil getPerfil() {
		return perfil;
	}
}
