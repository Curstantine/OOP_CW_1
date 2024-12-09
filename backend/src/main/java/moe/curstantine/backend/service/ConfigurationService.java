package moe.curstantine.backend.service;

import com.google.gson.JsonSyntaxException;
import moe.curstantine.shared.Configuration;
import moe.curstantine.shared.ReferenceConfiguration;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Delegate service for {@link Configuration}
 */
@Service
public class ConfigurationService {
	private final Configuration config;

	public ConfigurationService() throws IOException {
		this.config = Configuration.load();
	}

	public ReferenceConfiguration getRef() {
		return new ReferenceConfiguration(config);
	}
	
	public int getTotalTickets() {
		return config.getTotalTickets();
	}

	public void setTotalTickets(int totalTickets) throws IllegalArgumentException {
		config.setTotalTickets(totalTickets);
	}

	public int getTicketReleaseRate() {
		return config.getTicketReleaseRate();
	}

	public void setTicketReleaseRate(int ticketReleaseRate) throws IllegalArgumentException {
		config.setTicketReleaseRate(ticketReleaseRate);
	}

	public int getCustomerRetrievalRate() {
		return config.getCustomerRetrievalRate();
	}

	public void setCustomerRetrievalRate(int customerRetrievalRate) throws IllegalArgumentException {
		config.setCustomerRetrievalRate(customerRetrievalRate);
	}

	public int getMaxTicketCapacity() {
		return config.getMaxTicketCapacity();
	}

	public void setMaxTicketCapacity(int maxTicketCapacity) throws IllegalArgumentException {
		config.setMaxTicketCapacity(maxTicketCapacity);
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
		return Configuration.load();
	}

	/**
	 * Saves a configuration file to `{cwd}/config.json`.
	 * <p>
	 * This method is NOT thread-safe. Consult the information available in the {@link Configuration} javadoc.
	 *
	 * @throws IOException When the system fails to read the config.json file
	 */
	public void save() throws IOException {
		config.save();
	}
}
