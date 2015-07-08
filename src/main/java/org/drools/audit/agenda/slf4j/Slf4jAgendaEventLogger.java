package org.drools.audit.agenda.slf4j;

import org.drools.audit.agenda.AbstractAgendaEventLogger;
import org.drools.audit.agenda.AgendaEventLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jAgendaEventLogger extends AbstractAgendaEventLogger {
	private static final Logger LOG = LoggerFactory.getLogger(Slf4jAgendaEventLogger.class);

	@Override
	protected void log(AgendaEventLog log) {
		LOG.info(log.toString());
	}

}
