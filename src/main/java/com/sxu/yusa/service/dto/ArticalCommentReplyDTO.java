package com.sxu.yusa.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ArticalCommentReply entity.
 */
public class ArticalCommentReplyDTO implements Serializable {

    private Long id;

    private String content;

    private LocalDate creatDate;

    private Long articalCommentId;

    private Long clientUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(LocalDate creatDate) {
        this.creatDate = creatDate;
    }

    public Long getArticalCommentId() {
        return articalCommentId;
    }

    public void setArticalCommentId(Long articalCommentId) {
        this.articalCommentId = articalCommentId;
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

        ArticalCommentReplyDTO articalCommentReplyDTO = (ArticalCommentReplyDTO) o;
        if(articalCommentReplyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), articalCommentReplyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArticalCommentReplyDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", creatDate='" + getCreatDate() + "'" +
            "}";
    }
}
