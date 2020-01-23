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
    private long userId;
    private Timestamp date;
    private BigDecimal amount;
    private String status;
    private Map<Item, Integer> items = new HashMap<>();

    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

        if (userId != order.userId) {
            return false;
        }
        if (!date.equals(order.date)) {
            return false;
        }
        if (!amount.equals(order.amount)) {
            return false;
        }
        if (!status.equals(order.status)) {
            return false;
        }
        return items.equals(order.items);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + Long.hashCode(userId);
        result = 31 * result + date.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + items.hashCode();
        return result;
    }
}
