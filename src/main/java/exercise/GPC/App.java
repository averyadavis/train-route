package exercise.GPC;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import exercise.GPC.trains.domain.Dist;
import exercise.GPC.trains.domain.Stops;
import exercise.GPC.trains.domain.TrainRoutes;
import exercise.GPC.trains.model.NoSuchRouteException;
import exercise.GPC.trains.model.RouteGraph;

public class App 
{
    public static void main( String[] args )
    {
    	TrainRoutes trainroutes = null;
    	
    	if(args.length != 1) {
           System.out.println( usage() );
           System.exit(1);
    	}
    	
    	try {
			trainroutes = configure(args[0]);
		} catch (IOException e) {
			System.err.println("Error accessing file " + args[0] + ": " + e.getMessage());
			System.exit(2);
		} catch (InvalidConfigurationException e) {
			System.err.println("Invalid config in file " + args[0] + ", must be in form AB5, BC4, CD8, ...");
			System.exit(3);
		}
    	    	
    	String output = "";
    	
    	output = "Output #1: ";  
    	
    	try {
    		output += trainroutes.routeDist("A", "B", "C");
		} catch (NoSuchRouteException e) {
		 	output += "NO SUCH ROUTE";
		}
    	
    	System.out.println(output);
    	
    	output = "Output #2: ";

    	try {
			output += trainroutes.routeDist("A", "D");
		} catch (NoSuchRouteException e1) {
		 	output += "NO SUCH ROUTE";
		}
    	
    	System.out.println(output);
    	
    	output = "Output #3: ";
    	
    	try {
			output += trainroutes.routeDist("A", "D", "C");
		} catch (NoSuchRouteException e1) {
		 	output += "NO SUCH ROUTE";
		}

    	System.out.println(output);
    	
    	output = "Output #4: ";
    	
    	try {
			output += trainroutes.routeDist("A","E","B","C","D");
		} catch (NoSuchRouteException e) {
		 	output += "NO SUCH ROUTE";
		}

    	System.out.println(output);

    	output = "Output #5: ";

    	try {
			output += trainroutes.routeDist("A","E","D");
		} catch (NoSuchRouteException e) {
		 	output += "NO SUCH ROUTE";
		}

    	System.out.println(output);
    	
    	output = "Output #6: ";
    	try {
			output += trainroutes.numRoutesWithStops("C", "C", Stops.MAX.setVal(3));
		} catch (NoSuchRouteException e) {
		 	output += "NO SUCH ROUTE";
		}

    	System.out.println(output);
    	
    	output = "Output #7: ";
		try {
			output += trainroutes.numRoutesWithStops("A", "C", Stops.EXACTLY.setVal(4));
		} catch (NoSuchRouteException e) {
		 	output += "NO SUCH ROUTE";
		}

		System.out.println(output);

    	output = "Output #8: ";
    	try {
			output += trainroutes.shortestRouteDist("A","C");
		} catch (NoSuchRouteException e) {
		 	output += "NO SUCH ROUTE";
		}

    	System.out.println(output);

    	output = "Output #9: ";
    	try {
			output += trainroutes.shortestRouteDist("B","B");
		} catch (NoSuchRouteException e) {
		 	output += "NO SUCH ROUTE";
		}

    	System.out.println(output);

    	output = "Output #10: ";
    	try {
			output += trainroutes.numRoutesWithDist("C", "C", Dist.MAX.setVal(30));
		} catch (NoSuchRouteException e) {
		 	output += "NO SUCH ROUTE";
		}

    	System.out.println(output);

    }
    
    private static String usage() {
    	return "usage: java -jar trainroute.jar file";
    }
    
    private static TrainRoutes configure(String fileName) throws IOException, InvalidConfigurationException  {
    	
    	StringBuffer buf = new StringBuffer();

        Files.lines(Paths.get(fileName), StandardCharsets.UTF_8).forEach(l -> buf.append(l.toUpperCase()));
        
        String g = buf.toString();
        
        if(!g.matches("[0-9A-Za-z\\s,]+"))
        	throw new InvalidConfigurationException(fileName + "format is invalid");
        
        return new TrainRoutes(new RouteGraph(g));
        
    }
}
