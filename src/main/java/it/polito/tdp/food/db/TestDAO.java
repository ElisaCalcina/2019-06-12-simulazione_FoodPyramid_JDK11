package it.polito.tdp.food.db;

import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.food.model.Condiment;

public class TestDAO {

	public static void main(String[] args) {
		Map<Integer, Condiment> idMap= new HashMap<>();
		FoodDAO dao = new FoodDAO();
		
		System.out.println(dao.getVertici(idMap, 700));
		System.out.println(dao.getArchi(idMap));
	}

}