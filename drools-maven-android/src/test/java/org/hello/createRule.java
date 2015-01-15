package org.hello;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.drools.core.util.DroolsStreamUtils;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

public class createRule {

	@Test
	public void doIt() throws Exception {
		// File rule = new File(getClass().getClassLoader()
		// .getResource("helloworld.drl").getPath());

		// KieBase newKieBase;
		// newKieBase = (KieBase) DroolsStreamUtils.streamIn(rules, this
		// .getClass().getClassLoader(), true);
		// kieSession = newKieBase.newKieSession();

		// InputStream in = new FileInputStream(rule);
		//
		// final KieBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		// List<KnowledgePackage> pkgs = new ArrayList<KnowledgePackage>();
		// KnowledgePackage knowledgePackage = null;

		// Object streamIn = DroolsStreamUtils.streamIn(in);
		// knowledgePackage = (KnowledgePackage) streamIn;
		// pkgs.add(knowledgePackage);
		// kbase.(pkgs);
		// for(KnowledgePackage pkg : kbase.getKnowledgePackages()) {
		// System.out.println("Loaded rule package: " + pkg.toString());
		// for (Rule rule : pkg.getRules()) {
		// System.out.println("Rule: " + rule);
		// }
		// }
		// }
		//

		// read second rule from String
		String myRule = " package org.hello"
				+ " import org.hello.object.Message;"
				+ " global java.util.List list"
				+ " rule \"Hello World\""
				+ "    dialect \"mvel\""
				+ "     when"
				+ "         m : Message( status == Message.HELLO, message : message )"
				+ "     then"
				+ "     modify ( m ) { message = \"Goodbye cruel world\","
				+ "                    status = Message.GOODBYE };" + " end";

		KieServices kieServices = KieServices.Factory.get();
		File rule = new File(getClass().getClassLoader()
				.getResource("HelloWorld.drl").getPath());

		KieFileSystem kfs = kieServices.newKieFileSystem();

		//
		// ResourceFactory.newFileResource(rule)
		//
		// Resource myResource = kieServices.getResources()
		// .newReaderResource(new StringReader(myRule));
		kfs.write("src/main/resources/HelloWorld.drl",
				ResourceFactory.newFileResource(rule));

		// kbuilder.add( ResourceFactory.newClassPathResource(
		// "/hellodrools/testrules.drl",HelloDroolsNew.class),ResourceType.DRL
		// );

		KieBuilder kieBuilder = kieServices.newKieBuilder(kfs);

		// newKieBuilder(kfs).buildAll();
		Results results = kieBuilder.getResults();
		if (results.hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
			System.out.println(results.getMessages());
			throw new IllegalStateException("### errors ###");
		}

		KieContainer kieContainer = kieServices.newKieContainer(kieServices
				.getRepository().getDefaultReleaseId());
		KieBase kieBase = kieContainer.getKieBase();

		File compiledRuleFile = new File(getClass().getClassLoader()
				.getResource("compiledRules").getPath());
		// File newTemporaryFile = Files.newTemporaryFile();
		System.out.println(compiledRuleFile.getCanonicalPath());
		// Serialization.serialization(kieBase, compiledRuleFile);
		DroolsStreamUtils.streamOut(new FileOutputStream(compiledRuleFile),
				kieBase, false);

		KieBase newKieBase = (KieBase) DroolsStreamUtils.streamIn(
				new FileInputStream(compiledRuleFile), this.getClass()
						.getClassLoader(), false);
		// KieBase newKieBase = Serialization.deserialization(KieBase.class, compiledRuleFile);

		// (KieBase) DroolsStreamUtils.streamIn(
		// new FileInputStream(newTemporaryFile), this.getClass()
		// .getClassLoader(), true);
		KieSession newKieSession = newKieBase.newKieSession();
		newKieSession.fireAllRules();
		//
		// KieContainer newKieContainer =
		// kieServices.newKieClasspathContainer();
		// newKieContainer.newStatelessKieSession();
		//
		// KieSession kieSession = kieContainer.newKieSession();

	}
}
