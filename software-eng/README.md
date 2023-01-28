PRE-ALPHA STAGE
____

## Project Overview

TempConv is short for temperature conversions where the user will convert the temperatures into other units of measure. In this program, this is a command-line interface where the user will supply the unit and value to be converted from and the unit to convert to. This program allows for precision formatting to be made optionally


## High-Level Requirements

- Fahrenheit, Celsius, and Kelvin should be supported for conversions
- There is a minimum temperature that's absolute 0 (e.g 0 Kelvin)
- We recognize the maximum temperature, but this program cannot support this.


## Usage Section

    Given user input of a temperature in fahrenheit, celsius, or Kelvin,
    the program converts the temperature into a new unit.

    Usage: TempConv

    -h,--help                  Prints the help message

    -i,--input DEGREE=VALUE    The specified degree and unit to convert from, where DEGREE is the unit
                               and VALUE is the temperature

    -o,--output DEGREE         The specified unit to convert the temperature to,
                               where DEGREE is either C, F, or K
                               
    -p,--precision             The decimal placement for resulting values

    TempConv --input UNIT VALUE --output UNIT (--precision VALUE)


## Locale Support

- English (United States)
- French (France)
- German (Germany)
- Spanish (México)
- Italian (Italy)

## Bonus Locale Support
- Mandarin (Mainland China)


# Team

- Nicolle
- Harry
- Camden
