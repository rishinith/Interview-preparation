/**
 * 127. Word Ladder
A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:

Every adjacent pair of words differs by a single letter.
Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
sk == endWord
Given two words, beginWord and endWord, and a dictionary wordList,
 return the number of words in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.

Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
Output: 5
Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> cog", which is 5 words long.

Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
Output: 0
Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
 */

//Time: O(N*26*M*M) Space: O(N*M)  N is total no of words, M is length of maximum word
    public int ladderLength(String start, String end, List < String > wordList) {

        // Use queue to help BFS
        Queue < String > queue = new LinkedList < String > ();
        queue.add(start);
        Set < String > dict = new HashSet < > (wordList);
        dict.remove(start);

        int level = 0;

        while (!queue.isEmpty()) {

            level++;

            int size = queue.size();

            for (int s = 0; s < size; s++) {
                String str = queue.poll();

                if (str.equals(end)) {
                    return level;
                }

                // Modify str's each character (so word distance is 1)
                for (int i = 0; i < str.length(); i++) {
                    char[] chars = str.toCharArray();

                    for (char c = 'a'; c <= 'z'; c++) {
                        chars[i] = c;

                        String word = new String(chars);

                        // Put it to the queue
                        //dict itself act as a visted if we keep removing the words
                        if (dict.contains(word)) {
                            queue.add(word);
                            dict.remove(word);
                        }
                    }
                }
            }
        }

        return 0;
    }

/**
 * * 126. Word Ladder II
A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:

Every adjacent pair of words differs by a single letter.
Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
sk == endWord
Given two words, beginWord and endWord, and a dictionary wordList, return all the shortest transformation sequences from beginWord to endWord, 
or an empty list if no such sequence exists. 
Each sequence should be returned as a list of the words [beginWord, s1, s2, ..., sk].
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
Output: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
Explanation: There are 2 shortest transformation sequences:
"hit" -> "hot" -> "dot" -> "dog" -> "cog"
"hit" -> "hot" -> "lot" -> "log" -> "cog"
*/

class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {

        List<List<String>> result=new ArrayList<>();

        //queue to save continous bfs sequence
        Queue<List<String>> queue=new LinkedList<>();

        Set<String> wordSet=new HashSet<>(wordList);

        queue.add(Arrays.asList(beginWord));


        while(!queue.isEmpty()){

            int size=queue.size();

            //Store string that are being visited on current level. 
            //This will be removed after all the elements on this level is visited
            //We cant mark visted immediately because there can be multiplse sequence will the same lement on this level
            //for eg: abc->abd->add  and  abc->adc->add and both has to be recorded in answer
            Set<String> vistedOnCurrentLevel=new HashSet<>();

            for(int i=0;i<size;i++){

                List<String> currentSeq=queue.poll();
                String currentWord=currentSeq.get(currentSeq.size()-1);

                //End word found now, we can add the answer and continue, 
                //There may be other sequences in the queue ending with the endword. We have to record all of them
                if(currentWord.equals(endWord)){
                    result.add(currentSeq);
                    continue;
                }

                //we have found the end word, now no need to further run the bfs for other words
                if(result.size()>0 && currentSeq.size()==result.size()){
                    continue;
                }

                char[] currentWordArray=currentWord.toCharArray();
                for(int j=0;j<currentWordArray.length;j++){
                    for(char c='a';c<='z';c++){
                        currentWordArray[j]=c;
                        String newWord=new String(currentWordArray);
                        //acting as a visited data structure
                        if(wordSet.contains(newWord)){
                            var newList=new ArrayList<>(currentSeq);
                            newList.add(newWord);
                            queue.add(newList);
                            vistedOnCurrentLevel.add(newWord);
                        }
                    }
                    currentWordArray[j]=currentWord.charAt(j);
                }


            }

            //Removed all the visited elements of this level.
            wordSet.removeAll(vistedOnCurrentLevel);
        }

        return result;
        
    }


}


/**
 * 
 * This is the most optimized approach to find solution
 * 
 * BFS to prepare level map i.e at ehich level each word exists while level traversal
 * DFS to backtrack from the end, level by level to reach start word and saving the sequence
 * 
 */

class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {

        Map<String,Integer> levelMap=bfs(beginWord,endWord,wordList);
        if(!levelMap.containsKey(endWord)){
            return Collections.emptyList();
        }
        List<List<String>> result=new ArrayList<>();
        var currentSeq=new ArrayList<String>();
        currentSeq.add(endWord);
        dfs(endWord,beginWord, result,currentSeq, levelMap); 

        return result;
    }

    //Back tracking on the level map from end to start and saving all the sequence going to the start
    void dfs(String curentWord,String beginWord,List<List<String>> result,List<String> currentSeq,  Map<String,Integer> levelMap){
        if(curentWord.equals(beginWord)){
            var newSeq=new ArrayList<>(currentSeq);
            Collections.reverse(newSeq);
            result.add(newSeq);
            return;
        }

        var neigbours=getAllNeigbours(curentWord);

        int currentLevel=levelMap.get(curentWord);
        //check if neigbours exists in the just lower level
        for(var neighbour:neigbours){
            if(levelMap.containsKey(neighbour) && levelMap.get(neighbour)==currentLevel-1){
                currentSeq.add(neighbour);
                dfs(neighbour,beginWord,result,currentSeq,levelMap);
                currentSeq.remove(currentSeq.size()-1);
            }
        }
    }


    //return the map od each string and its level in the all the final sequence from start to end
    public Map<String,Integer> bfs(String start, String end, List<String> wordList) {

        Map<String,Integer> levelMap=new HashMap<>(0);

        // Use queue to help BFS
        Queue<String> queue = new LinkedList<String>();
        queue.add(start);
        Set<String> dict=new HashSet<>(wordList);        
        dict.remove(start);
        
        int level = 0;
        
        while (!queue.isEmpty()) {

            level++;

            int size=queue.size();

            for(int s=0;s<size;s++){
                String str = queue.poll();
                levelMap.put(str,level);
                List<String> neighbours=getAllNeigbours(str);
                for(String neighbour:neighbours){
                    if(dict.contains(neighbour)){
                        dict.remove(neighbour);
                        queue.add(neighbour);
                    }
                }
            }
        }

        return levelMap;

    }

    List < String > getAllNeigbours(String word) {
        List<String> result=new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char[] chars = word.toCharArray();

            for (char c = 'a'; c <= 'z'; c++) {
                chars[i] = c;
                if(c==word.charAt(i)){
                    continue;
                }
                String newWord = new String(chars);
                result.add(newWord);

            }
        }

        return result;
    }


}