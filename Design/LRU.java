class LRUCache {
    int capacity;
    Map<Integer,Node> map;
    Node head;
    Node tail;

    int noOfNodes=0;
    class Node{
        int key;
        int val;
        Node next;
        Node prev;
    }

    //most recent nodes on tail

    public LRUCache(int capacity) {
       this.capacity=capacity;
       map=new HashMap<>();
       head=new Node();
       tail=new Node();
       head.next=tail;
       tail.prev=head;
    }
    
    public int get(int key) {
       var node=map.get(key);
       if(node==null){
           return -1;
       }else{
           removeNode(node);
           addNodetoTail(key,node.val);
           return node.val;
       }
    }
    
    public void put(int key, int val) {
        var node=map.get(key);
        if(node==null){
            addNodetoTail(key,val);
        }else{
            removeNode(node);
            addNodetoTail(key,val);
        }

    }

    private void removeNode(Node node){
        node.prev.next=node.next;
        node.next.prev=node.prev;
        map.remove(node.key);
        noOfNodes--;
    }

    private Node addNodetoTail(int key, int val){
        Node node=new Node();
        node.key=key;
        node.val=val;
        node.prev=tail.prev;
        node.next=tail;
        tail.prev.next=node;
        tail.prev=node;  
        noOfNodes++; 
        if(noOfNodes>capacity){
          removeNode(head.next);
        }
        map.put(key,node);
        return node;
    }

    
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */