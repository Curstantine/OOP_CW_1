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
	private final Logger logger;

	public RunnableVendor(Vendor self, TicketPoolService ticketPoolService, ConfigurationService configurationService) {
		super(self);

		this.ticketPoolService = ticketPoolService;
		this.configurationService = configurationService;

		this.logger = Logger.getLogger(this.getClass().getName() + "-" + self.getId());
	}


	@Override
	public void run() {
		while (isRunning) {
			final int ticketReleaseRate = configurationService.getTicketReleaseRate();

//			for (int i = 0; i < ticketReleaseRate; i++) {
			final Ticket ticket = new Ticket(self.getId());

			try {
				ticketPoolService.addTicket(ticket);
				logger.info("Ticket created: " + ticket);
			} catch (ArrayIndexOutOfBoundsException e) {
				logger.info("Max amount of tickets already reached!");
			}
//			}

			try {
				//noinspection BusyWait
				Thread.sleep(ticketReleaseRate);
			} catch (InterruptedException e) {
				logger.severe("Thread sleep interrupted: " + e.getMessage());
				break;
			}
		}
	}
}
