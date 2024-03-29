package workoutsystem.control;

import java.sql.SQLException;
import java.util.List;

import workoutsystem.dao.FichaDao;
import workoutsystem.dao.IFichaDao;
import workoutsystem.dao.ISerieDao;
import workoutsystem.dao.ITreinoDao;
import workoutsystem.dao.SerieDao;
import workoutsystem.dao.TreinoDao;
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
			mensagem = "Nenhuma � um nome invalido !";
		}else if(ficha.getAntecedencia()>ficha.getDuracao()){
			mensagem = "Dura��o tem que ser maior que alerta!";
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
			mensagem = "N�o h� fichas cadastradas";
			throw new Exception(mensagem);
		}
		Ficha ficha = dao.buscarFichaCodigo(codigo);
		return ficha;
	}

	private String inserirFicha(Ficha ficha) throws Exception{
		IFichaDao dao = new FichaDao();
		String mensagem = "";

		if(buscarFichaNome(ficha.getNome())!= null){
			mensagem = "J� existe uma ficha com este nome!";
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
			mensagem = "Nome invalido : outra ficha j� possui este nome!";
			throw new Exception(mensagem);
		}
		return mensagem;
	}

	public Ficha buscarFichaAtual() throws Exception{
		Ficha f = null;
		IFichaDao dao = new FichaDao();
		ITreinoDao daoTreino = new TreinoDao();
		f = dao.buscarFichaAtual();
		String mensagem = "N�o h� ficha de treino atual selecionada";
		if (f == null ){
			throw new Exception(mensagem);
		}
		f.setTreinos(daoTreino.listarTreinos(f.getCodigo()));
		return f;
	}
	public List<Ficha> buscarFicha() throws SQLException{
		ITreinoDao daoTreino = new TreinoDao();
		IFichaDao dao = new FichaDao();
		ISerieDao daoSerie = new SerieDao();
		List<Ficha> lista = dao.listarFichas();
		for(Ficha f : lista){
			f.setTreinos(daoTreino.listarTreinos(f.getCodigo()));

			for(Treino t : f.getTreinos()){
				t.setSerie(daoSerie.listarSerie(t.getCodigo()));
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
			mensagem = "Erro ao executar a exclus�o de ficha(s)!";
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
		ISerieDao daoSerie = new SerieDao();

		f = dao.buscarFichaCodigo(codigo);
		if(f != null){
			f.setTreinos(daoTreino.listarTreinos(f.getCodigo()));
			for(Treino t : f.getTreinos()){
				t.setSerie
				(daoSerie.listarSerie(t.getCodigo()));
			}
		}else{
			f = new Ficha();
		}
		return f;
	}

	public String desativarFichaAtual() throws SQLException{
		IFichaDao dao = new FichaDao();
		dao.desativarFichaAtual();
		return "N�o possui uma ficha atual";
	}

	public String mudarFichaAtual(Ficha ficha) throws Exception {
		IFichaDao dao = new FichaDao();
		ControleRotina controle = new ControleRotina();
		desativarFichaAtual();
		controle.atualizarRealizacao(1, 0);
		controle.removerRealizacaoSerie();
		String mensagem = "";
		boolean retorno = dao.alterarFichaAtual(ficha.getCodigo());
		if(retorno){
			mensagem = "Ficha atual alterada com sucesso!";
		}
		return mensagem;
	}

	public List<Ficha> buscarFichaDiferente(long codigo) throws Exception {
		IFichaDao dao = new FichaDao();
		List<Ficha> listaFicha = dao.buscarFichaDiferente(codigo);
		String erro = "N�o possui treinos com series"+"\n"+"em outras fichas !";
		if(listaFicha.size()<=0){
			throw new Exception(erro);
		}
		return listaFicha;
	}
	/**
	 * Metodo responsavel pelo realiza��es de treinos restantes
	 * para a troca da ficha !
	 * @return
	 * @throws Exception
	 */

	public String calcularRestante() throws Exception {
		String mensagem = "";
		IFichaDao dao = new FichaDao();
		Ficha ficha = dao.buscarFichaAtual();
		if(ficha.getCodigo() != 0){
			int calculo = ficha.getDuracao() - ficha.getRealizacoes();
			if(calculo<0){
				mensagem = "J� passou prazo da troca da ficha!";
			}else if (calculo<=ficha.getAntecedencia()){
				mensagem = "Faltando " + calculo + " treino(s) para troca da ficha!";
			}

		}

		return mensagem;
	}
	
	public void atualizarRealizacoes() throws SQLException{
		IFichaDao dao = new FichaDao();
		dao.atualizarRealizacoes();
	}


}
