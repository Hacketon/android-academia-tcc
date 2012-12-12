package workoutsystem.model;

import java.util.Date;
import java.util.List;

public class Medicao {
	

	private double valor;
	private	Date dataMedicao;
	private Medida codigoMedida;
	private Usuario codigoUsuario;
	private Perfil codigoPerfil;
	
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
	public Medida getCodigoMedida() {
		return codigoMedida;
	}
	public void setCodigoMedida(Medida codigoMedida) {
		this.codigoMedida = codigoMedida;
	}
	public Usuario getCodigoUsuario() {
		return codigoUsuario;
	}
	public void setCodigoUsuario(Usuario codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}
	public Perfil getCodigoPerfil() {
		return codigoPerfil;
	}
	public void setCodigoPerfil(Perfil codigoPerfil) {
		this.codigoPerfil = codigoPerfil;
	}
	
		

}
