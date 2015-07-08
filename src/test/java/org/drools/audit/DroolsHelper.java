package org.drools.audit;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;

public class DroolsHelper {
	private static final KieBase kieBase = KieServices.Factory.get()
			.getKieClasspathContainer(DroolsHelper.class.getClassLoader())
			.getKieBase();

	public static KieSession getKieSession() {
        return kieBase.newKieSession();
	}
}
