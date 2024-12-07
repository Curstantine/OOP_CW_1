package moe.curstantine.shared;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Holds the configuration used between the backend and the cli.
 * <p>
 * To load a configuration, use the {@link Configuration#loadConfiguration()} method.
 */
public class Configuration {
	int totalTickets;
	int ticketReleaseRate;
	int customerRetrievalRate;
	int maxTicketCapacity;

	private Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
		this.totalTickets = totalTickets;
		this.ticketReleaseRate = ticketReleaseRate;
		this.customerRetrievalRate = customerRetrievalRate;
		this.maxTicketCapacity = maxTicketCapacity;
	}

	public int getTotalTickets() {
		return totalTickets;
	}

	public int getTicketReleaseRate() {
		return ticketReleaseRate;
	}

	public int getCustomerRetrievalRate() {
		return customerRetrievalRate;
	}

	public int getMaxTicketCapacity() {
		return maxTicketCapacity;
	}

	/**
	 * Either deserialize or write a default {@link Configuration} to `{currentWorkingDirectory}/config.json`
	 *
	 * @return
	 * @throws IOException         thrown when the system fails to either write or read to config.json
	 * @throws JsonSyntaxException thrown when the JSON is malformed
	 */
	public static Configuration loadConfiguration() throws IOException, JsonSyntaxException {
		final String currentDir = System.getProperty("user.dir");
		final Path configPath = Path.of(currentDir, "config.json");
		final File configFile = configPath.toFile();

		final Gson gson = new Gson();
		Configuration config = null;

		if (!configFile.exists()) {
			config = new Configuration(100, 1, 1, 1000);
			final String jsonString = gson.toJson(config);
			Files.write(configPath, jsonString.getBytes());
		} else {
			final String jsonString = Files.readString(configPath);
			config = gson.fromJson(jsonString, Configuration.class);
		}

		return config;
	}
}
