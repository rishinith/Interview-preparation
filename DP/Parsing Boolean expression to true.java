/**Given an expression, A, with operands and operators (OR, AND, XOR), in how many ways can you evaluate the expression to be true, by grouping it in different ways?

Operands are only true and false.
Input: expression = “T|T&F”
Output: 1
Explanation: The only way to get the result as true is:
(T) | (T&F) = T|F = T 
 */

import java.util.* ;
import java.io.*; 
public class Solution {

    static int mod=1000000007;
    public static int evaluateExp(String exp) {
        

        Integer[][][] dp=new Integer[exp.length()][exp.length()][2];
        return evaluateExp(0, exp.length()-1, 1, exp, dp);
    }

    static int evaluateExp(int i, int j, int value, String exp, Integer[][][] dp){

        if(i>j){
            return 0;
        }

        if(dp[i][j][value]!=null){
            return dp[i][j][value];
        }

        if(i==j){
            if(value==1 && exp.charAt(i)=='T'){
                return 1;
            }else if(value==0 && exp.charAt(i)=='F'){
                return 1;
            }
            return 0;
        }

       

        int ways=0;
        for(int idx=i+1;idx<=j-1;idx=idx+2){

            //calculate the left side and right side xperssion for true and false
            int lt=evaluateExp(i, idx-1, 1, exp, dp);
            int lf=evaluateExp(i, idx-1, 0, exp, dp);
            int rt=evaluateExp(idx+1, j, 1, exp, dp);
            int rf=evaluateExp(idx+1, j, 0, exp, dp);


            //on basis of different operator and required value calculate no of ways
            if(exp.charAt(idx)=='&'){
                //T=T&T F=T&F, F&T, F&F
                if(value==1){
                    ways+=(lt*rt)%mod;
                    ways=ways%mod;
                }else{
                    ways+=((lt*rf)%mod +(lf*rt)%mod)%mod + (lf*rf)%mod;
                    ways=ways%mod;
                }

            }
            else if(exp.charAt(idx)=='|'){
                //T=T|T, F|T, T|F   F=F|F
                if(value==0){
                    ways+=(lf*rf)%mod;
                    ways=ways%mod;
                }else{
                    ways+=((lt*rf)%mod +(lf*rt)%mod)%mod + (lt*rt)%mod;
                    ways=ways%mod;
                }

            }else if(exp.charAt(idx)=='^'){
                //T=F^T, T^F  F=F^F, T^T
                if(value==1){
                    ways+=(lt*rf)%mod+(lf*rt)%mod;
                    ways=ways%mod;
                }else{
                    ways+=(lt*rt)%mod +(lf*rf)%mod;
                    ways=ways%mod;
                }
            }

        }

        return dp[i][j][value]=ways%mod;
    }
}