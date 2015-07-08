package org.drools.audit.runtime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.rule.FactHandle;

@Entity
public class RuntimeEventLog {
	
	public static final int OBJECT_INSERTED = 0;
	public static final int OBJECT_UPDATED = 1;
	public static final int OBJECT_DELETED = 2;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column
	private int type;
	
	@Column
	private Object fact;
	
	@Column
	private FactHandle factHandle;
	
	private Environment environment;
	
	public RuntimeEventLog(ObjectInsertedEvent event) {
		this.type = OBJECT_INSERTED;
		this.fact = event.getObject();
		this.factHandle = event.getFactHandle();
		this.environment = event.getKieRuntime().getEnvironment();
	}
	
	public RuntimeEventLog(ObjectUpdatedEvent event) {
		this.type = OBJECT_UPDATED;
		this.fact = event.getObject();
		this.factHandle = event.getFactHandle();
		this.environment = event.getKieRuntime().getEnvironment();
	}
	
	public RuntimeEventLog(ObjectDeletedEvent event) {
		this.type = OBJECT_DELETED;
		this.fact = event.getOldObject();
		this.factHandle = event.getFactHandle();
		this.environment = event.getKieRuntime().getEnvironment();
	}
	
	public int getType() {
		return type;
	}
	
	public Object getFact() {
		return fact;
	}
	
	public FactHandle getFactHandle() {
		return factHandle;
	}
	
	public Environment getEnvironment() {
		return environment;
	}

	@Override
	public String toString() {
		return "RuntimeEventLog [type=" + type + ", fact=" + fact
				+ ", factHandle=" + factHandle + "]";
	}
	
	
}
