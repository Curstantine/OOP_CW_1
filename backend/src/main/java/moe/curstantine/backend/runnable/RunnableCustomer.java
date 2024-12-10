package moe.curstantine.backend.runnable;

import lombok.Getter;
import moe.curstantine.backend.entity.Customer;
import moe.curstantine.backend.entity.Ticket;
import moe.curstantine.backend.entity.Vendor;
import moe.curstantine.backend.service.ConfigurationService;
import moe.curstantine.backend.service.TicketPoolService;
import moe.curstantine.shared.AbstractRunnable;

import java.util.logging.Logger;

public class RunnableCustomer extends AbstractRunnable<Customer> {
	private final TicketPoolService ticketPoolService;
	private final ConfigurationService configurationService;
	private final Logger logger;

	public RunnableCustomer(Customer self, TicketPoolService ticketPoolService, ConfigurationService configurationService) {
		super(self);

		this.ticketPoolService = ticketPoolService;
		this.configurationService = configurationService;

		this.logger = Logger.getLogger("-" + self.getId());
	}

	@Override
	public void run() {
		while (isRunning) {
			final int retrievalRate = configurationService.getCustomerRetrievalRate();

			try {
				ticketPoolService.bookLatestTicket(self.getId());
				logger.info("Ticket booked: ");
			} catch (ArrayIndexOutOfBoundsException e) {
				logger.info("Max amount of tickets already reached!");
			}

			try {
				//noinspection BusyWait
				Thread.sleep(retrievalRate);
			} catch (InterruptedException e) {
				logger.severe("Thread sleep interrupted: " + e.getMessage());
				break;
			}
		}
	}
}
