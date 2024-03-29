package workoutsystem.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import workoutsystem.model.Medicao;
import workoutsystem.model.Medida;

import android.util.Log;

public class MedidaDao implements IMedidaDao{

	@Override
	public int buscarMedida(String nome, String lado) {
		int codigo =0;
		try{
			Connection con = ResourceManager.getConexao();
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
			Connection con = ResourceManager.getConexao();
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
			Connection con = ResourceManager.getConexao();
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
	public List<Medicao> buscarListaMedicao(int codigo,int codigoPerfil) {
		List<Medicao> lista = new ArrayList<Medicao>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try{
			Connection con = ResourceManager.getConexao();
			String sql =" select * from medicao " +
			" where codigomedida = ? and codigoperfil = ? " +
			" order by datamedicao desc;";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setInt(1, codigo);
			prepare.setInt(2,codigoPerfil);
			ResultSet result = prepare.executeQuery();

			while (result.next()){
				Medicao medicao = new Medicao();
				medicao.setCodigo(result.getInt(1));
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


	public List<Medida> buscarMedidas(){
		List<Medida> lista = new ArrayList<Medida>();
		try{
			Connection con = ResourceManager.getConexao();
			String sql = "select codigo, nome, unidade , lado from medida";
			PreparedStatement prepare = con.prepareStatement(sql);
			ResultSet result = prepare.executeQuery();
			while(result.next()){
				Medida medida = new Medida();
				medida.setCodigo(result.getInt(1));
				medida.setNome(result.getString(2));
				medida.setUnidade(result.getString(3));
				medida.setLado(result.getString(4));
				medida.setMedicao(new ArrayList<Medicao>());
				lista.add(medida);
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
			Connection con = ResourceManager.getConexao();
			String sql =" update medicao set valor = ? " +
			" where codigomedida = ? and datamedicao = ?;";
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
			Connection con = ResourceManager.getConexao();
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


	public boolean excluirMedicoes(int codigo){
		try{
			boolean verificador = false;
			Connection con = ResourceManager.getConexao();
			String sql = "delete from medicao where codigoperfil = ?";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setInt(1, codigo);
			int resultado = prepare.executeUpdate();

			if (resultado == 0 ){
				verificador = false;
			}else{
				verificador = true;
			}
			prepare.close();
			con.close();

			return verificador;

		}catch (SQLException e) {
			// TODO: handle exception
			return false;
		}


	}

	@Override
	public boolean verificarMedicao(int codigo) {
		boolean verificador = false;
		try{
			Connection con = ResourceManager.getConexao();
			String sql = "select * from medicao where codigoperfil = ?";
			PreparedStatement prepared = con.prepareStatement(sql);
			prepared.setInt(1, codigo);
			ResultSet result = prepared.executeQuery();

			if (result.next()){
				verificador = true;
			}

			prepared.close();
			con.close();
		}catch (SQLException e) {
			// TODO: handle exception
		}
		return verificador;
	}

	@Override
	public List<Medicao> ultimasMedicoes(int codigoPerfil, int codigoMedida) throws SQLException, ParseException{
		List<Medicao> medicoes = new ArrayList<Medicao>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Connection con = ResourceManager.getConexao();
		String sql = "select codigo,valor,datamedicao from medicao " +
		" where codigoperfil = ? and codigomedida = ? " +
		" order by datamedicao desc limit 3 ";
		PreparedStatement prepared = con.prepareStatement(sql);
		prepared.setInt(1, codigoPerfil);
		prepared.setInt(2, codigoMedida);
		ResultSet result = prepared.executeQuery();

		while (result.next()){
			Medicao m = new Medicao();
			m.setCodigo(result.getInt("codigo"));
			m.setValor(result.getDouble("valor"));
			m.setDataMedicao(sdf.parse(result.getString("datamedicao")));
			m.setCodigoPerfil(codigoPerfil);
			m.setCodigoMedida(codigoMedida);
			medicoes.add(m);
		}

		prepared.close();
		con.close();

		return medicoes;
	}

	@Override
	public List<Medicao> buscarMedicao(int codigo) {
		List<Medicao> lista = new ArrayList<Medicao>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try{
			Connection con = ResourceManager.getConexao();
			String sql ="select codigo, valor, datamedicao, codigomedida, codigoperfil " +
			"from medicao where codigoperfil = ? order by datamedicao desc";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setInt(1, codigo);
			ResultSet result = prepare.executeQuery();

			while (result.next()){
				Medicao medicao = new Medicao();
				medicao.setCodigo(result.getInt(1));
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

	public List<Medida> ultimaMedicao(int codigo) {
		List<Medida> lista = new ArrayList<Medida>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int contador = 1;
		boolean verificador = true;
		String data = null;
		try{
			Connection con = ResourceManager.getConexao();
			String sql = " select medida.codigo as medida_codigo," +
					     "  medida.nome as medida_nome, medida.lado as medida_lado ," +
					     "  medicao.codigo as medicao_codigo, medicao.valor as medicao_valor , " +
					     "  medicao.datamedicao as medicao_data  from medicao inner join medida " +
					     "  on medida.codigo = medicao.codigomedida " +
			             "  where codigoperfil = ? order by datamedicao desc limit 3";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setInt(1, codigo);
			ResultSet result = prepare.executeQuery();

			while(result.next()){
				if (verificador == true){
					data = result.getString("medicao_data");
					verificador = false;
				}

				if (data.equalsIgnoreCase(result.getString("medicao_data"))){
					Medida medida = new Medida();
					Medicao medicao = new Medicao();
					
					medida.setCodigo(result.getInt("medida_codigo"));
					medida.setNome(result.getString("medida_nome"));
					medida.setLado(result.getString("medida_lado"));
					medida.setMedicao(new ArrayList<Medicao>());

					medicao.setCodigo(result.getInt("medicao_codigo"));
					medicao.setValor(result.getDouble("medicao_valor"));
					medicao.setDataMedicao(sdf.parse(data));
					medicao.setCodigoMedida(medida.getCodigo());
					medicao.setCodigoPerfil(codigo);
					medida.getMedicao().add(medicao);
					lista.add(medida);
					
				} else {
					break;
				}
			}
			prepare.close();
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return lista;


	}

	@Override
	public java.util.Date buscarDataUltimaMedicao() throws SQLException, ParseException {
		Connection con = ResourceManager.getConexao(); 
		String sql = "select datamedicao from medicao order by datamedicao desc limit 1";
		PreparedStatement prepare = con.prepareStatement(sql);
		ResultSet result = prepare.executeQuery();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date data = new java.util.Date();
		while (result.next()) {
			data = sdf.parse(result.getString("datamedicao"));
		}
		prepare.close();
		con.close();
		return data;
	}



}
