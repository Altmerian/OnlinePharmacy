package by.epam.pavelshakhlovich.onlinepharmacy.entity;

public class ShoppingCart {
    int drugId;
    int quantity;

    public int getName() {
        return drugId;
    }

    public void setName(int drug_id) {
        this.drugId = drug_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
