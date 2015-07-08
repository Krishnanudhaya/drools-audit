package org.drools.audit.agenda.jpa;

import org.drools.audit.agenda.AbstractAgendaEventLogger;
import org.drools.audit.agenda.AgendaEventLog;
import org.drools.audit.jpa.KieRuntimeEventPersister;

public class JpaAgendaEventLogger extends AbstractAgendaEventLogger {

	@Override
	protected void log(AgendaEventLog log) {
		KieRuntimeEventPersister persister = new KieRuntimeEventPersister(log.getEnvironment());
		persister.persist(log);
	}
}
