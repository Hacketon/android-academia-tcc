package workoutsystem.utilitaria;

public enum Unidade {
	
	REPETICAO("Repetições"),
	SEGUNDO("Segundos"),
	MINUTO("Minuto(s)"),
	HORAS("Hora(s)");
	
	private Unidade(String s ){
		unidade = s;
	}
	
	private String unidade;
	
	public String getUnidade(){
		return unidade;
	}
	
	

}
