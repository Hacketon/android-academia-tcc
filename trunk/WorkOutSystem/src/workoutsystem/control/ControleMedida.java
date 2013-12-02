package 
workoutsystem.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import workoutsystem.dao.IMedidaDao;
import workoutsystem.dao.MedidaDao;
import workoutsystem.model.Medicao;
import workoutsystem.model.Medida;
import android.annotation.SuppressLint;

public class ControleMedida {
	
	
	@SuppressLint("SimpleDateFormat")
	public String buscarDataUltimaMedicao() throws Exception{
		IMedidaDao dao = new MedidaDao();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date data = dao.buscarDataUltimaMedicao();
		String formatData = sdf.format(data);
		return formatData;
	}
	
	
	
	//corrigir codigo
	public String adicionarMedicao(List<Medicao> medicoes){
		String mensagem = "  Erro ao adicionar Medicao";
		IMedidaDao dao = new MedidaDao();
		
		if(medicoes != null){
			if(dao.adicionarMedicao(medicoes)){
				mensagem = " Medições : Adicionadas com sucesso";
			}
		}

		return mensagem;	
	}
	
	public List<Medida> buscarMedidas(){
		List<Medida> lista = new ArrayList<Medida>();
		IMedidaDao daoMed = new MedidaDao();
		
		if(daoMed.buscarMedidas() != null){
			lista = daoMed.buscarMedidas();
		}else{
			return null;
		}
		
		return lista;
	}
	
	public List<Medicao> buscarListaMedicaoes(int codigo,int codigoperfil){
		List<Medicao> lista = new ArrayList<Medicao>();
		IMedidaDao daoMed = new MedidaDao();
		
		if(daoMed.buscarMedidas() != null){
			lista = daoMed.buscarListaMedicao(codigo,codigoperfil);
		}else{
			return null;
		}
		
		return lista;
	}
	
	
	
	public int buscarMedida(String nome, String lado){
		int codigo = 0;
		IMedidaDao dao = new MedidaDao();
		
		if(dao.buscarMedida(nome, lado)!= 0){
			codigo = dao.buscarMedida(nome, lado);
		}
		return codigo;
	}
	
	
	public List<Medicao> buscarMedicao(int codigo){
		IMedidaDao dao = new MedidaDao();
		return  dao.buscarMedicao(codigo);
	}
	
	
	
	public Double buscarValor(int codigo){
		Double valor = null ;
		IMedidaDao dao = new MedidaDao();
		
		if(dao.buscarValorMedicao(codigo)!= null){
			valor = dao.buscarValorMedicao(codigo);
		
		}
		
		return valor;
	}
	
	public String alterarMedicoes(List<Medicao> medicoes){
		String mensagem = "Erro ao atualizar";
		IMedidaDao dao = new MedidaDao();
		
		if(dao.alterarMedicao(medicoes)== true){
			mensagem = " Medidas : Atualizadas com sucesso";
		}		
		
		return mensagem;
	}
	
	public String alterarUltimasMedicoes(List<Medicao> medicoes){
		String mensagem = "Erro ao atualizar";
		IMedidaDao dao = new MedidaDao();
		
		if(dao.alterarUltimaMedicao(medicoes)!= false){
			mensagem = " Medições : Atualizadas com sucesso";
		}		
		
		return mensagem;
	}
	public String excluirMedicoes(int codigo){
		String mensagem = "Não possui medidas cadastradas ! ";
		IMedidaDao dao = new MedidaDao();
		
		if(dao.excluirMedicoes(codigo)== true){
			mensagem = "Todas Medições foram excluídas! ";
		}
		return mensagem;
	}

	public boolean verificarMedicao(int codigo) {
		IMedidaDao dao = new  MedidaDao();
		return dao.verificarMedicao(codigo);
		
	}
	
	public List<Medicao> ultimasMedicoes
					(int codigoPerfil,int codigoMedida) throws Exception{
		
		IMedidaDao dao = new  MedidaDao();
		List<Medicao> medicao = 
			dao.ultimasMedicoes(codigoPerfil, codigoMedida);
		
		return medicao;
	}

	
	

	public List<Medida> ultimaMedicao(int codigo) {
		IMedidaDao dao = new MedidaDao();
		return dao.ultimaMedicao(codigo);
	}

	/**
	 * Metodo responsavel pelo calculo do progresso das ultimas medicoes!
	 * @param medicao
	 * @return valores[hash map com o codigo e o valor de progresso ]
	 */
	@SuppressLint("UseSparseArrays")
	public HashMap<Integer, Integer> calcularProgresso(List<Medicao> medicao) {
		HashMap<Integer, Integer> valores = new HashMap<Integer, Integer>();
		double maiorValor = 0.0;
		int contador = 0;
		int porcentagem = 0;
		double valor = 0.0;
		double calculo = 0.0;
				
		Collections.sort(medicao, new Medicao());
		Collections.reverse(medicao);
		
		if(!medicao.isEmpty()){
			for(Medicao m : medicao){
				valor = m.getValor();
				if(contador == 0){
					maiorValor = m.getValor();
					maiorValor = (maiorValor * 0.2) + maiorValor;
				}
					calculo = (valor * 100) / maiorValor;
					porcentagem = (int) Math.round(calculo);
					valores.put(m.getCodigo(), porcentagem);
					contador++;
			}
		}
		
		return valores;
	}

	
	
}
