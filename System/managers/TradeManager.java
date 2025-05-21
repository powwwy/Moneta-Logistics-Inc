// System/managers/TradeManager.java
package System.managers;

import System.models.Trade;

import java.io.*;
import java.util.*;

public class TradeManager {
    private static final String FILE_PATH = "data/trades.txt";

    public static List<Trade> loadTrades() {
        List<Trade> trades = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 3) {
                    int id = Integer.parseInt(parts[0]);
                    int exporterId = Integer.parseInt(parts[1]);
                    int importerId = Integer.parseInt(parts[2]);

                    trades.add(new Trade(id, exporterId, importerId));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading trades: " + e.getMessage());
        }
        return trades;
    }
}
