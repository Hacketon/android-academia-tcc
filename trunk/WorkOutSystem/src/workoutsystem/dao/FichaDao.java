package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.model.DiaSemana;

public class FichaDao implements IDiaSemana{

	@Override
	public List<DiaSemana> listarDias() {
		List<DiaSemana> listaDias = null;
		try{
			Connection con = Banco.conexao();
			String sql = "select codigo,diasemana from diasemana";
			PreparedStatement prepare = con.prepareStatement(sql);
			listaDias = new ArrayList<DiaSemana>();
			ResultSet result = prepare.executeQuery();
			while (result.next()){
				DiaSemana dia = new DiaSemana();
				dia.setCodigo(result.getInt(1));
				dia.setDiaSemana(result.getString(2));
				listaDias.add(dia);
			}
			
		}catch (SQLException e) {
			
		}
		
		return listaDias;
	}

}
