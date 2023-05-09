/**
 * 

  For the purpose of this question, the phrases "airport route" and "airport
    connection" are used interchangeably.


  You're given a list of airports (three-letter codes like "JFK"),
  a list of routes (one-way flights from one airport to another like
  ["JFK", "SFO"]), and a starting airport.


  Write a function that returns the minimum number of airport connections
  (one-way flights) that need to be added in order for someone to be able to
  reach any airport in the list, starting at the starting airport.


  Note that routes only allow you to fly in one direction; for instance,
  the route
  ["JFK", "SFO"] only allows you to fly from "JFK" to
  "SFO".


  Also note that the connections don't have to be direct; it's okay if an
  airport can only be reached from the starting airport by stopping at other
  airports first.

Sample Input
airports = [
  "BGI", "CDG", "DEL", "DOH", "DSM", "EWR", "EYW", "HND", "ICN",
  "JFK", "LGA", "LHR", "ORD", "SAN", "SFO", "SIN", "TLV", "BUD",
]
routes = [
  ["DSM", "ORD"],
  ["ORD", "BGI"],
  ["BGI", "LGA"],
  ["SIN", "CDG"],
  ["CDG", "SIN"],
  ["CDG", "BUD"],
  ["DEL", "DOH"],
  ["DEL", "CDG"],
  ["TLV", "DEL"],
  ["EWR", "HND"],
  ["HND", "ICN"],
  ["HND", "JFK"],
  ["ICN", "JFK"],
  ["JFK", "LGA"],
  ["EYW", "LHR"],
  ["LHR", "SFO"],
  ["SFO", "SAN"],
  ["SFO", "DSM"],
  ["SAN", "EYW"],
]
startingAirport = "LGA"

Sample Output
3 // ["LGA", "TLV"], ["LGA", "SFO"], and ["LGA", "EWR"]

 */

/**
 * 
 * Since the given graph is directed, we cannot use the Union-Find algorithm. 
 * To find all airports that are not reachable from the starting airport, we can follow the below steps:

1) Perform a depth-first search (DFS) starting from the given starting airport to identify all reachable airports.

2) Create a set of all airports in the graph, and remove all reachable airports from this set. 
This set will now contain only those airports that are not reachable from the starting airport.

3) Iterate through each unreachable airport in the set created in step 2, and keep removing all airports that are reachable from it. 
By the end of this process, we will be left with a list of airports that are not reachable from the starting airport.

4) The list obtained in step 3 will be our answer.

The time complexity of this solution is O(N*(N+E)), where N is the number of airports and E is the number of routes in the graph. 
The space complexity is O(N+E), where N is the number of airports and E is the number of routes in the graph.
 */

class Program {
    public static int airportConnections(
        List < String > airports, List < List < String >> routes, String startingAirport) {

        Map < String, List < String >> graph = getGraph(airports, routes);

        Set < String > unreachablePorts = new HashSet < > (airports);

        dfs(startingAirport, graph, unreachablePorts);

        System.out.println(unreachablePorts);
        for (String port: airports) {
            if (unreachablePorts.contains(port)) {
                dfs(port, graph, unreachablePorts);
                //adding it back because in dfs stack its removed
                unreachablePorts.add(port);
            }
        }

        return unreachablePorts.size();
    }


    static void dfs(String port, Map < String, List < String >> graph, Set < String > unreachablePorts) {
        if (!unreachablePorts.contains(port)) {
            return;
        }

        unreachablePorts.remove(port);

        for (String neiPort: graph.get(port)) {
            dfs(neiPort, graph, unreachablePorts);
        }
    }



    static Map < String, List < String >> getGraph(List < String > airports, List < List < String >> routes) {

        Map < String, List < String >> result = new HashMap < > ();

        for (String port: airports) {
            result.put(port, new ArrayList < > ());
        }

        for (var route: routes) {
            result.get(route.get(0)).add(route.get(1));
        }

        return result;

    }

}