package doa_jewelry.entity;

public abstract class Jewelry implements Entity<Long> {
    private Long id;
    private String name;
    private MaterialType material;
    private double weight;
    private double price;
    private int stockQuantity;
    private CategoryType category;

    public enum MaterialType {
        GOLD, SILVER, PLATINUM, DIAMOND
    }

    public enum CategoryType {
        LUXURY, CASUAL
    }

    public Jewelry(String name, MaterialType material, double weight, double price, int stockQuantity,
            CategoryType category) {
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

    public MaterialType getMaterial() {
        return material;
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

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public CategoryType getCategory() {
        return category;
    }
}
