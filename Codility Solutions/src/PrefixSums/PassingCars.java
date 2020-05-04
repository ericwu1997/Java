package PrefixSums;

// link
// https://app.codility.com/programmers/lessons/5-prefix_sums/passing_cars/

public class PassingCars {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int A[] = {0, 1, 0, 1, 1};
		System.out.println(new Solution().solution(A));
	}
	
	static class Solution {
	    public int solution(int[] A) {
	        // write your code in Java SE 8
	    	int max = 1000000000;
	        int size = A.length, pass = 0;
	        int w = 0, mark = size - 1;
	        while(mark >= 0 && A[mark] != 1) {
	            mark--;
	        }
	        
	        while(mark >= 0) {
	        	if(A[mark] == 1) {
	        		w++;
	        	} else {
	        		if(pass - max > 0) return -1;
	        		pass += w;
	        	}
	        	mark--;
	        }
	        
	        return pass;
	    }
	}
}
