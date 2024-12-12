package model;

public class Vendor {
    private String name;
    private String address;
    private String description;
    private String phone;

    // Constructor
    public Vendor(String name, String address, String description, String phone) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.phone = phone;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getPhone() {
        return phone;
    }
}