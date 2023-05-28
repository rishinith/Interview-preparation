/**
 * A car travels from a starting position to a destination which is target miles east of the starting position.

There are gas stations along the way. The gas stations are represented as an array stations where stations[i] = [positioni, fueli] 
indicates that the ith gas station is positioni miles east of the starting position and has fueli liters of gas.

The car starts with an infinite tank of gas, which initially has startFuel liters of fuel in it. 
It uses one liter of gas per one mile that it drives. When the car reaches a gas station, it may stop and refuel, transferring all the gas from the station into the car.

Return the minimum number of refueling stops the car must make in order to reach its destination. 
If it cannot reach the destination, return -1.

Note that if the car reaches a gas station with 0 fuel left, the car can still refuel there. 
If the car reaches the destination with 0 fuel left, it is still considered to have arrived.
 */

class Solution {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {

        //max heap
        PriorityQueue<Integer> maxHeap=new PriorityQueue<>((a,b)-> stations[b][1]-stations[a][1]);


        int i = 0;
        int n = stations.length;
        int stops = 0;
        int maxDistance = startFuel;
        // Loop until the car reach the target or the car is out of fuel
        while (maxDistance < target) {
            // If there are still stations and the next one is within range, add its fuel capacity to the max heap
            if (i < n && stations[i][0] <= maxDistance) {
                maxHeap.add(i);
                i++;
            }
            // If there are no more stations and we can't reach the target, return -1
            else if (maxHeap.isEmpty()) {
                return -1;
            }
            // Otherwise, fill up at the station with the highest fuel capacity and increment stops
            else {
                maxDistance += stations[maxHeap.poll()][1];
                stops++;
            }
        }

        return stops;
        
    }
}