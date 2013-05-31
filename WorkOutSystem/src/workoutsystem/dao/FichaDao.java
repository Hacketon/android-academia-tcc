package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.model.Especificacao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Ficha;
import workoutsystem.model.Frequencia;
import workoutsystem.model.Treino;

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
		" ficha_atual,padrao from ficha";
		PreparedStatement prepared = con.prepareStatement(sql);
		ResultSet result = prepared.executeQuery();
		List<Ficha> list = new ArrayList<Ficha>();

		while(result.next()){
			Ficha f = new Ficha();
			f.setCodigo(result.getInt("codigo"));
			f.setDuracao(result.getInt("duracao"));
			f.setAtual(result.getInt("ficha_atual"));
			f.setNome(result.getString("nome"));
			f.setObjetivo(result.getString("objetivo"));
			f.setPadrao(result.getInt("padrao"));
			f.setRealizacoes(result.getInt("realizacoes"));
			list.add(f);
		}


		prepared.close();
		con.close();

		return list;
	}

	
	/*
	 * 
	 * @Override
	public List<Especificacao> listarEspecificacao(long codigoTreino,
			long codigoExercicio,long codigoFicha) throws SQLException {
		int aux = 1;
		Connection con = ResourceManager.getConexao();

		String sql = "select exercicio_codigo,treino_codigo,especificacao_ordem," +
		" especificacao_repeticao,especificacao_carga, " +
		" especificacao_unidade from [especificacao_exercicio_treino] " +
		" where exercicio_codigo = ? and treino_codigo = ? and ficha_codigo = ? " +
		" order by especificacao_ordem asc";

		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigoExercicio);
		prepare.setLong(aux++, codigoTreino);
		prepare.setLong(aux++, codigoFicha);
		ResultSet result = prepare.executeQuery();
		List<Especificacao> list = new ArrayList<Especificacao>();

		while (result.next()){

			Especificacao esp = new Especificacao();
			esp.setCodigoExercicio(result.getInt("exercicio_codigo"));
			esp.setCodigoTreino(result.getInt("treino_codigo"));
			esp.setOrdem(result.getInt("especificacao_ordem"));
			esp.setQuantidade(result.getInt("especificacao_repeticao"));
			esp.setCarga(result.getInt("especificacao_carga"));
			esp.setUnidade(result.getString("especificacao_unidade"));


			list.add(esp);
		}


		prepare.close();
		con.close();

		return list;
	}

	 * 
	 */

	
	@Override
	public boolean inserirFicha(Ficha ficha) throws SQLException {
		int aux = 1;
		int resultado = 0;
		boolean verificar = false;
		Connection con = ResourceManager.getConexao();
		String sql = "insert into ficha(nome,duracao,objetivo, " +
		" realizacoes,ficha_atual,padrao)"+ 
		"values (?,?,?,?,?,?)";

		PreparedStatement prepare = con.prepareStatement(sql);

		prepare.setString(aux++, ficha.getNome());
		prepare.setInt(aux++, ficha.getDuracao());
		prepare.setString(aux++, ficha.getObjetivo());
		prepare.setInt(aux++, ficha.getRealizacoes());
		prepare.setInt(aux++, ficha.getAtual());
		prepare.setInt(aux++, ficha.getPadrao());
		resultado= prepare.executeUpdate();

		if (resultado > 0){
			verificar = true;
		}


		prepare.close();
		con.close();

		return  verificar;
	}

	

	@Override
	public Ficha buscarFicha(String nome) throws SQLException {
		// TODO Auto-generated method stub
		return null;
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
	public boolean setPerfil(int codigoPerfil) throws SQLException {
		Connection con = ResourceManager.getConexao();
		String sql = "update ficha set codigoperfil = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		int aux = 1;
		prepare.setInt(aux ++, codigoPerfil);
		int valor = prepare.executeUpdate();
		prepare.close();
		con.close();
		return (valor>0);
	}

	

	@Override
	public Ficha buscarFichaAtual() throws SQLException {
		Connection con = ResourceManager.getConexao();
		Ficha f = null;
		String sql = "select codigo,nome, " +
						"duracao,objetivo, " +
						"realizacoes,ficha_atual, " +
						"padrao from ficha " +
						"where ficha_atual = ? ";
		int aux = 1;
		int fichaAtual = 1;
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setInt(aux++, fichaAtual);
		ResultSet result = prepare.executeQuery();
		if(result.next()){
			f = new Ficha();
			f.setCodigo(result.getInt("codigo"));
			f.setNome(result.getString("nome"));
			f.setDuracao(result.getInt("duracao"));
			f.setObjetivo(result.getString("objetivo"));
			f.setRealizacoes(result.getInt("realizacoes"));
			f.setPadrao(result.getInt("padrao"));
		}	
		prepare.close();
		con.close();
		
		
		
		return f;
	}

	@Override
	public boolean alterarFichaAtual(int codigoFicha) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Ficha buscarFichaCodigo(long i) throws SQLException {
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		String sql = "select codigo,nome,duracao, " +
		" objetivo,realizacoes, " +
		" ficha_atual,padrao from ficha where codigo = ?";
		PreparedStatement prepared = con.prepareStatement(sql);
		prepared.setLong(aux++, i);
		ResultSet result = prepared.executeQuery();

		Ficha f = null;
		if(result.next()){
			f = new Ficha();
			f.setCodigo(result.getInt("codigo"));
			f.setDuracao(result.getInt("duracao"));
			f.setAtual(result.getInt("ficha_atual"));
			f.setNome(result.getString("nome"));
			f.setObjetivo(result.getString("objetivo"));
			f.setPadrao(result.getInt("padrao"));
			f.setRealizacoes(result.getInt("realizacoes"));
		}
		prepared.close();
		con.close();
		return f;
	}

	
	@Override
	public boolean excluirEspecificacao(long codigoTreino, long codigoExercicio)
			throws SQLException {
		
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		String sql = "delete from especificacao where codigoTreino = ? " +
					"	and codigoExercicio = ?	";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigoTreino);
		prepare.setLong(aux++, codigoExercicio);
		int valor = prepare.executeUpdate();
		con.close();
		prepare.close();
		return (valor>0);
		
	}

	@Override
	public boolean desativarFichaAtual() throws SQLException {
		Connection con = ResourceManager.getConexao();
		String sql = "update ficha set ficha_atual = 0";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.executeUpdate();
		return true;
	}
	
	






}
