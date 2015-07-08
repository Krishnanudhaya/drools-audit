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

	public static final int MATCH_CREATED = 0;
	public static final int MATCH_CANCELLED = 1;
	public static final int BEFORE_MATCH_FIRED = 2;
	public static final int AFTER_MATCH_FIRED = 3;
	public static final int AGENDA_GROUP_POPPED = 4;
	public static final int AGENDA_GROUP_PUSHED = 5;
	public static final int RULEFLOW_GROUP_ACTIVATED = 6;
	public static final int RULEFLOW_GROUP_DEACTIVATED = 7;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private int type;
	
	@Column
	private String name;
	
	private Environment environment;

	public AgendaEventLog(MatchCreatedEvent event) {
		this.type = MATCH_CREATED;
		this.name = event.getMatch().getRule().getName();
		this.environment = event.getKieRuntime().getEnvironment();
	}

	public AgendaEventLog(MatchCancelledEvent event) {
		this.type = MATCH_CANCELLED;
		this.name = event.getMatch().getRule().getName();
		this.environment = event.getKieRuntime().getEnvironment();
	}

	public AgendaEventLog(BeforeMatchFiredEvent event) {
		this.type = BEFORE_MATCH_FIRED;
		this.name = event.getMatch().getRule().getName();
		this.environment = event.getKieRuntime().getEnvironment();
	}

	public AgendaEventLog(AfterMatchFiredEvent event) {
		this.type = AFTER_MATCH_FIRED;
		this.name = event.getMatch().getRule().getName();
		this.environment = event.getKieRuntime().getEnvironment();
	}

	public AgendaEventLog(AgendaGroupPoppedEvent event) {
		this.type = AGENDA_GROUP_POPPED;
		this.name = event.getAgendaGroup().getName();
		this.environment = event.getKieRuntime().getEnvironment();
	}

	public AgendaEventLog(AgendaGroupPushedEvent event) {
		this.type = AGENDA_GROUP_PUSHED;
		this.name = event.getAgendaGroup().getName();
		this.environment = event.getKieRuntime().getEnvironment();
	}

	public AgendaEventLog(RuleFlowGroupActivatedEvent event) {
		this.type = RULEFLOW_GROUP_ACTIVATED;
		this.name = event.getRuleFlowGroup().getName();
		this.environment = event.getKieRuntime().getEnvironment();
	}

	public AgendaEventLog(RuleFlowGroupDeactivatedEvent event) {
		this.type = RULEFLOW_GROUP_DEACTIVATED;
		this.name = event.getRuleFlowGroup().getName();
		this.environment = event.getKieRuntime().getEnvironment();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getType() {
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
