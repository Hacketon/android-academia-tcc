package workoutsystem.model;

import java.util.Date;
import java.util.List;

public class Medicao {
	

	private double valor;
	private	Date dataMedicao;
	private int codigoMedida;
	private int codigoUsuario;
	private int codigoPerfil;
	
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
	public int getCodigoUsuario() {
		return codigoUsuario;
	}
	public void setCodigoUsuario(int codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}
	public int getCodigoPerfil() {
		return codigoPerfil;
	}
	public void setCodigoPerfil(int codigoPerfil) {
		this.codigoPerfil = codigoPerfil;
	}
	
		

}
