
//T: log(min(m,n))
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

     //return linearSearch(nums1,nums2);

     return binarySearch(nums1,nums2);

     //return -1;

        
    }

    private double binarySearch(int[] nums1, int[] nums2){

        int n1=nums1.length;
        int n2=nums2.length;

        if(n2<n1){
            return binarySearch(nums2,nums1);
        }

        int total=n1+n2;


        int l=0;
        int r=n1;
        while(l<=r){
            int cut1=(l+r)/2;
            int cut2=((total+1)/2)-cut1;

            int l1=cut1==0?Integer.MIN_VALUE:nums1[cut1-1];
            int r1=(cut1==n1)?Integer.MAX_VALUE:nums1[cut1];

            int l2=cut2==0?Integer.MIN_VALUE:nums2[cut2-1];
            int r2=(cut2==n2)?Integer.MAX_VALUE:nums2[cut2];

            if(l1<=r2 && l2<=r1){
                if(total%2!=0){
                    return Math.min(l1,l2);
                }else{
                    return (Math.max(l1,l2)+Math.min(r1,r2))*1.0/2.0;
                }
            }
            else if(l1>r2){
                r=cut1-1;
            }
            else{
                l=cut1+1;
            }

        }

        return 0.0;

     }

    private double linearSearch(int[] nums1, int[] nums2){
        int n1=nums1.length;
        int n2=nums2.length;

        int total=n1+n2;

        int mid=total/2;

        int i=0, j=0;
        int count=0;
        int lastValue=-1;
        int currentValue=-1;
        while(count<=mid){
            lastValue=currentValue;
            if(i<n1 && j<n2){
                if(nums1[i]<=nums2[j]){
                    currentValue=nums1[i++];
                }else{
                    currentValue=nums2[j++];
                }
            }
            else if(i<n1){
                currentValue=nums1[i++];
            }else{
                currentValue=nums2[j++];
            }
            count++;
        }
        

        return total%2==0?(lastValue+currentValue)*1.0/2:currentValue*1.0;
    }
}