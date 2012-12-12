package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	public boolean adicionarMedicao(Medicao medicao) {
		boolean verificador = false;
		try{
			Connection con = Banco.conexao();
			String sql ="insert into medicao (valor, datamedicao, codigomedida, codigousuario, codigoperfil )" +
			" values (?,?,?,?,?);";
			PreparedStatement prepare = con.prepareStatement(sql);

			prepare.setDouble(1,medicao.getValor() );
			java.sql.Date dataSql = new java.sql.Date( medicao.getDataMedicao().getTime());
			prepare.setDate(2, dataSql);
			prepare.setInt(3, medicao.getCodigoMedida() );
			prepare.setInt(4, medicao.getCodigoPerfil());
			prepare.setInt(5, medicao.getCodigoUsuario());
			if(prepare.executeUpdate()!=0){
				verificador = true;
			}else{
				verificador = false;
			}
			prepare.close();
			con.close();
			return verificador;
		}catch (SQLException erro) {
			Log.e ("SQL",erro.getMessage());
			return false;
		}

	}

	@SuppressWarnings("null")
	@Override
	public Medicao buscarValorMedicao(int codigo) {
		Medicao medicao = null;
		try{
			Connection con = Banco.conexao();
			String sql ="select (valor) from medicao where codigomedida = ?;";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setInt(1, codigo);
			ResultSet result = prepare.executeQuery();
			
			if(result.next()){
				medicao.setValor(result.getInt(1));
			}

			prepare.close();
			con.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return medicao;
	}

}
