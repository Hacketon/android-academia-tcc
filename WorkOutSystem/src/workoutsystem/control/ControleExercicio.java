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

	public String adicionarExercicio(Exercicio exercicio){
		String mensagem = "Erro ao adicionar ou exercicio já cadastrado";
		IExercicioDao dao = new ExercicioDao();
		
		if (exercicio!= null){
			GrupoMuscular grupo = exercicio.getGrupoMuscular();
			grupo.setCodigo(dao.buscarGrupoMuscular(grupo.getNome()));
			if (dao.buscarExercicio(exercicio.getNomeExercicio()) == null){
				if (grupo.getCodigo() != 0 ){
					if(dao.adicionarExercicio(exercicio)){
						mensagem = "Exercicio criado com sucesso !";
					}
				}
			}
			
		}
		return mensagem;
	}

	public void excluirExercicio(Exercicio exercicio){
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

	public List<GrupoMuscular> listarGrupos(){
		return new ExercicioDao().listarGrupos();
	}

	public List<Exercicio> listarExercicios(String grupo, int personalizado) {
		IExercicioDao exercicioDao = new ExercicioDao();
		return exercicioDao.listarExercicios(grupo, personalizado);
		
	}
}
