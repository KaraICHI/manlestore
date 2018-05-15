package com.sxu.yusa.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A Artical.
 */
@Entity
@Table(name = "artical")
public class Artical implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "figure")
    private String figure;

    @Column(name = "creat_date")
    private Date creatDate;

    @Column(name = "favorite")
    private Long favorite;

    @ManyToOne
    private ClientUser clientUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Artical title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public Artical content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFigure() {
        return figure;
    }

    public Artical figure(String figure) {
        this.figure = figure;
        return this;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public Artical creatDate(Date creatDate) {
        this.creatDate = creatDate;
        return this;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    public ClientUser getClientUser() {
        return clientUser;
    }

    public Artical clientUser(ClientUser clientUser) {
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
        Artical artical = (Artical) o;
        if (artical.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), artical.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Artical{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", figure='" + getFigure() + "'" +
            ", creatDate='" + getCreatDate() + "'" +
            ", favorite='" + getFavorite() + "'" +
            ", clientUserId='" + getClientUser().getId() + "'" +

            "}";
    }

    public Long getFavorite() {
        return favorite;
    }

    public void setFavorite(Long favorite) {
        this.favorite = favorite;
    }
}
