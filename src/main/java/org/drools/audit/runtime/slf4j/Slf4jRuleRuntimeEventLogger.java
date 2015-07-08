package org.drools.audit.runtime.slf4j;

import org.drools.audit.runtime.AbstractRuleRuntimeEventLogger;
import org.drools.audit.runtime.RuntimeEventLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jRuleRuntimeEventLogger extends AbstractRuleRuntimeEventLogger{
	private static final Logger LOG = LoggerFactory.getLogger(Slf4jRuleRuntimeEventLogger.class);

	@Override
	protected void log(RuntimeEventLog log) {
		LOG.info(log.toString());
	}

}
