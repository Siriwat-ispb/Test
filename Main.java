// p Ex9_6581098;

// Siriwat Ittisompiboon 6581098

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.alg.color.*;



public class Main {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        ActorGraph actorGraph = new ActorGraph();
        String filePath = "/workspaces/Test/movies.txt";
        // String filePath = "/src/main/java/Ex9_6581098/movies.txt";

        actorGraph.buildGraph(filePath);
        actorGraph.baconParties();

        while (true) {
            if (!askInput(actorGraph, scanner)) {
                System.out.println("Exiting Program...");
                break;
            }
        }
        scanner.close();
    }

    public static boolean askInput(ActorGraph actorGraph, Scanner scanner) {
        System.out.println("Enter name or surname, or 0 to quit");
        String userInput[] = scanner.nextLine().split(",");
          
        if (userInput[0].contains("0")) return false;

        HashSet<String> chosenActor = new HashSet<>();
        for (int i=0; i<userInput.length; i++) {
            chosenActor.add(userInput[i].trim());
        }

        actorGraph.findActor(chosenActor); 
        actorGraph.baconDegree();
        return true;
    }
}



class ActorGraph {
    private Graph<String, DefaultEdge> costarGraph;     
    private GreedyColoring<String, DefaultEdge> conflictGraph;

    private TreeMap<String, LinkedHashSet<String>> workingMap;
    private LinkedHashSet<String> resultSet;

    public static final String BACON = "Kevin Bacon";

    public ActorGraph() {
        this.costarGraph = new SimpleGraph<>(DefaultEdge.class);
        this.workingMap = new TreeMap<>();
        this.resultSet = new LinkedHashSet<>();
    }

    public void buildGraph(String fileName) {
        try {
            Scanner fileScanner = new Scanner(new File(fileName));

            while (fileScanner.hasNextLine()) {      
                String line = fileScanner.nextLine().trim();
                String[] data = line.split(";");    
                String movie = data[0];

                HashSet<String> actors = new HashSet<>();
                for (int i=1; i < data.length; i++) {
                    actors.add(data[i]);
                }
                addMovieActor(movie, actors);

                for (int i=1; i<data.length; i++) {  
                    costarGraph.addVertex(data[i]);
                    for (int j=1; j<i; j++) {
                        costarGraph.addEdge(data[i], data[j]);
                    }
                }
            }
            Graph<String, DefaultEdge> tempGraph = new SimpleGraph<>(DefaultEdge.class);
            for (String vertex : costarGraph.vertexSet()) { 
                tempGraph.addVertex(vertex);
            }
            for (DefaultEdge edge : costarGraph.edgeSet()) {  
                String source = costarGraph.getEdgeSource(edge);
                String target = costarGraph.getEdgeTarget(edge);
                tempGraph.addEdge(source, target);
            }
            tempGraph.removeVertex(BACON);

            conflictGraph = new GreedyColoring<>(tempGraph);
            fileScanner.close();
        }
        catch (FileNotFoundException e){
            System.out.printf("Error: File not found (%s)\n", fileName);
            e.printStackTrace();
        }
    }

    public void addMovieActor(String movie, HashSet<String> actors) {
        for (String actor : actors) {
            if (!workingMap.containsKey(actor)) {
                workingMap.put(actor, new LinkedHashSet<>());
            }
            workingMap.get(actor).add(movie);
        }
    }

    public void findActor(HashSet<String> chosenActor) {
        resultSet.clear();
        for (String input : chosenActor) { 
            for (String actor : workingMap.keySet()) {
                if (actor.toLowerCase().contains(input.toLowerCase())) {
                    resultSet.add(actor);
                }
            }
        }
    }

    public void baconDegree() { 
        System.out.println("============================== Bacon degrees ==============================");
        System.out.println("Valid actors = " + resultSet);
        System.out.println();
        for (String actor : resultSet) {
            String start = actor;
            String target = "Kevin Bacon";

            LinkedList<String> queue = new LinkedList<>();
            queue.add(start);

            Set<String> visited = new HashSet<>();
            visited.add(start);

            Map<String, String> parentMap = new HashMap<>();
            boolean found = false;

            while (!queue.isEmpty() && !found) {
                String current = queue.poll();

                for (DefaultEdge edge : costarGraph.edgesOf(current)) {
                    String neighbor = costarGraph.getEdgeSource(edge).equals(current)
                        ? costarGraph.getEdgeTarget(edge)
                        : costarGraph.getEdgeSource(edge);
                    
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        parentMap.put(neighbor, current);
                        queue.add(neighbor);

                        if (neighbor.equals(target)) {
                            found = true;
                            break;
                        }
                    }
                }
            }

            List<String> path = new LinkedList<>();
            if (found) {
                String current = target;
                while (current != null) {
                    path.add(0, current);
                    current = parentMap.get(current);
                }
                    // PRINT
                System.out.printf("%s  >>  Bacon degree = %d\n\n", start, path.size()-1);
                for (int i=0; i<path.size()-1; i++) {
                    String actor1 = path.get(i);
                    String actor2 = path.get(i+1);

                    Set<String> movieActor1 = workingMap.get(actor1);
                    Set<String> movieActor2 = workingMap.get(actor2);

                    Set<String> sharedMovie = new HashSet<>(movieActor1);
                    sharedMovie.retainAll(movieActor2);

                    System.out.printf("%20s - %-20s %s\n", actor1, actor2, sharedMovie);
                }
                System.out.println();
            } else {
                System.out.println("Connection not found");
            }
        }
        System.out.println("============================== Bacon degrees ==============================");
    }

    public void baconParties() {
        List<Set<String>> colorList = conflictGraph.getColoring().getColorClasses();

        System.out.println("\n\n============================== Bacon parties ==============================");
        System.out.println("By GreedyColoring  >>  total parties = " + colorList.size());
        System.out.println();
        for (int i=0; i<colorList.size();i++) {
            System.out.printf("Parties %d >> guests = %d", i+1, colorList.get(i).size());
            Set<String> colorClass = colorList.get(i);
            int count = 0;
            for (String actor : colorClass) {
                if (count % 6 == 0) System.out.println();  
                System.out.printf("%-20s", actor);
                count++;
            }
            System.out.printf("\n\n");
        }
        System.out.println("============================== Bacon parties ==============================");
    }
}