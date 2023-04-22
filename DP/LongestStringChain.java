/**
 * 

  Given a list of strings, write a function that returns the longest string
  chain that can be built from those strings.


  A string chain is defined as follows: let string A be a string in
  the initial array; if removing any single character from string
  A yields a new string B that's contained in the
  initial array of strings, then strings A and B form
  a string chain of length 2. Similarly, if removing any single character from
  string B yields a new string C that's contained in
  the initial array of strings, then strings A, B, and
  C form a string chain of length 3.


  The function should return the string chain in descending order (i.e., from
  the longest string to the shortest one). Note that string chains of length 1
  don't exist; if the list of strings doesn't contain any string chain formed by
  two or more strings, the function should return an empty array.


  You can assume that there will only be one longest string chain.

Sample Input
strings = ["abde", "abc", "abd", "abcde", "ade", "ae", "1abde", "abcdef"]

Sample Output
["abcdef", "abcde", "abde", "ade", "ae"]


 */

import java.util.*;

//just like longest increasing sequence
//Time: n*m*m + nlogn
//Space: n*m
//n= length of string list, m= size of longest string
class Program {
  public static List<String> longestStringChain(List<String> strings) {

    Collections.sort(strings,(a,b)->a.length()-b.length());
    Map<String,Integer> stringIndexLookup=new HashMap<>();
    for(int i=0;i<strings.size();i++){
      stringIndexLookup.put(strings.get(i),i);
    }

    int[] longestChain=new int[strings.size()];
    int [] indices=new int[strings.size()];
    int maxIdx=0;
    Arrays.fill(indices,-1);
    Arrays.fill(longestChain,1);

    for(int i=1;i<strings.size();i++){
      String str=strings.get(i);
      //remvong each character one by one and checking if there is any entry in stringIndexLookup
      for(int j=0;j<str.length();j++){
        String subStr=str.substring(0,j)+str.substring(j+1);
        if(stringIndexLookup.containsKey(subStr) && longestChain[stringIndexLookup.get(subStr)]+1>longestChain[i]){
          longestChain[i]=longestChain[stringIndexLookup.get(subStr)]+1;
          indices[i]=stringIndexLookup.get(subStr);
        }
        
      }

      if(longestChain[maxIdx]<longestChain[i]){
        maxIdx=i;
      }
    }
    
    
    return getChain(maxIdx,indices,strings);
  }

  static List<String> getChain(int maxIdx, int[] indices, List<String> strings){
    List<String> chain=new ArrayList<>();

    while(maxIdx!=-1){
      chain.add(strings.get(maxIdx));
      maxIdx=indices[maxIdx];
    }

    return chain.size()==1?new ArrayList<>():chain;
  }
}
