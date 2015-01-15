package org.hello.drools;

//import java.io.IOException;
import java.io.InputStream;



import org.hello.Serialization;
import org.kie.api.KieBase;
//import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;

public class Session {

	private KieSession kieSession;

	public Session(InputStream rules) {

//		KieBase kieBase = Serialization.deserialize(KieBase.class, rules);
//			kieSession = kieBase.newKieSession();

	}

	public void insert(Object o) {
		kieSession.insert(o);
	}

	public void fireAllRules() {
		kieSession.fireAllRules();
	}

}
