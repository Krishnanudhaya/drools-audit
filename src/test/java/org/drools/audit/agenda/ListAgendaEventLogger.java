package org.drools.audit.agenda;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListAgendaEventLogger extends AbstractAgendaEventLogger{
	private final List<AgendaEventLog> logs = new CopyOnWriteArrayList<AgendaEventLog>();

	@Override
	protected void log(AgendaEventLog log) {
		logs.add(log);
	}
	
	public List<AgendaEventLog> getLogs() {
		return logs;
	}

}
