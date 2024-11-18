package Ex8_6581098;
//Siriwat Ittisompiboon 6581098

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class ActorMap { 
    // Key = actor, Value = set of movies 
    // Java's String already has consistent equals & compareTo for equality check 
    private TreeMap<String, LinkedHashSet<String>> workingMap; 
    private LinkedHashSet<String> resultSet;

    public ActorMap() {   
        workingMap = new TreeMap<>();
        resultSet = new LinkedHashSet<>();
    }

    public void addMovieActor(String movie, HashSet<String> actors) {
        for (String actor : actors) {
            if (!workingMap.containsKey(actor)) {
                workingMap.put(actor, new LinkedHashSet<>());
            }
            workingMap.get(actor).add(movie);
        }
    }

    public void initialActors(HashSet<String> chosenActor) {
        resultSet.clear();     
        for (String input : chosenActor) {   
            for (String actor : workingMap.keySet()) {
                if (actor.toLowerCase().contains(input.toLowerCase())) {
                    resultSet.add(actor);
                }
            }
        }

        HashSet<String> totalMovies = new HashSet<>();
        System.out.println("Valid input actors = " + resultSet + "\n");

        for (String actor : resultSet) { 
            LinkedHashSet<String> movies = workingMap.get(actor);
            totalMovies.addAll(movies); 

            System.out.printf("%20s", actor);
            for (String movie : movies) {
                System.out.printf("  >>  %-25s", movie);
            }
            System.out.println();
        }
        System.out.println("\nResult = " + totalMovies);
        System.out.printf("\nTotal movies = %d\n", totalMovies.size());
    }

    public void containActors(HashSet<String> chosenActor) {

        HashSet<String> TActors = new HashSet<>();
        for (String input : chosenActor) {
            for (String actor : resultSet) {
                if (actor.toLowerCase().contains(input.toLowerCase())) {
                TActors.add(actor);
                }
            }
        }

        boolean isFirstActor = true;
        HashSet<String> TMovies = new HashSet<>();
        System.out.println("Valid input actors = " + TActors + "\n");
        for (String actor : TActors) { 
            LinkedHashSet<String> movies = workingMap.get(actor);

            System.out.printf("%20s", actor); 
            for (String movie : movies) {
                System.out.printf("  >>  %-25s", movie);
            }
            System.out.println();

            if (isFirstActor) {   
                TMovies.addAll(movies);
                isFirstActor = false;
            } else TMovies.retainAll(movies);
        }

        System.out.println("\nResult = " + TMovies);
        System.out.printf("\nTotal movies = %d\n", TMovies.size());
    }
     
    public void withoutActors(HashSet<String> chosenActor) {

        HashSet<String> TActors = new HashSet<>();
        HashSet<String> TMovies = new HashSet<>();
        for (String input : chosenActor) {
            for (String actor : resultSet) {
                TMovies.addAll(workingMap.get(actor));
                if (actor.toLowerCase().contains(input.toLowerCase())) {
                TActors.add(actor);
                }
            }
        }
        System.out.println("Valid input actors = " + TActors + "\n");

        for (String actor : TActors) { 
            LinkedHashSet<String> movies = workingMap.get(actor);

            System.out.printf("%20s", actor);    
            for (String movie : movies) {
                System.out.printf("  >>  %-25s", movie);
            }
            System.out.println();

            TMovies.removeAll(movies);  
        }

        System.out.println("\nResult = " + TMovies);   
        System.out.printf("\nTotal movies = %d\n", TMovies.size());   
    }

    public void printWorkingMap() {     
        HashSet<String> totalMovies = new HashSet<>();

        for (Map.Entry<String, LinkedHashSet<String>> entry : workingMap.entrySet()) { 
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

public class Main {
    public static void main(String args[]) {
        ActorMap actorMap = new ActorMap();

        try {
            Scanner fileScanner = new Scanner(new File("/src/main/java/Ex8_6581098/movies.txt"));
            while (fileScanner.hasNextLine()) {         
                String line = fileScanner.nextLine();
                String[] data = line.split(";");
                
                String movie = data[0].trim();
                HashSet<String> actors = new HashSet<>();
                for (int i=1; i < data.length; i++) {   
                    actors.add(data[i].trim());
                }
                actorMap.addMovieActor(movie, actors);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) { e.printStackTrace(); }

        actorMap.printWorkingMap();    

        Scanner scanner = new Scanner(System.in);
        while (true) {     
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
            choice = Integer.parseInt(scanner.nextLine()); 
        } catch (NumberFormatException e) { choice = -1; }

        if (choice < 0 || choice > 2) { return false; } 

        HashSet<String> chosenActor = new HashSet<>();
        System.out.printf("Add names or surnames separated by comma ',' = ");  
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
        return true;  
    }
}