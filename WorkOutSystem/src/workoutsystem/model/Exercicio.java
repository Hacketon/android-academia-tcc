package workoutsystem.model;

import java.util.List;

public class Exercicio {
	
	private long codigo;
	private String nomeExercicio;
	private GrupoMuscular grupo;
	private String descricao;
	private int personalizado;
	private List<Passo> listaPassos;
	
	public Exercicio(){
		descricao = "";
		personalizado = 1;
	}

	public String toString(){
		
		return String.format("codigo : %s \n , nome : %s \n " +
				", descricao: %s \n , persolanizado: %s \n",
				codigo  , nomeExercicio, descricao,personalizado);
	
	}
	
	public long getCodigo() {
		return codigo;
	}
	public String getNomeExercicio() {
		return nomeExercicio;
	}
	public GrupoMuscular getGrupoMuscular() {
		return grupo;
	}
	public String getDescricao() {
		return descricao;
	}
	/*
	 *public int getPersonalizado(){
		if (personalizado = true){
			return 1;
		}else{
			return 0;
		}
	} 
	 */
	
	public int getPersonalizado() {
		return personalizado;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public void setNomeExercicio(String nomeExercicio) {
		this.nomeExercicio = nomeExercicio;
	}
	public void setGrupoMuscular(GrupoMuscular grupo) {
		this.grupo = grupo;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setPersonalizado(int personalizado) {
		this.personalizado = personalizado;
	}

	public void setListaPassos(List<Passo> listaPassos) {
		this.listaPassos = listaPassos;
	}

	public List<Passo> getListaPassos() {
		return listaPassos;
	}
}
