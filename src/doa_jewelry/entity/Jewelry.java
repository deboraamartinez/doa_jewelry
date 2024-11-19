package doa_jewelry.entity;

public abstract class Jewelry implements Entity<Long> {
    private Long id;
    private String name;
    private MaterialType material;
    private double weight;
    private double price;
    private int stockQuantity;
    private JewelryCategory category;

    public Jewelry(String name, MaterialType material, double weight, double price, int stockQuantity,
            JewelryCategory category) {
        this.name = name;
        this.material = material;
        this.weight = weight;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MaterialType getMaterial() {
        return material;
    }

    public void setMaterial(MaterialType material) {
        this.material = material;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public JewelryCategory getCategory() {
        return category;
    }

    public void setCategory(JewelryCategory category) {
        this.category = category;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
