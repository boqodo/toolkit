package java.leveldb;
import org.iq80.leveldb.*;
import org.junit.Test;

import static org.fusesource.leveldbjni.JniDBFactory.*;
import java.io.*;

public class LevelDBTest {

	@Test
	public final void test() throws Exception{
		Options options = new Options();
		options.createIfMissing(true);
		DB db = factory.open(new File("example"), options);
		try {
			// Use the db in here....
			//db.put(bytes("Tampa"), bytes("rocks"));
			String value = asString(db.get(bytes("Tampa")));
			System.out.println(value);
			//db.delete(bytes("Tampa"));
			
		} finally {
		  // Make sure you close the db to shutdown the 
		  // database and avoid resource leaks.
		  db.close();
		}
	}
}
