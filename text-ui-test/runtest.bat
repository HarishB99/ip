@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT
if exist ACTUAL_BLANK.TXT del ACTUAL_BLANK.TXT
if exist ACTUAL_ERRONEOUS.TXT del ACTUAL_ERRONEOUS.TXT
if exist data rmdir /s /q data

REM compile the code into the bin folder
javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\bhaymax\Bhaymax.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin bhaymax.Bhaymax < input.txt > ACTUAL.TXT
rmdir /s /q data
java -classpath ..\bin bhaymax.Bhaymax < input_blank.txt > ACTUAL_BLANK.TXT
rmdir /s /q data
java -classpath ..\bin bhaymax.Bhaymax < input_erroneous.txt > ACTUAL_ERRONEOUS.TXT

REM compare the output to the expected output
ECHO ========================================
ECHO             NORMAL TEST
ECHO ========================================
FC ACTUAL.TXT EXPECTED.TXT
ECHO ========================================
ECHO             BLANK TEST
ECHO ========================================
FC ACTUAL_BLANK.TXT EXPECTED_BLANK.TXT
ECHO ========================================
ECHO             ERRONEOUS TEST
ECHO ========================================
FC ACTUAL_ERRONEOUS.TXT EXPECTED_ERRONEOUS.TXT
