all:
	javac -cp "lib/*" Main.java
	java -cp "lib/*:." Main

compile:
	javac -cp "lib/*" Main.java

run:
	java -cp "lib/*:." Main


# Ex9_6581167 Direcory:

# To compile: (At working directory)
# : javac -cp "lib/*" Main.java

# To run:
# : java -cp "lib/*:." Main