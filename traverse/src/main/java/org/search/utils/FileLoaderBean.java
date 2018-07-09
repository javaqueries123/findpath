package org.search.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.search.models.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Scope(value="singleton")
@Component
public class FileLoaderBean {

	private static final Logger logger= LoggerFactory.getLogger(FileLoaderBean.class);

	public FileLoaderBean() {
		
	}
	 
	 String YES="yes";
	 String NO="no";
	 HashMap<String, Node> cityNodeMap= new HashMap<String, Node>();

	 private Node parentNode=null;
	 
	 @PostConstruct
	 public void postConstruct() {
	    Path path = null;

	    Stream<String> lines = null;
	    
		try {
			path = Paths.get(getClass().getClassLoader().getResource("city.txt").toURI());
			lines = Files.lines(path);
			
			parentNode= new Node();
			parentNode.city="parent";
			cityNodeMap.put("parent", parentNode);
			
			lines.forEach(line -> {
				processCitiesFromLine(line);
			});
			lines.close();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
		}
	 }
	 
	 private void processCitiesFromLine(String line){
			StringTokenizer stringTokenizer= new StringTokenizer(line, ",");
			String previousCity=null;
			while(stringTokenizer.hasMoreTokens()) {
				String city =stringTokenizer.nextToken();
				city=city.trim().toLowerCase();

				if(previousCity ==null ) {
					Node node= null;
					Node cityNode=getNode(city);
					if(cityNode ==null) {
						node=addNode("parent", city);
						cityNodeMap.put(city, node);
					}
				}else {
					Node node =addNode(previousCity,city);
					cityNodeMap.put(city, node);
				}
				previousCity=city;
			}
	 }
	 
	 private Node addNode(String origin, String destination) {
		 Node sourceNode=getNode(origin);
		 Node destinationNode= new Node();
		 destinationNode.city=destination;
		 sourceNode.adjacentCities.add(destinationNode);
		 logger.debug("--> Adding Node  destination :"+destinationNode.city+"   origin "+sourceNode.city);
		 
		 return destinationNode;
	 }
	 private Node getNode(String cityName) {
		 Node node=null;
		 node=cityNodeMap.get(cityName);
		 return node;
	 }
	 
	 private boolean  isOriginAndOrDestinationEmpty(String origin, String destination){
		 if (origin ==null || destination==null){
			 return true;
		 }else {
			 return false;
		 }
	 }
	 
	 public boolean  isOriginAndOrDestinationEmpty(Node origin, Node destination){
		 if (origin ==null || destination==null){
			 return true;
		 }else {
			 return false;
		 }
	 }
	 
	 public String hasPathBetweenOriginAndDestination(String origin, String destination) {
		 			 String result=NO;
		 			
					 if(isOriginAndOrDestinationEmpty(origin, destination)) {
						 return result;
					 }
					 
					 HashSet<String> visitedCities=new HashSet<String>();
					 LinkedList<Node> nextToVisitCities=new LinkedList<Node>();
					 
					 Node originCityNode=getNode(origin);
					 Node destinationCityNode=getNode(destination);
					 
					 if(isOriginAndOrDestinationEmpty(originCityNode, destinationCityNode)) {
						 return result;
					 }
					 
					 logger.debug("origin "+origin+" destination  "+destination);
					 
					 nextToVisitCities.push(originCityNode);
					 
					 while(!nextToVisitCities.isEmpty()) {
						 
						 Node node= nextToVisitCities.remove();
						 logger.debug("Visiting City "+node.city);
						 
						 if(node==destinationCityNode) {
							 return  YES;
						 }
						 
						 logger.debug("Visiting City "+node.city +"  destinationCityNode "+destinationCityNode.city);
						 
						 if(visitedCities.contains(node.city)) {
							 continue;
						 }
						 
						 visitedCities.add(node.city);
						 logger.debug("Adding Visited City "+node.city );
						 
						 for (Node chidlCityNode : node.adjacentCities) {
							 logger.debug("origin "+origin+" destination  "+destination+"  Adding Child Node to Visit "+chidlCityNode.city);
							 nextToVisitCities.add(chidlCityNode);
						 }
					 }
		 		 
		 return result;
	 }

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}
}
