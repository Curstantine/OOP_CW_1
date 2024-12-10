package moe.curstantine.backend.interfaces;

import moe.curstantine.backend.entity.Ticket;

public interface Consumer {
	void bookTicket(Ticket ticket);
}
