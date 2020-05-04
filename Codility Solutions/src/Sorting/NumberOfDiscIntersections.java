package Sorting;

import java.util.Arrays;

// link
// https://app.codility.com/programmers/lessons/6-sorting/number_of_disc_intersections/

public class NumberOfDiscIntersections {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int A[] = { 1, 2147483647, 0 };
		System.out.println(new Solution().solution(A));
	}

	static class Solution {
		public int solution(int[] A) {
			// write your code in Java SE 8
			int ans = 0, size = A.length, max = 10000000;
			int begin[] = new int[size];
			int end[] = new int[size];
			int open = 0, j = 0, i = 0;
			for (i = 0; i < size; i++) {
				begin[i] = i - A[i];
				end[i] = i + A[i];
			}
			Arrays.sort(begin);
			Arrays.sort(end);
			System.out.println(Arrays.toString(begin));
			System.out.println(Arrays.toString(end));
			
			i = 0;
			while(i < size) {
				if(ans - max > 0) return -1;
				if(begin[i] <= end[j]) {
					ans += open;
					open++;
					i++;
				} else {
					j++;
					open--;
				}
			}
			return ans;
		}
	}
}
