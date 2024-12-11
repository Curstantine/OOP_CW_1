package moe.curstantine.backend.service;

import moe.curstantine.backend.entity.Vendor;
import moe.curstantine.backend.runnable.RunnableVendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
public class VendorPoolService {
	private final TicketPoolService ticketPoolService;
	private final ConfigurationService configurationService;

	private final Collection<RunnableVendor> pool = Collections.synchronizedCollection(new ArrayList<>());

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

	public void resumeSuspended() {
		for (RunnableVendor runnable : pool) {
			if (runnable.isRunning()) continue;
			runnable.start();

			final Thread thread = new Thread(runnable);
			thread.start();
		}
	}

	public void suspendRunning() {
		for (RunnableVendor runnable : pool) {
			runnable.stop();
		}
	}
}
