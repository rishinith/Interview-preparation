import java.util.*;

//Space: O(m)
//Time: O(m+n) m=length of substring, n=length of main string

//HACK: the actual search function and build pattern function work in similar fashion.
// This is easier than Rabin karp
//If you forget the implementation details watch algoexpert video: https://www.algoexpert.io/questions/knuth-morris-pratt-algorithm

class Program {
  public static boolean knuthMorrisPrattAlgorithm(String string, String substring) {
    var pattern=  buildPattern(substring);
    return doesMatch(string, substring, pattern);
  }

  static boolean doesMatch(String string, String substring, int[] pattern){
    int i=0;
    int j=0;

    //while condition can be i+substring.length()-j<=string.length()
    while(i<string.length()){
      if(string.charAt(i)==substring.charAt(j)){
        //found the pattern
        if(j==substring.length()-1){
          return true;
        }
        i++;
        j++;
      }
      else if(j>0){
        //Go to the last matched prefix's next element
        j=pattern[j-1]+1;
      }
      else{
        i++;
      }
    }
    return false;
  }

  static int[] buildPattern(String substring){

    int[] pattern=new int[substring.length()];
    Arrays.fill(pattern,-1);
    int j=0; int i=1;
    while(i<substring.length()){
      if (substring.charAt(i)==substring.charAt(j)){
        pattern[i]=j;
        i++;
        j++;
      }
      else if(j>0){
        j=pattern[j-1]+1;
      }
      else{
        i++;
      }
    }
    return pattern;
    
  }
}
