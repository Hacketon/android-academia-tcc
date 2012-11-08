package workoutsystem.model;

public class Exercicio {
	
	private long codigoExercicio;
	private String nomeExercicio;
	private String grupoMuscular;
	private String descricao;
	private boolean personalizado;
	
	public long getCodigoExercicio() {
		return codigoExercicio;
	}
	public String getNomeExercicio() {
		return nomeExercicio;
	}
	public String getGrupoMuscular() {
		return grupoMuscular;
	}
	public String getDescricao() {
		return descricao;
	}
	public boolean isPersonalizado() {
		return personalizado;
	}
	public void setCodigoExercicio(long codigoExercicio) {
		this.codigoExercicio = codigoExercicio;
	}
	public void setNomeExercicio(String nomeExercicio) {
		this.nomeExercicio = nomeExercicio;
	}
	public void setGrupoMuscular(String grupoMuscular) {
		this.grupoMuscular = grupoMuscular;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setPersonalizado(boolean personalizado) {
		this.personalizado = personalizado;
	}
}
