package doa_jewelry.entity;

public class Necklace extends Jewelry {
    private double length;

    public Necklace(String name, MaterialType material, double weight, double price, int stockQuantity,
            JewelryCategory category, double length) {
        super(name, material, weight, price, stockQuantity, category);
        this.length = length;
    }

    public Necklace(Long id, String name, MaterialType material, double weight, double price, int stockQuantity,
            JewelryCategory category, double length) {
        super(name, material, weight, price, stockQuantity, category);
        this.setId(id);
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String toString() {
        return "Necklace{" +
                "ID=" + getId() +
                ", Name='" + getName() + '\'' +
                ", Material='" + getMaterial() + '\'' +
                ", Weight=" + getWeight() +
                ", Price=" + getPrice() +
                ", Stock=" + getStockQuantity() +
                ", Category=" + getCategory() +
                ", Length=" + getLength() +
                '}';
    }
}
