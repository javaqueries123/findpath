package org.search.models;

import java.util.LinkedList;

public class Node {
	
	public int id;
	public String city;
	
	public LinkedList<Node> adjacentCities=new LinkedList<Node>();
	
	
}
