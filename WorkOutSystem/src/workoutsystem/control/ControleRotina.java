package workoutsystem.control;

import java.sql.SQLException;
import java.util.List;

import workoutsystem.dao.ISerieDao;
import workoutsystem.dao.SerieDao;
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


	public boolean inserirRealizacaoSerie(Serie serie) throws SQLException {
		ISerieDao dao = new SerieDao();
		boolean retorno = dao.inserirRealizacaoSerie(serie);
		return retorno;

	}
}
