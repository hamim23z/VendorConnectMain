package model;

public class Vendor {
    private int id;
    private String name;
    private String address;
    private String description;
    private String phone;

    // Constructor
    public Vendor(int id, String name, String address, String description, String phone) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone = phone;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    
    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}