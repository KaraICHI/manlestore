package com.sxu.yusa.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A OrderComment.
 */
@Entity
@Table(name = "order_comment")
public class OrderComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_level", nullable = false)
    private Float level;

    @Column(name = "content")
    private String content;

    @Column(name = "creat_date")
    private LocalDate creatDate;

    @OneToOne
    @JoinColumn(unique = true)
    private OrderItem orderItem;

    @ManyToOne
    private ClientUser clientUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getLevel() {
        return level;
    }

    public OrderComment level(Float level) {
        this.level = level;
        return this;
    }

    public void setLevel(Float level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public OrderComment content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatDate() {
        return creatDate;
    }

    public OrderComment creatDate(LocalDate creatDate) {
        this.creatDate = creatDate;
        return this;
    }

    public void setCreatDate(LocalDate creatDate) {
        this.creatDate = creatDate;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public OrderComment orderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
        return this;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public ClientUser getClientUser() {
        return clientUser;
    }

    public OrderComment clientUser(ClientUser clientUser) {
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
        OrderComment orderComment = (OrderComment) o;
        if (orderComment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderComment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderComment{" +
            "id=" + getId() +
            ", level=" + getLevel() +
            ", content='" + getContent() + "'" +
            ", creatDate='" + getCreatDate() + "'" +
            "}";
    }
}
