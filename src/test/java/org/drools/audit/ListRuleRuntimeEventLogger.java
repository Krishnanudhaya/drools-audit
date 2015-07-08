package org.drools.audit;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.drools.audit.runtime.AbstractRuleRuntimeEventLogger;
import org.drools.audit.runtime.RuntimeEventLog;

public class ListRuleRuntimeEventLogger extends AbstractRuleRuntimeEventLogger {
	private final List<RuntimeEventLog> logs = new CopyOnWriteArrayList<RuntimeEventLog>();

	@Override
	protected void log(RuntimeEventLog log) {
		logs.add(log);
	}
	
	public List<RuntimeEventLog> getLogs() {
		return logs;
	}

}
