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
 *
 */
public class SearchHistory {
    private Map<String, Integer> freqMap;
    private PriorityQueue<String> pq;
    private int maxSize;

    public SearchHistory(int maxSize) {
        this.maxSize = maxSize;
        freqMap = new HashMap<>();
        pq = new PriorityQueue<>((a, b) -> freqMap.get(a) - freqMap.get(b));
    }

    public void addSearch(String search) {
        int freq = freqMap.getOrDefault(search, 0) + 1;
        freqMap.put(search, freq);
        pq.remove(search);
        pq.add(search);
        if (pq.size() > maxSize) {
            freqMap.remove(pq.poll());
        }
    }

    public void printHistory() {
        PriorityQueue<String> tempPq = new PriorityQueue<>((a, b) -> freqMap.get(b) - freqMap.get(a));
        tempPq.addAll(pq);
        while (!tempPq.isEmpty()) {
            String search = tempPq.poll();
            System.out.println(search + " - " + freqMap.get(search));
        }
    }
    }