// System/models/Trade.java
package System.models;

public class Trade {
    private int id;
    private int exporterId;
    private int importerId;

    public Trade(int id, int exporterId, int importerId) {
        this.id = id;
        this.exporterId = exporterId;
        this.importerId = importerId;
    }

    public int getId() {
        return id;
    }

    public int getExporterId() {
        return exporterId;
    }

    public int getImporterId() {
        return importerId;
    }

    @Override
    public String toString() {
        return String.format("Trade #%d: Country %d → Country %d", id, exporterId, importerId);
    }
}
