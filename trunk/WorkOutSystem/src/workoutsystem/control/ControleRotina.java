package workoutsystem.control;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import workoutsystem.dao.ISerieDao;
import workoutsystem.dao.ITreinoDao;
import workoutsystem.dao.SerieDao;
import workoutsystem.dao.TreinoDao;
import workoutsystem.model.Realizacao;
import workoutsystem.model.Serie;

public class ControleRotina {
	
	
	public boolean inserirRealizacao(Serie serie, long codigoFicha) throws SQLException {
		ISerieDao dao = new SerieDao();
		boolean retorno = dao.inserirRealizacao(serie, codigoFicha);
		return retorno;
	}
	
	



	public boolean removerRealizacaoSerie(Serie serie) throws SQLException {
		ISerieDao dao = new SerieDao();
		boolean retorno = dao.removerRealizacaoSerie(serie);
		return retorno;

	}

	public boolean removerTudoRealizacaoSerie() throws SQLException {
		ISerieDao dao = new SerieDao();
		boolean retorno = dao.removerTodasRealizacoesSerie();
		return retorno;

	}


	public List<Serie> listarRealizacaoSerie() throws SQLException {
		ISerieDao dao = new SerieDao();
		List<Serie> retorno = dao.listarRealizacaoSerie();
		return retorno;

	}

	public int buscarTreinoIniciado() throws SQLException {
		ISerieDao dao = new SerieDao();
		int retorno = dao.buscarTreinoIniciado();
		return retorno;

	}

	public Realizacao buscarUltimoTreinoRealizado() throws Exception{
		ITreinoDao dao = new TreinoDao();
		Realizacao realizacao = dao.buscarUltimoTreinoRealizado();
		String mensagem = "Nenhum treino foi realizado!";
		if(realizacao.getCodigo() == 0){
			throw new Exception(mensagem);
		}
		return realizacao;
	}
	public boolean inserirRealizacaoSerie(Serie serie) throws SQLException {
		ISerieDao dao = new SerieDao();
		boolean retorno = dao.inserirRealizacaoSerie(serie);
		return retorno;

	}
}
