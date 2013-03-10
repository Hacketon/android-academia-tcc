package workoutsystem.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.dao.Banco;
import workoutsystem.dao.ExercicioDao;
import workoutsystem.interfaces.IExercicioDao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.GrupoMuscular;
import workoutsystem.model.Passo;
import workoutsystem.model.Treino;

public class ControleExercicio {

	//alterar os metodos no diagrama de classes 

	public String manipularExercicio(Exercicio exercicio) throws SQLException{
		String mensagem = "Não foi possivel realizar a operação";
		IExercicioDao dao = new ExercicioDao();

		if (exercicio!= null){
			GrupoMuscular grupo = exercicio.getGrupoMuscular();
			grupo.setCodigo(dao.buscarGrupoMuscular(grupo.getNome()));

			if (!dao.buscarExercicio(exercicio.getCodigo())){
				if (dao.buscarExercicio(exercicio.getNomeExercicio()) == null){
					if(dao.adicionarExercicio(exercicio)){
						mensagem = "Exercicio criado com sucesso !";
					}
				}else if (dao.reativarExercicio(exercicio.getNomeExercicio(),0)){
						mensagem = "Exercicio reativado com sucesso !";
				}
			}else{
				if (!dao.buscarExercicio(exercicio.getNomeExercicio(),exercicio.getCodigo())){
					if (dao.alterarExercicio(exercicio.getCodigo(), exercicio)){
						mensagem = "Exercicio atualizado com sucesso";
					}
				}
			}
		}

		return mensagem;
	}


	public boolean excluirExercicio(ArrayList<String> exercicios) throws SQLException{
		boolean resultado = true;
		IExercicioDao dao = new  ExercicioDao();
		String mensagem = "Exercicio excluido!";

		for (String e : exercicios){
			String nome = e; 
			Exercicio exercicio = dao.buscarExercicio(nome);
			resultado = dao.excluirExercicio(exercicio.getCodigo());
			
		}
		

		return resultado;
	}

	public List<Passo> visualizarPassos(Exercicio exercicio){
		return null;

	}

	public List buscarExercicio (Treino treino){
		return null;
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


	public Exercicio visualizarExercicio(String nomeExercicio){
		return null;
	}

	public List<GrupoMuscular> listarGrupos(){
		return new ExercicioDao().listarGrupos();
	}

	public List<Exercicio> listarExercicios(String grupo, int personalizado) throws SQLException {
		IExercicioDao exercicioDao = new ExercicioDao();
		int ngrupo = exercicioDao.buscarGrupoMuscular(grupo);
		return exercicioDao.listarExercicios(ngrupo, personalizado);

	}
}
