package moe.curstantine.cli;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Prompter {
	public static int promptRangeInteger(int start, int end, String message, String invalidMessage) {
		int value;
		final Scanner input = new Scanner(System.in);

		while (true) {
			System.out.print(message);

			try {
				final String optString = input.nextLine();

				if (optString.isBlank()) {
					value = -1;
					break;
				}

				final int opt = Integer.parseInt(optString);
				if (opt < start || opt > end) throw new InputMismatchException();

				value = opt;
				break;
			} catch (InputMismatchException | NumberFormatException e) {
				System.out.println(invalidMessage);
			}
		}

		return value;
	}

	/**
	 * Prompts the user to input a "yes" "no" condition.
	 */
	public static boolean promptConditional(String message) {
		boolean value;
		final Scanner input = new Scanner(System.in);

		while (true) {
			System.out.print(message + " (Y/N): ");

			try {
				final String stringVal = input.next().toLowerCase();

				value = switch (stringVal) {
					case "y", "yes" -> true;
					case "n", "no" -> false;
					default -> throw new InputMismatchException();
				};

				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input! Allowed values are either Y(es) or N(o)");
			}
		}

		return value;
	}
}
