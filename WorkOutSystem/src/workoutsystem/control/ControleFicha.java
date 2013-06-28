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
import workoutsystem.model.Serie;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Ficha;
import workoutsystem.model.Frequencia;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.Validadora;

public class ControleFicha {

	public List<Frequencia> listarDias() {
		return new FichaDao().listarDias();
	}

	public String manipularFicha(Ficha ficha) throws Exception{
		String mensagem = "";
		Validadora<Ficha> validadora = new Validadora<Ficha>(ficha);
		ficha.setNome(
				Validadora.verificarString(ficha.getNome()));
		if(!validadora.validarObjeto()){
			mensagem = validadora.getMessage();
		}else if (ficha.getNome().equalsIgnoreCase("Nenhuma")){
			mensagem = "Nenhuma é um nome invalido !";
		}else{
			if(buscarFichaCodigo(ficha.getCodigo()).getCodigo() == 0){
				mensagem = inserirFicha(ficha);
			}else{
				mensagem = atualizarFicha(ficha); 
			}
		}
		return mensagem;

	}
	public Ficha buscarUltimaFicha() throws Exception{
		IFichaDao dao = new FichaDao();
		String mensagem = "";
		long codigo = dao.buscarUltimaFicha();
		if(codigo == 0){
			mensagem = "Não há fichas cadastradas";
			throw new Exception(mensagem);
		}
		Ficha ficha = dao.buscarFichaCodigo(codigo);
		return ficha;
	}
	
	private String inserirFicha(Ficha ficha) throws Exception{
		IFichaDao dao = new FichaDao();
		String mensagem = "";
		if(buscarFichaNome(ficha.getNome())!= null){
			mensagem = "Já existe uma ficha com este nome!";
			throw new Exception(mensagem);
		}else{
			boolean resultado = dao.inserirFicha(ficha);
			if (resultado){
				mensagem = "Ficha inserida com sucesso!";
				
			}else{
				mensagem = "Falha ao inserir ficha no sistema";
				throw new Exception(mensagem);
			}
		}
		return mensagem;
	} 

	private String atualizarFicha(Ficha ficha) throws Exception{
		Ficha busca = buscarFichaNome(ficha.getNome());
		IFichaDao dao = new FichaDao();
		String mensagem = "";
		if((busca == null) || (busca.getCodigo() == ficha.getCodigo())){
			boolean resultado = dao.atualizarFicha(ficha);
			if (resultado){
				mensagem = "Ficha atualizada com sucesso!";
			}else{
				mensagem = "Erro ao inserir a ficha no sistema";
			}
			
		}else{
			mensagem = "Nome invalido : outra ficha já possui este nome!";
			throw new Exception(mensagem);
		}
		return mensagem;
	}

	public Ficha buscarFichaAtual() throws Exception{
		Ficha f = null;
		IFichaDao dao = new FichaDao();
		ITreinoDao daoTreino = new TreinoDao();
		f = dao.buscarFichaAtual();
		String mensagem = "Não há ficha de treino atual selecionada";
		if (f == null ){
			throw new Exception(mensagem);
		}
		f.setTreinos(daoTreino.listarTreinos(f.getCodigo()));
		return f;
	}
	public List<Ficha> buscarFicha() throws SQLException{
		ITreinoDao daoTreino = new TreinoDao();
		IFichaDao dao = new FichaDao();
		List<Ficha> lista = dao.listarFichas();
		for(Ficha f : lista){
			f.setTreinos(daoTreino.listarTreinos(f.getCodigo()));
			for(Treino t : f.getTreinos()){
				t.setSerie
				(daoTreino.listarSerie(t.getCodigo()));
			}
		}
		return lista;
	}
	

	public String excluirFicha(List<Ficha> fichas) throws Exception {
		boolean resultado = true;
		IFichaDao daoFicha = new FichaDao();
		ControleTreino daoTreino = new ControleTreino();
		String mensagem = "Ficha(s) excluidas com sucesso";
		try{
			for (Ficha f : fichas){
				for(Treino t : f.getTreinos()){
					daoTreino.removerTreino(t.getCodigo(),f.getCodigo());
				}
				daoFicha.excluirFicha(f.getCodigo());
			}
		}catch (Exception e) {
			mensagem = "Erro ao executar a exclusão de ficha(s)!";
			throw new Exception();
		}
		return mensagem;

	}





	public Ficha buscarFichaNome(String nome) throws Exception {
		IFichaDao dao = new  FichaDao();
		Ficha ficha = dao.buscarFicha(Validadora.verificarString(nome));
		return ficha;
	}


	public Ficha buscarFichaCodigo(long codigo) throws Exception{
		Ficha f = null;
		IFichaDao dao = new FichaDao();
		ITreinoDao daoTreino = new TreinoDao();
		
		f = dao.buscarFichaCodigo(codigo);
		if(f != null){
			f.setTreinos(daoTreino.listarTreinos(f.getCodigo()));
			for(Treino t : f.getTreinos()){
				t.setSerie
				(daoTreino.listarSerie(t.getCodigo()));
			}
		}else{
			f = new Ficha();
		}
		return f;
	}

	public String desativarFichaAtual() throws SQLException{
		IFichaDao dao = new FichaDao();
		dao.desativarFichaAtual();
		return "Não possui uma ficha atual";
	}

	public String mudarFichaAtual(Ficha ficha) throws SQLException {
		IFichaDao dao = new FichaDao();
		desativarFichaAtual();
		String mensagem = "";
		boolean retorno = dao.alterarFichaAtual(ficha.getCodigo());
		if(retorno){
			mensagem = "Ficha atual alterada com sucesso!";
		}
		return mensagem;

	}



}
