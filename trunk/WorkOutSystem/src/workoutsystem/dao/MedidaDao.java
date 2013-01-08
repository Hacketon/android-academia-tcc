package workoutsystem.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import workoutsystem.interfaces.IMedidaDao;
import workoutsystem.model.Medicao;

import android.util.Log;

public class MedidaDao implements IMedidaDao{

	@Override
	public int buscarMedida(String nome, String lado) {
		int codigo =0;
		try{
			Connection con = Banco.conexao();
			String sql = "select codigo from medida where nome = ? and lado= ?;";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setString(1, nome);
			prepare.setString(2, lado);
			ResultSet result = prepare.executeQuery();

			while(result.next()){
				codigo = result.getInt(1);
			}

			prepare.close();
			con.close();

		}catch(SQLException e) {
			Log.e ("SQL",e.getMessage());
		}
		return codigo;
	}

	@Override
	public boolean adicionarMedicao(List<Medicao> medicoes) {
		boolean verificador = false;
		try{
			Connection con = Banco.conexao();
			String sql ="insert into medicao (valor,  codigomedida,codigoperfil,datamedicao) values (?,?,?,?);";
			PreparedStatement prepare = con.prepareStatement(sql);

			for(Medicao m: medicoes){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				prepare.setDouble(1,m.getValor() );
				prepare.setInt(2, m.getCodigoMedida() );
				prepare.setInt(3, m.getCodigoPerfil());
				String s = sdf.format(m.getDataMedicao());  
				prepare.setString(4, s);

			//	Log.i("Dados", m.toString());

				if(prepare.executeUpdate()!= 0){
					verificador = true;
				}else{
					verificador = false;
				}
			}

			prepare.close();
			con.close();
			return verificador;
		}catch (SQLException erro) {
			Log.e ("SQL",erro.getMessage());
			return false;
		}

	}
	
//	public void deleta(){
//		try{
//			Connection con = Banco.conexao();		
//			String sql ="delete from medidcao ;";
//			PreparedStatement prepare = con.prepareStatement(sql);
//      		prepare.executeUpdate();
//			prepare.close();
//			con.close();
//			
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//	}

	public Medicao buscarData(int codigo){
		Medicao medicao = null;
		try{
			Connection con = Banco.conexao();		
			String sql ="select valor,datamedicao from medicao;";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			PreparedStatement prepare = con.prepareStatement(sql);
			//prepare.setInt(1, codigo);
			ResultSet result = prepare.executeQuery();
			while(result.next()){
				Double string2 = result.getDouble(1);
				String string = result.getString(2);
				//Date date = result.getDate(2);
				
				String Resultado = "Valor :" +string2 + "Data String : "+ string;
				
				
				
			}

			prepare.close();
			con.close();
		}catch (Exception e) {
			
			// TODO: handle exceptionma
			
		}
		return medicao;
		
	}
	
	@Override
	public Double buscarValorMedicao(int codigo) {
		Double valor = null;
		try{
			Connection con = Banco.conexao();
			String sql ="select (valor) from medicao where codigomedida = ?;";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setInt(1, codigo);
			ResultSet result = prepare.executeQuery();

			if(result.next()){
				valor = result.getDouble(1);
			}

			prepare.close();
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return valor;
	}


	@Override
	public List<Medicao> buscarMedicao(int codigo) {
		List<Medicao> lista = new ArrayList<Medicao>();


		try{
			Connection con = Banco.conexao();
			String sql ="select valor, datamedicao, codigomedida, codigoperfil " +
			"from medicao where codigoperfil = ?;";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setInt(1, codigo);
			ResultSet result = prepare.executeQuery();

			while(result.next()){
				Medicao medicao = new Medicao();
				medicao.setValor(result.getDouble(1));
				//medicao.setDataMedicao(result.getDate(2));
				medicao.setCodigoMedida(result.getInt(3));
				medicao.setCodigoPerfil(result.getInt(4));
				lista.add(medicao);
			}

			prepare.close();
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return lista;


	}

}
