package workoutsystem.model;

import java.util.Date;
import java.util.List;

import android.text.method.DateTimeKeyListener;

public class Medicao {


	private int codigo;
	private double valor;
	private	Date dataMedicao;
	private int codigoMedida;
	private int codigoPerfil;

	public int getCodigo(){
		return codigo;
	}
	public double getValor() {
		return valor;
	}

	public Date getDataMedicao() {
		return dataMedicao;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public void setDataMedicao(Date dataMedicao) {
		this.dataMedicao = dataMedicao;
	}
	public int getCodigoMedida() {
		return codigoMedida;
	}
	public void setCodigoMedida(int codigoMedida) {
		this.codigoMedida = codigoMedida;
	}
	public int getCodigoPerfil() {
		return codigoPerfil;
	}

	public void setCodigo(int codigo){
		this.codigo = codigo;
	}
	public void setCodigoPerfil(int codigoPerfil) {
		this.codigoPerfil = codigoPerfil;
	}
	@Override
	public String toString() {
		String texto = String.format("codigo perfil : %d /n " +
				" codigo : %d " +
				"/n data : %s " +
				"/n valor : %f" +
				" /n ", codigoPerfil,codigo,""+dataMedicao,valor);
		
		return texto;
	}

	

}