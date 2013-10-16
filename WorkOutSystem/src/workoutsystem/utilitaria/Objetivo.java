package workoutsystem.utilitaria;

public enum Objetivo {
	
	PERCAGORDURA("Perda de Gordura"),
	GANHOMASSA("Ganho de Massa Muscular"),
	DEFINICAOMUSCULAR("Defini��o muscular"),
	GANHOPESO("Ganho de Peso"),
	REABILITACAO("Reabilita��o"),
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
