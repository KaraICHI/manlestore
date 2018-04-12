package com.sxu.yusa.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ArticalComment.
 */
@Entity
@Table(name = "artical_comment")
public class ArticalComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    private Artical artical;

    @ManyToOne
    private ClientUser clientUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public ArticalComment content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Artical getArtical() {
        return artical;
    }

    public ArticalComment artical(Artical artical) {
        this.artical = artical;
        return this;
    }

    public void setArtical(Artical artical) {
        this.artical = artical;
    }

    public ClientUser getClientUser() {
        return clientUser;
    }

    public ArticalComment clientUser(ClientUser clientUser) {
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
        ArticalComment articalComment = (ArticalComment) o;
        if (articalComment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), articalComment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArticalComment{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
