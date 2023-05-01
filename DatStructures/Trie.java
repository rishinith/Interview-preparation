class TrieNode{
    Map<Character,TrieNode> children=new HashMap<>();
    boolean isWord=false;
    String word=""; //optional
}

class Solution{

    Node root=new TrieNode();

    void buildTrie(String[] words){
        for(String word:words){
            TrieNode node=root;
            for(int i=0;i<word.length();i++){
                char c=word.charAt(i);
                var childNode=node.children.get(c);
                if (childNode==null){
                    childNode=new TrieNode();
                    node.children.put(c,childNode);
                }
                node=childNode;
            }
            node.isWord=true;
            node.word=word;
        }
    }

    public boolean searchWord(String word) {
    TrieNode curr = root;
    for (char c : word.toCharArray()) {
        if (!curr.children.containsKey(c)) {
            return false;
        }
        curr = curr.children.get(c);
    }
    return curr.isWord;
    }



}
