package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.interfaces.IDiaSemana;
import workoutsystem.model.Frequencia;

public class FichaDao implements IDiaSemana{

	@Override
    public List<Frequencia> listarDias() {
		List<Frequencia> listaDias = null;
		try{
			Connection con = ResourceManager.conexao();
			String sql = "select codigo,diasemana from diasemana";
			PreparedStatement prepare = con.prepareStatement(sql);
			listaDias = new ArrayList<Frequencia>();
			ResultSet result = prepare.executeQuery();
			while (result.next()){
				Frequencia dia = new Frequencia();
				dia.setCodigo(result.getInt(1));
				dia.setDiaSemana(result.getString(2));
				listaDias.add(dia);
			}
			
			prepare.close();
			con.close();
		}catch (SQLException e) {
			
		}
		
		return listaDias;
	}

	public int buscarCodigoDia(String Nome){
		int codigo = 0;
		try{
			Connection con = ResourceManager.conexao();
			String sql =" select codigo from diaSemana where diaSemana like ?";
			PreparedStatement prepared = con.prepareStatement(sql);
			prepared.setString(1, Nome);
			ResultSet result = prepared.executeQuery();
			
			if (result.next()) {
				codigo = result.getInt(1);
			}
			prepared.close();
			con.close();
		}catch (SQLException e) {
			
		}
		
		return codigo;
		
	}
}
