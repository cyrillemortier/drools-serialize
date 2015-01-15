package org.hello.drools;

//import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;

import lombok.extern.slf4j.Slf4j;

import org.hello.Serialization;
import org.kie.api.KieBase;
//import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;

@Slf4j
public class Session {

	private KieSession kieSession;

	public Session(InputStream stream) {
		try {
			File file = Tofile(stream);
			KieBase kieBase = Serialization.deserialize(KieBase.class, file);
			kieSession = kieBase.newKieSession();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	private File Tofile(InputStream stream) {
		File file = null;
		OutputStream outputStream = null;
		try {
			file = File.createTempFile("temp", "object");
			outputStream = new FileOutputStream(file);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = stream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				log.error(e.getLocalizedMessage());
			}
			try {
				// outputStream.flush();
				outputStream.close();
			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
			}
		}
		return file;
	}

	public void insert(Object o) {
		try {
			kieSession.insert(o);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	public void fireAllRules() {
		try {
			kieSession.fireAllRules();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		
	}

}
