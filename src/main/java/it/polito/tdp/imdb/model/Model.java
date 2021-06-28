package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;


import it.polito.tdp.imdb.db.ImdbDAO;



public class Model {
	
	SimpleWeightedGraph <Director, DefaultWeightedEdge> grafo;
	Map<Integer,Director> idMap;
	ImdbDAO dao;
	List <Director> result;
	double peso;
	
	public Model(){
	dao= new ImdbDAO();
	idMap=new HashMap<>();
	
	}

	public void creaGrafo(int year) {
	
		dao.listAllDirectorByYear(year,idMap);
		grafo= new SimpleWeightedGraph <Director, DefaultWeightedEdge>( DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, idMap.values());
		for (Adiacenza a: dao.listAdiacenze(year,idMap)) {
				Graphs.addEdge(grafo, a.getD1(), a.getD2(), a.getPeso());
			}
			
		
	}
	public int numVertici(){
		return grafo.vertexSet().size();
	}
	public int numArchi(){
		return grafo.edgeSet().size();
	}
	
	public List<Director> getDirectors(){
		List<Director> d= new ArrayList(grafo.vertexSet());
		Collections.sort(d);
		return d;
	}
	
	public List<Director> getAdiacenti (Director d){
		List <Director> directors=Graphs.neighborListOf(grafo, d);
		for(Director dir : directors) {
			dir.setPeso(grafo.getEdgeWeight(grafo.getEdge(d, dir)));
		}
		Collections.sort(directors, new Comparator<Director>() {
		    public int compare(Director d1, Director d2) {
		        return Double.compare(d2.getPeso(), d1.getPeso());
		    }
		});
		return directors;
		
	}
	
	public List<Director> cercaMassimo(int c, Director d) {
		List <Director> parziale= new ArrayList<>();
		parziale.add(d);
		Map <Integer,Director>  map= new HashMap<>();
		map.put(d.getId(), d);
		recursive(map, parziale, c, 0, 0);
		
		return result;
	}
	
	public double pesoCamminoMassimo() {
		if(result!=null) {
			return peso;
		}
		return 0;
	}
	
	private void recursive(Map <Integer,Director>  map,List <Director> parziale, int c, int level, double pesoParziale) {
		int max=-1;
		if(max<level ) {
			result=new ArrayList(parziale);
			peso=pesoParziale;
			max=level;
		}
			
		List <Director> adiacenti= Graphs.neighborListOf(grafo,parziale.get(level));
			for(Director d: adiacenti) {
				if(!map.containsKey(d.getId()) && !d.equals(parziale.get(level))) {
					System.out.println(parziale.get(level)+"  "+d);
					double peso=grafo.getEdgeWeight(grafo.getEdge(parziale.get(level),d));
					System.out.println(peso);
					pesoParziale+=peso;
					parziale.add(d);
					map.put(d.getId(), d);
					if(pesoParziale<c) {
						recursive(map,parziale, c, level+1, pesoParziale);
					}
					parziale.remove(parziale.get(level+1));
					map.remove(d.getId());
					pesoParziale-=peso;
				}
				
				
			}
		
		
		
	}
}
