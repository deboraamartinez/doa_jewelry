package doa_jewelry.entity;

public class Ring extends Jewelry {
    private double size;

    public Ring(String name, MaterialType material, double weight, double price, int stockQuantity,
            JewelryCategory category, double size) {
        super(name, material, weight, price, stockQuantity, category);
        this.size = size;
    }

    public Ring(Long id, String name, MaterialType material, double weight, double price, int stockQuantity,
            JewelryCategory category, double size) {
        super(name, material, weight, price, stockQuantity, category);
        this.setId(id);
        this.size = size;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String toString() {
        return "Ring{" +
                "ID=" + getId() +
                ", Name='" + getName() + '\'' +
                ", Material='" + getMaterial() + '\'' +
                ", Weight=" + getWeight() +
                ", Price=" + getPrice() +
                ", Stock=" + getStockQuantity() +
                ", Category=" + getCategory() +
                ", Size=" + getSize() +
                '}';
    }
}
