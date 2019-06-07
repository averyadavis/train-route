package exercise.GPC.trains.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.alg.cycle.TarjanSimpleCycles;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.alg.shortestpath.KShortestSimplePaths;

public class RouteGraph {
	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> g;
	
	public RouteGraph(String routes) {
		initGraphFromString(routes);		
	}
	
    public int distFor(List<String> route) throws NoSuchRouteException {
    	String from = route.get(0);
    	String to = route.get(route.size() - 1);

    	AllDirectedPaths<String, DefaultWeightedEdge> pathFinder = new AllDirectedPaths<>(g);
    	
    	List<GraphPath<String, DefaultWeightedEdge>> found_paths = pathFinder.getAllPaths(from, to, false, g.vertexSet().size() * 3);
    	// FIXME forget simple graphs and find standard boundary condition to allow circular graphs
    	
    	GraphPath<String, DefaultWeightedEdge> path = null;
    	
    	for(GraphPath<String, DefaultWeightedEdge> contender : found_paths) {
    		if(contender.getVertexList().equals(route)) {
    			path = contender;
    			break;
    		}
    	}
    	
    	if(path == null)
    		throw new NoSuchRouteException("No route " + route.toString());
    	
    	return (int) path.getWeight();
    }
    
    public int shortestRouteDist(String from, String to) throws NoSuchRouteException {
    	DijkstraShortestPath<String, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(g);
    	        
    	 GraphPath<String, DefaultWeightedEdge> shortestPath = dijkstraAlg.getPath(from, to);
    	 

    	
    	 if(shortestPath == null)
    		 throw new NoSuchRouteException("No route from " + from + " to " + to);
    		 
    	 return (int) shortestPath.getWeight();
    }

    public int shortestCycleDist(String target) throws NoSuchRouteException {
    	TarjanSimpleCycles<String, DefaultWeightedEdge> tarjanAlg = new TarjanSimpleCycles<>(g);
    	        
    	 List<List<String>> allCycles = tarjanAlg.findSimpleCycles();
    	 
    	 List<List<String>> targetCycles = allCycles.stream()
    			 							.filter(c -> c.get(0).equals(target))
    			 							.collect(Collectors.toList());
    	    	
    	 List<Integer> cycleDists = new Vector<>();
    	 List<String> routeHome = new Vector<>();

    	 for(List<String> targetCycle : targetCycles) 
 		 { 		
    		 
    		routeHome.add(targetCycle.get(targetCycle.size() -1 ));
    		routeHome.add(target);
						
			try {
				int dist = distFor(targetCycle) + distFor(routeHome);
				cycleDists.add(dist);
			} catch (NoSuchRouteException e) {
				//TODO: should really warn someone about this!
				continue;
			} finally {
				routeHome.clear();
			}

 		 }
    	 
    	 return cycleDists.stream().min(Comparator.comparing(Integer::valueOf)).get();
    }
    
    public List<List<String>> allRoutes(String from, String to) throws NoSuchRouteException {

    	List<List<String>> paths = new Vector<>();
    	AllDirectedPaths<String, DefaultWeightedEdge> pathFinder = new AllDirectedPaths<>(g);
    	
    	List<GraphPath<String, DefaultWeightedEdge>> found_paths = pathFinder.getAllPaths(from, to, false, g.vertexSet().size() * 3);
    	
    	if(found_paths == null)
    		throw new NoSuchRouteException("No route from " + from + " to " + to);
    	    	
    	found_paths.forEach(p -> {if(p.getVertexList().size() > 1) paths.add(p.getVertexList());});
    	    	
    	return paths;
    	
    }

    private void initGraphFromString(String graph) {
    	
    	List<String> vertices = Stream.of(graph.replaceAll("\\s+","").replaceAll("[^a-zA-Z]",""))
    			   .map(r -> r.split(""))
    	           .flatMap(Arrays::stream)
    	           .distinct()
    	           .collect(Collectors.toList());
    	
    	g = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    	
    	vertices.forEach(v -> g.addVertex(v));
    	
    	for(String route : graph.replaceAll("\\s+","").split(",")) {
    		String from = String.valueOf(route.charAt(0));
    		String to = String.valueOf(route.charAt(1));
    		int dist = Integer.parseInt(String.valueOf(route.charAt(2)));
    		
    		DefaultWeightedEdge e = g.addEdge(from, to);
    		g.setEdgeWeight(e, dist);    		    		
    	}    	    	

    }
}
