import java.util.*;
import org.apache.commons.cli.*;
import java.math.*;


/**
 * <p>Temperature conversion program using Apache Commons CLI library.</p>
 *
 * <p>The program accepts the following flags:</p>
 *
 * <p>--input UNIT=VALUE, -i VALUE: the input temperature following the format:
 *              <temp> <unit> where <temp> is a number and <unit> is either
 *              'C' for Celsius or 'F' for Fahrenheit or 'K' for Kelvin.</p>
 *
 * <p>--output UNIT, -o UNIT: the output temperature following the format:
 *               <unit> where <unit> is either 'C' for Celsius or 'F' for
 *               Fahrenheit or 'K' for Kelvin.</p>
 *
 * <p>The example proper usage of the program is:</p>
 *
 * <p>java TempConv -i C=10 -o F</p>
 *               <p>where the input temperature is 10 Celsius and the output
 *               temperature is Fahrenheit.</p>
 *
 * <p>The program will print the converted temperature
 * to the standard output.</p>
 *
 * @author hzhu20@georgefox.edu (Harry Zhu)
 * @author ccalquhoun19@georgefox.edu (Cameron Calquhoun)
 * @author nnitta18@georgefox.edu (Nicolle Nitta)
 */
public class TempConv {


    /**
     * Enumeration of the possible exit statuses
     * of the program.
     *
     * @author hzhu20@georgefox.edu (Harry Zhu)
     */
    public enum ExitStatus {
        SUCCESS(0),
        INC_ARGS(1),
        INV_IO_ARGS(2),
        NAN_RESULT(3),
        INF_RESULT(4),
        NULL_RESULT(5),
        MISSING_RESOURCE(6);

        private final int status;

        ExitStatus(int status) {
            this.status = status;
        }

        public int code() {
            return status;
        }
    }


    // The default precision for the output temperature is 5 decimal places
    private static final int DEFAULT_PRECISION = 7;

    // The minimum precision for the output temperature is 0 decimal place
    private static final int MINIMUM_PRECISION = 0;

    // The maximum precision for the output temperature is 5 decimal places
    private static final int MAXIMUM_PRECISION = DEFAULT_PRECISION;


    /**
     * The main method of the program. Accepts the input and output units and
     * converts the input temperature to the output temperature.
     *
     * @author ccalquhoun19@georgefox.edu (Cameron Calquhoun)
     * @param args the input unit and value, and the output unit to convert to
     *
     * @return the exit status of the program after successful conversion and
     *         display of the converted temperature
     */
    public static void main(String[] args) {

        // if args is not empty
        if (args.length == 0) {
            System.exit(ExitStatus.INC_ARGS.code());
        }

        // LOCALE
        Locale currentLocale = Locale.getDefault();
        String header = "";
        String inputDesc = "";
        String outputDesc = "";
        String helpDesc = "";; 
        String precisionDesc = "";
        String translationCredits = "";

        try {

            ResourceBundle messages = ResourceBundle.getBundle("TempConv", currentLocale);
            header = messages.getString("header");
            inputDesc = messages.getString("inputOptDesc");
            outputDesc = messages.getString("outputOptDesc");
            helpDesc = messages.getString("helpOptDesc");
            precisionDesc = messages.getString("precisionOptDesc");
            translationCredits = messages.getString("translationCredits");

        } catch (MissingResourceException e) {
            System.exit(ExitStatus.MISSING_RESOURCE.code());
        }

        // Declare input and output temperature
        String[] input = new String[2];
        String output = "";

        // Declare input temp as float and input unit variables as String
        float inputValue = 0f;
        String inputUnit = "";

        // Declare precision as the default decimal places
        int precision = DEFAULT_PRECISION;

        // Declare a float to store the converted temperature
        float result = 0f;

        // Create the command line parser
        CommandLineParser parser = new DefaultParser();

        // Create the formatter for the help message
        HelpFormatter formatter = new HelpFormatter();

        // Create the options
        Options options = new Options();

        // Create the input option to accept the input temperature
        Option inputOpt = new Option("i", "input", true, inputDesc);

        // Allows users to specify output unit
        Option outputOpt = new Option("o", "output", true, outputDesc);

        // Provide users assistance with the program
        Option helpOpt = new Option("h", "help", false, helpDesc);

        // Allow users to specify the precision of the output temperature
        Option precisionOpt = new Option("p", "precision", true, precisionDesc);

        // Add each option to options
        options.addOption(inputOpt);
        options.addOption(outputOpt);
        options.addOption(helpOpt);
        options.addOption(precisionOpt);

        // Specify the parameters of the options, allowing for multiple parameters
        // and requiring a value from each option

        inputOpt.setArgs(2);
        inputOpt.setValueSeparator('=');
        inputOpt.setRequired(true);
        outputOpt.setRequired(true);

        try {
            // Parse a command line and store the result in a
            // CommandLine object
            CommandLine line = parser.parse(options, args);

            // Check for option H
            if (line.hasOption("help")) {

                // If the option is found, print the help message
                // and exit with status 0

                formatter.printHelp("TempConv", header, options,
                    translationCredits, true);

                System.exit(ExitStatus.SUCCESS.code());
            }

            // Check for option i
            if (line.hasOption("input")) {
                // If the option is found, store the input temperature
                // and unit in the input array
                input = line.getOptionValues("input");
                inputUnit = input[0];
                inputValue = Float.parseFloat(input[1]);

                // directly inside checking input, check for option o,
                if (line.hasOption("output")) {
                    // If the option is found, store the output
                    // temperature in the output variable
                    output = line.getOptionValue("output");
                }
            }

            // Check for option p
            if (line.hasOption("precision")) {
                // If the option is found, store the precision in
                // the precision variable or else
                precision = Integer.parseInt(line.getOptionValue("precision"));

                // if precision is less than 0, exit with status 2
                if (precision < MINIMUM_PRECISION
                    || precision > MAXIMUM_PRECISION) {

                    System.exit(ExitStatus.INV_IO_ARGS.code());
                }
            }
        } catch (ParseException e) {
            System.exit(ExitStatus.INV_IO_ARGS.code());
        } catch (NumberFormatException e) {
            System.exit(ExitStatus.NAN_RESULT.code());
        } catch (NullPointerException e) {
            System.exit(ExitStatus.NULL_RESULT.code());
        } catch (MissingResourceException e) {
            System.exit(ExitStatus.MISSING_RESOURCE.code());
        }

        // select the conversion method and evaluate the result
        result = selectConversion(inputValue, inputUnit, output);

        // exits when the result is not a number
        checkNaN(result);

        // exits when the result is infinite
        checkInfinite(result);

        // recognize modern science
        result = normalize(result, output);

        // round the result to the specified precision if given
        result = round(result, precision);

        // display the result to stdout
        System.out.println(result);

        // exit with success code
        System.exit(ExitStatus.SUCCESS.code());
    }

    /**
     * Selects the conversion method based on the input and output units
     *
     * @author hzhu20@georgefox.edu (Harry Zhu)
     * @param inputValue the input temperature value
     * @param inputUnit the input temperature unit
     * @param output the output temperature unit
     * @return the converted temperature
     */
    private static float selectConversion(float inputValue,
                                          String inputUnit,
                                          String output) {

        float result = 0f;

        if (inputUnit.equals("C") || inputUnit.equals("Celsius")) {

            if (output.equals("F") || output.equals("Fahrenheit")) {
                result = convertCelsiusToFahrenheit(inputValue);

            } else if (output.equals("K") || output.equals("Kelvin")) {
                result = convertCelsiusToKelvin(inputValue);

            } else if (output.equals("C") || output.equals("Celsius")) {
                result = inputValue;

            } else {
                System.exit(ExitStatus.INV_IO_ARGS.code());
            }

        } else if (inputUnit.equals("F") || inputUnit.equals("Fahrenheit")) {

            if (output.equals("C") || output.equals("Celsius")) {
                result = convertFahrenheitToCelsius(inputValue);

            } else if (output.equals("K") || output.equals("Kelvin")) {
                result = convertFahrenheitToKelvin(inputValue);

            } else if (output.equals("F") || output.equals("Fahrenheit")) {
                result = inputValue;

            } else {
                System.exit(ExitStatus.INV_IO_ARGS.code());
            }

        } else if (inputUnit.equals("K") || inputUnit.equals("Kelvin")) {

            if (output.equals("C") || output.equals("Celsius")) {
                result = convertKelvinToCelsius(inputValue);

            } else if (output.equals("F") || output.equals("Fahrenheit")) {
                result = convertKelvinToFahrenheit(inputValue);

            } else if (output.equals("K") || output.equals("Kelvin")) {
                result = inputValue;

            } else {
                System.exit(ExitStatus.INV_IO_ARGS.code());
            }
        } else {
            System.exit(ExitStatus.INV_IO_ARGS.code());
        }

        return result;
    }


    /**
     * Rounds a float to the specified precision and returns the result
     *
     * @author hzhu20@georgefox.edu (Harry Zhu)
     * @param value the float to round
     * @param precision the precision to round to
     * @return the rounded float
     */
    private static float round(float result, int precision) {

        // BigDecimal is used to round the result to the specified precision
        BigDecimal bd = new BigDecimal(result);

        // round the result to the specified precision
        bd = bd.setScale(precision, RoundingMode.UP);

        // return the rounded result
        return bd.floatValue();
    }


    /**
     * Checks for Kelvin absolute 0 and changes result to 0 K
     *
     * @param value the value to check
     */
    private static float normalize(float value, String output) {

        float result = 0.0f;


        // If value is 0 and output is Kelvin, change result to 0 K
        if (value < 0f && (output.equals("K") || output.equals("Kelvin"))) {
            result = 0f;
        } else {
            result = value;
        }

        return result;
    }


    /**
     * Validates the input temperature value
     *
     * @author hzhu20@georgefox.edu (Harry Zhu)
     * @param value the input temperature value
     */
    private static void checkNaN(float value) {
        if (value == Float.NaN) {
            System.exit(ExitStatus.NAN_RESULT.code());
        }
    }


    /**
     * Checks if value is INF
     *
     * @author hzhu20@georgefox.edu (Harry Zhu)
     * @param value the value to check
     */
    private static void checkInfinite(float value) {

        // If the value is infinite, exit with status 4
        if (Float.isInfinite(value)) {
            System.exit(ExitStatus.INF_RESULT.code());
        }
    }


    /**
     * Converts the temperature from Fahrenheit to Celsius
     * �C = 5/9 (�F - 32)
     *
     * @author nnitta18@georgefox.edu (Nicolle Nitta)
     * @param fahrenheit the temperature in Fahrenheit
     * @return the temperature in Celsius
     */
    private static float convertFahrenheitToCelsius(float fahrenheit) {
        float celsius = ((fahrenheit - 32) * (5 / 9f));
        return celsius;
    }


    /**
     * Converts the temperature from Celsius to Fahrenheit
     * �F = 9/5 (�C) + 32
     *
     * @author nnitta18@georgefox.edu (Nicolle Nitta)
     * @param celsius the temperature in Celsius
     * @return the temperature in Fahrenheit
     */
    private static float convertCelsiusToFahrenheit(float celsius) {
        float fahrenheit = ((9 / 5f) * celsius) + 32;
        return fahrenheit;
    }


    /**
     * Convert a temperature in Kelvin to Celsius
     * �C = K - 273.15
     *
     * @author nnitta18@georgefox.edu (Nicolle Nitta)
     * @param kelvin the temperature in Kelvin
     * @return the temperature in Celsius
     */
    private static float convertKelvinToCelsius(float kelvin) {
        float celsius = kelvin - 273.15f;
        return celsius;
    }


    /**
     * Convert a temperature in Celsius to Kelvin
     * K = �C + 273.15
     *
     * @author nnitta18@georgefox.edu (Nicolle Nitta)
     * @param celsius the temperature in Celsius
     * @return the temperature in Kelvin
     */
    private static float convertCelsiusToKelvin(float celsius) {
        float kelvin = celsius + 273.15f;
        return kelvin;
    }


    /**
     * Convert a temperature in Kelvin to Fahrenheit
     * �F = 9/5 (K - 273.15) + 32
     *
     * @author nnitta18@georgefox.edu (Nicolle Nitta)
     * @param kelvin the temperature in Kelvin
     * @return the temperature in Fahrenheit
     */
    private static float convertKelvinToFahrenheit(float kelvin) {
        float fahrenheit = ((9 / 5f) * (kelvin - 273.15f) + 32);
        return fahrenheit;
    }


    /**
     * Convert a temperature in Fahrenheit to Kelvin
     * K = 5/9 (�F - 32) + 273.15
     *
     * @author nnitta18@georgefox.edu (Nicolle Nitta)
     * @param fahrenheit the temperature in Fahrenheit
     * @return the temperature in Kelvin
     */
    private static float convertFahrenheitToKelvin(float fahrenheit) {
        float kelvin = ((5 / 9f) * (fahrenheit - 32)) + 273.15f;
        return kelvin;
    }
}