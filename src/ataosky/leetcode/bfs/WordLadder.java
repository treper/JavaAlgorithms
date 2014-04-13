package ataosky.leetcode.bfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

//============================================================================
//Word Ladder
//Given two words (start and end), and a dictionary, find the length of 
//shortest transformation sequence from start to end, such that:
//
//Only one letter can be changed at a time
//Each intermediate word must exist in the dictionary
//For example,
//
//Given:
//start = "hit"
//end = "cog"
//dict = ["hot","dot","dog","lot","log"]
//As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
//return its length 5.
//============================================================================
public class WordLadder {
	public int ladderLength(String start, String end, HashSet<String> dict) {

		int level = 1;
		HashSet<String> visited = new HashSet<String>();
		ArrayList<Queue<String>> candidates = new ArrayList<Queue<String>>();
		candidates.add(new LinkedList<String>());
		candidates.add(new LinkedList<String>());

	
		candidates.get(0).add(start);
		int cur = 0;
		int nex = 1;
		Queue<String> current = candidates.get(cur);
		Queue<String> next = candidates.get(nex);
		while (!current.isEmpty()) {
			while (!current.isEmpty()) {
				String word = current.remove();
				StringBuilder newword = new StringBuilder(word);
				for (int i = 0; i < start.length(); i++) {
					for (char c = 'a'; c <= 'z'; c++) {
						if (word.charAt(i) == c)
							continue;
						char t = word.charAt(i);
						newword.setCharAt(i, c);
						if (newword.toString().equals(end)) {
							return level + 1;
						}
						if (dict.contains(newword.toString())
								&& !visited.contains(newword.toString())) {
							visited.add(newword.toString());
							next.add(newword.toString());
						}
						// restore
						newword.setCharAt(i, t);
					}
				}
			}
			//current.clear();
			// swap
			cur = cur == 0 ? 1 : 0;
			nex = nex == 1 ? 0 : 1;
			current = candidates.get(cur);
			next = candidates.get(nex);
			level++;
		}

		return 0;
	}

	public static void main(String args[]) {
		WordLadder solution = new WordLadder();
		String start = new String("hit");
		String end = new String("cog");
		HashSet<String> dict = new HashSet<String>(Arrays.asList("hot", "dot",
				"dog", "lot", "log"));

		int step = solution.ladderLength(start, end, dict);

		System.out.println(step);
	}
}
