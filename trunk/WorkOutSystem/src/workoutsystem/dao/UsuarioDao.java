package workoutsystem.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import workoutsystem.model.Usuario;

public class UsuarioDao implements IUsuarioDao {

	private Banco banco;
	
	public UsuarioDao(Context contexto){
		banco = new Banco(new BancoWorkOutSystem(contexto));
	}
	@Override
	public Usuario buscarUsuario(Usuario u) {
		try{
			Usuario resultado = null;
			banco.open();
			String sql = "select nome,senha from usuario where nome like ?";
			String [] valores = {u.getNome()};
			Cursor cursor = banco.get().rawQuery(sql, valores);
			while (cursor.moveToNext()){
				resultado= new Usuario();
				resultado.setNome(cursor.getString(1));
				resultado.setSenha(cursor.getString(2));
				
			}
			
			return resultado;

		}catch (SQLException s){
			s.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean cadastrarUsuario(Usuario u) {
		banco.open();
		boolean retorno;
		String [] valores = {u.getNome(),u.getSenha()};
		String sql = "insert into usuario (nome,senha) values (?,?)";
		Cursor cursor = banco.get().rawQuery(sql,valores);
		if (cursor.moveToNext()){
			retorno = true;
		}else {
			retorno = false;
		}
		
		banco.close();
		return retorno;
	}


}
