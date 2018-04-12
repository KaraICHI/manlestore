package com.sxu.yusa.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.sxu.yusa.domain.enumeration.OrderStatus;

/**
 * A DTO for the OrderMaster entity.
 */
public class OrderMasterDTO implements Serializable {

    private Long id;

    private String orderNumber;

    private BigDecimal totalPrices;

    private Integer totalQuanity;

    private OrderStatus orderStatus;

    private Long addressId;

    private Long clientUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getTotalPrices() {
        return totalPrices;
    }

    public void setTotalPrices(BigDecimal totalPrices) {
        this.totalPrices = totalPrices;
    }

    public Integer getTotalQuanity() {
        return totalQuanity;
    }

    public void setTotalQuanity(Integer totalQuanity) {
        this.totalQuanity = totalQuanity;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(Long clientUserId) {
        this.clientUserId = clientUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderMasterDTO orderMasterDTO = (OrderMasterDTO) o;
        if(orderMasterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderMasterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderMasterDTO{" +
            "id=" + getId() +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", totalPrices=" + getTotalPrices() +
            ", totalQuanity=" + getTotalQuanity() +
            ", orderStatus='" + getOrderStatus() + "'" +
            "}";
    }
}
