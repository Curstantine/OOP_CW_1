package moe.curstantine.cli;


import moe.curstantine.shared.Configuration;

public class Cli {
	public static void main(String[] args) {
		System.out.println("Hello World");
		try {
			final Configuration config = Configuration.loadConfiguration();
			System.out.println(config.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
