package com.sxu.yusa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "cover_price", precision=10, scale=2)
    private BigDecimal coverPrice;

    @Column(name = "origin_price", precision=10, scale=2)
    private BigDecimal originPrice;

    @Column(name = "produce_date")
    private LocalDate produceDate;

    @Column(name = "period")
    private Integer period;

    @Column(name = "figure")
    private String figure;

    @Column(name = "brand")
    private String brand;

    @Column(name = "supply")
    private String supply;

    @Column(name = "brief")
    private String brief;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Theme theme;

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private Set<ClientUser> clientUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public Product productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getCoverPrice() {
        return coverPrice;
    }

    public Product coverPrice(BigDecimal coverPrice) {
        this.coverPrice = coverPrice;
        return this;
    }

    public void setCoverPrice(BigDecimal coverPrice) {
        this.coverPrice = coverPrice;
    }

    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public Product originPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
        return this;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    public LocalDate getProduceDate() {
        return produceDate;
    }

    public Product produceDate(LocalDate produceDate) {
        this.produceDate = produceDate;
        return this;
    }

    public void setProduceDate(LocalDate produceDate) {
        this.produceDate = produceDate;
    }

    public Integer getPeriod() {
        return period;
    }

    public Product period(Integer period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getFigure() {
        return figure;
    }

    public Product figure(String figure) {
        this.figure = figure;
        return this;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getBrand() {
        return brand;
    }

    public Product brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSupply() {
        return supply;
    }

    public Product supply(String supply) {
        this.supply = supply;
        return this;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    public String getBrief() {
        return brief;
    }

    public Product brief(String brief) {
        this.brief = brief;
        return this;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public Product category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Theme getTheme() {
        return theme;
    }

    public Product theme(Theme theme) {
        this.theme = theme;
        return this;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Set<ClientUser> getClientUsers() {
        return clientUsers;
    }

    public Product clientUsers(Set<ClientUser> clientUsers) {
        this.clientUsers = clientUsers;
        return this;
    }

    public Product addClientUser(ClientUser clientUser) {
        this.clientUsers.add(clientUser);
        clientUser.getProducts().add(this);
        return this;
    }

    public Product removeClientUser(ClientUser clientUser) {
        this.clientUsers.remove(clientUser);
        clientUser.getProducts().remove(this);
        return this;
    }

    public void setClientUsers(Set<ClientUser> clientUsers) {
        this.clientUsers = clientUsers;
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
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", coverPrice=" + getCoverPrice() +
            ", originPrice=" + getOriginPrice() +
            ", produceDate='" + getProduceDate() + "'" +
            ", period=" + getPeriod() +
            ", figure='" + getFigure() + "'" +
            ", brand='" + getBrand() + "'" +
            ", supply='" + getSupply() + "'" +
            ", brief='" + getBrief() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
