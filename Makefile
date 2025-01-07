# Variables
JAR = algs4.jar
SRC_DIR = Chapter_4-1
CLASS = _TestSearch
DATA_DIR = algs4-data
DEFAULT_INPUT = tinyG.txt

# Targets
all: compile run

compile:
	javac -cp .:$(JAR) $(SRC_DIR)/$(CLASS).java

run:
	java -cp .:$(JAR):$(SRC_DIR) $(CLASS) $(if $(INPUT),$(INPUT),$(DATA_DIR)/$(DEFAULT_INPUT)) 0

clean:
	rm -f $(SRC_DIR)/*.class
