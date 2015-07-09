package org.drools.audit.agenda;

import org.drools.audit.DroolsHelper;
import org.drools.audit.model.Person;
import org.junit.Assert;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

public class BaseAgendaEventLoggerTest {

	@Test
	public void testMatchCreatedAndFired() {
		ListAgendaEventLogger logger = new ListAgendaEventLogger();
		KieSession kieSession = DroolsHelper.getKieSession();
		kieSession.addEventListener(logger);
		
		Person sal = new Person("Sal", "Elrahal", null);
		kieSession.insert(sal);
		kieSession.fireAllRules();
		
		Assert.assertEquals("Not exactly three events were captured!",3, logger.getLogs().size());
		Assert.assertEquals("Wrong type of event", AgendaEventLogType.MATCH_CREATED, logger.getLogs().get(0).getType());
		Assert.assertEquals("Wrong type of event", AgendaEventLogType.BEFORE_MATCH_FIRED, logger.getLogs().get(1).getType());
		Assert.assertEquals("Wrong type of event", AgendaEventLogType.AFTER_MATCH_FIRED, logger.getLogs().get(2).getType());

	}
	
	@Test
	public void testAgendaGroupPushed() {
		ListAgendaEventLogger logger = new ListAgendaEventLogger();
		KieSession kieSession = DroolsHelper.getKieSession();
		kieSession.addEventListener(logger);
		
		String agendaGroupName = "agendagroupname";
		kieSession.getAgenda().getAgendaGroup(agendaGroupName).setFocus();
		
		
		Assert.assertEquals("Not exactly five events were captured!", 1, logger.getLogs().size());
		Assert.assertEquals("Wrong type of event", AgendaEventLogType.AGENDA_GROUP_PUSHED, logger.getLogs().get(0).getType());
		Assert.assertEquals("Wrong name of event", agendaGroupName, logger.getLogs().get(0).getName());
	}
	
	@Test
	public void testAgendaGroupPopped() {
		ListAgendaEventLogger logger = new ListAgendaEventLogger();
		KieSession kieSession = DroolsHelper.getKieSession();
		kieSession.addEventListener(logger);
		
		String agendaGroupName = "agendagroupname";
		kieSession.getAgenda().getAgendaGroup(agendaGroupName).setFocus();
		kieSession.fireAllRules();
		
		
		Assert.assertEquals("Not exactly five events were captured!", 2, logger.getLogs().size());
		Assert.assertEquals("Wrong type of event", AgendaEventLogType.AGENDA_GROUP_PUSHED, logger.getLogs().get(0).getType());
		Assert.assertEquals("Wrong name of event", agendaGroupName, logger.getLogs().get(1).getName());
		Assert.assertEquals("Wrong type of event", AgendaEventLogType.AGENDA_GROUP_POPPED, logger.getLogs().get(1).getType());
		Assert.assertEquals("Wrong name of event", agendaGroupName, logger.getLogs().get(1).getName());
	}
}