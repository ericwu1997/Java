package Leader;

import java.util.Arrays;

// link
// https://app.codility.com/programmers/lessons/8-leader/dominator/

public class Dominator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int A[] = {1, 2, 1};
		
		System.out.println(new Solution().solution(A));
	}

	static class Solution {
	    public int solution(int[] A) {
	        // write your code in Java SE 8
	    	int size = A.length;
	    	int index = 0;
	    	if(size == 0) return -1;
	    	if(size == 1) return index;
	    	
	        int stack[] = new int[size];
	        int origin[] = A.clone();
	        int pass = size / 2;
	        int sp = 0, dominator = 0;
	        boolean found = false;
	        
	        Arrays.sort(A);

	        stack[sp] = A[0];

	        for(int i = 1; i < size; i++) {
	        	if(A[i] != stack[sp]) {
	        		sp = 0;
	        	} else {
	        		sp++;
	        		if(sp + 1 > pass) {
	        			dominator = A[i];
	        			found = true;
	        			break;
	        		}
	        	}
	        	stack[sp] = A[i];
	        }
	        	
	        if(found) {	
		        for(int i = 0; i < size; i++) {
		        	if(origin[i] == dominator)
		        		return i;
		        }
	        }

	        return -1;
	    }
	}
}
