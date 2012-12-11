package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.util.Log;

import workoutsystem.model.Exercicio;

public class ExercicioDao implements IExercicioDao {

	@Override
	public Exercicio buscarExercicio(Exercicio e) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public boolean adicionarExercicio(Exercicio e) {
		try{
			boolean verificador;
			Connection con = Banco.conexao();
			String sql = "insert into exercicio (nome, descricao, personalizado, codigogrupomuscular)" +
					" values (?,?,?,?)  ";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setString(1, e.getNomeExercicio());
			prepare.setString(2, e.getDescricao());
			prepare.setBoolean(3, e.isPersonalizado());
			prepare.setInt(4, e.getGrupoMuscular().getCodigo());
			
			if(prepare.executeUpdate()!=0){
				verificador = true;
			}else{
				verificador = false;
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
	public boolean alterarExercicio(int codigo, Exercicio e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluirExercicio(int codigo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Exercicio visualizarPassos(Exercicio e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exercicio visualizarExercicio(Exercicio e) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public int buscarGrupoMuscular(String nome) {

		int codigo = 0;
		try{
			Connection con = Banco.conexao();
			String sql = "select grupomuscular.codigo from grupomuscular where " +
			" grupomuscular.nome like ?";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setString(1, nome);
			ResultSet result = prepare.executeQuery();

			if (result.next()){
				codigo = result.getInt(1);
			}

			prepare.close();
			con.close();

		}catch(SQLException e) {
			Log.e ("SQL",e.getMessage());
		}

		return codigo;
	}

}
