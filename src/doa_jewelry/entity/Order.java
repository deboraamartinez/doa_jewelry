package doa_jewelry.entity;

import java.time.LocalDate;
import java.util.List;

public class Order {

    private Long id;
    private Long customerId;
    private LocalDate date;
    private List<Item> items;
    private double totalAmount;
    private OrderStatus status;

    public Order(Long id, Long customerId, LocalDate date, List<Item> items, double totalAmount, OrderStatus status) {
        this.id = id;
        this.customerId = customerId;
        this.date = date;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", date=" + date +
                ", items=" + items +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                '}';
    }

    public static class Item {
        private Long jewelryId;
        private int quantity;

        public Item(Long jewelryId, int quantity) {
            this.jewelryId = jewelryId;
            this.quantity = quantity;
        }

        // Getters and Setters

        public Long getJewelryId() {
            return jewelryId;
        }
        public void setJewelryId(Long jewelryId) {
            this.jewelryId = jewelryId;
        }
        public int getQuantity() {
            return quantity;
        }
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        @Override
        public String toString() {
            return "Item{" +
                    "jewelryId=" + jewelryId +
                    ", quantity=" + quantity +
                    '}';
        }
    }
}
