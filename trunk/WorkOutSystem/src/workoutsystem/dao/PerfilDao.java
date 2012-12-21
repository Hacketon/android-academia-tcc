package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.interfaces.IPerfilDao;
import workoutsystem.model.DiaSemana;
import workoutsystem.model.Perfil;
import workoutsystem.model.Usuario;

public class PerfilDao implements IPerfilDao{

	public Perfil buscarPerfil(Usuario u) {
		Perfil perfil = null;
		try{
			Connection con = Banco.conexao();
			String sql = "select codigo, nome ,sexo from perfil where codigousuario = ?";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setInt(1, u.getCodigo());
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


	@Override
	public boolean criarPerfil(Perfil perfil,Usuario usuario) {

		try{
			boolean verificador = false;
			Connection con = Banco.conexao();
			String sql ="insert into perfil (nome, sexo, codigousuario) values (?,?,?);";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setString(1, perfil.getNome());
			prepare.setBoolean(2, perfil.getSexo());
			prepare.setInt(3, usuario.getCodigo());

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
	public boolean excluirPerfil(int codigoUsuario) {
		try{
			boolean verificador = false;
			Connection con = Banco.conexao();
			String sql = "delete from perfil where codigousuario = ?";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setInt(1, codigoUsuario);
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
	public boolean atualizarPerfil(Perfil perfil,Usuario usuario) {
		boolean verificador = true;
		try{
			Connection con = Banco.conexao();
			String sql = "update Perfil set nome = ?,sexo = ?" +
			"where codigousuario =?";
			PreparedStatement prepare = con.prepareStatement(sql);
			
			prepare.setString(1, perfil.getNome());
			prepare.setBoolean(2, perfil.getSexo());
			prepare.setInt(3, usuario.getCodigo());
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
			Connection con = Banco.conexao();
			sql = "delete from frequenciaperfil where codigoperfil = ?";
			prepare = con.prepareStatement(sql);
			prepare.setInt(1, perfil.getCodigo());
			prepare.executeUpdate();

			
			for (DiaSemana d: perfil.getFrequencia()){

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


	@Override
	public List<DiaSemana> buscarFrequencia(Perfil perfil) {
		List<DiaSemana> dias = new ArrayList<DiaSemana>(); 


		try{
			Connection con = Banco.conexao();
			String sql = "select codigodia,codigoperfil from frequenciaPerfil where codigoperfil = ?";
			PreparedStatement prepared = con.prepareStatement(sql );
			prepared.setInt(1, perfil.getCodigo());
			ResultSet result = prepared.executeQuery();
			while (result.next()){
				DiaSemana dia = new DiaSemana();
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

}
