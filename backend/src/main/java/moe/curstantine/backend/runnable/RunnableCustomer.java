package moe.curstantine.backend.runnable;

import moe.curstantine.backend.entity.Customer;
import moe.curstantine.backend.entity.Ticket;
import moe.curstantine.backend.service.ConfigurationService;
import moe.curstantine.backend.service.TicketPoolService;
import moe.curstantine.shared.AbstractRunnable;

import java.util.logging.Logger;

public class RunnableCustomer extends AbstractRunnable<Customer> {
	private final TicketPoolService ticketPoolService;
	private final ConfigurationService configurationService;

	private final static Logger LOGGER = Logger.getLogger(RunnableCustomer.class.getName());

	public RunnableCustomer(Customer self, TicketPoolService ticketPoolService, ConfigurationService configurationService) {
		super(self);

		this.ticketPoolService = ticketPoolService;
		this.configurationService = configurationService;
	}

	@Override
	public void action() {
		try {
			final Ticket latest = ticketPoolService.bookLatestTicket(self.getId());
			LOGGER.info("Ticket booked: " + latest.getId() + " by " + self.getName());
		} catch (ArrayIndexOutOfBoundsException e) {
			LOGGER.info("Max amount of tickets already reached!");
		}
	}

	@Override
	protected int getDelayDuration() {
		return configurationService.getCustomerRetrievalRate();
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
