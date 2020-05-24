package it.uniba.main.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import it.uniba.main.AppMain;

public class AppMainTest {
	@Test
	public void testMain() {

	    String input = "play\nboard\ne4\nquit\nsi\n";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);

	    AppMain.main(null);
	}
}
