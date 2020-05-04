package Stack_Queues;

// link
// https://app.codility.com/programmers/lessons/7-stacks_and_queues/stone_wall/

public class StoneWall {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int H[] = {8, 8, 5, 7, 9, 8, 7, 4, 8};
		System.out.println(new Solution().solution(H));
	}

	static class Solution {
	    public int solution(int[] H) {
	        // write your code in Java SE 8
	    	int size = H.length;
	    	int ans = H.length;
	    	int stack[] = new int[size];
	    	int sp = -1;
	    	for(int n : H) {
    			while(sp >= 0) {
    				if(stack[sp] < n) break;
    				if(stack[sp] == n) ans--;
    				sp--;
    			}
	    		sp++;
	    		stack[sp] = n;
	    	}
	    	return ans;
	    }
	}
}
