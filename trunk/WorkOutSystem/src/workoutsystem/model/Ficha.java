package workoutsystem.model;

public class Ficha {
	
	private long codigoFicha;
	private String nomeFicha;
	private int duracaoDias;
	private int realizacoes;
	private String objetivo;
	
	public long getCodigoFicha() {
		return codigoFicha;
	}
	public String getNomeFicha() {
		return nomeFicha;
	}
	public int getDuracaoDias() {
		return duracaoDias;
	}
	public int getRealizacoes() {
		return realizacoes;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public void setCodigoFicha(long codigoFicha) {
		this.codigoFicha = codigoFicha;
	}
	public void setNomeFicha(String nomeFicha) {
		this.nomeFicha = nomeFicha;
	}
	public void setDuracaoDias(int duracaoDias) {
		this.duracaoDias = duracaoDias;
	}
	public void setRealizacoes(int realizacoes) {
		this.realizacoes = realizacoes;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	
	public int calcularDiasRestantes(){
		return 0;
	}
}
