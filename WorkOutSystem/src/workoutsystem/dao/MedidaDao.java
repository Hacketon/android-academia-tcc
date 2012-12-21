package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import workoutsystem.interfaces.IMedidaDao;
import workoutsystem.model.Medicao;

import android.util.Log;

public class MedidaDao implements IMedidaDao{

	@Override
	public int buscarMedida(String nome, String lado) {
		int codigo =0;
		try{
			Connection con = Banco.conexao();
			String sql = "select codigo from medida where nome = ? and lado= ?;";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setString(1, nome);
			prepare.setString(1, lado);
			ResultSet result = prepare.executeQuery();

			while(result.next()){
				codigo = result.getInt(1);
			}

			prepare.close();
			con.close();

		}catch(SQLException e) {
			Log.e ("SQL",e.getMessage());
		}
		return codigo;
	}

	@Override
	public boolean adicionarMedicao(List<Medicao> medicoes) {
		boolean verificador = false;
		try{
			Connection con = Banco.conexao();
			PreparedStatement prepare = null;

			for(Medicao m: medicoes){
				String sql ="insert into medicao (valor, datamedicao, codigomedida, codigousuario, codigoperfil )" +
				" values (?,?,?,?,?);";
				 prepare = con.prepareStatement(sql);

				prepare.setDouble(1,m.getValor() );
				java.sql.Date dataSql = new java.sql.Date( m.getDataMedicao().getTime());
				prepare.setDate(2, dataSql);
				prepare.setInt(3, m.getCodigoMedida() );
				prepare.setInt(4, m.getCodigoPerfil());
				prepare.setInt(5, m.getCodigoUsuario());
				if(prepare.executeUpdate()!=0){
					verificador = true;
				}else{
					verificador = false;
				}
			}

			prepare.close();
			con.close();
			return verificador;
		}catch (SQLException erro) {
			Log.e ("SQL",erro.getMessage());
			return false;
		}

	}

	@Override
	public Double buscarValorMedicao(int codigo) {
		Double valor = null;
		try{
			Connection con = Banco.conexao();
			String sql ="select (valor) from medicao where codigomedida = ?;";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setInt(1, codigo);
			ResultSet result = prepare.executeQuery();

			if(result.next()){
				valor = result.getDouble(1);
			}

			prepare.close();
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return valor;
	}


	@Override
	public Medicao buscarMedicao(int codigo) {
		Medicao medicao = null;
		try{
			Connection con = Banco.conexao();
			String sql ="select valor, datamedicao,codigomedida,  codigousuario, codigoperfil " +
			"from medicao where codigomedida = ?;";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setInt(1, codigo);
			ResultSet result = prepare.executeQuery();

			while(result.next()){
				medicao = new Medicao();
				medicao.setValor(result.getDouble(1));
				medicao.setDataMedicao(result.getDate(2));
				medicao.setCodigoMedida(result.getInt(3));
				medicao.setCodigoPerfil(result.getInt(4));
				medicao.setCodigoUsuario(result.getInt(5));

			}

			prepare.close();
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return medicao;


	}

}
