package org.hello;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.hello.object.Message;
import org.hello.object.Status;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class HelloActivity extends Activity implements Observer {
	
	private KieSession kieSession;
	
	private Button fireRules;
	private Button loadDefaultMessage;

	private TextView messageScreen;
	private Spinner statusScreen;

	private static final ArrayList<String> status = new ArrayList<String>();
	static {
		for (Status sta : Status.values()) {
			status.add(sta.name());
		}
	}

	private Message message;

//	private static final String RULE = "	package org.hello                                                    "
//			+ "	                                                                     "
//			+ "	import org.hello.object.*;                                     "
//			+ "                                                                       "
//			+ "	global java.util.List list                                           "
//			+ "	                                                                     "
//			+ "	rule \"Hello World\"                                                 "
//			+ "		dialect \"mvel\"                                                 "
//			+ "		when                                                             "
//			+ "			m : Message( status == Status.HELLO, message : message )    "
//			+ "		then                                                             "
//			+ "		modify ( m ) {                                                   "
//			+ "						message = \"Goodbye cruel world\",               "
//			+ "						status = Status.GOODBYE                         "
//			+ "					  };                                                 "
//			+ "	end                                                                  ";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello_layout);

		fireRules = (Button) findViewById(R.id.fire_rules);
		loadDefaultMessage = (Button) findViewById(R.id.load_default_message);

		messageScreen = (EditText) findViewById(R.id.message_message);
		statusScreen = (Spinner) findViewById(R.id.message_status);

		ArrayAdapter<String> statusInitialAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, status);
		statusScreen.setAdapter(statusInitialAdapter);
		statusScreen.setSelection(1);
		
		message = new Message();
		message.addObserver(this);

		statusScreen.getSelectedItem().toString();

		
		
		// kieSession = new Ki(getResources().openRawResource(R.raw.kbase));

		 KieServices ks = KieServices.Factory.get();
		 KieContainer kContainer = ks.getKieClasspathContainer();
		 kieSession = kContainer.newKieSession();

		// Resource newClassPathResource =
		// ResourceFactory.newClassPathResource("MyProcess.bpmn");
		// InputStream openRawResource =
		// getResources().openRawResource(R.raw.kbase);

		// KieHelper kieHelper = new KieHelper();
		// KieBase kieBase = kieHelper.addContent(RULE,
		// ResourceType.DRL).build();
		// Collection<? extends KieSession> kieSessions =
		// kieBase.getKieSessions();
		// kieSessions.size();

		 kieSession.insert(message);

		fireRules.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				message.setMessage(messageScreen.getText().toString());
				message.setStatus(Status.valueOf(statusScreen
						.getSelectedItem().toString()));

				kieSession.fireAllRules();
			}
		});

		loadDefaultMessage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				message.setMessage("Hello World!");
				message.setStatus(Status.HELLO);
			}
		});

	}

	@Override
	public void update(Observable observable, Object data) {
		if (observable instanceof Message) {
			Message m = Message.class.cast(observable);
			if(m.getMessage()!=null){
				messageScreen.setText(m.getMessage());				
			}
			if (m.getStatus() != null){
				statusScreen.setSelection(status.indexOf(m.getStatus().name()));				
			}
		}
	}
}