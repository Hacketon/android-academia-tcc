package workoutsystem.model;

import java.io.Serializable;
import java.util.List;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("serial")
public class Exercicio implements Serializable {
	
	private long codigo;
	@NotEmpty(message="Nome obrigatorio")
	@Length(max=20,message="Nome deve ser menor que ou igual a 20 caracteres")
	private String nome;
	private Grupo grupo;
	@Length(max=20,message="Descricao deve ser menor que ou igual a 20 caracteres")
	private String descricao;
	private int ativo;
	private int padrao;
	private List<Passo> listaPassos;
	
	public Exercicio(){
		descricao = "";
		padrao = 1;
		ativo = 1;
	}
	
	
	public String toString(){
		
		return String.format("codigo : %s \n , nome : %s \n " +
				", descricao: %s \n , persolanizado: %s \n",
				codigo  , nome, descricao,padrao);
	
	}

	public int getAtivo(){
		return ativo;
	}
	public void setAtivo(int a){
		ativo = a;
	}
	public long getCodigo() {
		return codigo;
	}
	public String getNome() {
		return nome;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public String getDescricao() {
		return descricao;
	}
		
	public int getPadrao() {
		return padrao;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public void setNome(String nomeExercicio) {
		nome = nomeExercicio;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setPadrao(int padrao) {
		this.padrao = padrao;
	}

	public void setListaPassos(List<Passo> listaPassos) {
		this.listaPassos = listaPassos;
	}

	public List<Passo> getListaPassos() {
		return listaPassos;
	}

	
}