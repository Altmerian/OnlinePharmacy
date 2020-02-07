package by.epam.pavelshakhlovich.onlinepharmacy.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Class {@code Prescription} represents an prescription that some drugs are required in
 */
public class Prescription implements Serializable {
    private static final long serialVersionUID = 8927772728101362089L;
    private long id;
    private LocalDateTime validUntil;
    private String status;
    private long drugId;
    private long userId;
    private long doctorId;

    public Prescription() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getDrugId() {
        return drugId;
    }

    public void setDrugId(long drugId) {
        this.drugId = drugId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
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
        if (drugId != that.drugId) {
            return false;
        }

        if (!status.equals(that.status)) {
            return false;
        }

        if (!validUntil.equals(that.validUntil)) {
            return false;
        }
        if (userId != that.userId) {
            return false;
        }
        return doctorId != that.doctorId;
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + validUntil.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + Long.hashCode(drugId);
        result = 31 * result + Long.hashCode(userId);
        result = 31 * result + Long.hashCode(doctorId);
        return result;
    }
}
