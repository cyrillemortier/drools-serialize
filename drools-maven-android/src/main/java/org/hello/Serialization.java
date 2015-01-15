package org.hello;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import lombok.extern.slf4j.Slf4j;

public class Serialization {

	public static <T> void serialize(T o, File outputFile)
			throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
				outputFile));
		out.writeObject(o);
		out.close();

	}

	public static <T> T deserialize(Class<T> klass, File inputFile)
			throws StreamCorruptedException, FileNotFoundException,
			IOException, ClassNotFoundException {
		T t = null;
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(
				inputFile));
		t = klass.cast(in.readObject());
		in.close();
		return t;
	}
}
