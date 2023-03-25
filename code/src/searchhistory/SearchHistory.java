package searchhistory;


import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/*
 * Method to store entered input as history
 * @param maxSize limit to size of saved searches
 * @param freq keep track of frequency of search words
 */

/**
 * @author GAGAN SINGH
 */

//Function to save all inputs entered by user
public class SearchHistory {
    private Map<String, Integer> freqMap;
    private PriorityQueue<String> pq;
    private int maxSize;

//Create hashmap to store inputs
    public SearchHistory(int maxSize) {
        this.maxSize = maxSize;
        freqMap = new HashMap<>();
        //Priority queue to store the frequency of search queries
        pq = new PriorityQueue<>((a, b) -> freqMap.get(a) - freqMap.get(b));
    }

    //Add new search query
    public void addSearch(String search) {
        //Repeatition of query increments the frequency of searches
        int freq = freqMap.getOrDefault(search, 0) + 1;
        freqMap.put(search, freq);
        pq.remove(search);
        pq.add(search);
        if (pq.size() > maxSize) {
            freqMap.remove(pq.poll());
        }
    }

    //Printing the stored history
    public void printHistory() {
        PriorityQueue<String> tempPq = new PriorityQueue<>((a, b) -> freqMap.get(b) - freqMap.get(a));
        tempPq.addAll(pq);
        while (!tempPq.isEmpty()) {
            String search = tempPq.poll();
            System.out.println(search + " - " + freqMap.get(search));
        }
    }
    }