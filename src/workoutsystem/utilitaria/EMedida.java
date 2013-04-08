package workoutsystem.utilitaria;

public enum EMedida {

	ALTURA(1),
	PESO(2),
	CINTURA(3),
	QUADRIL(4),
	PEITO(6),
	BRACODIREITO(6),
	BRACOESQUERDO(7),
	COXADIREITA(8),
	COXAESQUERDA(9),
	PANTURILHADIREITA(10),
	PANTURILHAESQUERDA(11),
	COSTAS(12);

	private int medida;
	
	private EMedida(int m){
		medida = m; 
	}
	
	public int getMedida(){
		return medida;
	}
	
	
}
