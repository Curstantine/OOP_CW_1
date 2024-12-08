package moe.curstantine.cli;

class Constants {
	public static final String WELCOME_MESSAGE = """
			************************* Welcome to the CLI! **************************
			This application overall configures the vendor system and its settings.
			To start the configuration process, use the configure subcommand. For more
			information, consult the help messages by running: cli help configure.
			************************************************************************""";

	public static final String BASE_HELP = """
			Usage: cli  <COMMAND>
			
			Available commands:
			configure	Prompts to configure the vendor settings.
			start		Start vendor ticket handling.
			stop		Stop vendor ticket handling.
			help		Prints this help message.""";

	public static final String CONFIGURE_HELP = """
			Configure vendor settings and save them into a config.json usable by other services.
			
			Usage: cli configure""";

	public static final String START_HELP = """
			Starts the vendor ticket handling process.
			
			Usage: cli start""";

	public static final String STOP_HELP = """
			Stops the vendor ticket handling process.
			
			Usage: cli stop""";
	;
}
