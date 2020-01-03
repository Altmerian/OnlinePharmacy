package by.epam.pavelshakhlovich.onlinepharmacy.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Class {@code Order} represents a user's order for a list of items. Contains creation date, in case it was already
 * submitted. Order's owner is also specified.
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 4320267879429888765L;
    private long id;
    private User owner;
    private Timestamp timestamp;
    private BigDecimal amount;
    private OrderStatus status;
    private Map<Item, Integer> items = new HashMap<>();

    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }

        Order order = (Order) o;

        if (id != order.id) {
            return false;
        }

        if (!owner.equals(order.owner)) {
            return false;
        }
        if (!timestamp.equals(order.timestamp)) {
            return false;
        }
        if (!amount.equals(order.amount)) {
            return false;
        }
        if (status != order.status) {
            return false;
        }
        return items.equals(order.items);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + owner.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + items.hashCode();
        return result;
    }
}
