package Sorting;

import java.util.Arrays;

// link
// https://app.codility.com/programmers/lessons/6-sorting/max_product_of_three/

public class MaxProductOfThree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int A[] = {-3, 1, 2, -2, 5, 6};
		System.out.println(new Solution().solution(A));
	}

	static class Solution {
	    public int solution(int[] A) {
	        // write your code in Java SE 8
	        int size = A.length;
	        Arrays.sort(A);
	        int negative_test = A[0] * A[1] * A[size - 1];
	        int normal = A[size - 1] * A[size - 2] * A[size - 3];
	        return normal > negative_test ? normal : negative_test; 
	    }
	}
}
