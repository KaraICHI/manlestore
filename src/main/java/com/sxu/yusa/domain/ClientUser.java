package com.sxu.yusa.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ClientUser.
 */
@Entity
@Table(name = "client_user")
public class ClientUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @NotNull
    @Column(name = "jhi_password", nullable = false)
    private String password;

    @Column(name = "figure")
    private String figure;

    @Column(name = "point")
    private Float point;

    @ManyToMany
    @JoinTable(name = "client_user_product",
               joinColumns = @JoinColumn(name="client_users_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="products_id", referencedColumnName="id"))
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public ClientUser userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public ClientUser phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public ClientUser password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFigure() {
        return figure;
    }

    public ClientUser figure(String figure) {
        this.figure = figure;
        return this;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public Float getPoint() {
        return point;
    }

    public ClientUser point(Float point) {
        this.point = point;
        return this;
    }

    public void setPoint(Float point) {
        this.point = point;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public ClientUser products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public ClientUser addProduct(Product product) {
        this.products.add(product);
        product.getClientUsers().add(this);
        return this;
    }

    public ClientUser removeProduct(Product product) {
        this.products.remove(product);
        product.getClientUsers().remove(this);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
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
        ClientUser clientUser = (ClientUser) o;
        if (clientUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientUser{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", password='" + getPassword() + "'" +
            ", figure='" + getFigure() + "'" +
            ", point=" + getPoint() +
            "}";
    }
}
