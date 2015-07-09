package org.drools.audit.runtime;


import org.drools.audit.DroolsHelper;
import org.drools.audit.model.Person;
import org.drools.audit.runtime.RuntimeEventLog;
import org.junit.Assert;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

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
	
	@Test
	public void testInsertUpdateObject() {
		ListRuleRuntimeEventLogger logger = new ListRuleRuntimeEventLogger();
		KieSession kieSession = DroolsHelper.getKieSession();
		kieSession.addEventListener(logger);
		
		Person sal = new Person("Sal", "Elrahal", 87);
		FactHandle salHandle = kieSession.insert(sal);
		sal.setAge(33);
		kieSession.update(salHandle, sal);
		
		Assert.assertEquals("Not exactly two events were  captured!",2, logger.getLogs().size());
		Assert.assertEquals("Wrong type of event" ,RuntimeEventLog.OBJECT_INSERTED, logger.getLogs().get(0).getType());
		Assert.assertEquals("Wrong type of event" ,RuntimeEventLog.OBJECT_UPDATED, logger.getLogs().get(1).getType());
	}
	
	@Test
	public void testInsertDeleteObject() {
		ListRuleRuntimeEventLogger logger = new ListRuleRuntimeEventLogger();
		KieSession kieSession = DroolsHelper.getKieSession();
		kieSession.addEventListener(logger);
		
		Person sal = new Person("Sal", "Elrahal", 87);
		FactHandle salHandle = kieSession.insert(sal);
		sal.setAge(33);
		kieSession.delete(salHandle);
		
		Assert.assertEquals("Not exactly two events were  captured!",2, logger.getLogs().size());
		Assert.assertEquals("Wrong type of event" ,RuntimeEventLog.OBJECT_INSERTED, logger.getLogs().get(0).getType());
		Assert.assertEquals("Wrong type of event" ,RuntimeEventLog.OBJECT_DELETED, logger.getLogs().get(1).getType());
	}
	
	@Test
	public void testInsertUpdateDeleteObject() {
		ListRuleRuntimeEventLogger logger = new ListRuleRuntimeEventLogger();
		KieSession kieSession = DroolsHelper.getKieSession();
		kieSession.addEventListener(logger);
		
		Person sal = new Person("Sal", "Elrahal", 87);
		FactHandle salHandle = kieSession.insert(sal);
		sal.setAge(33);
		kieSession.update(salHandle, sal);
		kieSession.delete(salHandle);
		
		Assert.assertEquals("Not exactly three events were captured!",3, logger.getLogs().size());
		Assert.assertEquals("Wrong type of event" ,RuntimeEventLog.OBJECT_INSERTED, logger.getLogs().get(0).getType());
		Assert.assertEquals("Wrong type of event" ,RuntimeEventLog.OBJECT_UPDATED, logger.getLogs().get(1).getType());
		Assert.assertEquals("Wrong type of event" ,RuntimeEventLog.OBJECT_DELETED, logger.getLogs().get(2).getType());
	}
}
