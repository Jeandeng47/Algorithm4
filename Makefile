# Variables
# Modify CLASS, SRC_DIRC, DATA_DIR, and ARGS as needed
JAR = algs4.jar
SRC_DIR = Chapter_3-3
CLASS = P_3_3_100
DATA_DIR = algs4-data
DEFAULT_INPUT = tale.txt
ARGS ?= $(DEFAULT_INPUT) 6
JAVA_FILES = $(wildcard $(SRC_DIR)/*.java)
TARGET_FILE = $(SRC_DIR)/$(CLASS).java
TARGET_CLASS = $(SRC_DIR)/$(CLASS).class
EXTRA_CLASS = _ThreeSum
EXTRA_FILE = $(SRC_DIR)/$(EXTRA_CLASS).java
EXTRA_CLASS_FILE = $(SRC_DIR)/$(EXTRA_CLASS).class

all: compile run


# compile with extra class:
# compile: $(TARGET_FILE) $(EXTRA_FILE)
# 	javac -cp .:$(JAR) $(TARGET_FILE) $(EXTRA_FILE)

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
	java -cp .:$(JAR):$(SRC_DIR) $(CLASS) 
	

clean:
	rm -f $(TARGET_CLASS)
