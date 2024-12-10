package moe.curstantine.backend.service;

import moe.curstantine.backend.entity.Customer;
import moe.curstantine.backend.repository.CustomerRepository;
import moe.curstantine.backend.runnable.RunnableCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
public class CustomerPoolService {
	private final TicketPoolService ticketPoolService;
	private final ConfigurationService configurationService;

	private final Collection<RunnableCustomer> pool = Collections.synchronizedCollection(new ArrayList<>());

	@Autowired
	public CustomerPoolService(TicketPoolService ticketPoolService, ConfigurationService configurationService) {
		this.ticketPoolService = ticketPoolService;
		this.configurationService = configurationService;
	}


	public void startRunnable(Customer customer) {
		final RunnableCustomer runnable = new RunnableCustomer(customer, ticketPoolService, configurationService);
		final Thread thread = new Thread(runnable);

		thread.start();
		pool.add(runnable);
	}

	public void resumeSuspended() {
		for (RunnableCustomer runnable : pool) {
			if (runnable.isRunning()) continue;
			runnable.start();

			final Thread thread = new Thread(runnable);
			thread.start();
		}
	}

	public void suspendRunning() {
		for (RunnableCustomer runnable : pool) {
			runnable.stop();
		}
	}
}
