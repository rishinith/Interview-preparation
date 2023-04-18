/**
 * 
 * 
    You're given an array of integers prices of length
    n with the retail prices of various quantities of juice. Each
    index in this array corresponds to the price of that amount of juice. For
    example, prices[2] would be the retail price of 2 units of
    juice.
  

  
    You have n - 1 total units of juice. For example, if the length
    of prices is 5, then you would have 4 total units of juice.
    Write a function to determine the optimal way to bottle the juice such that
    it maximizes revenue. This function should return a list of all of the
    juice quantities required in ascending order.
  

  
    Note that the first value in the prices array will always be 0,
    because there is no value in no juice. All other values will be positive
    integers. Additionally, a larger quantity of juice will not always be more
    expensive than a smaller quantity. For simplicity, all of the test cases
    only have one possible solution.
  

  Sample Input
  prices = [0, 1, 3, 2]
  Sample Output
  [1, 2] // We have 3 total units of juice,
// because the length of prices is 4.
// To maximize revenue, we split the juice into
// quantities of 1 and 2, giving a revenue of 1 + 3 = 4



 */

import java.util.*;

//Time: O(n^3) Space(n^2)
class Program {
  public List<Integer> juiceBottling(int[] prices) {

   int numSizes=prices.length;
   int[] maxProfit=new int[numSizes];
    Map<Integer,List<Integer>> solutions=new HashMap<>();

    for(int size=0;size<prices.length;size++){
      for(int dividingPoint=0;dividingPoint<=size;dividingPoint++){
           int possibleProfit=maxProfit[size-dividingPoint]+prices[dividingPoint];
          if(possibleProfit>maxProfit[size]){
             maxProfit[size]=possibleProfit;
            var dividingPointList=solutions.get(size-dividingPoint);
           dividingPointList=dividingPointList==null?new ArrayList<Integer>():dividingPointList;
            var sizeList=new ArrayList<Integer>();
            sizeList.add(dividingPoint);
            sizeList.addAll(dividingPointList);
            solutions.put(size,sizeList);
          }
      }
    }
    
    return solutions.get(numSizes-1);
     
  }
}


import java.util.*;
//Time: O(n^2) Space(n)

class Program {
  public List<Integer> juiceBottling(int[] prices) {

   int numSizes=prices.length;
   int[] maxProfit=new int[numSizes];
    int dividingPoints[]=new int[numSizes];

    for(int size=0;size<prices.length;size++){
      for(int dividingPoint=0;dividingPoint<=size;dividingPoint++){
           int possibleProfit=maxProfit[size-dividingPoint]+prices[dividingPoint];
          if(possibleProfit>maxProfit[size]){
             maxProfit[size]=possibleProfit;
             dividingPoints[size]=dividingPoint; 
          }
      }
    }

    List<Integer> result=new ArrayList<>();

    int point=numSizes-1;
    
    while(point>0){
      result.add(dividingPoints[point]);
      point=point-dividingPoints[point];
    }
    
    return result;
     
  }
}
