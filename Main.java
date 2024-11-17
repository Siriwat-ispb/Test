// p Ex8_6581098

//@Author Siriwat Ittisompiboon

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class ActorMap{
    // Key = actor, Value = set of movies
    // Java's String already has consistent equals & compareTo for equality check
    
    private TreeMap<String, LinkedHashSet<String>> workingMap;
    private LinkedHashSet<String> resultSet;

    public ActorMap() {     // Constructor
        workingMap = new TreeMap<>();
        resultSet = new LinkedHashSet<>();
    }

    public void addMovieActor(String movie, ArrayList<String> actors) {
        for (String actor : actors) {
            if (!workingMap.containsKey(actor)) {
                workingMap.put(actor, new LinkedHashSet<>());
            }
            workingMap.get(actor).add(movie);
        }
    }

    public void initialActors(){ 
        resultSet = new LinkedHashSet<>();

        for(LinkedHashSet<String> movies: workingMap.values()){
            resultSet.addAll(movies);
        }     
    }

    public void containActors(String... actors){ 
        for (String actor : actors) {
        // Check if the actor exists in the workingMap
        if (workingMap.containsKey(actor)) {
            // Intersect resultSet with the movies of the actor
            resultSet.retainAll(workingMap.get(actor));
        } else {
            // If actor not found, clear the resultSet as no match is possible
            resultSet.clear();
            break;
            }
        }
    }

    public void withoutActors(String... actors){ 
        for (String actor : actors) {
        // Check if the actor exists in the workingMap
        if (workingMap.containsKey(actor)) {
            // Remove the actor's movies from the resultSet
            resultSet.removeAll(workingMap.get(actor));
            }
        } 
    }

    public static int askInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===============================================================");
        System.out.println("   Find movies >> 0 = Set initial actors");
        System.out.println("                  1 = Contain actors");
        System.out.println("                  2 = Without actors");
        System.out.println("              Other = Quit");

        try {
            int input = Integer.parseInt(scanner.nextLine());
            return input;
        } catch (NumberFormatException e) {return -1;}
    }

    public void printworkingMap() {
        int TotalActor = workingMap.size();
        HashSet<String> TotalMovie = new HashSet<>();

        for (Map.Entry<String, LinkedHashSet<String>> entry : workingMap.entrySet()) {
            String actor = entry.getKey();
            LinkedHashSet<String> movies = entry.getValue();

            System.out.printf("\n%20s", actor);

            for (String movie : movies) {
                System.out.printf(" >> %-15s", movie);
                TotalMovie.add(movie);
            }
        }
        System.out.println("Total movies = " + TotalMovie);
    }
}


public class Main{
    public static void main(String[] args){
        ActorMap AM = new ActorMap();
        int actorCount, movieCount;
        try{
            File file = new File("/workspaces/Test/movies.txt");
            Scanner filescanner = new Scanner(file);
            while(filescanner.hasNextLine()){
                String line = filescanner.nextLine();
                String[] data = line.split(";");

                String movie = data[0].trim();
                ArrayList<String> actors = new ArrayList<>();
                for (int i=1; i < data.length; i++) {   // Check every ';' of that line
                    actors.add(data[i].trim());
                }
                AM.addMovieActor(movie, actors);
            }
            filescanner.close();
        }catch (FileNotFoundException e) {e.printStackTrace();}
        AM.printworkingMap();

         while (true) {
            int input = AM.askInput();
            
            if (input == 0) { AM.initialActors(); }
            else if (input == 1) { AM.containActors(); }
            else if (input == 2) { AM.withoutActors(); }
            else {
                System.out.println("Exiting the program...");
                break;
            }
        }
    }     
}
