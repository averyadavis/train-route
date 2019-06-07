package exercise.GPC.trains.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import exercise.GPC.trains.domain.TrainRoutes;
import exercise.GPC.trains.model.NoSuchRouteException;
import exercise.GPC.trains.model.RouteGraph;


public class TrainRoutesTest {
	private static RouteGraph routes = new RouteGraph("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
	
	
	@Test
	public void willCalcRouteDist() {
		TrainRoutes trainRoutes = new TrainRoutes(routes);
		
		int expected = 22;
		
		try {
			int dist = trainRoutes.routeDist("A","E","B","C","D");
			assertEquals(expected, dist);
		} catch (NoSuchRouteException e) {
			fail("Exception thrown - " + e.getMessage());		}

	}

	@Test
	public void willThrowExceotionforInvalidRoute() {
		TrainRoutes trainRoutes = new TrainRoutes(routes);
		
		try {
			int dist = trainRoutes.routeDist("A", "E", "D");
			fail("Exception not thrown");
		} catch (NoSuchRouteException e) {
			assertNotNull(e.getMessage());		
		}

	}

	@Test
	public void willCalcNumRoutesWithExactStops() {
		TrainRoutes trainRoutes = new TrainRoutes(routes);
		
		int expected = 3;
		
		try {
			int num = trainRoutes.numRoutesWithStops("A", "C", Stops.EXACTLY.setVal(4));
			assertEquals(expected,num);
		} catch (NoSuchRouteException e) {
			fail("Exception thrown - " + e.getMessage());		
		}
		
	}

	@Test
	public void willCalcNumRoutesWithMaxStops() {
		TrainRoutes trainRoutes = new TrainRoutes(routes);
		
		int expected = 2;
		
		try {
			int num = trainRoutes.numRoutesWithStops("C", "C", Stops.MAX.setVal(3));
			assertEquals(expected,num);
		} catch (NoSuchRouteException e) {
			fail("Exception thrown - " + e.getMessage());		
		}
		
	}

	
	@Test
	public void willCalcNumRoutesWithMaxDist() {
		TrainRoutes trainRoutes = new TrainRoutes(routes);
		
		int expected = 7;
		
		try {
			int num = trainRoutes.numRoutesWithDist("C", "C", Dist.MAX.setVal(30));
			assertEquals(expected,num);
		} catch (NoSuchRouteException e) {
			fail("Exception thrown - " + e.getMessage());		
		}
		
	}

	@Test
	public void willCalcDistForShortestRoute() {
		TrainRoutes trainRoutes = new TrainRoutes(routes);
		
		int expected = 9;
		
		try {
			int dist = trainRoutes.shortestRouteDist("A","C");
			assertEquals(expected,dist);
		} catch (NoSuchRouteException e) {
			fail("Exception thrown - " + e.getMessage());		
		}
		
	}

}
