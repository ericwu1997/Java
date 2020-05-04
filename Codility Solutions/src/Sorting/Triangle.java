package Sorting;

import java.util.Arrays;

// link
// https://app.codility.com/programmers/lessons/6-sorting/triangle/

public class Triangle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int A[] = {10, 2, 5, 1, 8, 20};
		System.out.println(new Solution().solution(A));
	}

	static class Solution {
	    public int solution(int[] A) {
	        // write your code in Java SE 8
	        int size = A.length;
	        Arrays.sort(A);
	        for(int i = 0; i + 2 < size; i++) {
	        	if(  
	                ((long)A[i] + (long)A[i+1] > A[i+2]) &&  
	                ((long)A[i+1] + (long)A[i+2] > A[i]) &&
	                ((long)A[i] + (long)A[i+2] > A[i+1]) 
	              )
	            return 1;
	        }
	        return 0;
	    }
	}
}
