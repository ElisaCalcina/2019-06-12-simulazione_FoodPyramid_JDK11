package it.polito.tdp.food.db;

import java.sql.Connection;import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Food;

import it.polito.tdp.food.model.Archi;
import it.polito.tdp.food.model.Condiment;

public class FoodDAO {

	public List<Food> listAllFood(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getInt("portion_default"), 
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"),
							res.getDouble("calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiment(Map<Integer, Condiment> idMap){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
				if(!idMap.containsKey(res.getInt("food_code"))) {
					Condiment c= new Condiment(res.getInt("condiment_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getString("condiment_portion_size"), 
							res.getDouble("condiment_calories")
							);
					list.add(c);
					idMap.put(c.getFood_code(), c);
				}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> getVertici(Map<Integer, Condiment> idMap ,Integer calorie){
		String sql="SELECT * " + 
				"FROM condiment " + 
				"WHERE condiment_calories<? ";
		List<Condiment> result= new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, calorie);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
					Condiment c= new Condiment(res.getInt("condiment_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getString("condiment_portion_size"), 
							res.getDouble("condiment_calories")
							);
					result.add(c);
					//idMap.put(c.getFood_code(), c);
					
				
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	
	//Archi
	public List<Archi> getArchi(Map<Integer, Condiment> idMap){
		String sql="SELECT f1.condiment_food_code AS cc, f2.condiment_food_code AS cc1, COUNT(DISTINCT f1.food_code) AS peso " + 
				"FROM food_condiment f1, food_condiment f2 " + 
				"WHERE f1.condiment_food_code>f2.condiment_food_code " + 
				"		AND f1.food_code=f2.food_code " + 
				"GROUP BY cc, cc1 " +
				"HAVING peso!=0 ";
		
		List<Archi> result= new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
				Condiment c1= idMap.get(res.getInt("cc"));
				Condiment c2= idMap.get(res.getInt("cc1"));
				
				
				if(c1!=null && c2!=null) {	
					Archi a= new Archi(c1, c2, res.getInt("peso"));
					result.add(a);
				}
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
}

