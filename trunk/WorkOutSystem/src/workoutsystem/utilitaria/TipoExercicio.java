package workoutsystem.utilitaria;

public enum TipoExercicio {
	
	Peito("Peito"),
	Costa("Costas"),
	Biceps("Biceps"),
	Triceps("Triceps"),
	Aerobico("Aerobico"),
	Ombro("Ombro"),
	MembrosInferiores("Membros Inferiores"),
	Abdomen("Abdomen");
	
	
	
	private String tipoExercicio;
	
	private TipoExercicio(String t){
		tipoExercicio = t;
	}
	
	public String getTipoExercicio(){
		return tipoExercicio;
	}

}

