// package Ex8_6581167;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class ActorMap { 
    // Key = actor, Value = set of movies 
    // Java's String already has consistent equals & compareTo for equality check 
    private TreeMap<String, LinkedHashSet<String>> workingMap;  // Movie(value) , Actors(key)
    private LinkedHashSet<String> resultSet;
    private HashSet<String> totalActors;

    public ActorMap() {     // Constructor
        workingMap = new TreeMap<>();
        resultSet = new LinkedHashSet<>();
        totalActors = new HashSet<>();
    }

    public void addMovieActor(String movie, HashSet<String> actors) {
        for (String actor : actors) {
            if (!workingMap.containsKey(actor)) {
                workingMap.put(actor, new LinkedHashSet<>());
            }
            workingMap.get(actor).add(movie);
        }
    }

    public void checkActor(HashSet<String> chosenActor) {   // Find same name/surname with different surname/name
        totalActors.clear();
        resultSet.clear();

        for (String input : chosenActor) {      // Check actor name
            for (String actor : workingMap.keySet()) {
                if (actor.toLowerCase().contains(input.toLowerCase())) {
                    totalActors.add(actor);
                }
            }
        }
    }

    public void initialActors(HashSet<String> chosenActor) {
        checkActor(chosenActor);    // <--- this function already done it all

        HashSet<String> totalMovies = new HashSet<>();
        System.out.println("Valid input actors = " + totalActors + "\n");

        for (String actor : totalActors) { 
            LinkedHashSet<String> movies = workingMap.get(actor);
            totalMovies.addAll(movies);     // movies count

            System.out.printf("%20s", actor);   // print showcase
            for (String movie : movies) {
                System.out.printf("  >>  %-25s", movie);
            }
            System.out.println();
        }
        System.out.println("\nResult = " + totalActors);
        System.out.printf("\nTotal movies = %d\n", totalMovies.size());
    }

    public void containActors(HashSet<String> chosenActor) {
        checkActor(chosenActor);    // Function

        System.out.println("Valid input actors = " + totalActors + "\n");
    
        boolean isFirstActor = true;
        for (String actor : totalActors) {  // actor from result
            LinkedHashSet<String> movies = workingMap.get(actor);

            System.out.printf("%20s", actor);   // print showcase
            for (String movie : movies) {
                System.out.printf("  >>  %-25s", movie);
            }
            System.out.println();

            if (isFirstActor) {     // Assign resultSet a data of actor's movie itself on first time
                resultSet.addAll(movies);
                isFirstActor = false;
            } else resultSet.retainAll(movies);     // Keep all repeated movies
        }

        System.out.println("\nResult = " + resultSet);
        System.out.printf("\nTotal movies = %d\n", resultSet.size());
    }
     
    public void withoutActors(HashSet<String> chosenActor) {
        checkActor(chosenActor);    // Function

        System.out.println("Valid input actors = " + totalActors + "\n");
        for (LinkedHashSet<String> movies : workingMap.values()) resultSet.addAll(movies);  // Assign resultSet every existing movies

        for (String actor : totalActors) {  // actor from result
            LinkedHashSet<String> movies = workingMap.get(actor);

            System.out.printf("%20s", actor);      // Print showcase
            for (String movie : movies) {
                System.out.printf("  >>  %-25s", movie);
            }
            System.out.println();

            resultSet.removeAll(movies);    // remove movies with input actor's movie
        }

        System.out.println("\nResult = " + resultSet);      // print all movie result
        System.out.printf("\nTotal movies = %d\n", resultSet.size());   // print total movie result
    }

    public void printWorkingMap() {     // Initial map
        HashSet<String> totalMovies = new HashSet<>();  // Store movie excluding duplicate, purpose for printing movie count

        for (Map.Entry<String, LinkedHashSet<String>> entry : workingMap.entrySet()) {  // Check whole workingMap for actor and movies
            String actor = entry.getKey();
            LinkedHashSet<String> movies = entry.getValue();

            System.out.printf("\n%20s", actor);
            for (String movie : movies) {
                System.out.printf("  >>  %-25s", movie);
                totalMovies.add(movie);
            }
        }
        System.out.printf("\n\nTotal Movies = %d\nTotal Actors = %d\n", totalMovies.size(), workingMap.size());
    }
} 


public class Blue {
    public static void main(String args[]) {
        ActorMap actorMap = new ActorMap();

        try {
            Scanner fileScanner = new Scanner(new File("/workspaces/Test/movies.txt"));
            // Scanner fileScanner = new Scanner(new File("/src/main/java/Ex8_6581167/movies.txt"));
            while (fileScanner.hasNextLine()) {         // Check every existing line
                String line = fileScanner.nextLine();
                String[] data = line.split(";");
                
                String movie = data[0].trim();
                HashSet<String> actors = new HashSet<>();
                for (int i=1; i < data.length; i++) {   // Check every ';' of that line
                    actors.add(data[i].trim());
                }
                actorMap.addMovieActor(movie, actors);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) { e.printStackTrace(); }

        actorMap.printWorkingMap();     // Print initial map

        Scanner scanner = new Scanner(System.in);
        while (true) {      // Loop ask for input
            if (!askInput(actorMap, scanner)) {
                System.out.println("Exiting Program...");
                break;
            }
        }
        return;
    }

    public static boolean askInput(ActorMap actorMap, Scanner scanner) {
        int choice;
        System.out.println("=======================================================================");
        System.out.println("Find movies >> 0 = Set initial actors");
        System.out.println("               1 = Contain actors");
        System.out.println("               2 = Without actors");
        System.out.println("               Other = Quit");

        try {
            choice = Integer.parseInt(scanner.nextLine());  // Select choice
        } catch (NumberFormatException e) { choice = -1; }

        if (choice < 0 || choice > 2) { return false; } // Other: exit program

        HashSet<String> chosenActor = new HashSet<>();
        System.out.printf("Add names or surnames separated by comma ',' = ");   // Insert actor name > HashSet
        String[] actorInput = (scanner.nextLine()).split(",");
        for (int i=0; i < actorInput.length; i++) {
            chosenActor.add(actorInput[i].trim());
        }

        switch (choice) {
            case 0: actorMap.initialActors(chosenActor); break;
            case 1: actorMap.containActors(chosenActor); break;
            case 2: actorMap.withoutActors(chosenActor); break;
            default: break;
        }
        return true;    // Continue Loop back at main
    }
}