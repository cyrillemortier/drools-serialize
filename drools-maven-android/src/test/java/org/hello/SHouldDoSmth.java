
package org.hello;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.drools.core.util.DroolsStreamUtils;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

@Slf4j
public class SHouldDoSmth {
	
	@Test
	public void compile() throws Exception {
		 KieServices ks = KieServices.Factory.get();
		    KieFileSystem kfs = ks.newKieFileSystem();
		    
		    
		    File rule = new File(getClass().getClassLoader()
					.getResource("HelloWorld.drl").getPath());
		    kfs.write( "src/main/resources/HelloWorld.drl",
		                   ks.getResources().newInputStreamResource( rule.toURI().toURL().openStream() ) );
		    
		    KieBuilder kieBuilder = ks.newKieBuilder( kfs ).buildAll();
		    Results results = kieBuilder.getResults();
		    
		    if( results.hasMessages( Message.Level.ERROR ) ){
				for (Message message : results.getMessages()) {
					log.error( message.getText() );
				}
		        throw new IllegalStateException( "### errors ###" );
		    }
		    
		    KieContainer kieContainer =
		        ks.newKieContainer( ks.getRepository().getDefaultReleaseId() );

		    // CEP - get the KIE related configuration container and set the EventProcessing (from default cloud) to Stream
		    KieBaseConfiguration config = ks.newKieBaseConfiguration();
		    config.setOption( EventProcessingOption.STREAM );
		    KieBase kieBase = kieContainer.newKieBase( config );
		    File compiledRuleFile = new File(getClass().getClassLoader()
					.getResource("compiledRules").getPath());

		    log.debug(compiledRuleFile.getCanonicalPath());
		   
		    Serialization.serialize(kieBase, compiledRuleFile);
		    kieBase = Serialization.deserialize(KieBase.class, compiledRuleFile);
		    
		    //      KieSession kieSession = kieContainer.newKieSession();
		    KieSession kieSession = kieBase.newKieSession();
	}

	
}
