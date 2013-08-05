package workoutsystem.utilitaria;

public enum Dias {
	
	SEGUNDA (1) ,
	TERCA (2),
	QUARTA (3 ), 
	QUINTA (4 ),
	SEXTA (5),
	SABADO (6 ), 
	DOMINGO (7);
	
	private int ndia;
	private Dias (int n){
		ndia = n;
	}
	
	public int getDia(){
		return ndia;
	}

}
