import static org.hamcrest.Matchers.*;
import org.junit.*;
import org.junit.rules.*;
import static org.junit.Assert.*;

import java.util.Locale;

import org.hamcrest.*;
import org.junit.contrib.java.lang.system.*;

@SuppressWarnings("deprecation")
public class TempConvTest {

    private static final double EPSILON = 1e-6;

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Rule
    public final SystemOutRule stdout = new SystemOutRule().enableLog().mute();

    @Before
    public void setup() {
        Locale.setDefault(Locale.US);
    }

    @Test
    public void test_TempConv_fahreinheitToKelvin()
    {
        //  set up args with Fahrenheit = 0
        final String[] args = {"-i", "F", "0", "-o", "K"};

        // set up the expected correct result, 255.37222
        final double correctResult = 255.37222;

        // expect the program to exit 0 (success)
        exit.expectSystemExitWithStatus(0);

        // check the result that is printed to standard output is the correct value
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String printedResult = stdout.getLog();
                double result = Double.parseDouble(printedResult);
                assertThat(result, closeTo(correctResult, EPSILON));
            }
        });
        TempConv.main(args);
    }

    @Test
    public void test_TempConv_fahreinheitToCelsius()
    {
        //  set up args with Fahrenheit = 32
        final String[] args = {"-i", "F", "32", "-o", "C"};

        // set up the expected correct result, 0
        final double correctResult = 0;

        // expect the program to exit 0 (success)
        exit.expectSystemExitWithStatus(0);

        // check the result that is printed to standard output is the correct value
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String printedResult = stdout.getLog();
                double result = Double.parseDouble(printedResult);
                assertThat(result, closeTo(correctResult, EPSILON));
            }
        });
        TempConv.main(args);
    }

    @Test
    public void test_TempConv_fahreinheitToFahrenheit()
    {
        //  set up args with Fahrenheit = 32
        final String[] args = {"-i", "F", "32", "-o", "F"};

        // set up the expected correct result, 32
        final double correctResult = 32;

        // expect the program to exit 0 (success)
        exit.expectSystemExitWithStatus(0);

        // check the result that is printed to standard output is the correct value
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String printedResult = stdout.getLog();
                double result = Double.parseDouble(printedResult);
                assertThat(result, closeTo(correctResult, EPSILON));
            }
        });
        TempConv.main(args);
    }

    @Test
    public void test_TempConv_celsiusToFahrenheit()
    {
        //  set up args with Celsius = 0
        final String[] args = {"-i", "C", "0", "-o", "F"};

        // set up the expected correct result, 32
        final double correctResult = 32;

        // expect the program to exit 0 (success)
        exit.expectSystemExitWithStatus(0);

        // check the result that is printed to standard output is the correct value
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String printedResult = stdout.getLog();
                double result = Double.parseDouble(printedResult);
                assertThat(result, closeTo(correctResult, EPSILON));
            }
        });
        TempConv.main(args);
    }

    @Test
    public void test_TempConv_celsiusToCelsius()
    {
        //  set up args with Celsius = 32
        final String[] args = {"-i", "C", "32", "-o", "C"};

        // set up the expected correct result, 32
        final double correctResult = 32;

        // expect the program to exit 0 (success)
        exit.expectSystemExitWithStatus(0);

        // check the result that is printed to standard output is the correct value
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String printedResult = stdout.getLog();
                double result = Double.parseDouble(printedResult);
                assertThat(result, closeTo(correctResult, EPSILON));
            }
        });
        TempConv.main(args);
    }

    @Test
    public void test_TempConv_celsiusToKelvin()
    {
        //  set up args with Fahrenheit = 32
        final String[] args = {"-i", "C", "0", "-o", "K"};

        // set up the expected correct result, 273.15
        final double correctResult = 273.15;

        // expect the program to exit 0 (success)
        exit.expectSystemExitWithStatus(0);

        // check the result that is printed to standard output is the correct value
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String printedResult = stdout.getLog();
                double result = Double.parseDouble(printedResult);
                assertThat(result, closeTo(correctResult, EPSILON));
            }
        });
        TempConv.main(args);
    }

    @Test
    public void test_TempConv_kelvinToFahrenheit()
    {
        //  set up args with Kelvin = 32
        final String[] args = {"-i", "K", "32", "-o", "F"};

        // set up the expected correct result, -402.06998
        final double correctResult = -402.06998;

        // expect the program to exit 0 (success)
        exit.expectSystemExitWithStatus(0);

        // check the result that is printed to standard output is the correct value
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String printedResult = stdout.getLog();
                double result = Double.parseDouble(printedResult);
                assertThat(result, closeTo(correctResult, EPSILON));
            }
        });
        TempConv.main(args);
    }

    @Test
    public void test_TempConv_kelvinToCelsius()
    {
        //  set up args with Kelvin = 32
        final String[] args = {"-i", "K", "32", "-o", "C"};

        // set up the expected correct result, -241.15
        final double correctResult = -241.15;

        // expect the program to exit 0 (success)
        exit.expectSystemExitWithStatus(0);

        // check the result that is printed to standard output is the correct value
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String printedResult = stdout.getLog();
                double result = Double.parseDouble(printedResult);
                assertThat(result, closeTo(correctResult, EPSILON));
            }
        });
        TempConv.main(args);
    }

    @Test
    public void test_TempConv_kelvinToKelvin()
    {
        //  set up args with Kelvin = 32
        final String[] args = {"-i", "K", "32", "-o", "K"};

        // set up the expected correct result, 32
        final double correctResult = 32;

        // expect the program to exit 0 (success)
        exit.expectSystemExitWithStatus(0);

        // check the result that is printed to standard output is the correct value
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String printedResult = stdout.getLog();
                double result = Double.parseDouble(printedResult);
                assertThat(result, closeTo(correctResult, EPSILON));
            }
        });
        TempConv.main(args);
    }

    @Test
    public void test_TempConv_precisionTwo()
    {
        //  set up args with precision = 2
        final String[] args = {"-i", "K", "34", "-o", "F", "-p", "2"};

        // set up the expected correct result, -398.47
        final double correctResult = -398.47;

        // expect the program to exit 0 (success)
        exit.expectSystemExitWithStatus(0);

        // check the result that is printed to standard output is the correct value
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String printedResult = stdout.getLog();
                double result = Double.parseDouble(printedResult);
                assertThat(result, closeTo(correctResult, EPSILON));
            }
        });
        TempConv.main(args);
    }

    @Test
    public void test_TempConv_longPrecisionFlag()
    {
        //  set up args with long precision flag
        final String[] args = {"-i", "K", "34", "-o", "F", "--precision", "2"};

        // set up the expected correct result, -398.47
        final double correctResult = -398.47;

        // expect the program to exit 0 (success)
        exit.expectSystemExitWithStatus(0);

        // check the result that is printed to standard output is the correct value
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String printedResult = stdout.getLog();
                double result = Double.parseDouble(printedResult);
                assertThat(result, closeTo(correctResult, EPSILON));
            }
        });
        TempConv.main(args);
    }

    @Test
    public void test_TempConv_longInputFlag()
    {
        //  set up args with precision = 2
        final String[] args = {"--input", "F", "32", "-o", "C"};

        // set up the expected correct result, 0
        final double correctResult = 0;

        // expect the program to exit 0 (success)
        exit.expectSystemExitWithStatus(0);

        // check the result that is printed to standard output is the correct value
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String printedResult = stdout.getLog();
                double result = Double.parseDouble(printedResult);
                assertThat(result, closeTo(correctResult, EPSILON));
            }
        });
        TempConv.main(args);
    }

    @Test
    public void test_TempConv_longOutputFlag()
    {
        //  set up args with precision = 2
        final String[] args = {"-i", "F", "32", "--output", "C"};

        // set up the expected correct result, 0
        final double correctResult = 0;

        // expect the program to exit 0 (success)
        exit.expectSystemExitWithStatus(0);

        // check the result that is printed to standard output is the correct value
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String printedResult = stdout.getLog();
                double result = Double.parseDouble(printedResult);
                assertThat(result, closeTo(correctResult, EPSILON));
            }
        });
        TempConv.main(args);
    }

    @Test
    public void test_TempConv_mixedArgumentsWithPrecision()
    {
        //  set up mixed args, input F=43, output C, precision 2
        final String[] args = {"-o", "C", "-p", "2", "-i", "F", "43"};

        // set up the expected correct result, 6.11
        final double correctResult = 6.12;

        // expect the program to exit 0 (success)
        exit.expectSystemExitWithStatus(0);

        // check the result that is printed to standard output is the correct value
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String printedResult = stdout.getLog();
                double result = Double.parseDouble(printedResult);
                assertThat(result, closeTo(correctResult, EPSILON));
            }
        });
        TempConv.main(args);
    }

    @Test
    public void test_TempConv_mixedArguments_longFlags()
    {
        //  set up mixed args, input F=43, output C
        final String[] args = {"--output", "C", "--input", "F", "43"};

        // set up the expected correct result, 6.1111116
        final double correctResult = 6.1111116;

        // expect the program to exit 0 (success)
        exit.expectSystemExitWithStatus(0);

        // check the result that is printed to standard output is the correct value
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String printedResult = stdout.getLog();
                double result = Double.parseDouble(printedResult);
                assertThat(result, closeTo(correctResult, EPSILON));
            }
        });
        TempConv.main(args);
    }


    @Test
    public void test_TempConv_mixedArguments_precisionFlag_1()
    {
        //  set up mixed args, input F=43, output C
        final String[] args = {"--output", "C", "--input", "F", "43" , "-p", "2"};

        // set up the expected correct result, 6.11
        final double correctResult = 6.12;

        // expect the program to exit 0 (success)
        exit.expectSystemExitWithStatus(0);

        // check the result that is printed to standard output is the correct value
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                String printedResult = stdout.getLog();
                double result = Double.parseDouble(printedResult);
                assertThat(result, closeTo(correctResult, EPSILON));
            }
        });
        TempConv.main(args);
    }


    // TODO: Harry

    @Test
    public void test_TempConv_emptyArgumentStream()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {};

        // expect the program to exit 1, incomplete arguments
        exit.expectSystemExitWithStatus(1);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_emptyStrings()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"", "", "", ""};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_missingOutputArgument()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"-i", "F", "20", "-o"};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_missingFirstInputArgument()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"-i", "20", "-o", "F"};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_missingSecondInputArgument()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"-i", "F", "-o", "F"};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_missingBothInputArgument()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"-i", "-o", "F"};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_missingOutputFlagAndArgument()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"-i", "F"};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_missingInputFlagAndArgument()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"-o", "F"};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_missingArguments_ButWithFlags()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"-i", "-o"};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_only_arguments_no_flags()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"F", "20", "F"};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_only_flags_no_arguments()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"-i", "-o", "-p"};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_only_flags_no_arguments_with_spaces()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"-i", "-o", "-p", " "};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_only_flags_no_arguments_with_spaces_and_tabs()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"-i", "-o", "-p", " \t"};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_only_flags_no_arguments_with_spaces_and_tabs_and_newlines()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"-i", "-o", "-p", " \t\n"};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_longHand_nullArguments_withInputOuputFlags()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"--input", "F", null, "--ouput", "F", "-p", "2"};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(5);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_longHand_nullArguments_precisionValue()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"--input", "F", "20", "--ouput", "F", "-p", null};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_longHand_NonNumberArguments_inputValue()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"--input", "F", "null", "--ouput", "F", "-p", "2"};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_longHand_nullArguments_withInputOuputFlags_withSpaces()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {"--input", "F", "20", "--ouput", "F", "-p", null};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_onlyNull()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {null};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(5);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_onlyNull_withSpaces()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {" \t\n"};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }

    // A conversion that results in NaN
    @Test
    public void test_TempConv_args_onlyNull_withSpaces_and_tabs()
    {
        // set up args with the correct number of arguments:
        // empty string

        final String[] args = {" \t\n", "20", "F", "F", "-p", "2"};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);

    }


    @Test
    public void test_TempConv_args_onlyNull_withSpaces_and_tabs_and_newlines()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {" \t\n", "20", "F", "F", "-p", "2"};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(2);

        TempConv.main(args);
    }


    @Test
    public void test_TempConv_args_onlyNull_withSpaces_and_tabs_and_newlines_and_null()
    {
        // set up args with the correct number of arguments:
        // empty string
        final String[] args = {" \t\n", "20", null, "F", "-p", "2", null};

        // expect the program to exit 2, invalid i/o arguments
        exit.expectSystemExitWithStatus(5);

        TempConv.main(args);
    }
}