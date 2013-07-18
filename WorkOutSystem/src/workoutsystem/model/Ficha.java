package workoutsystem.model;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotEmpty;

public class Ficha implements Serializable {
	
	private long codigo;
	@NotEmpty(message = "Nome da ficha é obrigatorio")
	@Length(max=20,
		message = "Nome da ficha deve ser menor que 20 caracteres")
	private String nome;
	@Min(value = 1,
		message="O valor minimo para duração de dias é 1")
	private int duracao;
	private int realizacoes;
	@Length (max = 100,
			message = "O tamanho maximo para o campo objetivo é 100 caracteres")
	private String objetivo;
	private List<Treino> treinos;
	private int atual;
	private int antecedencia;
	
	public Ficha(){
		atual = 0;
		realizacoes = 0;
		antecedencia = 0;
		
	}
	
	public long getCodigo() {
		return codigo;
	}
	public String getNome() {
		return nome;
	}
	public int getDuracao() {
		return duracao;
	}
	public int getRealizacoes() {
		return realizacoes;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public void setCodigo(long codigoFicha) {
		this.codigo = codigoFicha;
	}
	public void setNome(String nomeFicha) {
		this.nome = nomeFicha;
	}
	public void setDuracao(int duracaoDias) {
		this.duracao = duracaoDias;
	}
	public void setRealizacoes(int realizacoes) {
		this.realizacoes = realizacoes;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	
	public void setTreinos(List<Treino> treinos) {
		this.treinos = treinos;
	}
	public List<Treino> getTreinos() {
		return treinos;
	}


	public void setAtual(int atual) {
		this.atual = atual;
	}

	public int getAtual() {
		return atual;
	}

	public void setAntecedencia(int antecedencia) {
		this.antecedencia = antecedencia;
	}

	public int getAntecedencia() {
		return antecedencia;
	}


}
