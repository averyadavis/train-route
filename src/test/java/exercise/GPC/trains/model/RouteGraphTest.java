package exercise.GPC.trains.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.Test;

public class RouteGraphTest {

	@Test
	public void willBuildRouteGraph() {
		RouteGraph r = new RouteGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
		
		List<List<String>> routes = null;
		
		try {
			routes = r.allRoutes("A", "B");
		} catch (NoSuchRouteException e) {
			fail("Exception thrown - " + e.getMessage());
		}
		
		assertNotNull(routes);		
	}

	@Test
	public void willCalcRouteDist() {
		RouteGraph r = new RouteGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");

		int expected = 5;
		List<String> route = Arrays.asList( new String[]{"A","D"});
	
		try {
			int dist = r.distFor(route);
			assertEquals(dist, expected);
		} catch (NoSuchRouteException e) {
			fail("Exception thrown - " + e.getMessage());
		}		
	}

	@Test
	public void willCalcCycleDist() {
		RouteGraph r = new RouteGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");

		int expected = 9;
	
		try {
			int dist = r.shortestCycleDist("B");
			assertEquals(dist, expected);
		} catch (NoSuchRouteException e) {
			fail("Exception thrown - " + e.getMessage());
		}		
	}
	
	
	@Test
	public void willThrowExceptionInvalidRoute() {
		RouteGraph r = new RouteGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");

		List<String> route = Arrays.asList( new String[]{"A","E","D"});
	
		try {
			r.distFor(route);
			fail("Exception not thrown");
		} catch (NoSuchRouteException e) {
			assertNotNull(e.getMessage());
		}
		
	}
	
	
	@Test
	public void willCalcShortestRouteDist() {
		RouteGraph r = new RouteGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");

		int expected = 9;
	
		try {
			int dist = r.shortestRouteDist("A","C");
			assertEquals(expected, dist);
		} catch (NoSuchRouteException e) {
			fail("Exception thrown - " + e.getMessage());
		}
	}

	@Test
	public void willFindAllPaths() {
		RouteGraph r = new RouteGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");

		int expected_size = 2;
		List<List<String>> expected = new Vector<>();
		
		expected.add(Arrays.asList( new String[]{"C","D","C"}));
		expected.add(Arrays.asList( new String[]{"C","E","B","C"}));
		
		try {
			List<List<String>> paths = r.allRoutes("C", "C").stream().filter(p -> p.size() <= 4).collect(Collectors.toList());
			assertEquals(expected_size,paths.size());
			assertEquals(expected, paths);
		} catch (NoSuchRouteException e) {
			fail("Exception thrown - " + e.getMessage());
		}
	}

}
