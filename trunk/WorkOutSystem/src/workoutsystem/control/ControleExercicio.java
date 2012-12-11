package workoutsystem.control;

import java.util.List;

import workoutsystem.dao.ExercicioDao;
import workoutsystem.dao.IExercicioDao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.GrupoMuscular;
import workoutsystem.model.Treino;

public class ControleExercicio {

	//alterar os metodos no diagrama de classes 

	public String adicionarExercicio(Exercicio exercicio){
		String mensagem = "Erro ao adicionar o exercicio";
		IExercicioDao dao = new ExercicioDao();
		
		if (exercicio!= null){
			GrupoMuscular grupo = exercicio.getGrupoMuscular();
			grupo.setCodigo(dao.buscarGrupoMuscular(grupo.getNome()));
			if (grupo.getCodigo() != 0 ){
				if(dao.adicionarExercicio(exercicio)){
					mensagem = "Exercicio criado com sucesso !";
				}
			}
		}
		return mensagem;
	}

	public void excluirExercicio(Exercicio exercicio){
	}

	public List visualizarPassos(Exercicio exercicio){
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
	
	public String buscarExercicioPersonalizado(Exercicio exercicio){

		String mensagem = "Erro ao buscar exercicios personalizados";
		IExercicioDao dao = new ExercicioDao();

		if(dao.buscarExercicioPersonalizado(exercicio) != null){
			mensagem = "Exercicios encontrados";
		}else {
			mensagem = "Não foram encontrados exercicios";
		}

		return mensagem;
	}

	
	public Exercicio visualizarExercicio(String nomeExercicio){
		return null;
	}

}
