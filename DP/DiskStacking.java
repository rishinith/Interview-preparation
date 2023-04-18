/**
 * 

  You're given a non-empty array of arrays where each subarray holds three
  integers and represents a disk. These integers denote each disk's width,
  depth, and height, respectively. Your goal is to stack up the disks and to
  maximize the total height of the stack. A disk must have a strictly smaller
  width, depth, and height than any other disk below it.


  Write a function that returns an array of the disks in the final stack,
  starting with the top disk and ending with the bottom disk. Note that you
  can't rotate disks; in other words, the integers in each subarray must
  represent [width, depth, height] at all times.


  You can assume that there will only be one stack with the greatest total
  height.

Sample Input
disks = [[2, 1, 2], [3, 2, 3], [2, 2, 8], [2, 3, 4], [1, 3, 1], [4, 4, 5]]

Sample Output
[[2, 1, 2], [3, 2, 3], [4, 4, 5]]
// 10 (2 + 3 + 5) is the tallest height we can get by
// stacking disks following the rules laid out above.


 */
import java.util.*;

class Program {
    public static List < Integer[] > diskStacking(List < Integer[] > disks) {
        // sort the disks based on their height (third element)
        Collections.sort(disks, (a, b) - > a[2] - b[2]);
        // initialize an array to store the maximum height of the stack up to the current disk
        int heights[] = new int[disks.size()];
        for (int i = 0; i < disks.size(); i++) {
            heights[i] = disks.get(i)[2];
        }

        // initialize an array to store the index of the disk below the current disk to form the maximum height
        int sequence[] = new int[disks.size()];
        Arrays.fill(sequence, -1);
        int maxHeightIndex = 0;

        // loop through all disks and try to stack them on top of other disks
        for (int i = 1; i < disks.size(); i++) {
            var currentDisk = disks.get(i);
            for (int j = 0; j < i; j++) {
                var otherDisk = disks.get(j);
                // check if the current disk can be placed on top of other disk while following the given condition
                if (canBePlaced(otherDisk, currentDisk)) {
                    // update the maximum height of the stack if it's possible to form a higher stack with the current disk
                    if (heights[i] < currentDisk[2] + heights[j]) {
                        heights[i] = currentDisk[2] + heights[j];
                        sequence[i] = j;
                    }
                }
            }
            // update the index of the disk with maximum height
            if (heights[maxHeightIndex] < heights[i]) {
                maxHeightIndex = i;
            }
        }

        // return the list of disks forming the maximum height stack
        return buildDisksList(sequence, disks, maxHeightIndex);
    }

    static List < Integer[] > buildDisksList(int[] sequence, List < Integer[] > disks, int maxHeightIndex) {
        List < Integer[] > result = new ArrayList < > ();
        // traverse through the index of the disk below the current disk to form the maximum height stack
        while (maxHeightIndex != -1) {
            result.add(disks.get(maxHeightIndex));
            maxHeightIndex = sequence[maxHeightIndex];
        }
        // reverse the result as the disks are added from bottom to top
        Collections.reverse(result);

        return result;
    }

    // check if a disk can be placed on top of other disk based on their width, depth, and height
    private static boolean canBePlaced(Integer[] diskUp, Integer[] diskBelow) {
        return diskUp[0] < diskBelow[0] && diskUp[1] < diskBelow[1] && diskUp[2] < diskBelow[2];
    }
}

// The disk stacking problem is solved using dynamic programming approach.
// The disks are sorted based on their height and then the maximum height of the stack is calculated for each disk
// by checking if it can be placed on top of any other disk. The maximum height is stored in an array and the index of
// the disk below is stored in another array to form the maximum height stack. Finally, the list of disks is returned
// which forms the maximum height stack.