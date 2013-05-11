package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.interfaces.IPerfilDao;
import workoutsystem.model.Frequencia;
import workoutsystem.model.Perfil;

public class PerfilDao implements IPerfilDao{

	public Perfil buscarPerfil() {
		Perfil perfil = null;
		try{
			Connection con = ResourceManager.getConexao();
			String sql = "select codigo, nome ,sexo from perfil";
			PreparedStatement prepare = con.prepareStatement(sql);
			ResultSet result = prepare.executeQuery();

			if (result.next()){
				perfil = new Perfil();
				perfil.setCodigo(result.getInt(1));
				perfil.setNome(result.getString(2));
				perfil.setSexo(result.getBoolean(3));
			}

			prepare.close();
			con.close();

		}catch (Exception e) {
			// TODO: handle exception
		}
		return perfil;

	}


	
	public boolean criarPerfil(Perfil perfil) {

		try{
			boolean verificador = false;
			Connection con = ResourceManager.getConexao();
			String sql ="insert into perfil (nome, sexo) values (?,?);";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setString(1, perfil.getNome());
			prepare.setBoolean(2, perfil.getSexo());
			

			int resultado = prepare.executeUpdate();

			if (resultado == 0 ){
				verificador = false;
			}else{
				verificador = true;
			}

			con.close();
			prepare.close();

			return verificador;

		}catch (Exception e) {
			// TODO: handle exception
			return false;

		}

	}

	@Override
	public boolean excluirPerfil() {
		try{
			boolean verificador = false;
			Connection con = ResourceManager.getConexao();
			String sql = "delete from perfil";
			PreparedStatement prepare = con.prepareStatement(sql);
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
	public boolean atualizarPerfil(Perfil perfil) {
		boolean verificador = true;
		try{
			Connection con = ResourceManager.getConexao();
			String sql = "update Perfil set nome = ?,sexo = ?";
			
			PreparedStatement prepare = con.prepareStatement(sql);

			prepare.setString(1, perfil.getNome());
			prepare.setBoolean(2, perfil.getSexo());
			int atualizados =prepare.executeUpdate();
			if (atualizados >0){
				verificador = true;
			}else{
				verificador = false;

			}
			prepare.close();
			con.close();
		}catch (SQLException e) {
			verificador = false;
		}
		return verificador;

	}

	@Override
	public boolean frequenciaPerfil(Perfil perfil) {

		PreparedStatement prepare = null;
		String sql = "";
		try{
			Connection con = ResourceManager.getConexao();
			sql = "delete from frequenciaperfil where codigoperfil = ?";
			prepare = con.prepareStatement(sql);
			prepare.setInt(1, perfil.getCodigo());
			prepare.executeUpdate();


			for (Frequencia d: perfil.getFrequencia()){

				sql = "insert into frequenciaperfil (codigodia,codigoperfil) values (?,?)";
				prepare = con.prepareStatement(sql);
				prepare.setInt(1,d.getCodigo());
				prepare.setInt(2, perfil.getCodigo());
				prepare.executeUpdate();
			}
			prepare.close();
			con.close();

			return true;

		}catch (SQLException e) {
			return false;
		}

	}
	public boolean excluirFrequencia(Perfil perfil){
		PreparedStatement prepare = null;
		String sql = "";
		try{
			Connection con = ResourceManager.getConexao();
			sql = "delete from frequenciaperfil where codigoperfil = ?";
			prepare = con.prepareStatement(sql);
			prepare.setInt(1, perfil.getCodigo());
			prepare.executeUpdate();
			prepare.close();
			con.close();

			return true;

		}catch (SQLException e) {
			return false;
		}


	}


	@Override
	public List<Frequencia> buscarFrequencia(Perfil perfil) {
		List<Frequencia> dias = new ArrayList<Frequencia>(); 


		try{
			Connection con = ResourceManager.getConexao();
			String sql = "select codigodia,codigoperfil from frequenciaPerfil where codigoperfil = ?";
			PreparedStatement prepared = con.prepareStatement(sql );
			prepared.setInt(1, perfil.getCodigo());
			ResultSet result = prepared.executeQuery();
			while (result.next()){
				Frequencia dia = new Frequencia();
				dia.setCodigo(result.getInt(1));
				perfil.setCodigo(result.getInt(2));
				dias.add(dia);
			}

			prepared.close();
			con.close();

		}catch (SQLException e) {
			// TODO: handle exception
		}
		return dias;
	}


	

	@Override
	public int quantidadeDias(Perfil perfil) {
		int quantidade = 0;
		try{
			Connection con = ResourceManager.getConexao();
			String sql = " select count (*) codigodia from frequenciaPerfil where codigoperfil = ?;";
			PreparedStatement prepared = con.prepareStatement(sql);
			prepared.setInt(1, perfil.getCodigo());
			ResultSet result = prepared.executeQuery();
			
			while(result.next()){
				quantidade = result.getInt(1);
			}

		}catch (SQLException e) {
			// TODO: handle exception
		}
		return quantidade;
	}



	@Override
	public int buscarUltimoPerfil() throws SQLException {
		Connection con = ResourceManager.getConexao();
		int retorno = 0;
		String sql = "select max(codigo) as " +
				" perfil_max_codigo from perfil ";
		PreparedStatement prepare = con.prepareStatement(sql);
		ResultSet result = prepare.executeQuery();
		if(result.next()){
			retorno = result.getInt("perfil_max_codigo");
		}
				
		return retorno;
	}



	
}
