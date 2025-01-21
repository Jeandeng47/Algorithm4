# Variables
JAR = algs4.jar
SRC_DIR = Chapter_1-1
CLASS = P_1_1_10
DATA_DIR = algs4-data
INPUT_FILE = $(DATA_DIR)/$(DEFAULT_INPUT)
DEFAULT_INPUT = tinyG.txt

# Targets
all: compile run

compile:
	javac -cp .:$(JAR) $(SRC_DIR)/$(CLASS).java

run:
	@if [ -n "$(INPUT)" ]; then \
		java -cp .:$(JAR):$(SRC_DIR) $(CLASS) $(INPUT); \
	else \
		java -cp .:$(JAR):$(SRC_DIR) $(CLASS); \
	fi

clean:
	rm -f $(SRC_DIR)/*.class
