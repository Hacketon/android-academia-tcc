package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.content.Context;
import android.util.Log;
import workoutsystem.model.Usuario;

public class UsuarioDao implements IUsuarioDao {
	public UsuarioDao(Context context){
		
	}
	@Override
	public Usuario buscarUsuario(Usuario u) {
		try {
			
			Connection con = Banco.conexao();
			String sql = "select nome,senha from usuario where nome like ?";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setString(1, u.getNome());
			ResultSet result = prepare.executeQuery();
			if (!result.next()){
				 u = null;
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
			String sql = "insert into usuario (nome,senha) values (?,?)";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setString(1,u.getNome());
			prepare.setString(2,u.getSenha());
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
			String sql = "select nome,senha from usuario where nome like ? and senha like ?";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setString(1,u.getNome());
			prepare.setString(2, u.getSenha());
			ResultSet result = prepare.executeQuery();
			if (result.next()){
				verificador = true;
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
	
	


}
