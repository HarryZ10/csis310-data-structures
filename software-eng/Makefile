# Implementation Notes:
# Decided to get a little fancy with the Makefile to display the
# commands being run more clearly and aesthetically.
#
# Author: hzhu20@georgefox.edu
# Date:   2021-11-28
# Since:  GNU Make 4.3 using homebrew on MacOS


JFLAGS = -g -Xlint:deprecation
COMMONS_JAR = lib/commons-cli-1.5.0.jar
JUNIT_JAR = lib/junit-4.13.2.jar
HAMCREST_JAR = lib/hamcrest-2.2.jar
SYSTEM_RULES_JAR = lib/system-rules-1.19.0.jar

CP = -cp
BIN = ./bin
BUILD = ./build
JARS_FLAGS = $(CP) $(JUNIT_JAR):$(HAMCREST_JAR):$(SYSTEM_RULES_JAR):$(COMMONS_JAR):.
JC = javac
JVM= java
ARGS=

# LOCALE

LOCALE_EN = -Duser.language=en -Duser.country=US
LOCALE_ES = -Duser.language=es -Duser.country=MX
LOCALE_DE = -Duser.language=de -Duser.country=DE
LOCALE_FR = -Duser.language=fr -Duser.country=FR
LOCALE_IT = -Duser.language=it -Duser.country=IT
LOCALE_JA = -Duser.language=ja -Duser.country=JP
LOCALE_ZH = -Duser.language=zh -Duser.country=CH


.SILENT:


.SUFFIXES: .java .class


.java.class:
	$(JC) $(JARS_FLAGS) $(JFLAGS) $*.java


CLASSES = \
	TempConv.java

MAIN = TempConv
TESTER = TempConvTest


all: run


help:
	@echo "===========================[ HELP ]=============================="
	@echo "USAGE: Please use \`make <target>' where <target> is one of"
	@printf "\n"
	@echo "  all               to compile and run the main Java *.java files"
	@echo "  clean             to remove all Java *.class files"
	@echo "  compile           to compile all Java *.java files"
	@echo "  run ARGS=\"\"       to run Java program with specified arguments"
	@echo "  compile-test      to compile all the tester *.java files"
	@echo "  run-test          to run all compiled tests"
	@echo "  help              to display this help message"
	@printf "\n"
	@echo "An example of ARGS is: -i F <VALUE> -o C -p 2"
	@printf "\n"
	@echo "================================================================="


classes:
	@echo "================================================================="
	$(CLASSES:.java=.class) \
	@echo "\nSuccessfully compiled Java *.java files.\n"

compile: clean \
	$(MAIN).class
	@echo "===========================[ COMPILING ]========================="
	@echo "\nSuccessfully compiled Java *.java files.\n"

run: compile
	@echo "===========================[ RUNNING ]==========================="
	@printf "\nArguments: "
	@echo "$(ARGS)" || sed 's/^/- /'
	@printf "\nOutput:\n\n"
	$(JVM) $(JARS_FLAGS) $(MAIN) $(ARGS)
	@echo "\n==============================================================="
	@echo "Successfully ran Java program."


compile-test: clean \
	$(TESTER).class
	@echo "===========================[ COMPILING ]========================="
	@echo "\nSuccessfully compiled tester *.java files.\n"


run-test: compile-test
	@echo "===========================[ RUNNING ]==========================="
	@printf "\n"
	$(JVM) $(JARS_FLAGS) org.junit.runner.JUnitCore $(TESTER)
	@echo "================================================================="
	@echo "\nSuccessfully ran all compiled tests.\n"


clean:
	@echo "===========================[ CLEANING ]=========================="
	@echo "\nCleaning up Java *.class files..."
	$(RM) ./bin/test/*.*
	$(RM) *.class
	$(RM) .makefile.swp
	$(RM) .Makefile.swp
	@echo "Successfully cleaned up.\n"


run-en_US: compile
	@echo "=====================[ RUNNING EN_US ]==========================="
	@printf "\nArguments: "
	@echo "$(ARGS)" || sed 's/^/- /'
	@printf "\nOutput:\n\n"
	$(JVM) $(LOCALE_EN) $(JARS_FLAGS) $(MAIN) $(ARGS)
	@echo "\n==============================================================="
	@echo "Successfully ran Java program."


run-es_MX: compile
	@echo "=====================[ RUNNING ES_MX ]==========================="
	@printf "\nArguments: "
	@echo "$(ARGS)" || sed 's/^/- /'
	@printf "\nOutput:\n\n"
	$(JVM) $(LOCALE_ES) $(JARS_FLAGS) $(MAIN) $(ARGS)
	@echo "\n==============================================================="
	@echo "Successfully ran Java program."


run-de_DE: compile
	@echo "=====================[ RUNNING de_DE ]==========================="
	@printf "\nArguments: "
	@echo "$(ARGS)" || sed 's/^/- /'
	@printf "\nOutput:\n\n"
	$(JVM) $(LOCALE_DE) $(JARS_FLAGS) $(MAIN) $(ARGS)
	@echo "\n==============================================================="
	@echo "Successfully ran Java program."


run-it_IT: compile
	@echo "=====================[ RUNNING it_IT ]==========================="
	@printf "\nArguments: "
	@echo "$(ARGS)" || sed 's/^/- /'
	@printf "\nOutput:\n\n"
	$(JVM) $(LOCALE_IT) $(JARS_FLAGS) $(MAIN) $(ARGS)
	@echo "\n==============================================================="
	@echo "Successfully ran Java program."


run-fr_FR: compile
	@echo "=====================[ RUNNING fr_FR ]==========================="
	@printf "\nArguments: "
	@echo "$(ARGS)" || sed 's/^/- /'
	@printf "\nOutput:\n\n"
	$(JVM) $(LOCALE_FR) $(JARS_FLAGS) $(MAIN) $(ARGS)
	@echo "\n==============================================================="
	@echo "Successfully ran Java program."

run-zh_CH: compile
	@echo "=====================[ RUNNING zh_CH ]==========================="
	@printf "\nArguments: "
	@echo "$(ARGS)" || sed 's/^/- /'
	@printf "\nOutput:\n\n"
	$(JVM) $(LOCALE_ZH) $(JARS_FLAGS) $(MAIN) $(ARGS)
	@echo "\n==============================================================="
	@echo "Successfully ran Java program."
