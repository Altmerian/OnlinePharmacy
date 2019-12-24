package by.epam.pavelshakhlovich.onlinepharmacy.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

/**
 * Class {@code Prescription} represents an prescription that some drugs are required in
 */
public class Prescription {
    private long id;
    private Timestamp timestamp;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private Item item;
    private User user;
    private User doctor;
    private boolean isRequested;
    private boolean isCanceled;

    public Prescription() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public boolean isRequested() {
        return isRequested;
    }

    public void setRequested(boolean requested) {
        isRequested = requested;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prescription)) {
            return false;
        }

        Prescription that = (Prescription) o;

        if (id != that.id) {
            return false;
        }
        if (!item.equals(that.item)) {
            return false;
        }
        if (isRequested != that.isRequested) {
            return false;
        }
        if (isCanceled != that.isCanceled) {
            return false;
        }
        if (!timestamp.equals(that.timestamp)) {
            return false;
        }
        if (validFrom != null ? !validFrom.equals(that.validFrom) : that.validFrom != null) {
            return false;
        }
        if (validUntil != null ? !validUntil.equals(that.validUntil) : that.validUntil != null) {
            return false;
        }
        if (!user.equals(that.user)) {
            return false;
        }
        return doctor.equals(that.doctor);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
        result = 31 * result + (validUntil != null ? validUntil.hashCode() : 0);
        result = 31 * result + item.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + doctor.hashCode();
        result = 31 * result + (isRequested ? 1 : 0);
        result = 31 * result + (isCanceled ? 1 : 0);
        return result;
    }
}
