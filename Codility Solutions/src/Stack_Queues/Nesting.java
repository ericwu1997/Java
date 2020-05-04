package Stack_Queues;

// link
// https://app.codility.com/programmers/lessons/7-stacks_and_queues/nesting/

public class Nesting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String S = "{[()()]}";
		System.out.println(new Solution().solution(S));
	}

	static class Solution {
		public int solution(String S) {
			// write your code in Java SE 8
			char stack[] = new char[S.length()];
			int sp = -1;
			for (char c : S.toCharArray()) {
				switch (c) {
				case '(':
					sp++;
					stack[sp] = c;
					break;
				case ')':
					if (sp < 0 || stack[sp] != '(')
						return 0;
					sp--;
					break;
				default:
					return 0;
				}
			}
			return sp == -1 ? 1 : 0;
		}
	}
}
