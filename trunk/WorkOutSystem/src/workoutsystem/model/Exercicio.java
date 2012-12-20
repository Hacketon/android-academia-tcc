package workoutsystem.model;

import java.io.Serializable;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercicio implements Serializable, Parcelable {
	
	private long codigo;
	private String nomeExercicio;
	private GrupoMuscular grupo;
	private String descricao;
	private int personalizado;
	private List<Passo> listaPassos;
	
	public Exercicio(){
		descricao = "";
		personalizado = 1;
	}

	public String toString(){
		
		return String.format("codigo : %s \n , nome : %s \n " +
				", descricao: %s \n , persolanizado: %s \n",
				codigo  , nomeExercicio, descricao,personalizado);
	
	}
	
	public long getCodigo() {
		return codigo;
	}
	public String getNomeExercicio() {
		return nomeExercicio;
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
		this.nomeExercicio = nomeExercicio;
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
}
