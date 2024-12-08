package moe.curstantine.shared;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Holds the configuration used between the backend and the cli.
 * <p>
 * To load a configuration, use the {@link Configuration#load()} method.
 * <p>
 * Note: {@link #load()} and {@link #save} methods utilize nio to do non-blocking io calls.
 * These two methods ARE NOT thread-safe. DO NOT use them inside concurrent or parallel running contexts.
 */
public class Configuration {
	private static final Logger LOGGER = Logger.getLogger(Configuration.class.getName());

	public static int DEFAULT_TOTAL_TICKETS = 1000;
	public static int DEFAULT_TICKET_RELEASE_RATE = 20;
	public static int DEFAULT_CUSTOMER_RETRIEVAL_RATE = 19;
	public static int DEFAULT_MAX_TICKET_CAPACITY = 1000;

	public static int MAX_TOTAL_TICKETS = 1000;
	public static int MAX_TICKET_RELEASE_RATE = 1000;
	public static int MAX_CUSTOMER_RETRIEVAL_RATE = 1000;
	public static int MAX_MAX_TICKET_CAPACITY = 1000;

	private int totalTickets;
	private int ticketReleaseRate;
	private int customerRetrievalRate;
	private int maxTicketCapacity;

	private Configuration() {
		this.totalTickets = DEFAULT_TOTAL_TICKETS;
		this.ticketReleaseRate = DEFAULT_TICKET_RELEASE_RATE;
		this.customerRetrievalRate = DEFAULT_CUSTOMER_RETRIEVAL_RATE;
		this.maxTicketCapacity = DEFAULT_MAX_TICKET_CAPACITY;
	}

	public int getTotalTickets() {
		return totalTickets;
	}

	public void setTotalTickets(int totalTickets) throws IllegalArgumentException {
		if (totalTickets < 0) throw new IllegalArgumentException();
		this.totalTickets = totalTickets;
	}

	public int getTicketReleaseRate() {
		return ticketReleaseRate;
	}

	public void setTicketReleaseRate(int ticketReleaseRate) {
		if (ticketReleaseRate < 0) throw new IllegalArgumentException();
		this.ticketReleaseRate = ticketReleaseRate;
	}

	public int getCustomerRetrievalRate() {
		return customerRetrievalRate;
	}

	public void setCustomerRetrievalRate(int customerRetrievalRate) {
		if (customerRetrievalRate < 0) throw new IllegalArgumentException();
		this.customerRetrievalRate = customerRetrievalRate;
	}

	public int getMaxTicketCapacity() {
		return maxTicketCapacity;
	}

	public void setMaxTicketCapacity(int maxTicketCapacity) {
		if (maxTicketCapacity < 0) throw new IllegalArgumentException();
		this.maxTicketCapacity = maxTicketCapacity;
	}

	/**
	 * Either deserialize or write a default {@link Configuration} to <code>{cwd}/config.json</code>
	 * <p>
	 * This method is NOT thread-safe. Consult the information available in the {@link Configuration} javadoc.
	 *
	 * @throws IOException         thrown when the system fails to either write or read to config.json
	 * @throws JsonSyntaxException thrown when the JSON is malformed
	 */
	public static Configuration load() throws IOException, JsonSyntaxException {
		final String currentDir = System.getProperty("user.dir");
		final Path configPath = Path.of(currentDir, "config.json");

		final Gson gson = new Gson();
		Configuration config;

		if (Files.exists(configPath)) {
			final String jsonString = Files.readString(configPath);
			config = gson.fromJson(jsonString, Configuration.class);
			LOGGER.fine("Loaded configuration from " + configPath);
		} else {
			config = new Configuration();
			final String jsonString = gson.toJson(config);
			Files.write(configPath, jsonString.getBytes());
			LOGGER.fine("No existing config.json file was found, default configuration was written to " + configPath);
		}

		return config;
	}

	/**
	 * Saves a configuration file to `{cwd}/config.json`.
	 * <p>
	 * This method is NOT thread-safe. Consult the information available in the {@link Configuration} javadoc.
	 *
	 * @throws IOException When the system fails to read the config.json file
	 */
	public void save() throws IOException {
		final String currentDir = System.getProperty("user.dir");
		final Path configPath = Path.of(currentDir, "config.json");

		final Gson gson = new Gson();
		final String jsonString = gson.toJson(this);
		Files.write(configPath, jsonString.getBytes());
		LOGGER.fine("Saved configuration to " + configPath);
	}

	public String toFormattedString() {
		return "Configuration Details:\n" +
				"Total Tickets: " + totalTickets + "\n" +
				"Ticket Release Rate: " + ticketReleaseRate + "\n" +
				"Customer Retrieval Rate: " + customerRetrievalRate + "\n" +
				"Max Ticket Capacity: " + maxTicketCapacity;
	}

	@Override
	public String toString() {
		return "Configuration{" +
				"totalTickets=" + totalTickets +
				", ticketReleaseRate=" + ticketReleaseRate +
				", customerRetrievalRate=" + customerRetrievalRate +
				", maxTicketCapacity=" + maxTicketCapacity +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Configuration that = (Configuration) o;
		return totalTickets == that.totalTickets && ticketReleaseRate == that.ticketReleaseRate && customerRetrievalRate == that.customerRetrievalRate && maxTicketCapacity == that.maxTicketCapacity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
	}
}
