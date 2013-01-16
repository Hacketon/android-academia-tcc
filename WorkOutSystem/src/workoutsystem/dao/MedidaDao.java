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
import workoutsystem.model.Medida;

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
	
	public List<String> buscarMedidas(){
		List<String> lista = new ArrayList<String>();
		
		try{
			Connection con = Banco.conexao();
			//criar campo descrição no banco para pegar o nome detalhado exemplo: Coxa Esquerda
			String sql = "select nome from medida";
			PreparedStatement prepare = con.prepareStatement(sql);
			ResultSet result = prepare.executeQuery();
			
			while(result.next()){
				Medida medida = new Medida();
				medida.setNome(result.getString(1));
				lista.add(medida.getNome());
			}

			prepare.close();
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return lista;
	}

	@Override
	public List<Medicao> buscarMedicao(int codigo) {
		List<Medicao> lista = new ArrayList<Medicao>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try{
			Connection con = Banco.conexao();
			String sql ="select codigo, valor, datamedicao, codigomedida, codigoperfil " +
			"from medicao where codigoperfil = ?;";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setInt(1, codigo);
			ResultSet result = prepare.executeQuery();

			while(result.next()){
				Medicao medicao = new Medicao();
				medicao.setCodigio(result.getInt(1));
				medicao.setValor(result.getDouble(2));
				String data =  result.getString(3);
				
				medicao.setDataMedicao(sdf.parse(data));
				medicao.setCodigoMedida(result.getInt(4));
				medicao.setCodigoPerfil(result.getInt(5));
				lista.add(medicao);
			}

			prepare.close();
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return lista;


	}

	public boolean alterarMedicao(List<Medicao> medicoes){

		boolean verificador = false;
		try{
			Connection con = Banco.conexao();
			String sql =" update medicao set valor = ? where codigomedida = ? and datamedicao = ?;";
			PreparedStatement prepare = con.prepareStatement(sql);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			for(Medicao m: medicoes){

				prepare.setDouble(1,m.getValor());
				prepare.setInt(2,m.getCodigoMedida());

				String s = sdf.format(m.getDataMedicao());  
				prepare.setString(3, s);

				int atualizados = prepare.executeUpdate();
				if (atualizados > 0){
					verificador = true;
				}else{
					verificador = false;

				}
				prepare.close();
				con.close();
			}
		}catch (SQLException e) 
		{
			verificador = false;
		}
		return verificador;


	}
	
	public boolean alterarUltimaMedicao(List<Medicao> medicao){

		boolean verificador = false;
		try{
			Connection con = Banco.conexao();
			String sql =" update medicao set valor = ? where codigo = ?;";
			PreparedStatement prepare = con.prepareStatement(sql);

			for(Medicao m : medicao){
				
				prepare.setDouble(1,m.getValor());
				prepare.setInt(2, m.getCodigo());
				
				int atualizados = prepare.executeUpdate();
				
			
				if (atualizados > 0){
					verificador = true;
				}else{
					verificador = false;

			}
				
			}
			prepare.close();
			con.close();
		}catch (SQLException e) 
		{
			verificador = false;
		}
		return verificador;


	}


	
}
