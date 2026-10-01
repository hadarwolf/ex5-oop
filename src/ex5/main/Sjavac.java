package ex5.main;

import ex5.Scopes.Global;
import ex5.Scopes.SecondRun;

import java.io.IOException;

/**
 * The Sjavac class represents the main entry point for the compilation process.
 */
public class Sjavac {
    private static final String ONE_ARG = "need to get one arg";
    private static final String SJAVAC = "need to finish with sjava";
    private static final String SJAVA = ".*sjava";
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;


    Global globalRun; // Handles the first pass of the compilation (global validation).
    SecondRun secondRun; // Handles the second pass of the compilation (detailed scope checking).

    /**
     * Runs the compilation process for the provided file.
     *
     * @param fileName The name of the file to compile.
     * @throws Exception If any validation or runtime error occurs during the process.
     */
    private void run(String fileName) throws Exception {
        globalRun = new Global(fileName);
        new SecondRun(globalRun.getVariables(), globalRun.getMethods(), fileName);
        System.out.println(ZERO);
    }

    /**
     * The main method serves as the entry point of the program.
     * Validates the file extension and handles the compilation process.
     *
     * @param args Command-line arguments. Expects the file name as the first argument.
     * @throws Exception If any error occurs during the compilation process.
     */
    public static void main(String[] args) throws Exception {
        Sjavac sjavac = new Sjavac();
        if (args.length != ONE) {
            System.out.println(TWO);
            System.err.println(ONE_ARG);
            System.exit(ONE);
        }
        if (!args[ZERO].matches(SJAVA)) {
            System.out.println(TWO);
            System.err.println(SJAVAC);
            System.exit(ONE);
        }



        try {
            sjavac.run(args[ZERO]);
        } catch (IOException e) {
            System.out.println(TWO);
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(ONE);
            System.err.println(e.getMessage());
        }
    }
}
