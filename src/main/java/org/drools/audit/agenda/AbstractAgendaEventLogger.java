package org.drools.audit.agenda;

import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;

public abstract class AbstractAgendaEventLogger implements AgendaEventListener{
	
	protected abstract void log(AgendaEventLog log);

	@Override
	public void matchCreated(MatchCreatedEvent event) {
		this.log(new AgendaEventLog(event));
	}

	@Override
	public void matchCancelled(MatchCancelledEvent event) {
		this.log(new AgendaEventLog(event));
	}

	@Override
	public void beforeMatchFired(BeforeMatchFiredEvent event) {
		this.log(new AgendaEventLog(event));
	}

	@Override
	public void afterMatchFired(AfterMatchFiredEvent event) {
		this.log(new AgendaEventLog(event));
	}

	@Override
	public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
		this.log(new AgendaEventLog(event));
	}

	@Override
	public void agendaGroupPushed(AgendaGroupPushedEvent event) {
		this.log(new AgendaEventLog(event));
	}

	@Override
	public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
		this.log(new AgendaEventLog(event));
	}

	@Override
	public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
		this.log(new AgendaEventLog(event));
	}

	@Override
	public void beforeRuleFlowGroupDeactivated(
			RuleFlowGroupDeactivatedEvent event) {
		this.log(new AgendaEventLog(event));
	}

	@Override
	public void afterRuleFlowGroupDeactivated(
			RuleFlowGroupDeactivatedEvent event) {
		this.log(new AgendaEventLog(event));
	}

}
