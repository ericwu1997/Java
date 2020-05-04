package Sorting;

import java.util.HashSet;
import java.util.Set;

// link
// https://app.codility.com/programmers/lessons/6-sorting/distinct/

public class Distinct {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int A[] = {2, 1, 1, 2, 3, 1};
		System.out.println(new Solution().solution(A));
	}

	static class Solution {
	    public int solution(int[] A) {
	        // write your code in Java SE 8
	    	Set<Integer> set = new HashSet<Integer>(); 
	    	for(int n : A) {
	    		set.add(n);
	    	}
	    	return set.size();
	    }
	}
}
