package cphbusiness.ufo.letterfrequencies;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.stream.Collectors.toMap;

/**
 * Frequency analysis Inspired by
 * https://en.wikipedia.org/wiki/Frequency_analysis
 *
 * @author kasper
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
       // String fileName = "/Users/kasper/GITHUB/ufo/letterfrequencies/src/main/resources/FoundationSeries.txt";
        String fileName = "C:\\Users\\sarke\\OneDrive\\Desktop\\letterfrequencies\\src\\main\\resources\\FoundationSeries.txt";
        
        int runTime = 100;
        int[] milisecond = new int[runTime];
        
        for (int i = 0; i < runTime; i++) {
            
            long startTime = System.nanoTime();
             Reader reader = new FileReader(fileName);
             Map<Integer, Long> freq = new HashMap<>();
             tallyChars(reader, freq);
             long duration   = System.nanoTime()- startTime;
             milisecond[i] = (int) duration /1_000_000; 
           
            
        }
          print(milisecond);
        
       
    }

    private static void tallyChars(Reader reader, Map<Integer, Long> freq) throws IOException {
        int b;
        while ((b = reader.read()) != -1) {
            try {
                freq.put(b, freq.get(b) + 1);
            } catch (NullPointerException np) {
                freq.put(b, 1L);
            };
        }
    }

    private static void print(int[] arr){
    String s = "";
    int sum = 0;
    
        for (int i:arr) {
            s  += i+ ",";
            sum +=i;
            
        }
        System.out.println(s.substring(0, s.length() -1));
        System.out.println("Mean: " + (sum/arr.length));
        
    }
    
    
    
    private static void print_tally(Map<Integer, Long> freq) {
        int dist = 'a' - 'A';
        Map<Character, Long> upperAndlower = new LinkedHashMap();
        for (Character c = 'A'; c <= 'Z'; c++) {
            upperAndlower.put(c, freq.getOrDefault(c, 0L) + freq.getOrDefault(c + dist, 0L));
        }
        Map<Character, Long> sorted = upperAndlower
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        for (Character c : sorted.keySet()) {
            System.out.println("" + c + ": " + sorted.get(c));;
        }
    }
}
