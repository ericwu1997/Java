package PrefixSums;

// link
// https://app.codility.com/programmers/lessons/5-prefix_sums/count_div/

public class CountDiv {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int A = 1, B = 10, K = 2;
		System.out.println(new Solution().solution(A, B, K));
	}

	static class Solution {
	    public int solution(int A, int B, int K) {
	        // write your code in Java SE 8
	        int n = A % K, m = B % K, ans = 0, k;
	        int begin = (n == 0) ? A : A + ( K - n );
	        int end = (m == 0) ? B : B - m;
	        ans = (end / K) - (begin / K) + 1;
	        return ans;
	    }
	}
}
