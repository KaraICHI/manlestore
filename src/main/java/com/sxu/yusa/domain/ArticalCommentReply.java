package com.sxu.yusa.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ArticalCommentReply.
 */
@Entity
@Table(name = "artical_comment_reply")
public class ArticalCommentReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "creat_date")
    private LocalDate creatDate;

    @ManyToOne
    private ArticalComment articalComment;

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

    public ArticalCommentReply content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatDate() {
        return creatDate;
    }

    public ArticalCommentReply creatDate(LocalDate creatDate) {
        this.creatDate = creatDate;
        return this;
    }

    public void setCreatDate(LocalDate creatDate) {
        this.creatDate = creatDate;
    }

    public ArticalComment getArticalComment() {
        return articalComment;
    }

    public ArticalCommentReply articalComment(ArticalComment articalComment) {
        this.articalComment = articalComment;
        return this;
    }

    public void setArticalComment(ArticalComment articalComment) {
        this.articalComment = articalComment;
    }

    public ClientUser getClientUser() {
        return clientUser;
    }

    public ArticalCommentReply clientUser(ClientUser clientUser) {
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
        ArticalCommentReply articalCommentReply = (ArticalCommentReply) o;
        if (articalCommentReply.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), articalCommentReply.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArticalCommentReply{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", creatDate='" + getCreatDate() + "'" +
            "}";
    }
}
