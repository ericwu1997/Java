package Stack_Queues;

public class Fish {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int A[] = {4, 3, 2, 1, 5};
		int B[] = {0, 1, 0, 0, 0};
 		System.out.println(new Solution().solution(A, B));
	}

	static class Solution {
	    public int solution(int[] A, int[] B) {
	        // write your code in Java SE 8
	        int size = A.length, alive = 0;
	        int[] stack = new int[size];
	        int sp = -1;
	        
	        if(size == 0) return 0;
	        
	        for(int i = 0; i < size; i++) {
	        	alive++;
	            if(B[i] == 0) {
	            	while(sp != -1) {
	            		alive--;
	            		if(A[i] > stack[sp]) {
	            			sp--;
	            			continue;
	            		}
	            		break;
	            	}
	            } else {
	            	sp++;
	            	stack[sp] = A[i];
	            }
	        }
	        return alive;
	    }
	}
}
