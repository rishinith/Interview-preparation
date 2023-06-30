class LFUCache {

    Map<Integer,Node> cache;
    Map<Integer, LinkedHashSet<Integer>> frequencyMap;

    int minFreq=0;
    int capacity=0;
    int currentCapacity=0;

    public LFUCache(int capacity) {

        cache=new HashMap<>();
        frequencyMap=new HashMap<>();
        minFreq=1;
        this.capacity=capacity;
   
    }
    
    public int get(int key) {
        
        var node=cache.get(key);
        if(node==null){
            return -1;
        } 

        //increase the frequency
        //update the minum frequency if required
        updateNode(key, node, node.value);
        return node.value;
        
    }
    
    public void put(int key, int value) {

        var node=cache.get(key);;
        if(node!=null){
            updateNode(key, node, value);
        }else{
            addNode(key, value);
        }
        
    }

    void updateNode(int key, Node node, int value){
        node.frequency++;
        node.value=value;
        cache.put(key, node);
        frequencyMap.computeIfAbsent(node.frequency, k->new LinkedHashSet<>()).add(key);

        var prevFreqList=frequencyMap.get(node.frequency-1);
        prevFreqList.remove(key);

        if(prevFreqList.size()==0 && minFreq==node.frequency-1){
            minFreq++;
        }
    }

    void addNode(int key, int value){

        Node node=new Node();
        node.value=value;
        node.frequency=1;
        cache.put(key, node);
        currentCapacity++;
        frequencyMap.computeIfAbsent(node.frequency, k->new LinkedHashSet<>()).add(key);

        if(currentCapacity>capacity){

            var set=frequencyMap.get(minFreq);
            int keyToDelete=set.iterator().next();
            set.remove(keyToDelete);
            cache.remove(keyToDelete);
            if(set.isEmpty()){
                frequencyMap.remove(minFreq);
            }
        }

        minFreq=1;

    }
}


class Node{
    int value;
    int frequency;
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */