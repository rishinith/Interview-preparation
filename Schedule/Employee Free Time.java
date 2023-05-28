/**
 * 759. Employee Free Time

We are given a list schedule of employees, which represents the working time for each employee.

Each employee has a list of non-overlapping Intervals, and these intervals are in sorted order.

Return the list of finite intervals representing common, positive-length free time for all employees, also in sorted order.

 */


// Definition for an Interval.
// class Interval {
//     public int start;
//     public int end;

//     public Interval() {}

//     public Interval(int _start, int _end) {
//         start = _start;
//         end = _end;
//     }
// };

//Time: NLogK  N is total no of schedules, K is no of employee
class Solution {
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {


       PriorityQueue<int[]> pq=new PriorityQueue<>((a,b)->{
           var interval1=schedule.get(a[0]).get(a[1]);
           var interval2=schedule.get(b[0]).get(b[1]);
           if(interval1.start==interval2.start){
               return interval1.end-interval2.end;
           }
           return interval1.start-interval2.start;
       });



       for(int i=0;i<schedule.size();i++){
        pq.add(new int[]{i,0});
       }

       int prev=schedule.get(pq.peek()[0]).get(0).end;

       List<Interval> result=new ArrayList<>();

       while(!pq.isEmpty()){

           var elem=pq.poll();
           var interval=schedule.get(elem[0]).get(elem[1]);

           if(interval.start>prev){
               result.add(new Interval(prev, interval.start));
           }

           prev=Math.max(prev, interval.end);

           if(elem[1]<schedule.get(elem[0]).size()-1){
               pq.add(new int[]{elem[0], elem[1]+1});
           }

       }

       return result;

        
    }
}