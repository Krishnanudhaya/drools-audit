package org.drools.audit.agenda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.kie.api.runtime.Environment;

@Entity
public class AgendaEventLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private AgendaEventLogType type;
	
	@Column
	private String name;
	
	private Environment environment;

	public AgendaEventLog(MatchCreatedEvent event) {
		this.type = AgendaEventLogType.MATCH_CREATED;
		this.name = event.getMatch().getRule().getName();
		this.environment = event.getKieRuntime().getEnvironment();
	}

	public AgendaEventLog(MatchCancelledEvent event) {
		this.type = AgendaEventLogType.MATCH_CANCELLED;
		this.name = event.getMatch().getRule().getName();
		this.environment = event.getKieRuntime().getEnvironment();
	}

	public AgendaEventLog(BeforeMatchFiredEvent event) {
		this.type = AgendaEventLogType.BEFORE_MATCH_FIRED;
		this.name = event.getMatch().getRule().getName();
		this.environment = event.getKieRuntime().getEnvironment();
	}

	public AgendaEventLog(AfterMatchFiredEvent event) {
		this.type = AgendaEventLogType.AFTER_MATCH_FIRED;
		this.name = event.getMatch().getRule().getName();
		this.environment = event.getKieRuntime().getEnvironment();
	}

	public AgendaEventLog(AgendaGroupPoppedEvent event) {
		this.type = AgendaEventLogType.AGENDA_GROUP_POPPED;
		this.name = event.getAgendaGroup().getName();
		this.environment = event.getKieRuntime().getEnvironment();
	}

	public AgendaEventLog(AgendaGroupPushedEvent event) {
		this.type = AgendaEventLogType.AGENDA_GROUP_PUSHED;
		this.name = event.getAgendaGroup().getName();
		this.environment = event.getKieRuntime().getEnvironment();
	}

	public AgendaEventLog(RuleFlowGroupActivatedEvent event) {
		this.type = AgendaEventLogType.RULEFLOW_GROUP_ACTIVATED;
		this.name = event.getRuleFlowGroup().getName();
		this.environment = event.getKieRuntime().getEnvironment();
	}

	public AgendaEventLog(RuleFlowGroupDeactivatedEvent event) {
		this.type = AgendaEventLogType.RULEFLOW_GROUP_DEACTIVATED;
		this.name = event.getRuleFlowGroup().getName();
		this.environment = event.getKieRuntime().getEnvironment();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AgendaEventLogType getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}
	
	public Environment getEnvironment() {
		return this.environment;
	}

	@Override
	public String toString() {
		return "AgendaEventLog [type=" + type + ", name=" + name + "]";
	}

}
