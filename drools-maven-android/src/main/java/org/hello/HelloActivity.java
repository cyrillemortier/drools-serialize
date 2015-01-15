package org.hello;

import org.hello.drools.Session;
import org.hello.object.Message;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelloActivity extends Activity {
	private Session session;
	private Button fireRules;
	private TextView messageInitial;
	private TextView messageFinal;
	private Message message;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello_layout);
		fireRules = (Button) findViewById(R.id.fire_rules);
		messageInitial = (TextView) findViewById(R.id.message_initial);
		messageFinal = (TextView) findViewById(R.id.message_final);

		session = new Session(getResources().openRawResource(R.raw.temp123));
		message = createMessage();
		
		messageInitial.setText(message.getMessage());

		session.insert(message);

		fireRules.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				session.fireAllRules();
				messageFinal.setText(message.getMessage());
			}
		});

	}

	private Message createMessage() {
		Message message = new Message("Hello World");
		message.setStatus(Message.HELLO);
		return message;
	}
}