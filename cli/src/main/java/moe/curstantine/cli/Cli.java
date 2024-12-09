package moe.curstantine.cli;

import moe.curstantine.shared.Configuration;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cli {
	private static final Logger LOGGER = Logger.getLogger(Cli.class.getName());

	private static final String WELCOME_MESSAGE = """
			************************* Welcome to the CLI! **************************
			This application configures the vendor system and its settings.
			To start the configuration process, use the configure subcommand. For more
			information, consult the help messages by running: cli help configure.
			************************************************************************""";

	private static final String HELP_BASE = """
			Usage: cli <COMMAND>
			
			Available commands:
			help 		Displays this help message. For further explanations, use cli help <COMMAND>
			configure	Prompts to change the service configuration
			attach		Displays the real-time events executing inside the backend application""";

	private static final String HELP_CONFIGURE = """
			Usage: cli configure
			
			Prompts the user to change the service-specific configurations like:
			- Total tickets
			- Ticket release rate
			- Customer retrieval rate
			- Max ticket capacity""";

	private static final String HELP_ATTACH = """
			Usage: cli attach
			
			Attaches this CLI into the backend application, displaying all events being executed by the process.""";


	public static void main(String[] args) {
		Configuration config;

		try {
			config = Configuration.load();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to load configuration: " + e.getLocalizedMessage(), e);
			System.err.println("Failed to load configuration: " + e.getLocalizedMessage());
			return;
		}

		if (args == null || args.length == 0) {
			System.out.println(WELCOME_MESSAGE);
			return;
		}

		switch (args[0]) {
			case "attach":
				attach();
				break;
			case "configure":
				configure(config);
				break;
			case "help":
				if (args.length > 1) help(args[1]);
				else help(null);
				break;
		}
	}

	public static void attach() {

	}

	public static void help(String subCommand) {
		if (subCommand == null || subCommand.isEmpty()) {
			System.out.println(HELP_BASE);
			return;
		}

		switch (subCommand) {
			case "configure":
				System.out.println(HELP_CONFIGURE);
				break;
			case "attach":
				System.out.println(HELP_ATTACH);
				break;
			default:
				System.out.println(HELP_BASE);
				break;
		}
	}

	public static void configure(Configuration config) {
		final int maxTicketCapacity = Prompter.promptRangeInteger(
				0, Configuration.MAX_MAX_TICKET_CAPACITY,
				String.format("Max ticket capacity (min: 0, max: %s) [current: %s]: ",
						Configuration.MAX_MAX_TICKET_CAPACITY,
						config.getMaxTicketCapacity()
				),
				"You need to enter a valid number!"
		);
		if (maxTicketCapacity != -1) config.setMaxTicketCapacity(maxTicketCapacity);
		
		final int totalTickets = Prompter.promptRangeInteger(
				0, Configuration.MAX_TOTAL_TICKETS,
				String.format("Total number of tickets (min: 0, max: %s) [current: %s]: ",
						Configuration.MAX_TOTAL_TICKETS,
						config.getTotalTickets()
				),
				"You need to enter a valid number!"
		);
		if (totalTickets != -1) config.setTotalTickets(totalTickets);

		final int ticketReleaseRate = Prompter.promptRangeInteger(
				0, Configuration.MAX_TICKET_RELEASE_RATE,
				String.format("Ticket release rate per second (min: 0, max: %s) [current: %s]: ",
						Configuration.MAX_TICKET_RELEASE_RATE,
						config.getTicketReleaseRate()
				),
				"You need to enter a valid number!"
		);
		if (ticketReleaseRate != -1) config.setTicketReleaseRate(ticketReleaseRate);

		final int customerRetrievalRate = Prompter.promptRangeInteger(
				0, Configuration.MAX_CUSTOMER_RETRIEVAL_RATE,
				String.format("Customer retrieval rate per second (min: 0, max: %s) [current: %s]: ",
						Configuration.MAX_CUSTOMER_RETRIEVAL_RATE,
						config.getCustomerRetrievalRate()
				),
				"You need to enter a valid number!"
		);
		if (customerRetrievalRate != -1) config.setCustomerRetrievalRate(customerRetrievalRate);


		try {
			System.out.println("\n" + config.toFormattedString());
			final boolean confirmSave = Prompter.promptConditional("Are you sure you want to save these settings? ");

			if (confirmSave) {
				config.save();
				LOGGER.info("Configuration has been saved successfully!");
			} else {
				LOGGER.info("Configuration changes were aborted!");
			}
		} catch (IOException e) {
			System.err.println("Failed to update configuration: " + e.getLocalizedMessage());
			LOGGER.log(Level.SEVERE, "Failed to update configuration: " + e.getLocalizedMessage(), e);
		}
	}
}
