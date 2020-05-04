package PrefixSums;

import java.util.Arrays;

// link
// https://app.codility.com/programmers/lessons/5-prefix_sums/genomic_range_query/

public class GenomicRangeQuery {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String S = new String("CAGCCTA");
		int P[] = {2, 5, 0};
		int Q[] = {4, 5, 6};
		System.out.println(Arrays.toString(new Solution().solution(S, P, Q)));
	}

	static class Solution {
	    public int[] solution(String S, int[] P, int[] Q) {
	        // write your code in Java SE 8
	    	int strlen = S.length();
	    	int size = P.length;
	        int occur[][] = new int[strlen][4];
	        int ans[] = new int[P.length];
	        int splice[] = new int[4];
	        int begin, end;
	        for(int i = 0; i < strlen; i++) {
	        	switch(S.charAt(i)) {
	        	case 'A':
	        		occur[i][0] = 1;
	        		break;
	        	case 'C':
	        		occur[i][1] = 1;
	        		break;
	        	case 'G':
	        		occur[i][2] = 1;
	        		break;
	        	case 'T':
	        		occur[i][3] = 1;
	        		break;
	        	}
	        }
	        for(int i = 1; i < strlen; i++) {
	        	occur[i][0] = occur[i][0] + occur[i - 1][0];
	        	occur[i][1] = occur[i][1] + occur[i - 1][1];
	        	occur[i][2] = occur[i][2] + occur[i - 1][2];
	        	occur[i][3] = occur[i][3] + occur[i - 1][3];
	        }
	        for(int i = 0; i < size; i++) {
	        	begin = P[i];
	        	end = Q[i];
	        	if(begin > 0) {	        		
	        		splice[0] = occur[end][0] - occur[begin - 1][0];
	        		splice[1] = occur[end][1] - occur[begin - 1][1];
	        		splice[2] = occur[end][2] - occur[begin - 1][2];
	        		splice[3] = occur[end][3] - occur[begin - 1][3];
	        	} else {
	        		splice[0] = occur[end][0];
	        		splice[1] = occur[end][1];
	        		splice[2] = occur[end][2];
	        		splice[3] = occur[end][3];
	        	}
	        	System.out.println(Arrays.toString(splice));
	        	for(int j = 0; j < 4; j++) {
	        		if(splice[j] > 0) {
	        			ans[i] = j + 1;
	        			break;
	        		}
	        	}
	        }
	        return ans;
	    }
	}
}
