package doa_jewelry.entity;

public class Earring extends Jewelry {
    private String claspType;

    public Earring(String name, MaterialType material, double weight, double price, int stockQuantity, CategoryType category, String claspType) {
        super(name, material, weight, price, stockQuantity, category);
        this.claspType = claspType;
    }

    public String getClaspType() { return claspType; }
}