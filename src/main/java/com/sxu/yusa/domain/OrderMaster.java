package com.sxu.yusa.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.sxu.yusa.domain.enumeration.OrderStatus;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "order_master")
public class OrderMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "total_prices", precision=10, scale=2)
    private BigDecimal totalPrices;

    @Column(name = "total_quanity")
    private Integer totalQuanity;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @ManyToOne
    private ClientUser clientUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public OrderMaster orderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getTotalPrices() {
        return totalPrices;
    }

    public OrderMaster totalPrices(BigDecimal totalPrices) {
        this.totalPrices = totalPrices;
        return this;
    }

    public void setTotalPrices(BigDecimal totalPrices) {
        this.totalPrices = totalPrices;
    }

    public Integer getTotalQuanity() {
        return totalQuanity;
    }

    public OrderMaster totalQuanity(Integer totalQuanity) {
        this.totalQuanity = totalQuanity;
        return this;
    }

    public void setTotalQuanity(Integer totalQuanity) {
        this.totalQuanity = totalQuanity;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public OrderMaster orderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Address getAddress() {
        return address;
    }

    public OrderMaster address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ClientUser getClientUser() {
        return clientUser;
    }

    public OrderMaster clientUser(ClientUser clientUser) {
        this.clientUser = clientUser;
        return this;
    }

    public void setClientUser(ClientUser clientUser) {
        this.clientUser = clientUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderMaster orderMaster = (OrderMaster) o;
        if (orderMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderMaster{" +
            "id=" + getId() +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", totalPrices=" + getTotalPrices() +
            ", totalQuanity=" + getTotalQuanity() +
            ", orderStatus='" + getOrderStatus() + "'" +
            "}";
    }
}
