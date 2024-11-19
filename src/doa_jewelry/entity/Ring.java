package doa_jewelry.entity;

public class Ring extends Jewelry {
    private double size;

    public Ring(String name, MaterialType material, double weight, double price, int stockQuantity,
            CategoryType category, double size) {
        super(name, material, weight, price, stockQuantity, category);
        this.size = size;
    }

    public double getSize() {
        return size;
    }
}
