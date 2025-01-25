#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]
then
    mkdir ../bin
fi

# delete output from previous run
if [ -e "./ACTUAL.TXT" ]
then
    rm ACTUAL.TXT
fi
if [ -e "./ACTUAL_BLANK.TXT" ]
then
    rm ACTUAL_BLANK.TXT
fi
if [ -e "./ACTUAL_ERRANEOUS.TXT" ]
then
    rm ACTUAL_ERRANEOUS.TXT
fi

# compile the code into the bin folder, terminates if error occurred
if ! javac -cp ../src/main/java -Xlint:none -d ../bin ../src/main/java/*.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ../bin Bhaymax < input.txt > ACTUAL.TXT
java -classpath ../bin Bhaymax < input_blank.txt > ACTUAL_BLANK.TXT
java -classpath ../bin Bhaymax < input_erraneous.txt > ACTUAL_ERRANEOUS.TXT

# convert to UNIX format
cp EXPECTED.TXT EXPECTED-UNIX.TXT
cp EXPECTED_BLANK.TXT EXPECTED_BLANK-UNIX.TXT
cp EXPECTED_ERRANEOUS.TXT EXPECTED_ERRANEOUS-UNIX.TXT
dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT
dos2unix ACTUAL_BLANK.TXT EXPECTED_BLANK-UNIX.TXT
dos2unix ACTUAL_ERRANEOUS.TXT EXPECTED_ERRANEOUS-UNIX.TXT

# compare the output to the expected output
diff ACTUAL.TXT EXPECTED-UNIX.TXT
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi
diff ACTUAL_BLANK.TXT EXPECTED_BLANK-UNIX.TXT
if [ $? -eq 0 ]
then
    echo "Test result (blank input): PASSED"
    exit 0
else
    echo "Test result (blank input): FAILED"
    exit 1
fi
diff ACTUAL_ERRANEOUS.TXT EXPECTED_ERRANEOUS-UNIX.TXT
if [ $? -eq 0 ]
then
    echo "Test result (erraneous input): PASSED"
    exit 0
else
    echo "Test result (erraneous input): FAILED"
    exit 1
fi
