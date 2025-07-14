# Variables
# Modify CLASS, SRC_DIRC, DATA_DIR, and ARGS as needed
JAR = algs4.jar
SRC_DIR = Chapter_1-1
CLASS = P_1_1_39
DATA_DIR = algs4-data
DEFAULT_INPUT = tinyGex2.txt
ARGS ?= $(DEFAULT_INPUT) 2
JAVA_FILES = $(wildcard $(SRC_DIR)/*.java)
TARGET_FILE = $(SRC_DIR)/$(CLASS).java
TARGET_CLASS = $(SRC_DIR)/$(CLASS).class


all: compile run

compile: $(TARGET_FILE)
	javac -cp .:$(JAR) $(TARGET_FILE)

# Targets
# Modify run commoands to include ARGS or DATA_DIR

# run with default input:
# java -cp .:$(JAR):$(SRC_DIR) $(CLASS)

# run with arguments:
# java -cp .:$(JAR):$(SRC_DIR) $(CLASS) $(ARGS)

# run with data file and arguments:
# java -cp .:$(JAR):$(SRC_DIR) $(CLASS) $(DATA_DIR)/$(ARGS)
run: compile
	java -cp .:$(JAR):$(SRC_DIR) $(CLASS) $(ARGS)

clean:
	rm -f $(TARGET_CLASS)
