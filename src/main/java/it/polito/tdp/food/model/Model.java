package it.polito.tdp.food.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDAO;

public class Model {
	private FoodDAO dao;
	private Graph<Condiment, DefaultWeightedEdge> grafo;
	private Map<Integer, Condiment> idMap;
	
	public Model() {
		dao= new FoodDAO();
		idMap= new HashMap<>();
	}
	
	public void creaGrafo(Integer calorie) {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.listAllCondiment(idMap);
		Graphs.addAllVertices(grafo, dao.getVertici(idMap, calorie));
		
		for(Archi a: dao.getArchi(idMap)) {
			if(a.getC1()!=a.getC2() && this.grafo.containsVertex(a.getC1()) && this.grafo.containsVertex(a.getC2())) {
				Graphs.addEdgeWithVertices(grafo, a.getC1(), a.getC2(), a.getPeso());
			}
		}
		
		System.out.println("Grafo creato con "+ this.grafo.vertexSet().size() +" vertici e con "+ this.grafo.edgeSet().size()+ "archi\n");
		
		
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Archi> listaC(){
		List<Archi> c= dao.getArchi(idMap);
		Collections.sort(c);
		return c;
	}
	
	public Integer nCibi(Condiment c) {
		Integer n=0;
		
		List<Condiment> vicini= Graphs.neighborListOf(grafo, c);
		for(Condiment cc: vicini) {
			Integer nu= (int) this.grafo.getEdgeWeight(this.grafo.getEdge(c, cc));
			n+=nu;
		}
		return n;
	}
}
