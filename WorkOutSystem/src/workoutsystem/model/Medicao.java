package workoutsystem.model;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.text.method.DateTimeKeyListener;

public class Medicao implements Comparator<Medicao> {


	private int codigo;
	private double valor;
	private	Date dataMedicao;
	private int codigoMedida;
	private int codigoPerfil;


	public Medicao(){
		valor = 0;
	}
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
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dataString = sdf.format(dataMedicao);
		String texto = String.format("Codigo perfil : %d \n " +
				" Codigo : %d " +
				"\n Data : %s " +
				"\n Valor : %f" +
				" \n ", codigoPerfil,codigo,""+dataString,valor);

		return texto;
	}
	@Override
	public int compare(Medicao m1, Medicao m2) {
		int retorno = 0;
		if(m1.getValor()>m2.getValor()){
			retorno = 1;
		}else if (m1.getValor() == m2.getValor()){
			retorno = 0;
		}else if(m1.getValor() < m2.getValor()){
			retorno = -1;
		}
			
		return retorno;
	}



	

}
