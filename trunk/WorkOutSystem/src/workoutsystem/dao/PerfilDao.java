package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import workoutsystem.model.Perfil;
import workoutsystem.model.Usuario;

public class PerfilDao implements IPerfilDao{

	public Perfil buscarPerfil() {
		Perfil perfil = null;
		try{
			Connection con = Banco.conexao();
			String sql = "select perfil.nome ,perfil.sexo from usuario inner join perfil on" +
					" perfil.codigousuario = usuario.codigo;";
			PreparedStatement prepare = con.prepareStatement(sql);
			ResultSet result = prepare.executeQuery();

			if(result.next()){
				perfil = new Perfil();
				perfil.setNome(result.getString(1));
				perfil.setSexo(result.getBoolean(2));
			}

			prepare.close();
			con.close();


		}catch (Exception e) {
			// TODO: handle exception
		}
		return perfil;

	}

	@Override
	public boolean criarPerfil(Perfil perfil) {
		
		try{
			boolean verificador = false;
			Connection con = Banco.conexao();
			String sql ="insert into perfil (nome, sexo, codigousuario) values (?,? ,? );";
			PreparedStatement prepare = con.prepareStatement(sql);
			
			prepare.setString(1, perfil.getNome());
			prepare.setBoolean(2, perfil.getSexo());
			prepare.setInt(3, perfil.getCodigousuario());
			
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
	public boolean excluirPerfil(Perfil perfil) {
		// TODO Auto-generated method stub
		return false;
	}

}
