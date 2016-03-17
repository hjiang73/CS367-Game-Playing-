///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  TheGame.java
// File:             DirectedGraph.java
// Semester:         CS 367 Fall 2015
//
// Author:           Han Jiang
// Email:            hjiang73@wisc.edu
// CS Login:         hjiang
// Lecturer's Name:  James Skretney
// Lab Section:      002
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     You Wu
// Email:            wu278@wisc.edu
// CS Login:         ywu
// Lecturer's Name:  James Skretney
// Lab Section:      001
//////////////////////////// 80 columns wide //////////////////////////////////
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * DirectedGraph<V> class implements GraphADT with an adjacency lists representation.
 * the graph will be stored in an instance of HashMap<V, ArrayList<V>>. 
 * The Hashmap will map a vertex to its adjacency list, which in turn is an ArrayList<V>.
 * <p>Bugs: None known
 * 
 * @author Han Jiang & You Wu
 */
public class DirectedGraph<V> implements GraphADT<V>{
	private HashMap<V, ArrayList<V>> hashmap;
	//DO NOT ADD ANY OTHER DATA MEMBERS
	//Constructor 1
	//Creates an empty graph.
	public DirectedGraph() {
		hashmap = new HashMap<V,ArrayList<V>>();
	}
	//Constructor 2
	//Creates a graph from a preconstructed hashmap.
	public DirectedGraph(HashMap<V, ArrayList<V>> hashmap) {
		if(hashmap==null)
			throw new IllegalArgumentException();
		this.hashmap = hashmap;
	}

	/**
	 * Add a vertex to the graph
	 * return true if the vertex was added, false if it was already in the graph.
	 * @param V vertex
	 * @return boolean
	 */
	@Override
	public boolean addVertex(V vertex) {
        if(vertex==null)
        	throw new IllegalArgumentException();
		if(hashmap.containsKey(vertex))
			return false;
		hashmap.put(vertex, new ArrayList<V>());
		return true;
	}

	/**
	 * Insert a directed Edge V into the graph.
	 * return true if the Edge<T> was added, false if from already has this Edge<T>
	 * @throws IllegalArgumentException
	 * if from/to are not verticies in the g
	 * @param V v1
	 * @param V v2
	 * @return boolean
	 */
	@Override
	public boolean addEdge(V v1, V v2) {
		if(v1==null||v2==null)
			throw new IllegalArgumentException();
		if(!hashmap.containsKey(v1)||!hashmap.containsKey(v2))
			throw new IllegalArgumentException();
		if(v1 == v2)
			return false;
		if(hashmap.get(v1).contains(v2))
			return false;
		hashmap.get(v1).add(v2);
		return true;

	}

	/**
	 * Given a vertex and return all vertexes that it directs to 
	 * @param V vertex
	 * @return a set of all neighbors
	 */
	@Override
	public Set<V> getNeighbors(V vertex) {
		if(vertex==null)
			throw new IllegalArgumentException();
		if(!hashmap.containsKey(vertex))
			throw new IllegalArgumentException();
		ArrayList<V> tmp = hashmap.get(vertex);
		Set<V> neighbors = new HashSet<V>();
		for(V v : tmp){
			neighbors.add(v);}
		return neighbors;
	}

	/**
	 * Remove an Edge<T> from the graph if and only if 
	 * there is an edge between v1 and v2
	 * @param V v1
	 * @param V v2
	 */
	@Override
	public void removeEdge(V v1, V v2) {
		if(hashmap.containsKey(v1)&&hashmap.containsKey(v2)&&hashmap.get(v1).contains(v2)){
			hashmap.get(v1).remove(v2);
		}

	}

	/**
	 * Return all vertexes in the graph
	 * @return a set of all vertexes
	 */
	@Override
	public Set<V> getAllVertices() {
		return hashmap.keySet();

	}

	@Override
	//Returns a String that depicts the Structure of the Graph
	//This prints the adjacency list
	//This has been done for you
	//DO NOT MODIFY
	public String toString() {
		StringWriter writer = new StringWriter();
		for (V vertex: this.hashmap.keySet()) {
			writer.append(vertex + " -> " + hashmap.get(vertex) + "\n");
		}
		return writer.toString();
	}

}