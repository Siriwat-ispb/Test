import java.util.*;

class ActorMap{
    private LinkedHashSet<String> resultSet = new LinkedHashSet<>();

    public void addition(){
        resultSet.add("Hello");
        resultSet.add("jjjjj");
        resultSet.add("jjjjj"); // Not print due to duplicate
    }

    public void display(){
        for(String item : resultSet){
            System.out.println(item);
        }
    }
}

public class LHS{
    public static void main(String[] args){
        ActorMap AM = new ActorMap();
        AM.addition();
        AM.display();
    }
}