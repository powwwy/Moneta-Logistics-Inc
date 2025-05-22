
//country manager
// system/models/Country.java
package System.models;

public class Country {
    private int id;
    private String name;
    private String contactNumber;
    private double accountBalance;
    private String managerName;
    private double importTariff;
    private double exportTariff;

    public Country(int id, String name, String contactNumber, double accountBalance, String managerName,
                   double importTariff, double exportTariff) {
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.accountBalance = accountBalance;
        this.managerName = managerName;
        this.importTariff = importTariff;
        this.exportTariff = exportTariff;
    }

    // Getters and setters (or use Lombok if allowed)
    public int getId() { return id; }
    public String getName() { return name; }
    public String getContactNumber() { return contactNumber; }
    public double getAccountBalance() { return accountBalance; }
    public String getManagerName() { return managerName; }
    public double getImportTariff() { return importTariff; }
    public double getExportTariff() { return exportTariff; }

    @Override
    public String toString() {
        return String.format("Country #%d: %s (Manager: %s)", id, name, managerName);
    }
}
