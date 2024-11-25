make:
	javac -cp "lib/*" Main.java
	java -cp "lib/*:." Main

compile:
	javac -cp "lib/*" Main.java

run:
	java -cp "lib/*:." Main

git:
	git add .
	git commit -m ~
	git push