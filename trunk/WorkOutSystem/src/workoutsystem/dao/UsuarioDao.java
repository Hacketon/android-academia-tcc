package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import android.content.Context;
import android.util.Log;
import workoutsystem.interfaces.IUsuarioDao;
import workoutsystem.model.Usuario;

public class UsuarioDao implements IUsuarioDao {
	
	@Override
	public Usuario buscarUsuario(Usuario u) {
		try {
			Connection con = Banco.conexao();
			String sql = "select codigo,nome,senha from usuario where nome like ?";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setString(1, u.getNome().trim());
			ResultSet result = prepare.executeQuery();
			if (!result.next()){
				 u = null;
			}else{
				u.setCodigo(result.getInt(1));
				u.setNome(result.getString(2));
				u.setSenha(result.getString(3));
			}
			prepare.close();
			con.close();
		}catch (SQLException e) {
			Log.e ("SQL",e.getMessage());
		}
		return u;
	}
	
	@Override
	public boolean cadastrarUsuario(Usuario u) {
		try{
			boolean verificador;
			Connection con = Banco.conexao();
			String sql = "insert into usuario (nome,senha,logado) values (?,?,?)";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setString(1,u.getNome().trim());
			prepare.setString(2,u.getSenha().trim());
			prepare.setInt(3, u.getLogado());
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
			Log.e ("SQL",e.getMessage());
			return false;
		}
	}
	
	@Override
	public boolean realizarLogin(Usuario u) {
		try{
			boolean verificador;
			Connection con = Banco.conexao();
			String sql = "select codigo, nome,senha from usuario" +
					" where nome like ? and senha like ?";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setString(1,u.getNome().trim());
			prepare.setString(2, u.getSenha().trim());
			ResultSet result = prepare.executeQuery();
			
			if (result.next()){
				verificador = true;
				
				sql = "update usuario set logado = 0";
				prepare = con.prepareStatement(sql);
				prepare.executeUpdate();
				sql = "update usuario set logado = 1 " +
						" where codigo = ?";
				prepare = con.prepareStatement(sql);
				prepare.setInt(1, result.getInt(1));
				prepare.executeUpdate();
				System.out.println("");
			}else{
				verificador =  false;
			}
			
			prepare.close();
			con.close();
			return verificador;
			
		}catch (SQLException e){
			return false;
		}
	}

	@Override
	public Usuario buscarUsuario() {
		Usuario u = null;
		try{
			Connection con = Banco.conexao();
			String sql = "select codigo,nome,senha,logado from usuario " +
					"where logado = 1" ;
			PreparedStatement prepare = con.prepareStatement(sql);
			ResultSet result = prepare.executeQuery();
			if (result.next()){
				u = new Usuario();
				u.setCodigo(result.getInt(1));
				u.setNome(result.getString(2));
				u.setSenha(result.getString(3));
				u.setLogado(result.getInt(4));
			}
			prepare.close();
			con.close();
		}catch (SQLException e) {
			
		}
		return u;
	}

	@Override
	public void desconectarUsuario() {
		try{
			Connection con = Banco.conexao();
			String sql = "update usuario set logado = 0 where logado = 1";
			PreparedStatement prepared = con.prepareStatement(sql);
			prepared.executeUpdate();
			prepared.close();
			con.close();
		}catch (SQLException e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public boolean alterarSenha(Usuario u,String senha) {
		boolean verificador = false;
		try{
			Connection con = Banco.conexao();
			String sql = "update usuario set senha = ? where codigo = ?";
			PreparedStatement prepared = con.prepareStatement(sql);
			prepared.setString(1, senha);
			prepared.setInt(2, u.getCodigo());
			if (prepared.executeUpdate() > 0) {
				verificador = true;
			}
			prepared.close();
			con.close();
			
		}catch (SQLException e) {
			// TODO: handle exception
		}
		
		return verificador;
	}
	
}
