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



//Using DFS
//Time: n*m*m
//Space: n*m
//n= length of string list, m= size of longest string
class Solution {

    private int dfs(Set<String> words, Map<String, Integer> memo, String currentWord) {
        // If the word is encountered previously we just return its value present in the map (memoization).
        if (memo.containsKey(currentWord)) {
            return memo.get(currentWord);
        }
        // This stores the maximum length of word sequence possible with the 'currentWord' as the
        int maxLength = 1;
        StringBuilder sb = new StringBuilder(currentWord);

        // creating all possible strings taking out one character at a time from the `currentWord`
        for (int i = 0; i < currentWord.length(); i++) {
            sb.deleteCharAt(i);
            String newWord = sb.toString();
            // If the new word formed is present in the list, we do a dfs search with this newWord.
            if (words.contains(newWord)) {
                int currentLength = 1 + dfs(words, memo, newWord);
                maxLength = Math.max(maxLength, currentLength);
            }
            sb.insert(i, currentWord.charAt(i));
        }
        memo.put(currentWord, maxLength);

        return maxLength;
    }

    public int longestStrChain(String[] words) {
        Map<String, Integer> memo = new HashMap<>();
        Set<String> wordsPresent = new HashSet<>();
        Collections.addAll(wordsPresent, words);
        int ans = 0;
        for (String word : words) {
            ans = Math.max(ans, dfs(wordsPresent, memo, word));
        }
        return ans;
    }
}
