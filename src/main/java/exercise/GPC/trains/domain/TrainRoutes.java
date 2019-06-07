package exercise.GPC.trains.domain;

import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import exercise.GPC.trains.model.NoSuchRouteException;
import exercise.GPC.trains.model.RouteGraph;

public class TrainRoutes {
		
	private RouteGraph model;
		
	public TrainRoutes(RouteGraph model) {this.model = model; }	
	
	public int routeDist(String first, String second, String ...rest) throws NoSuchRouteException{
		List<String> route = new Vector<>();
		
		route.add(first);
		route.add(second);
		
		Stream.of(rest).forEach(stop -> route.add(stop));
		
		return model.distFor(route);
		
	}
	public int numRoutesWithStops(String from, String to, Stops stops) throws NoSuchRouteException {
	
		List<List<String>> routes = model.allRoutes(from, to); 

		switch(stops.getOp())
		{
			case "<":
				routes = routes.stream()
				   .filter(r -> (r.size() - 1) <= stops.getVal())
				   .collect(Collectors.toList());

				break;
				
			case "=":
				routes = routes.stream()
							.filter(r -> (r.size() - 1) == stops.getVal())
							.collect(Collectors.toList());
				break;
		}
		
		return routes.size();
		
	}
	public int numRoutesWithDist(String from, String to, Dist dist) throws NoSuchRouteException {
		List<List<String>> routes = model.allRoutes(from, to); 

		switch(dist.getOp())
		{
			case "<":
				routes = routes.stream()
								.filter(r -> distFor(r) < dist.getVal())
								.collect(Collectors.toList());
				break;
				
			case "=":
				
				routes = routes.stream()
								.filter(r -> distFor(r) == dist.getVal())
								.collect(Collectors.toList());
				break;
		}
		
		return routes.size();
		
	}
	
	private int distFor(List<String> r) {
		int dist;
		
		try {
			   dist = model.distFor(r);
		} catch (NoSuchRouteException e) {
			dist = 0;
		}
		
		return dist;
	}

	public int shortestRouteDist(String from, String to) throws NoSuchRouteException {

		return  from.equals(to) ? model.shortestCycleDist(from) : model.shortestRouteDist(from, to);
	}
		
}
