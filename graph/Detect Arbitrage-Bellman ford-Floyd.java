/**
 * 

  You're given a two-dimensional array (a matrix) of equal height and width that
  represents the exchange rates of arbitrary currencies. The length of the array
  is the number of currencies, and every currency can be converted to every
  other currency. Each currency is represented by a row in the array, where
  values in that row are the floating-point exchange rates between the row's
  currency and all other currencies, as in the example below.

         0:USD 1:CAD  2:GBP 
  0:USD [  1.0, 1.27, 0.718] 
  1:CAD [ 0.74,  1.0,  0.56] 
  2:GBP [ 1.39, 1.77,   1.0]


  In the matrix above, you can see that row 0 represents USD, which
  means that row 0 contains the exchange rates for
  1 USD to all other currencies. Since row
  1 represents CAD, index 1 in the USD row contains
  the exchange for 1 USD to CAD. The currency labels are listed
  above to help you visualize the problem, but they won't actually be included
  in any inputs and aren't relevant to solving this problem.


  Write a function that returns a boolean representing whether an arbitrage
  opportunity exists with the given exchange rates. An arbitrage occurs if you
  can start with C units of one currency and execute a series of
  exchanges that lead you to having more than C units of the same
  currency you started with.


  Note: currency exchange rates won't represent real-world exchange rates, and
  there might be multiple ways to generate an arbitrage.

Sample Input
exchangeRates = [
  [   1.0, 0.8631, 0.5903],
  [1.1586,    1.0, 0.6849],
  [1.6939,   1.46,    1.0],
]

Sample Output
true 


 */

//Hint: Ultimately we have to dtecet cycles where edge products is greater than 1
// We can use Bellman ford or Floyd Warshall Algo
// Since graph is well connected Bellman ford can also work, we can start with any node.
//Note: Bellman ford and Floyd works only on directed graph, if graph is undirected, add both the edge i.e a to b and b to a for all the edge

import java.util.*;

//Sol1:Bellman Ford: T: n^3 S:n
class Program {
  
  public boolean detectArbitrage(ArrayList<ArrayList<Double>> exchangeRates) {
    List<List<Double>> er=new ArrayList<>();
    for(var row:exchangeRates){
      er.add(row);
    }
    return foundNegativeWeightWeightCycle(er,0);
}

  boolean foundNegativeWeightWeightCycle(List<List<Double>> graph, int start){
    double dist[]=new double[graph.size()];
    Arrays.fill(dist, Integer.MAX_VALUE);
    //not dist[start]=0 modifed bellman ford, 
    dist[start]=1;

    for(int i=1;i<=graph.size()-1;i++){
      if (!relaxEdgesAndUpdateDistances(graph,dist)){
        return false;
      }
    }

    return relaxEdgesAndUpdateDistances(graph,dist);
  }

/**
Modifying Bellman Ford Algo to relax edges and update distances.
Instead of saving sum of edges in distance, keep the product of edges.
**/
  boolean relaxEdgesAndUpdateDistances(List<List<Double>> graph, double[] dist){
    boolean updated=false;

    for(int s=0;s<graph.size();s++){
      for(int d=0;d<graph.size();d++){
        double wt=graph.get(s).get(d);
        //modifed bellman ford
        double newDistanceToDestination=dist[s]*wt;
        if(newDistanceToDestination>dist[d]){
          updated=true;
          dist[d]=newDistanceToDestination;
        }
      }
    }

    return updated;
  }

}



//Sol2: Bellman for with modyign the dges to log scale.
//if a*b>1, log(a)+log(b)>0
//-log(a)-log(b)<0
//so we can simple detect negative cycles as a standard approach suing bellman ford
//T: n^3 S:n^2
import java.util.*;

class Program {

List<List<Double>> convertToLogMatrix(ArrayList<ArrayList<Double>> exchangeRates){
  var result=new ArrayList<List<Double>>();
  for(var row:exchangeRates){

    var list=new ArrayList<Double>();
    for(var rate:row){
      list.add(-Math.log10(rate));
    }
    result.add(list);
  }

  return result;
}
  
  public boolean detectArbitrage(ArrayList<ArrayList<Double>> exchangeRates) {
    var logExchangeRates=convertToLogMatrix(exchangeRates);
    return foundNegativeWeightWeightCycle(logExchangeRates,0);
}

  boolean foundNegativeWeightWeightCycle(List<List<Double>> graph, int start){
    double dist[]=new double[graph.size()];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[start]=0;

    for(int i=1;i<=graph.size()-1;i++){
      if (!relaxEdgesAndUpdateDistances(graph,dist)){
        return false;
      }
    }

    return relaxEdgesAndUpdateDistances(graph,dist);
  }

  boolean relaxEdgesAndUpdateDistances(List<List<Double>> graph, double[] dist){
    boolean updated=false;

    for(int s=0;s<graph.size();s++){
      for(int d=0;d<graph.size();d++){
        double wt=graph.get(s).get(d);
        double newDistanceToDestination=dist[s]+wt;
        if(newDistanceToDestination<dist[d]){
          updated=true;
          dist[d]=newDistanceToDestination;
        }
      }
    }

    return updated;
  }

}


/**
 * Using Floyd Warshall Algo to detect cycles where edge multiplication is greater than 1.

 */

import java.util.*;

class Program {

  private static final int INF = Integer.MAX_VALUE;

  public boolean detectArbitrage(ArrayList < ArrayList < Double >> exchangeRates) {
    int n = exchangeRates.size();

    //memory 2d table to keep updating the maximum exchange rate multiplication we have found
    double cost[][] = new double[n][n];

    for (int v = 0; v < n; v++) {
      for (int u = 0; u < n; u++) {
        // Initially, the cost would be the same as the weight of the edge
        cost[v][u] = exchangeRates.get(v).get(u);
      }
    }
    // Run Floyd–Warshall
    for (int k = 0; k < n; k++) {
      for (int v = 0; v < n; v++) {
        for (int u = 0; u < n; u++) {
          // If vertex `k` is on the shortest path from `v` to `u`,
          // then update the value of cost[v][u]

          if (cost[v][k] != INF && cost[k][u] != INF &&
            cost[v][k] * cost[k][u] > cost[v][u]) {
            // cost[v][u] = cost[v][k] + cost[k][u];  Modiefied version of distance sum
            cost[v][u] = cost[v][k] * cost[k][u];
          }
        }

        // If diagonal elements become negative, the
        // graph contains a negative-weight cycle
        //cost[v][v] < 0 modified version of negative check
        if (cost[v][v] > 1) {
          return true;
        }
      }
    }

    return false;
  }
}