package io.github.williamandradesantana.ong_app_api.modules.donations.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.williamandradesantana.ong_app_api.modules.donations.enums.DonationStatus;
import io.github.williamandradesantana.ong_app_api.modules.donations.enums.PaymentMethod;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class DonationRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant donationDate;

    private PaymentMethod paymentMethod;
    private DonationStatus donationStatus;
    private UUID userId;
    private UUID projectId;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Instant donationDate) {
        this.donationDate = donationDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public DonationStatus getDonationStatus() {
        return donationStatus;
    }

    public void setDonationStatus(DonationStatus donationStatus) {
        this.donationStatus = donationStatus;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        DonationRequestDTO that = (DonationRequestDTO) o;
        return Objects.equals(amount, that.amount) && Objects.equals(donationDate, that.donationDate) && paymentMethod == that.paymentMethod && donationStatus == that.donationStatus && Objects.equals(userId, that.userId) && Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(amount);
        result = 31 * result + Objects.hashCode(donationDate);
        result = 31 * result + Objects.hashCode(paymentMethod);
        result = 31 * result + Objects.hashCode(donationStatus);
        result = 31 * result + Objects.hashCode(userId);
        result = 31 * result + Objects.hashCode(projectId);
        return result;
    }
}
