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

    private int TotalActor = 0;
    private int TotalMovie = 0;

    

    public ActorMap() {     // Constructor
        workingMap = new TreeMap<>();
        resultSet = new LinkedHashSet<>();
    }

    public int getTotalActor(){
        return TotalActor;
    }

    public int getTotalMovie(){
        return TotalMovie;
    }

    public void addMovieActor(String movie, ArrayList<String> actors) {
        for (String actor : actors) {
            if (!workingMap.containsKey(actor)) {
                workingMap.put(actor, new LinkedHashSet<>());
                TotalActor++;
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

    public void printworkingMap() {
        for (Map.Entry<String, LinkedHashSet<String>> entry : workingMap.entrySet()) {
            String actor = entry.getKey();
            LinkedHashSet<String> movies = entry.getValue();

            System.out.printf("\n%20s", actor);

            for (String movie : movies) {
                System.out.printf(" >> %-15s", movie);
            }
        }
    }

    public void printTotalCount(){
        // System.out.println("Total movies = " + TotalMovie); dk where to put ts
        System.out.println("Total actors = " + TotalActor); // correct
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
        AM.printTotalCount();
        AM.printworkingMap();
    }
}
