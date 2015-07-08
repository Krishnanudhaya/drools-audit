package org.drools.audit.runtime;

import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;

public abstract class AbstractRuleRuntimeEventLogger implements RuleRuntimeEventListener {
	
	protected abstract void log(RuntimeEventLog log);

	@Override
	public void objectInserted(ObjectInsertedEvent event) {
		this.log(new RuntimeEventLog(event));
	}

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
		this.log(new RuntimeEventLog(event));
	}

	@Override
	public void objectDeleted(ObjectDeletedEvent event) {
		this.log(new RuntimeEventLog(event));
	}
}
