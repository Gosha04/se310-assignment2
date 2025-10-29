package com.se310.store.observer;

import java.util.HashMap;
import java.util.Map;


/**
 * Concrete Observer that tracks statistics about device events
 *
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */
public class DeviceStatistics implements Observer {
    private int totalEvents = 0;

    private final Map<String, Integer> commandCount = new HashMap<>();

    @Override
    public void onStoreUpdate(String event) {
        totalEvents++;

        commandCount.merge(event, 1, Integer::sum);

        System.out.printf("[%s] %s -> count=%d, total=%d, commands=%d%n",
                event, commandCount.get(event), totalEvents);
    }

    public int getTotalEvents() {
        return totalEvents;
    }

    public int getCountFor(String event) {
        return commandCount.getOrDefault(event, 0);
    }

    public Map<String, Integer> getCounts() {
        return new HashMap<>(commandCount);
    }

    public void reset() {
        totalEvents = 0;
        commandCount.clear();
    }

    public void printSummary() {
        System.out.printf("===  Stats ===%n");
        System.out.printf("Total events  : %d%n", totalEvents);
        System.out.println("By type:");
        commandCount.forEach((t, c) -> System.out.printf("  %s : %d%n", t, c));
    }
}