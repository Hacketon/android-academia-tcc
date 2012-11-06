package workoutsystem.utilitaria;

public enum DiaSemana {
	
	SEGUNDA ("Segunda"),
	TERCA ("Terça"),
	QUARTA ("Quarta"),
	QUINTA ("Quinta"),
	SEXTA ("Sexta"),
	SABADO ("Sabado"),
	DOMINGO("Domingo");
	
	
	private String diasemana;
	
	
	private DiaSemana(String dia){
		diasemana = dia;
	}

	
	public String getDiaSemana(){
		return diasemana;
	}
}
