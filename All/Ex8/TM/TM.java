import java.util.*;

class ActorMap{
    public TreeMap<String, Integer> workingMap = new TreeMap<>();
}

public class TM{
    public static void main(String[] args){
        ActorMap AM = new ActorMap();
        AM.workingMap.put("HAHAHA", 6);
        System.out.println(AM.workingMap);
    }
}