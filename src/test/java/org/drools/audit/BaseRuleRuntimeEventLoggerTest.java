package org.drools.audit;


import org.drools.audit.model.Person;
import org.drools.audit.runtime.RuntimeEventLog;
import org.junit.Assert;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

public class BaseRuleRuntimeEventLoggerTest {
	
	@Test
	public void testInsertObject() {
		ListRuleRuntimeEventLogger logger = new ListRuleRuntimeEventLogger();
		KieSession kieSession = DroolsHelper.getKieSession();
		kieSession.addEventListener(logger);
		
		Person sal = new Person("Sal", "Elrahal", 87);
		kieSession.insert(sal);
		
		Assert.assertEquals("Not exactly one event was captured!",1, logger.getLogs().size());
		RuntimeEventLog log = logger.getLogs().get(0);
		Assert.assertEquals("Wrong type of event" ,RuntimeEventLog.OBJECT_INSERTED, log.getType());
		Assert.assertEquals("Wrong fact in event", sal, log.getFact());
	}
}
