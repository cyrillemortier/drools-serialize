package org.hello.object;

import java.util.List;

import lombok.Data;

/**
 * This is a sample file to launch a rule package from a rule source file.
 */
@Data
public class Message {
	public static final int HELLO = 0;
	public static final int GOODBYE = 1;

	private String message;
	private int status;


	public static Message doSomething(Message message) {
		return message;
	}

	public boolean isSomething(String msg, List<Object> list) {
		list.add(this);
		return this.message.equals(msg);
	}

	public Message(String message) {
		this.message = message;
	}
}
