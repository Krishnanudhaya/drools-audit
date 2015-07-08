package org.drools.audit.runtime.jpa;

import org.drools.audit.jpa.KieRuntimeEventPersister;
import org.drools.audit.runtime.AbstractRuleRuntimeEventLogger;
import org.drools.audit.runtime.RuntimeEventLog;

public class JpaRuleRuntimeEventLogger extends AbstractRuleRuntimeEventLogger{

	@Override
	protected void log(RuntimeEventLog log) {
		KieRuntimeEventPersister persister = new KieRuntimeEventPersister(log.getEnvironment());
		persister.persist(log);
	}

}
