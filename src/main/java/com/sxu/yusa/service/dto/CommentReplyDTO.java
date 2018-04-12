package com.sxu.yusa.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CommentReply entity.
 */
public class CommentReplyDTO implements Serializable {

    private Long id;

    private String content;

    private LocalDate creatDate;

    private Long orderCommentId;

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

    public Long getOrderCommentId() {
        return orderCommentId;
    }

    public void setOrderCommentId(Long orderCommentId) {
        this.orderCommentId = orderCommentId;
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

        CommentReplyDTO commentReplyDTO = (CommentReplyDTO) o;
        if(commentReplyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentReplyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommentReplyDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", creatDate='" + getCreatDate() + "'" +
            "}";
    }
}
