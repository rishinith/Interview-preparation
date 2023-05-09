/**
 * There is a new alien language that uses the English alphabet. However, the order among the letters is unknown to you.

You are given a list of strings words from the alien language's dictionary, where the strings in words are 
sorted lexicographically
 by the rules of this new language.

Return a string of the unique letters in the new alien language sorted in lexicographically increasing order by the new language's rules. If there is no solution, return "". If there are multiple solutions, return any of them.

 

Example 1:

Input: words = ["wrt","wrf","er","ett","rftt"]
Output: "wertf"
Example 2:

Input: words = ["z","x"]
Output: "zx"
Example 3:

Input: words = ["z","x","z"]
Output: ""
Explanation: The order is invalid, so return "".
 */

class Solution {
    public String alienOrder(String[] words) {
        
    Map<Character,List<Character>> adjMatrix=new HashMap<>();
    Map<Character,Integer> inDegree=new HashMap<>();

    for(String word: words){
        for(char c: word.toCharArray()){
            adjMatrix.putIfAbsent(c,new ArrayList<>());
            inDegree.putIfAbsent(c,0);
        }
    }
    
    for(int i=0;i<words.length-1;i++){

            String word1=words[i];
            String word2=words[i+1];

          
            int s1=0;
            int s2=0;
            //run the loop while equal charcaters are there
            while(s1<word1.length() && s1<word2.length() && word1.charAt(s1)==word2.charAt(s2)){
                s1++;
                s2++;
            }

            //word1 is prefix of word2
            if(s1<word1.length() && s2>=word2.length()){
                return "";
            }

             //if its not the end, add the edge
            if(s1<word1.length() && s1<word2.length()){
                var c1=word1.charAt(s1);
                var c2=word2.charAt(s2);

                adjMatrix.get(c1).add(c2);
                inDegree.put(c2,inDegree.get(c2)+1);
            }
    }

    StringBuilder sb=new StringBuilder();
    Queue<Character> queue=new LinkedList<>();

    for(char c:inDegree.keySet()){
        if(inDegree.get(c)==0){
            queue.add(c);
        }
    }


    while(!queue.isEmpty()){
        char c=queue.poll();
        sb.append(c);

        for(var child:adjMatrix.get(c)){
            var inDegreeCount=inDegree.get(child);
            inDegreeCount--;
            inDegree.put(child,inDegreeCount);
            if(inDegreeCount==0){
                queue.add(child);
            }
        }
    }


    if(sb.length()==inDegree.size()){
        return sb.toString();
    }
    return "";
        

    }
}