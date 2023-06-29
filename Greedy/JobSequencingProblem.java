/**
 * Job Sequencing Problem
Given a set of N jobs where each jobi has a deadline and profit associated with it.

Each job takes 1 unit of time to complete and only one job can be scheduled at a time. 
We earn the profit associated with job if and only if the job is completed by its deadline.

Find the number of jobs done and the maximum profit.

Note: Jobs will be given in the form (Jobid, Deadline, Profit) associated with that Job.


Example 1:

Input:
N = 4
Jobs = {(1,4,20),(2,1,10),(3,1,40),(4,1,30)}
Output:
2 60
Explanation:
Job1 and Job3 can be done with
maximum profit of 60 (20+40).
 */


class Solution
{
    //Function to find the maximum profit and the number of jobs done.
    int[] JobScheduling(Job arr[], int n)
    {
       
        //sort the job in decreasing profit
        Arrays.sort(arr, (job1,job2)-> job2.profit-job1.profit);
        
        
        //slots to show which are available or which are not
        boolean[] slots=new boolean[n];
 
        
        int profit=0;
        int noOfJobs=0;
        
        //iterate each job and book the last available slot just before deadline greedily
       for(int i=0;i<n;i++){
           
           Job job=arr[i];
           
           
           //iterating from the end/deadline, and moment I get the free slot, book it and break the loop
           for(int j=Math.min(job.deadline-1,n-1);j>=0;j--){
               if(slots[j]==false){
                   profit+=job.profit;
                   noOfJobs++;
                   slots[j]=true;
                   break;
               }
           }
       }
        
        return new int[]{noOfJobs,profit};
        
    }
}

/*
class Job {
    int id, profit, deadline;
    Job(int x, int y, int z){
        this.id = x;
        this.deadline = y;
        this.profit = z; 
    }
}
*/

