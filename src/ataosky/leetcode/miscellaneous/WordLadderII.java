package ataosky.leetcode.miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

//============================================================================
//Word Ladder II
//Given two words (start and end), and a dictionary, find all shortest 
//transformation sequence(s) from start to end, such that:
//
//Only one letter can be changed at a time
//Each intermediate word must exist in the dictionary
//For example,
//
//Given:
//start = "hit"
//end = "cog"
//dict = ["hot","dot","dog","lot","log"]
//Return
//[
//    ["hit","hot","dot","dog","cog"],
//    ["hit","hot","lot","log","cog"]
//]
//Note:
//All words have the same length.
//All words contain only lowercase alphabetic characters.
//============================================================================
public class WordLadderII {
	public ArrayList<ArrayList<String>> findLadders(String start, String end,
			HashSet<String> dict) {
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		ArrayList<String> intermediate = new ArrayList<String>();
		intermediate.add(start);
		bfs(start, end, dict, results);
		return results;
	}

	public void bfs(String start, String end, HashSet<String> dict,
			ArrayList<ArrayList<String>> results) {
		ArrayList<HashSet<String>> candidates = new ArrayList<HashSet<String>>();
		candidates.add(new HashSet<String>());
		candidates.add(new HashSet<String>());

		HashSet<String> visited = new HashSet<String>();
		HashMap<String, ArrayList<String>> father = new HashMap<String, ArrayList<String>>();

		int cur = 0;
		int nex = 1;
		boolean found = false;
		candidates.get(cur).add(start);
		while (!candidates.get(cur).isEmpty() && !found) {
			HashSet<String> current = candidates.get(cur);
			HashSet<String> next = candidates.get(nex);
			for (String word : current)
				visited.add(word);

			for (String word : current) {
				StringBuilder newword = new StringBuilder(word);
				for (int i = 0; i < start.length(); i++) {
					for (char c = 'a'; c <= 'z'; c++) {
						// same char do not change
						if (word.charAt(i) == c)
							continue;

						char t = word.charAt(i);
						newword.setCharAt(i, c);

						if (newword.toString().equals(end))
							found = true;

						if (!visited.contains(newword.toString())
								&& (dict.contains(newword.toString()) || newword
										.toString().equals(end))) {
							//the target word must not be in the father map
							next.add(newword.toString());
							if (!father.containsKey(newword.toString())) {
								father.put(newword.toString(),
										new ArrayList<String>());
							}
							father.get(newword.toString()).add(word);

						}
						// restore
						newword.setCharAt(i, t);
					}
				}
			}
			current.clear();
			// swap
			cur = cur == 0 ? 1 : 0;
			nex = nex == 1 ? 0 : 1;
		}
//		Iterator it = father.entrySet().iterator();
//		while (it.hasNext()) {
//			Map.Entry pair = (Map.Entry) (it.next());
//			System.out.print(pair.getKey() + "<-");
//			ArrayList<String> v = (ArrayList<String>) pair.getValue();
//			for (String s : v) {
//				System.out.print(s + " ");
//			}
//			System.out.println();
//		}

		// build path
		ArrayList<String> path = new ArrayList<String>();
		if (found) {
			buildPath(father, path, end, results);
		}

	}

	// dfs recover the path tree
	// father:   cog
	//           / \
	//         dog log
	//         /     \
	//        dot   lot
	//          \   /
	//           hot
	//            |
 	//           hit


	public void buildPath(HashMap<String, ArrayList<String>> father,
			ArrayList<String> path, String prev,
			ArrayList<ArrayList<String>> results) {
		if (!father.containsKey(prev)) {//get destination
			path.add(0, prev);
			ArrayList<String> npath = new ArrayList<String>(path);
			results.add(npath);
			path.remove(0);
			return;
		}
		path.add(0,prev);
		for (String parent : father.get(prev)) {
			buildPath(father, path, parent, results);
		}
		path.remove(0);

	}

	public void dfs(String start, String end, HashSet<String> dict,
			ArrayList<String> intermediate, ArrayList<ArrayList<String>> results) {
		if (start.equals(end)) {
			intermediate.add(end);
			results.add(intermediate);
			return;
		}

		for (char c = 'a'; c <= 'z'; c++) {
			StringBuilder newword = new StringBuilder(start);
			for (int i = 0; i < start.length(); i++) {

				if (start.charAt(i) == c)
					continue;
				char t = newword.charAt(i);
				newword.setCharAt(i, c);
				if (dict.contains(newword.toString())) {
					intermediate.add(newword.toString());
					dfs(newword.toString(), end, dict, intermediate, results);
					intermediate.remove(intermediate.size() - 1);
				}
				newword.setCharAt(i, t);

			}
		}

	}

	public static void main(String args[]) {
		WordLadderII solution = new WordLadderII();
		String start = new String("hit");
		String end = new String("cog");
		HashSet<String> dict = new HashSet<String>(Arrays.asList("hot", "dot",
				"dog", "lot", "log"));
		ArrayList<ArrayList<String>> results = solution.findLadders(start, end,
				dict);

		for (ArrayList<String> item : results) {
			for (String i : item) {
				System.out.print(i+" ");
			}

			System.out.println();
		}

	}
}
