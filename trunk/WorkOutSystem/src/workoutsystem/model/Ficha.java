package workoutsystem.model;

import java.util.List;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotEmpty;

public class Ficha {
	
	private long codigoFicha;
	@NotEmpty(message = "Nome da ficha é obrigatorio")
	@Length(max=20,message = "Nome da ficha deve ser menor que 20 caracteres")
	private String nomeFicha;
	@Min(value = 1,message="O valor minimo para duração de dias é 1")
	private int duracaoDias;
	private int realizacoes;
	@Length (max = 100, message = "O tamanho maximo para o campo objetivo é 100 caracteres")
	private String objetivo;
	private List<Treino> treinos;
	private int padrao;
	private int codigoPerfil;
	private int atual;
	
	public Ficha(){
		padrao = 1;
		atual = 0;
		realizacoes = 0;
		
	}
	
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
	public void setTreinos(List<Treino> treinos) {
		this.treinos = treinos;
	}
	public List<Treino> getTreinos() {
		return treinos;
	}

	public int getPadrao() {
		return padrao;
	}

	public void setPadrao(int padrao) {
		this.padrao = padrao;
	}

	public void setCodigoPerfil(int codigoPerfil) {
		this.codigoPerfil = codigoPerfil;
	}

	public int getCodigoPerfil() {
		return codigoPerfil;
	}

	public void setAtual(int atual) {
		this.atual = atual;
	}

	public int getAtual() {
		return atual;
	}


}
