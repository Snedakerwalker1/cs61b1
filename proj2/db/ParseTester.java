package db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ParseTester {
    private static final String EXIT   = "exit";
    private static final String PROMPT = "> ";
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(PROMPT);
        String line = "";
        while ((line = in.readLine()) != null) {
            if (EXIT.equals(line)) {
                break;
            }
            if (!line.trim().isEmpty()) {
                Parse.eval(line);
            }
            System.out.print(PROMPT);
        }
        in.close();
    }
}
