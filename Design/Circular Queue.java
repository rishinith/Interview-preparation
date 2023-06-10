/**
Design your implementation of the circular queue. The circular queue is a linear data structure in which the operations are performed based on FIFO (First In First Out) principle,
 and the last position is connected back to the first position to make a circle. It is also called "Ring Buffer".

One of the benefits of the circular queue is that we can make use of the spaces in front of the queue. In a normal queue, once the queue becomes full, 
we cannot insert the next element even if there is a space in front of the queue. But using the circular queue, we can use the space to store new values.

Implement the MyCircularQueue class:

MyCircularQueue(k) Initializes the object with the size of the queue to be k.
int Front() Gets the front item from the queue. If the queue is empty, return -1.
int Rear() Gets the last item from the queue. If the queue is empty, return -1.
boolean enQueue(int value) Inserts an element into the circular queue. Return true if the operation is successful.
boolean deQueue() Deletes an element from the circular queue. Return true if the operation is successful.
boolean isEmpty() Checks whether the circular queue is empty or not.
boolean isFull() Checks whether the circular queue is full or not.
You must solve the problem without using the built-in queue data structure in your programming language. 

**/

class MyCircularQueue {

    Integer queue[];
    int start=0;
    int end=0;
    public MyCircularQueue(int k) {
        queue=new Integer[k];
    }
    int size=0;
    
    public boolean enQueue(int value) {

        if(isFull()){
            return false;
        }
        queue[end]=value;
        end=(end+1)%queue.length;
        size++;
        return true;
    }
    
    public boolean deQueue() {
        if(isEmpty()){
            return false;
        }
        queue[start]=null;
        start=(start+1)%queue.length;
        size--;
        return true; 
    }
    
    public int Front() {
        if(size==0){
            return -1;
        }
        return queue[start];
    }
    
    public int Rear() {

        if(size==0){
            return -1;
        }

        int temp=end-1;
        if(temp<=-1){
            temp=queue.length-1;
        }
        return queue[temp];
        
    }
    
    public boolean isEmpty() {

        return size==0;
        
    }
    
    public boolean isFull() {

        return size==queue.length;
        
    }
}


//linked list

class MyCircularQueue {

    Node head=new Node(-1);
    int capacity;
    int listSize=0;
    Node tail=new Node(-1);

    public MyCircularQueue(int k) {
        head=new Node(-1);
        capacity=k;
        head.next=tail;
        tail.prev=head;
    }
    
    public boolean enQueue(int value) {

        if(isFull()){
            return false;
        }

        listSize++;

        var prev=tail.prev;
        var node=new Node(value);
        prev.next=node;
        node.next=tail;
        node.prev=prev;
        tail.prev=node;
        return true;
    }
    
    public boolean deQueue() {

        if(isEmpty()){
            return false;
        }

        listSize--;

        var next=head.next.next;
        head.next=next;
        next.prev=head;

        return true; 
    }
    
    public int Front() {

        if(isEmpty()){
            return -1;
        }

        return head.next.value;
        
    }
    
    public int Rear() {

        if(isEmpty()){
            return -1;
        }

        return tail.prev.value;
        
    }
    
    public boolean isEmpty() {
        return listSize==0;
    }
    
    public boolean isFull() {
        return listSize==capacity;
    }


}

class Node{
    int value;
    Node next;
    Node prev;
    Node(int val){
        value=val;
    }
}

/**
 * Your MyCircularQueue object will be instantiated and called as such:
 * MyCircularQueue obj = new MyCircularQueue(k);
 * boolean param_1 = obj.enQueue(value);
 * boolean param_2 = obj.deQueue();
 * int param_3 = obj.Front();
 * int param_4 = obj.Rear();
 * boolean param_5 = obj.isEmpty();
 * boolean param_6 = obj.isFull();
 */