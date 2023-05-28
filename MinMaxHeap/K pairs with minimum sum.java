/**
 * Given two lists and an integer  k
, find  k pairs of numbers with the smallest sum so that in each pair, each list contributes one number to the pair.
 */

/**
 * Let’s review the solution we’ve used to solve this problem:

We start by pairing only the first element of the second list with each element of the first list.
The sum of each pair and their respective indexes from the lists, i and j, are stored on a min-heap.
Next, we use a second loop in which at each step, we do the following:
We pop the pair with the smallest element from the min-heap and collect it in a result list.
We make a new pair in which the first element is the first element from the pair we just popped and the second element is the next element in the second list.
We push this pair along with its sum onto the min-heap.
We keep a count of the steps and stop when the min-heap becomes empty or when we have found  \k pairs.

Time complexity (m+k)logm

m=min(k,n1) n1 is length of first list

 */

class Pair {
    int sum;
    int i;
    int j;

    public Pair(int sum, int i, int j) {
        this.sum = sum;
        this.i = i;
        this.j = j;
    }
}
class FindKPairs {

    public static List<List<Integer>> kSmallestPairs(int[] list1, int[] list2, int k) {
        List<List<Integer>> pairs = new ArrayList<>();
        // storing the length of lists to use it in a loop later
        int listLength = list1.length;
        // declaring a min-heap to keep track of the smallest sums
        PriorityQueue<Pair> minHeap = new PriorityQueue<>((a, b) -> a.sum - b.sum);
        // iterate over the length of list 1
        for (int i = 0; i < Math.min(k, listLength); i++) {
            // computing sum of pairs all elements of list1 with first index
            // of list2 and placing it in the min-heap
            minHeap.add(new Pair(list1[i] + list2[0], i, 0));
        }

        int counter = 1;
        // iterate over elements of min-heap and only go upto k
        while (!minHeap.isEmpty() && counter <= k) {
            // placing sum of the top element of min-heap
            // and its corresponding pairs in i and j
            Pair pair = minHeap.poll();
            int i = pair.i;
            int j = pair.j;
            // add pairs with the smallest sum in the new list
            pairs.add(Arrays.asList(list1[i], list2[j]));
            // increment the index for 2nd list, as we've
            // compared all possible pairs with the 1st index of list2
            int nextElement = j + 1;
            // if next element is available for list2 then add it to the heap
            if (list2.length > nextElement) {
                minHeap.add(new Pair(list1[i] + list2[nextElement], i, nextElement));
            }
            counter++;
        }
        // return the pairs with the smallest sums
        return pairs;
    }

}