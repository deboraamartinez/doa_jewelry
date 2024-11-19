package doa_jewelry.entity;

public class Necklace extends Jewelry {
    private double length;

    public Necklace(String name, MaterialType material, double weight, double price, int stockQuantity,
            CategoryType category, double length) {
        super(name, material, weight, price, stockQuantity, category);
        this.length = length;
    }

    public double getLength() {
        return length;
    }
}
