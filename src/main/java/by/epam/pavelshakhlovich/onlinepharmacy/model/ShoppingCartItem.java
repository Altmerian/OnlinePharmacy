package by.epam.pavelshakhlovich.onlinepharmacy.model;

import java.io.Serializable;

public class ShoppingCartItem implements Serializable {
    private static final long serialVersionUID = 6579431109393424445L;
    int drugId;
    int count;

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(int drugId, int count) {
        this.drugId = drugId;
        this.count = count;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drug_id) {
        this.drugId = drug_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ShoppingCartItem [drugId=" + drugId + ", count=" + count + "]";
    }
}
