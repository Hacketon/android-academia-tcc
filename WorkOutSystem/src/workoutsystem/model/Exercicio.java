package workoutsystem.model;

public class Exercicio {
	
	private long codigo;
	private String nomeExercicio;
	private GrupoMuscular grupo;
	private String descricao;
	private boolean personalizado;
	
	public Exercicio(){
		descricao = "";
		personalizado = true;
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
	
	public boolean isPersonalizado() {
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
	public void setPersonalizado(boolean personalizado) {
		this.personalizado = personalizado;
	}
}
