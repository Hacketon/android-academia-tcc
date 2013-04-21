package workoutsystem.control;

import java.sql.SQLException;
import java.util.List;

import workoutsystem.dao.FichaDao;
import workoutsystem.interfaces.IFichaDao;
import workoutsystem.model.Especificacao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Ficha;
import workoutsystem.model.Frequencia;
import workoutsystem.model.Treino;

public class ControleFicha {

	public List<Frequencia> listarDias() {
		return new FichaDao().listarDias();
	}
	
	public boolean manipularFicha(Ficha ficha) throws SQLException{
		inserirFicha(ficha);
		return false;
		
	}
	
	public List<Ficha> buscarFicha() throws SQLException{
		
		return null;
	}
	
	private boolean inserirFicha(Ficha ficha) throws SQLException{
		IFichaDao dao = new FichaDao();
		dao.inserirFicha(ficha);
		for (Treino treino : ficha.getTreinos()){
			dao.inserirTreino(treino);
			for (Exercicio exercicio :treino.getExercicios()){
				for(Especificacao especificacao : exercicio.getListaEspecificacao()){
					dao.inserirEspecificacao(especificacao);
				}
				
			}
		}
		return true;
	}

}
