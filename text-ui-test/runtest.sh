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
if [ -e "./ACTUAL_ERRONEOUS.TXT" ]
then
    rm ACTUAL_ERRONEOUS.TXT
fi
if [ -d "./data" ]
then
    rm -rf data
fi

# compile the code into the bin folder, terminates if error occurred
if ! javac -cp ../src/main/java -Xlint:none -d ../bin ../src/main/java/bhaymax/main/Bhaymax.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ../bin bhaymax.main.Bhaymax < input.txt > ACTUAL.TXT
rm -rf data
java -classpath ../bin bhaymax.main.Bhaymax < input_blank.txt > ACTUAL_BLANK.TXT
rm -rf data
java -classpath ../bin bhaymax.main.Bhaymax < input_erroneous.txt > ACTUAL_ERRONEOUS.TXT

# convert to UNIX format
cp EXPECTED.TXT EXPECTED-UNIX.TXT
cp EXPECTED_BLANK.TXT EXPECTED_BLANK-UNIX.TXT
cp EXPECTED_ERRONEOUS.TXT EXPECTED_ERRONEOUS-UNIX.TXT
dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT
dos2unix ACTUAL_BLANK.TXT EXPECTED_BLANK-UNIX.TXT
dos2unix ACTUAL_ERRONEOUS.TXT EXPECTED_ERRONEOUS-UNIX.TXT

# compare the output to the expected output
diff ACTUAL.TXT EXPECTED-UNIX.TXT
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
else
    echo "Test result: FAILED"
    exit 1
fi
diff ACTUAL_BLANK.TXT EXPECTED_BLANK-UNIX.TXT
if [ $? -eq 0 ]
then
    echo "Test result (blank input): PASSED"
else
    echo "Test result (blank input): FAILED"
    exit 1
fi
diff ACTUAL_ERRONEOUS.TXT EXPECTED_ERRONEOUS-UNIX.TXT
if [ $? -eq 0 ]
then
    echo "Test result (erroneous input): PASSED"
    exit 0
else
    echo "Test result (erroneous input): FAILED"
    exit 1
fi
