package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import workoutsystem.interfaces.IExercicioDao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.GrupoMuscular;
import workoutsystem.model.Passo;


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
			String sql = "insert into exercicio (nomeexercicio, descricao, personalizado, codigogrupomuscular)" +
			" values (?,?,?,?) ";

			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setString(1, e.getNomeExercicio().trim());
			prepare.setString(2, e.getDescricao().trim());
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
	public List<Passo> visualizarPassos(Exercicio exercicio) {
		List<Passo> passos = null;
		try{
			Connection con = Banco.conexao();
			String sql = "select passo.sequencia,passo.explicacao" +
			" from passo inner join exercicio " +
			" on passo.codigoexercicio = exercicio.codigo" +
			" where exercicio.codigo = ?";
			PreparedStatement prepared = con.prepareStatement(sql);
			prepared.setLong(1, exercicio.getCodigo());
			ResultSet result = prepared.executeQuery();
			passos = new ArrayList<Passo>();
			while (result.next()) {
				Passo p = new Passo();
				p.setSequencia(result.getInt(1));
				p.setExplicacao(result.getString(2));
				passos.add(p);
			}
			prepared.close();
			con.close();
		}catch (SQLException e) {
			// TODO: handle exception
		}
		return passos;

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
			" grupomuscular.nome like ? ";
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





	public List<GrupoMuscular> listarGrupos() {
		List<GrupoMuscular> lista = null;
		try{
			Connection con = Banco.conexao();
			String sql = "select codigo,nome from grupomuscular";
			PreparedStatement prepare = con.prepareStatement(sql);
			ResultSet result = prepare.executeQuery();
			lista = new ArrayList<GrupoMuscular>();

			while (result.next()){
				GrupoMuscular grupo = new GrupoMuscular();
				grupo.setCodigo(result.getInt(1));
				grupo.setNome(result.getString(2));
				lista.add(grupo);
			}

			prepare.close();
			con.close();

		}catch(SQLException e) {
			Log.e ("SQL",e.getMessage());
		}
		return lista;

	}


	@Override
	public List<Exercicio> listarExercicioPersonalizado(String grupo) {
		List<Exercicio> lista = null;
		try{
			Connection con = Banco.conexao();

			String sql = "select exercicio.codigo,exercicio.nomeexercicio," +
			"exercicio.descricao,exercicio.personalizado," +
			"exercicio.codigogrupomuscular,grupomuscular.nome" +
			"from exercicio inner join grupomuscular " +
			"on exercicio.codigogrupomuscular = grupomuscular.codigo"+
			"where grupomuscular.nome like ? and personalizado = 1";

			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setString(1, grupo);
			ResultSet resultSet = prepare.executeQuery();
			lista = new ArrayList<Exercicio>();
			while (resultSet.next()){
				Exercicio exercicio = new Exercicio();
				exercicio.setCodigo(resultSet.getInt(1));
				exercicio.setNomeExercicio(resultSet.getString(2));
				exercicio.setDescricao(resultSet.getString(3));
				exercicio.setPersonalizado(resultSet.getBoolean(4));
				GrupoMuscular grupoMuscular= new GrupoMuscular();
				grupoMuscular.setCodigo(resultSet.getInt(5));
				grupoMuscular.setNome(resultSet.getString(6));
				exercicio.setGrupoMuscular(grupoMuscular);

				lista.add(exercicio);
			}

			prepare.close();
			con.close();
		}catch (SQLException e) {
			// TODO: handle exception
		}
		return lista;
	}

	@Override
	public Exercicio buscarExercicio(String nome) {
		Exercicio exercicio = null;
		try{
			Connection con = Banco.conexao();
			String sql = "select codigo,nomeexercicio,descricao,personalizado,codigogrupomuscular" +
			" from exercicio where nomeexercicio like ?";
			PreparedStatement prepared = con.prepareStatement(sql);
			prepared.setString(1, nome);
			ResultSet result = prepared.executeQuery();
			if(result.next()){
				exercicio = new Exercicio();
				exercicio.setCodigo(result.getInt(1));
				exercicio.setNomeExercicio(result.getString(2));
				exercicio.setDescricao(result.getString(3));
				exercicio.setPersonalizado(result.getBoolean(4));
				GrupoMuscular grupo = new GrupoMuscular();
				grupo.setCodigo(result.getInt(5));
				exercicio.setGrupoMuscular(grupo);
			}
			prepared.close();
			con.close();
		}catch (SQLException e) {

		}
		return exercicio;
	}



	@Override
	public List<Exercicio> listarExercicios(String grupo,boolean personalizado) {
		List<Exercicio> lista = null;
		try{
			Connection con = Banco.conexao();
			String sql = "select exercicio.codigo,exercicio.nomeexercicio," +
			"exercicio.descricao,exercicio.personalizado," +
			"exercicio.codigogrupomuscular,grupomuscular.nome" +
			"from exercicio inner join grupomuscular " +
			"on exercicio.codigogrupomuscular = grupomuscular.codigo"+
			"where grupomuscular.nome like ? and personalizado = ?";

			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setString(1, grupo);
			prepare.setBoolean(2, personalizado);
			ResultSet resultSet = prepare.executeQuery();
			lista = new ArrayList<Exercicio>();
			while (resultSet.next()){
				Exercicio exercicio = new Exercicio();
				exercicio.setCodigo(resultSet.getInt(1));
				exercicio.setNomeExercicio(resultSet.getString(2));
				exercicio.setDescricao(resultSet.getString(3));
				exercicio.setPersonalizado(resultSet.getBoolean(4));
				GrupoMuscular grupoMuscular= new GrupoMuscular();
				grupoMuscular.setCodigo(resultSet.getInt(5));
				grupoMuscular.setNome(resultSet.getString(6));
				exercicio.setGrupoMuscular(grupoMuscular);

				lista.add(exercicio);
			}

			prepare.close();
			con.close();
		}catch (SQLException e) {
			// TODO: handle exception
		}
		return lista;
	}



@Override
public Exercicio buscarExercicioPersonalizado(Exercicio exercicio) {
	GrupoMuscular grupomuscular = new GrupoMuscular();

	try{
		Connection con = Banco.conexao();
		String sql = "select (nome, descricao, personalizado, codigogrupomuscular) from exercicio where personalizado = ?;";

		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setBoolean(1, exercicio.isPersonalizado());
		ResultSet result = prepare.executeQuery();


		//Verificar se está correto 
		if (result.next()){
			exercicio.setNomeExercicio(result.getString(1));
			exercicio.setDescricao(result.getString(2));
			exercicio.setPersonalizado(result.getBoolean(3));
			// ver se está correto essa parte 
			grupomuscular.setCodigo(result.getInt(4));
			exercicio.setGrupoMuscular(grupomuscular);
		}

		prepare.close();
		con.close();

	}catch(SQLException e) {
		Log.e ("SQL",e.getMessage());
	}
	return exercicio;
}

@Override
public Exercicio buscarExercicioGrupoMuscular(GrupoMuscular grupo) {
	Exercicio exercicio = new Exercicio();
	GrupoMuscular grupomuscular = new GrupoMuscular();
	try{
		Connection con = Banco.conexao();
		String sql = "select (nomeexercicio, descricao, codigogrupomuscular, personalizado) from exercicio where codigogrupomuscular = ?;";
		PreparedStatement prepare = con.prepareStatement(sql);	
		prepare.setInt(1, exercicio.getGrupoMuscular().getCodigo());
		ResultSet result  = prepare.executeQuery();


		//Verificar se está correto 
		if(result.next()){
			exercicio.setNomeExercicio(result.getString(1));
			exercicio.setDescricao(result.getString(2));
			//verificar está parte
			grupomuscular.setCodigo(result.getInt(3));
			exercicio.setGrupoMuscular(grupomuscular);
			//
			exercicio.setPersonalizado(result.getBoolean(4));
		}

		prepare.close();
		result.close();

	}catch (Exception e) {
		Log.e ("SQL",e.getMessage());
	}

	return exercicio;
}


}