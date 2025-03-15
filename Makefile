# Variables
# Modify CLASS name to target class
JAR = algs4.jar
SRC_DIR = Chapter_1-1
CLASS = P_1_1_34
DATA_DIR = algs4-data
DEFAULT_INPUT = tinyGex2.txt
ARGS ?= $(DEFAULT_INPUT) 2
JAVA_FILES = $(wildcard $(SRC_DIR)/*.java)
TARGET_FILE = $(SRC_DIR)/$(CLASS).java
TARGET_CLASS = $(SRC_DIR)/$(CLASS).class

# Targets
# Modify run commoands to include ARGS or DATA_DIR

# run with default input:
# java -cp .:$(JAR):$(SRC_DIR) $(CLASS)

# run with arguments:
# java -cp .:$(JAR):$(SRC_DIR) $(CLASS) $(DATA_DIR)/$(ARGS)

# run with data file and arguments:
# java -cp .:$(JAR):$(SRC_DIR) $(CLASS) $(DATA_DIR)/$(ARGS)

all: compile run

compile: $(TARGET_FILE)
	javac -cp .:$(JAR) $(TARGET_FILE)

run: compile
	java -cp .:$(JAR):$(SRC_DIR) $(CLASS)

clean:
	rm -f $(TARGET_CLASS)
