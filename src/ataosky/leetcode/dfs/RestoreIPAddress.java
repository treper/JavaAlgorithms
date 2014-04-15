package ataosky.leetcode.dfs;

import java.util.ArrayList;

public class RestoreIPAddress {

	public ArrayList<String> restoreIpAddresses(String s) {
		ArrayList<String> ret = new ArrayList<String>();
		StringBuilder path = new StringBuilder();

		dfs(s, 0, 0, path, ret);
		return ret;
	}

	public void dfs(String s, int start, int step, StringBuilder path,
			ArrayList<String> ret) {
		if (start == s.length() && step == 4) {
			String npath = new String(path.substring(0, path.length() - 1));
			ret.add(npath);
			return;
		}
		if (s.length() - start > 3 * (4 - step))
			return;

		if (s.length() - start < (4 - step))
			return;

		int num = 0;
		for (int i = start; i < s.length(); i++) {
			num = num * 10 + (s.charAt(i) - '0');
			if (num <= 255) {
				//System.out.println(num+" "+path.toString());
				StringBuilder npath = new StringBuilder(path);
				npath.append(String.valueOf(num));
				npath.append(".");
				dfs(s, i + 1, step + 1, npath, ret);
			}
			if (num == 0)
				break;
		}

	}

	public void dfs2(String s, int start, int step, StringBuilder path,
			ArrayList<String> ret) {
		if (start == s.length() || step == 4) {
			if (start == s.length() && step == 4) {

				String npath = new String(path.toString());
				ret.add(npath);
				
			}
			return;
		}
		StringBuilder npath = new StringBuilder(path);

		if (start != 0)
			npath.append(".");

		int num = 0;
		for (int i = start; i < s.length(); i++) {
			num += num * 10 + (s.charAt(i) - '0');
			npath.append(s.charAt(i));
			if (num <= 255) {
				dfs(s, i + 1, step + 1, npath, ret);
			}
			if (num == 0)
				break;
		}
	}

	public static void main(String args[]) {
		// StringBuilder a=new StringBuilder("abcdefg");
		// System.out.print(a.substring(0,a.length()-1));

		RestoreIPAddress solution = new RestoreIPAddress();
		// ArrayList<String> ret = solution.restoreIpAddresses("25525511135");
		// ArrayList<String> ret = solution.restoreIpAddresses("0000");
		 ArrayList<String> ret = solution.restoreIpAddresses("010010");
		//ArrayList<String> ret = solution.restoreIpAddresses("172162541");// ["17.216.25.41","17.216.254.1","172.16.25.41","172.16.254.1","172.162.5.41","172.162.54.1"]
		System.out.println(ret.toString());
	}

}
