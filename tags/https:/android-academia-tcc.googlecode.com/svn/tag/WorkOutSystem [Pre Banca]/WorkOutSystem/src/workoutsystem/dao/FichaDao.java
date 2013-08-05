package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.model.Ficha;
import workoutsystem.model.Frequencia;

public class FichaDao implements IDiaSemana,IFichaDao{

	@Override
	public List<Frequencia> listarDias() {
		List<Frequencia> listaDias = null;
		try{
			Connection con = ResourceManager.getConexao();
			String sql = "select codigo,diasemana from diasemana";
			PreparedStatement prepare = con.prepareStatement(sql);
			listaDias = new ArrayList<Frequencia>();
			ResultSet result = prepare.executeQuery();
			while (result.next()){
				Frequencia dia = new Frequencia();
				dia.setCodigo(result.getInt(1));
				dia.setDiaSemana(result.getString(2));
				listaDias.add(dia);
			}

			prepare.close();
			con.close();
		}catch (SQLException e) {

		}

		return listaDias;
	}

	public int buscarCodigoDia(String Nome){
		int codigo = 0;
		try{
			Connection con = ResourceManager.getConexao();
			String sql =" select codigo from diaSemana where diaSemana like ?";
			PreparedStatement prepared = con.prepareStatement(sql);
			prepared.setString(1, Nome);
			ResultSet result = prepared.executeQuery();

			if (result.next()) {
				codigo = result.getInt(1);
			}
			prepared.close();
			con.close();
		}catch (SQLException e) {

		}

		return codigo;

	}

	public List<Ficha> listarFichas() throws SQLException{
		Connection con = ResourceManager.getConexao();
		String sql = "select codigo,nome,duracao, " +
					 " objetivo,realizacoes, " +
					 " ficha_atual,antecedencia from ficha";
		PreparedStatement prepared = con.prepareStatement(sql);
		ResultSet result = prepared.executeQuery();
		List<Ficha> list = new ArrayList<Ficha>();

		while(result.next()){
			Ficha f = new Ficha();
			f.setCodigo(result.getInt("codigo"));
			f.setDuracao(result.getInt("duracao"));
			f.setAtual(result.getInt("ficha_atual"));
			f.setAntecedencia(result.getInt("antecedencia"));
			f.setNome(result.getString("nome"));
			f.setObjetivo(result.getString("objetivo"));
			f.setRealizacoes(result.getInt("realizacoes"));
			list.add(f);
		}


		prepared.close();
		con.close();

		return list;
	}

	@Override
	public boolean inserirFicha(Ficha ficha) throws SQLException {
		String sql = "insert into ficha " +
		" (nome,duracao,objetivo,realizacoes," +
		" ficha_atual , antecedencia) " +
		" values (?,?,?,?,?,?)";
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setString(aux++, ficha.getNome());
		prepare.setInt(aux++, ficha.getDuracao());
		prepare.setString(aux++, ficha.getObjetivo());
		prepare.setInt(aux++, ficha.getRealizacoes());
		prepare.setInt(aux++, ficha.getAtual());
		prepare.setInt(aux++, ficha.getAntecedencia());
		int valor = prepare.executeUpdate();
		prepare.close();
		con.close();
		return (valor>0);
	}



	@Override
	public Ficha buscarFicha(String nome) throws SQLException {
		Connection con = ResourceManager.getConexao();
		Ficha f = null;
		String sql = "select codigo,nome, " +
		"duracao,objetivo, " +
		"realizacoes,ficha_atual,antecedencia " +
		"from ficha " +
		"where nome like ? ";
		int aux = 1;
		
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setString(aux++, nome);
		ResultSet result = prepare.executeQuery();
		if(result.next()){
			f = new Ficha();
			f.setCodigo(result.getInt("codigo"));
			f.setNome(result.getString("nome"));
			f.setDuracao(result.getInt("duracao"));
			f.setAntecedencia(result.getInt("antecedencia"));
			f.setObjetivo(result.getString("objetivo"));
			f.setRealizacoes(result.getInt("realizacoes"));
		}	
		prepare.close();
		con.close();

		return f;
	}

	@Override
	public boolean excluirFicha(long codigoFicha) throws SQLException {
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		String sql = "delete from ficha where codigo = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigoFicha);
		int resultado = prepare.executeUpdate();
		prepare.close();
		con.close();
		return resultado>0;

	}


	@Override
	public Ficha buscarFichaAtual() throws SQLException {
		Connection con = ResourceManager.getConexao();
		Ficha f = null;
		String sql = "select codigo,nome, " +
		"duracao,objetivo, " +
		"realizacoes,ficha_atual,antecedencia " +
		"from ficha " +
		"where ficha_atual = ? ";
		int aux = 1;
		int fichaAtual = 1;
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setInt(aux++, fichaAtual);
		ResultSet result = prepare.executeQuery();
		while(result.next()){
			f = new Ficha();
			f.setCodigo(result.getInt("codigo"));
			f.setNome(result.getString("nome"));
			f.setDuracao(result.getInt("duracao"));
			f.setObjetivo(result.getString("objetivo"));
			f.setAntecedencia(result.getInt("antecedencia"));
			f.setRealizacoes(result.getInt("realizacoes"));
			f.setAtual(result.getInt("ficha_atual"));
		}	
		prepare.close();
		con.close();



		return f;
	}

	@Override
	public boolean alterarFichaAtual(long codigoFicha) throws SQLException {
		Connection con = ResourceManager.getConexao();
		int aux = 1; 
		String sql = "update ficha set ficha_atual = 1 where codigo = ?";
		PreparedStatement prepared = con.prepareStatement(sql);
		prepared.setLong(aux++, codigoFicha);
		int valor = prepared.executeUpdate();
		prepared.close();
		con.close();
		return (valor>0);
	}

	@Override
	public Ficha buscarFichaCodigo(long codigo) throws SQLException {
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		String sql = "select codigo,nome,duracao, " +
		" objetivo,realizacoes,antecedencia, " +
		" ficha_atual from ficha where codigo = ?";
		PreparedStatement prepared = con.prepareStatement(sql);
		prepared.setLong(aux++, codigo);
		ResultSet result = prepared.executeQuery();

		Ficha f = null;
		if(result.next()){
			f = new Ficha();
			f.setCodigo(result.getInt("codigo"));
			f.setNome(result.getString("nome"));
			f.setDuracao(result.getInt("duracao"));
			f.setObjetivo(result.getString("objetivo"));
			f.setRealizacoes(result.getInt("realizacoes"));
			f.setAtual(result.getInt("ficha_atual"));
			f.setAntecedencia(result.getInt("antecedencia"));
			
			
		}
		prepared.close();
		con.close();
		return f;
	}
	
	public boolean atualizarRealizacoes() throws SQLException{
		Connection con = ResourceManager.getConexao();
		String sql = "update ficha set realizacoes = realizacoes + ? where ficha_atual = ?";
		int valor = 1;
		int aux = 1;
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setInt(aux++, valor);
		prepare.setInt(aux++, valor);
		int retorno = prepare.executeUpdate();
		prepare.close();
		con.close();
		return retorno>0;
	}

	@Override
	public boolean desativarFichaAtual() throws SQLException {
		Connection con = ResourceManager.getConexao();
		String sql = "update ficha set ficha_atual = 0 , realizacoes = 0";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.executeUpdate();
		prepare.close();
		con.close();
		return true;
	}

	@Override
	public boolean atualizarFicha(Ficha ficha) throws SQLException {
		String sql = "update ficha set  nome = ? ,duracao =  ? ," +
		"objetivo = ? ,realizacoes = ? ," +
		" ficha_atual = ?,antecedencia = ? where codigo = ?";
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setString(aux++, ficha.getNome());
		prepare.setInt(aux++, ficha.getDuracao());
		prepare.setString(aux++, ficha.getObjetivo());
		prepare.setInt(aux++, ficha.getRealizacoes());
		prepare.setInt(aux++, ficha.getAtual());
		prepare.setInt(aux++, ficha.getAntecedencia());
		prepare.setLong(aux++, ficha.getCodigo());
		int valor = prepare.executeUpdate();
		con.close();
		prepare.close();
		return (valor>0);
	}

	@Override
	public long buscarUltimaFicha() throws SQLException {
		String sql = "select Max(codigo) codigo_maximo from ficha";
		long codigo = 0;
		Connection con = ResourceManager.getConexao();
		PreparedStatement prepare = con.prepareStatement(sql);
		ResultSet result = prepare.executeQuery();
		if(result.next()){
			codigo = result.getLong("codigo_maximo");
		}
		con.close();
		prepare.close();
		return codigo;
	}

	@Override
	public List<Ficha> buscarFichaDiferente(long codigo) throws SQLException {
		String sql =" select ficha_codigo,ficha_nome,ficha_duracao,ficha_antecedencia, " +
					" ficha_objetivo,ficha_realizacao,ficha_atual " +
					" from ficha_serie " +
					" where ficha_codigo != ?";
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigo);
		ResultSet result = prepare.executeQuery();
		List<Ficha> listaFicha = new ArrayList<Ficha>();
		while(result.next()){
			Ficha ficha = new Ficha();
			ficha.setCodigo(result.getLong("ficha_codigo"));
			ficha.setNome(result.getString("ficha_nome"));
			ficha.setDuracao(result.getInt("ficha_duracao"));
			ficha.setObjetivo(result.getString("ficha_objetivo"));
			ficha.setRealizacoes(result.getInt("ficha_realizacao"));
			ficha.setAtual(result.getInt("ficha_atual"));
			listaFicha.add(ficha);
		}
		
		con.close();
		prepare.close();
		return listaFicha;
	}

	
}
