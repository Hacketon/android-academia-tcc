package workoutsystem.utilitaria;

public enum Objetivo {
	
	PERDAGORDURA("Perda de Gordura"),
	GANHOMASSA("Ganho de Massa Muscular"),
	DEFINICAOMUSCULAR("Definição muscular"),
	GANHOPESO("Ganho de Peso"),
	REABILITACAO("Reabilitação"),
	CONDICIONAMENTO("Condicionamento Fisico"),
	FLEXIBILIDADE("Flexibilidade");
	
	private Objetivo(String o){
		objetivo = o;
	}
	private String objetivo;
	
	public String getObjetivo(){
		return objetivo;
		
	}
	

}
