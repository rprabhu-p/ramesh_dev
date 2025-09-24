import java.util.*;

class CountingWords {
 
    public static void main(String args[]) {

        String str = "this is word sample sentence is of counting word";

        Map<String, Integer> wordCount = new HashMap<>();
        String[] words = str.split("\\s+");

        for(String word : words){
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        for(Map.Entry<String, Integer> mp : wordCount.entrySet()){
             System.out.println(mp.getKey() + " - " + mp.getValue());
        }
        System.out.println();
        
        Iterator <Map.Entry<String, Integer>> it = wordCount.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Integer> m = it.next();
            System.out.println(m.getKey() + " - " + m.getValue());
        }
    }
}