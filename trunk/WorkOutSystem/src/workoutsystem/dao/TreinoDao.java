package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.model.Exercicio;
import workoutsystem.model.Grupo;
import workoutsystem.model.Serie;
import workoutsystem.model.Treino;

public class TreinoDao implements ITreinoDao {

	@Override
	public boolean inserirTreino(Treino treino) throws SQLException {
		int aux = 1;
		int resultado = 0;
		Connection con = ResourceManager.getConexao();
		String sql = "insert into treino (nome,ordem,codigoFicha) values (?,?,?)";
		boolean verificar = false;

		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setString(aux++,treino.getNome());
		prepare.setInt(aux++,treino.getOrdem());
		prepare.setLong(aux++, treino.getCodigoFicha());

		resultado = prepare.executeUpdate();

		if (resultado > 0){
			verificar = true;
		}

		prepare.close();
		con.close();

		return verificar;
	}
	
	@Override
	public boolean reordenarTreino(int ordem, long codigoTreino) throws SQLException {
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		String sql = "update treino set ordem = ? where codigo = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setInt(aux++, ordem);
		prepare.setLong(aux++, codigoTreino);
		int resultado = prepare.executeUpdate();
		prepare.close();
		con.close();
		return resultado>0;
	}

	@Override
	public boolean excluirTreino(long codigoTreino, long codigoFicha)
	throws SQLException {
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		String sql = "delete from treino where codigo = ? and codigoFicha = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigoTreino);
		prepare.setLong(aux++, codigoFicha);
		int valor = prepare.executeUpdate();
		con.close();
		prepare.close();
		return (valor>0);
	}

	@Override
	public boolean verificarExercicio(long codigoExercicio) throws SQLException {
		String sql = "select * from serie where codigoexercicio = ?";
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		boolean retorno = false;
		PreparedStatement prepared = con.prepareStatement(sql);
		prepared.setLong(aux++, codigoExercicio);
		ResultSet result = prepared.executeQuery();
		if(result.next()){
			retorno = true;
		}
		prepared.close();
		con.close();
		return retorno;
	}


	@Override
	public boolean buscarTreino(String nomeTreino, long codigoFicha) throws SQLException {
		String sql = "select codigo , nome ,ordem ,codigoFicha " +
		"from treino where " +
		"codigoFicha = ? and nome = ?";
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		PreparedStatement prepared = con.prepareStatement(sql);
		prepared.setLong(aux++, codigoFicha);
		prepared.setString(aux++, nomeTreino);
		ResultSet result = prepared.executeQuery();
		boolean retorno = result.next();
		prepared.close();
		con.close();
		return retorno;
	}

	@Override
	public boolean alterarNomeTreino
	(String nomeTreino, long codigoFicha,long codigoTreino)
	throws SQLException {
		String sql = "update treino set nome = ? where codigoFicha = ? and codigo = ?";
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setString(aux++, nomeTreino);
		prepare.setLong(aux++, codigoFicha);
		prepare.setLong(aux++, codigoTreino);
		int retorno = prepare.executeUpdate();
		prepare.close();
		con.close();
		return retorno > 0 ;
	}

	@Override
	public int buscarQuantidadeTreino(long codigoFicha) throws SQLException {
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		String sql = "select count (*) as numero_treino " +
		"from treino where codigoFicha = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++,codigoFicha);
		ResultSet result = prepare.executeQuery();
		int resultado = 0;
		if(result.next()){
			resultado = result.getInt("numero_treino");
		}

		prepare.close();
		con.close();
		return resultado;
	}


	
	public List<Treino> listarTreinos(long codigoFicha) throws SQLException{
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		String sql = "select codigo,nome,ordem,codigoFicha from treino " +
		" where codigoFicha = ? order by ordem asc";
		PreparedStatement prepare = con.prepareStatement(sql);

		prepare.setLong(aux++, codigoFicha);
		ResultSet result = prepare.executeQuery();
		List<Treino> list = new ArrayList<Treino>(); 

		while(result.next()){
			aux = 1;
			Treino t = new Treino();
			t.setCodigo(result.getInt(aux++));
			t.setNome(result.getString(aux++));
			t.setOrdem(result.getInt(aux++));
			t.setCodigoFicha(result.getInt(aux++));

			list.add(t);
		}


		prepare.close();
		con.close();
		return list;
	}
	
	

	
	@Override
	public List<Treino> buscarTreinoValido(long codigoFicha) throws SQLException {
		String sql = " select treino_codigo,treino_nome," +
				 	 " treino_ordem,treino_codigoficha" +
				 	 " from treino_serie where ficha_codigo = ?";
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigoFicha);
		ResultSet result = prepare.executeQuery();
		List<Treino> listaTreino = new ArrayList<Treino>();
		while(result.next()){
			Treino treino = new Treino();
			treino.setCodigo(result.getInt("treino_codigo"));
			treino.setNome(result.getString("treino_nome"));
			treino.setOrdem(result.getInt("treino_ordem"));
			treino.setCodigoFicha(result.getInt("treino_codigoficha"));
			listaTreino.add(treino);
		}

		prepare.close();
		con.close();
		return listaTreino;
	}
	
		
	// tabelaRealizaSerie teste
	
	@Override
	public String buscarUltimoTreinoRealizado() throws SQLException {
		Connection con = ResourceManager.getConexao();
		String resultado = "";
		String sql = "select treino.nome as treino from realizacao" +
				" inner join treino on treino.codigo = codigotreino " +
				"order by datarealizacao asc  ";
		
		PreparedStatement prepare = con.prepareStatement(sql);
		ResultSet result = prepare.executeQuery();
		if(result.next()){
			resultado = result.getString("treino");
		}

		prepare.close();
		con.close();
		return resultado;	
	
	}
	
	public long buscarUltimoTreino() throws SQLException{
		Connection con = ResourceManager.getConexao();
		long resultado = 0;
		String sql = "select max(codigo) as codigo from treino";
		PreparedStatement prepare = con.prepareStatement(sql);
		ResultSet result = prepare.executeQuery();
		if(result.next()){
			resultado = result.getLong("codigo");
		}
		prepare.close();
		con.close();
		return resultado;	
	}

	

}