package workoutsystem.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.dao.ExercicioDao;
import workoutsystem.dao.FichaDao;
import workoutsystem.dao.IExercicioDao;
import workoutsystem.dao.IFichaDao;
import workoutsystem.dao.ITreinoDao;
import workoutsystem.dao.TreinoDao;
import workoutsystem.model.Especificacao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Ficha;
import workoutsystem.model.Frequencia;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.Validadora;

public class ControleFicha {

	public List<Frequencia> listarDias() {
		return new FichaDao().listarDias();
	}

	public boolean manipularFicha(Ficha ficha) throws SQLException{
		//inserirFicha(ficha);
		return false;

	}
	public List<Ficha> buscarFicha() throws SQLException{
		ITreinoDao daoTreino = new TreinoDao();
		IFichaDao dao = new FichaDao();
		IExercicioDao daoExercicio = new ExercicioDao();
		List<Ficha> lista = dao.listarFichas();
		for(Ficha f : lista){
			f.setTreinos(daoTreino.listarTreinos(f.getCodigoFicha()));
			for(Treino t : f.getTreinos()){
				t.setEspecificacao
				(daoExercicio.listarEspecificacao(t.getCodigo()));
			}
		}
		return lista;
	}

	/*
	 *	private boolean inserirFicha(Ficha ficha) throws SQLException{
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
	 */

	/*
	 *	public boolean excluirFicha(List<String> deletados) throws SQLException {
	boolean resultado = true;
	IFichaDao dao = new FichaDao();
	String mensagem = "Exercicio excluido!";

	for (String texto : deletados){
		String nome = texto; 
		Ficha ficha = dao.buscarFicha(nome);
		for(Treino t : ficha.getTreinos()){
			for (Exercicio e : t.getExercicios()){
				e.getListaEspecificacao();
			}


		}
		resultado = dao.excluirFicha(ficha.getCodigoFicha());

	}

	return resultado;
} 
	 */


	public Ficha buscarFichaNome(String nome) throws Exception {
		IFichaDao dao = new  FichaDao();
		Ficha ficha = dao.buscarFicha(nome.trim());
		if(ficha == null){
			String erro = "N�o foi possivel achar a ficha";
			throw new Exception(erro);
		}
		return ficha;

	}

	

	public void setPerfil(int codigoPerfil) throws SQLException {
		IFichaDao dao = new FichaDao();
		boolean resultado = dao.setPerfil(codigoPerfil);
	}

	
	public Ficha buscarFichaCodigo(long codigo) throws Exception{
		Ficha f = null;
		IFichaDao dao = new FichaDao();
		ITreinoDao daoTreino = new TreinoDao();
		IExercicioDao daoExercicio = new ExercicioDao();
		f = dao.buscarFichaCodigo(codigo);
		if(f != null){
			f.setTreinos(daoTreino.listarTreinos(f.getCodigoFicha()));
			for(Treino t : f.getTreinos()){
				t.setEspecificacao
				(daoExercicio.listarEspecificacao(t.getCodigo()));

			}

		}else{
			f = new Ficha();
			//throw new Exception("Erro ao encontrar a ficha");
		}

		return f;

	}

	

}
