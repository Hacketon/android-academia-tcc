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
	private GrupoMuscular grupo;
	@Length(max=20,message="Descricao deve ser menor que ou igual a 20 caracteres")
	private String descricao;
	private int ativo;
	private int personalizado;
	private List<Passo> listaPassos;
	
	public Exercicio(){
		descricao = "";
		personalizado = 1;
		ativo = 1;
	}
	
	
	public String toString(){
		
		return String.format("codigo : %s \n , nome : %s \n " +
				", descricao: %s \n , persolanizado: %s \n",
				codigo  , nome, descricao,personalizado);
	
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
	public String getNomeExercicio() {
		return nome;
	}
	public GrupoMuscular getGrupoMuscular() {
		return grupo;
	}
	public String getDescricao() {
		return descricao;
	}
	
		
	public int getPersonalizado() {
		return personalizado;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public void setNomeExercicio(String nomeExercicio) {
		nome = nomeExercicio;
	}
	public void setGrupoMuscular(GrupoMuscular grupo) {
		this.grupo = grupo;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setPersonalizado(int personalizado) {
		this.personalizado = personalizado;
	}

	public void setListaPassos(List<Passo> listaPassos) {
		this.listaPassos = listaPassos;
	}

	public List<Passo> getListaPassos() {
		return listaPassos;
	}
}