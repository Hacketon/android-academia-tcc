package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import android.content.Context;
import android.database.Cursor;
import java.sql.SQLException;
import workoutsystem.model.Usuario;

public class UsuarioDao implements IUsuarioDao {



	public UsuarioDao(Context contexto){

	}
	@Override
	public Usuario buscarUsuario(Usuario u) {
		try{
			Banco b = new Banco();
			Connection conexao = b.conexao();
			String sql = "";
			PreparedStatement prepare = 
			conexao.prepareStatement(sql);
			return null;
		}catch(SQLException e){
			return null;
		}



	}

	@Override
	public boolean cadastrarUsuario(Usuario u) {
		try {
			Banco b = new Banco();
			Connection conexao = b.conexao();
			String sql = "insert into usuario(nome,senha) values (?,?)";
			PreparedStatement prepare = conexao.prepareStatement(sql);
			prepare.setString(1, u.getNome());
			prepare.setString(2, u.getSenha());
			prepare.execute();
			prepare.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


}
