# Variables
# Modify CLASS name to target class
JAR = algs4.jar
SRC_DIR = Chapter_1-1
CLASS = P_1_1_24
DATA_DIR = algs4-data
DEFAULT_INPUT = tinyG.txt
ARGS ?= $(DEFAULT_INPUT) 0
JAVA_FILES = $(wildcard $(SRC_DIR)/*.java)

# Targets
all: compile run

compile: $(JAVA_FILES)
	javac -cp .:$(JAR) $(JAVA_FILES)

run:
	java -cp .:$(JAR):$(SRC_DIR) $(CLASS) $(DATA_DIR)/$(ARGS)

clean:
	rm -f $(SRC_DIR)/*.class
