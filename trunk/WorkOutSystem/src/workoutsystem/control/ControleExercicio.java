package workoutsystem.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.dao.FichaDao;
import workoutsystem.dao.IExercicioDao;
import workoutsystem.dao.IFichaDao;
import workoutsystem.dao.ITreinoDao;
import workoutsystem.dao.ResourceManager;
import workoutsystem.dao.ExercicioDao;
import workoutsystem.dao.TreinoDao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.GrupoMuscular;
import workoutsystem.model.Passo;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.Validadora;

public class ControleExercicio {

	//alterar os metodos no diagrama de classes 

	public String manipularExercicio(Exercicio exercicio) throws Exception{
		String mensagem = "";
		IExercicioDao dao = new ExercicioDao();
		exercicio.setNome(Validadora.verificarString(exercicio.getNome()));
		Validadora<Exercicio> v = new Validadora<Exercicio>(exercicio);
		String erro = "";
		if (exercicio!= null){
			erro = v.getMessage();
			if(erro.equalsIgnoreCase("")){
				GrupoMuscular grupo = exercicio.getGrupoMuscular();
				grupo.setCodigo(dao.buscarGrupoMuscular(grupo.getNome()));
				if (!dao.buscarExercicio(exercicio.getCodigo())){
					if (dao.buscarExercicio(exercicio.getNome()) == null){
						if(dao.adicionarExercicio(exercicio)){
							mensagem = "Exercicio criado com sucesso !";
						}
					}else if (dao.reativarExercicio(exercicio.getNome(),0)){
						mensagem = "Exercicio reativado com sucesso !";
					}else{
						erro = "Exercicio já existente !";
						throw new Exception(erro);
					}
				}else{
					if (!dao.buscarExercicio(exercicio.getNome(),exercicio.getCodigo())){
						if (dao.alterarExercicio(exercicio.getCodigo(), exercicio)){
							mensagem = "Exercicio atualizado com sucesso";
						}
					}
				}

			}else{
				mensagem = v.getMessage();
			}
		}else{
			throw new Exception(erro);
		}

		return mensagem;
	}


	public String excluirExercicio(List<Exercicio> lista) throws SQLException{
		String mensagem = "Exericios foram removidos com sucesso!";
		boolean resultado = false;
		IExercicioDao dao = new  ExercicioDao();
		ITreinoDao daoTreino = new TreinoDao();
		ControleSerie controle = new ControleSerie();
		controle.removerSerie(lista);
		for (Exercicio e : lista){
				resultado = dao.excluirExercicio(e.getCodigo());
				if(!resultado){
					break;
				}
		}
		if(!resultado){
			mensagem = "Não foi possivel remover os exercicios!";
		}
		
		return mensagem;
		
		
	}


	public String buscarExercicio(GrupoMuscular gMuscular){
		String mensagem = "Erro ao buscar Exercicio";
		IExercicioDao dao = new ExercicioDao();

		if(dao.buscarExercicioGrupoMuscular(gMuscular) != null){
			mensagem = "Encontardos os exercicios";
		}else{
			mensagem = "Exercicio não foram encontrados";
		}

		return mensagem;
	}

	public Exercicio buscarExercicio(String nome){
		IExercicioDao dao = new ExercicioDao();
		return dao.buscarExercicio(nome);
	}




	public List<GrupoMuscular> listarGrupos(){
		return new ExercicioDao().listarGrupos();
	}

	public List<Exercicio> listarExercicios(String grupo, int personalizado)
	throws SQLException {
		IExercicioDao exercicioDao = new ExercicioDao();
		int ngrupo = exercicioDao.buscarGrupoMuscular(grupo);
		return exercicioDao.listarExercicios(ngrupo, personalizado);

	}

	/**
	 * Metodo responsavel por listar os exercicios que estão 
	 * disponiveis para adicionar no exercicio
	 * @param codigoTreino
	 * @param codigoGrupo
	 * @return lista dos exercicios ou Exception caso não acha
	 * @throws Exception 
	 */
	public List<Exercicio> listarExercicioDisponiveis
	(long codigoTreino,long codigoGrupo) throws Exception {
		IExercicioDao dao = new ExercicioDao();
		List<Exercicio> listar = dao.listarExercicioFora(codigoTreino, codigoGrupo);
		List<Exercicio> listarSemTreino = dao.listarExercicioSemTreino(codigoGrupo);
		listar.addAll(listarSemTreino);
		if(listar.size()<=0){
			String erro = "Não há exercicios disponiveis para este grupo muscular";
			throw new Exception(erro);
		}

		return listar;

	}

	public List<Exercicio> listarExercicioTreino(long codigoTreino) throws Exception{
		IExercicioDao dao = new ExercicioDao();
		List<Exercicio> listar = dao.listarExercicioTreino(codigoTreino);
		if(listar.size()<0){
			String erro = "Não há exercicios disponiveis para este grupo muscular";
			throw new Exception(erro);
		}

		return listar;
	}
}
