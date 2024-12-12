package moe.curstantine.backend.service;

import moe.curstantine.backend.entity.Vendor;
import moe.curstantine.backend.runnable.RunnableVendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

@Service
public class VendorPoolService {
	private final TicketPoolService ticketPoolService;
	private final ConfigurationService configurationService;

	private final Collection<RunnableVendor> pool = Collections.synchronizedCollection(new ArrayList<>());

	private final static Logger LOGGER = Logger.getLogger(VendorPoolService.class.getName());

	@Autowired
	public VendorPoolService(TicketPoolService ticketPoolService, ConfigurationService configurationService) {
		this.ticketPoolService = ticketPoolService;
		this.configurationService = configurationService;
	}

	public void startRunnable(Vendor vendor) {
		final RunnableVendor runnable = new RunnableVendor(vendor, ticketPoolService, configurationService);
		final Thread thread = new Thread(runnable);

		thread.start();
		pool.add(runnable);
	}

	/**
	 * If at least one runnable is active
	 */
	public boolean isRunning() {
		for (RunnableVendor runnable : pool) {
			if (runnable.isRunning()) return true;
		}

		return false;
	}

	public void resumeSuspended() {
		for (RunnableVendor runnable : pool) {
			LOGGER.fine("Starting vendor " + runnable.getSelf().getId());

			if (runnable.isRunning()) continue;
			runnable.start();

			final Thread thread = new Thread(runnable);
			thread.start();
		}
	}

	public void suspendRunning() {
		for (RunnableVendor runnable : pool) {
			LOGGER.fine("Stopping vendor " + runnable.getSelf().getId());
			runnable.stop();
		}
	}
}
