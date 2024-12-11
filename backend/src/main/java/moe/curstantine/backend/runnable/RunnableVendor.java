package moe.curstantine.backend.runnable;

import moe.curstantine.backend.entity.Ticket;
import moe.curstantine.backend.entity.Vendor;
import moe.curstantine.backend.service.ConfigurationService;
import moe.curstantine.backend.service.TicketPoolService;
import moe.curstantine.shared.AbstractRunnable;

import java.util.logging.Logger;

public class RunnableVendor extends AbstractRunnable<Vendor> {
	private final TicketPoolService ticketPoolService;
	private final ConfigurationService configurationService;

	private static final Logger LOGGER = Logger.getLogger(RunnableVendor.class.getName());

	public RunnableVendor(Vendor self, TicketPoolService ticketPoolService, ConfigurationService configurationService) {
		super(self);

		this.ticketPoolService = ticketPoolService;
		this.configurationService = configurationService;
	}

	@Override
	protected void action() {
		final Ticket ticket = new Ticket(self.getId());

		try {
			ticketPoolService.addTicket(ticket);
			LOGGER.info("Ticket created: " + ticket);
		} catch (ArrayIndexOutOfBoundsException e) {
			LOGGER.info("Max amount of tickets already reached!");
		}
	}

	@Override
	protected int getDelayDuration() {
		return configurationService.getTicketReleaseRate();
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
