@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT
if exist ACTUAL_BLANK.TXT del ACTUAL_BLANK.TXT
if exist ACTUAL_ERRONEOUS.TXT del ACTUAL_ERRONEOUS.TXT

REM compile the code into the bin folder
javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin Bhaymax < input.txt > ACTUAL.TXT
java -classpath ..\bin Bhaymax < input_blank.txt > ACTUAL_BLANK.TXT
java -classpath ..\bin Bhaymax < input_erroneous.txt > ACTUAL_ERRONEOUS.TXT

REM compare the output to the expected output
ECHO ========================================
ECHO             NORMAL TEST
ECHO ========================================
ECHO ========================================
ECHO             BLANK TEST
ECHO ========================================
ECHO ========================================
ECHO             ERRONEOUS TEST
ECHO ========================================
FC ACTUAL.TXT EXPECTED.TXT
FC ACTUAL_BLANK.TXT EXPECTED_BLANK.TXT
FC ACTUAL_ERRONEOUS.TXT EXPECTED_ERRONEOUS.TXT
