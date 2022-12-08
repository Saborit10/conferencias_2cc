package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner f = new Scanner(System.in);

		String text = f.next();
		String pattern = f.next();

		System.out.println(knuthMorrisPratt(text, pattern).size());
	}

	static List<Integer> knuthMorrisPratt(String T, String P) {
		int[] fail = new int[P.length()];
		List<Integer> matches = new ArrayList<>();

		fail[0] = 0;
		for (int i = 1, st; i < P.length(); i++) {
			st = fail[i - 1];

			while (st > 0 && P.charAt(st) != P.charAt(i))
				st = fail[st - 1];

			if (P.charAt(st) == P.charAt(i))
				fail[i] = st + 1;
		}

		for (int i = 0, st=0; i < T.length(); i++) {
			while (st > 0 && P.charAt(st) != T.charAt(i))
				st = fail[st - 1];
			if (P.charAt(st) == T.charAt(i))
				st++;
			if (st == P.length()) {
				matches.add(i - P.length() + 1);
				st = fail[st - 1];
			}
		}
		return matches;
	}
}