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
import workoutsystem.dao.ResourceManager;
import workoutsystem.dao.ExercicioDao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.GrupoMuscular;
import workoutsystem.model.Passo;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.Validadora;

public class ControleExercicio {

	//alterar os metodos no diagrama de classes 

	public String manipularExercicio(Exercicio exercicio) throws SQLException{
		String mensagem = "N�o foi possivel realizar a opera��o";
		IExercicioDao dao = new ExercicioDao();
		Validadora<Exercicio> v = new Validadora<Exercicio>(exercicio);
		if (exercicio!= null){
			if(v.validarObjeto()){
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

			}else{
				mensagem = v.getMessage();
			}
		}

		return mensagem;
	}

	//caso de erro voltar para ArrayList<String>
	public boolean excluirExercicio(List<String> exercicios) throws SQLException{
		boolean resultado = true;
		IExercicioDao dao = new  ExercicioDao();
		IFichaDao daoFicha = new FichaDao();
			
		
		for (String e : exercicios){
			String nome = e; 
			Exercicio exercicio = dao.buscarExercicio(nome);
			if(!daoFicha.verificarExercicio(exercicio.getCodigo())){
				dao.excluirExercicio(exercicio.getCodigo());	
			}else{
				resultado = false;
			}
			

		}
		return resultado;
	}

	
	public String buscarExercicio(GrupoMuscular gMuscular){
		String mensagem = "Erro ao buscar Exercicio";
		IExercicioDao dao = new ExercicioDao();

		if(dao.buscarExercicioGrupoMuscular(gMuscular) != null){
			mensagem = "Encontardos os exercicios";
		}else{
			mensagem = "Exercicio n�o foram encontrados";
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

	public List<Exercicio> listarExercicios(String grupo, int personalizado) throws SQLException {
		IExercicioDao exercicioDao = new ExercicioDao();
		int ngrupo = exercicioDao.buscarGrupoMuscular(grupo);
		return exercicioDao.listarExercicios(ngrupo, personalizado);

	}
}
