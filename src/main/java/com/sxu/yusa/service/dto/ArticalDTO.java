package com.sxu.yusa.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Artical entity.
 */
public class ArticalDTO implements Serializable {

    private Long id;

    private String title;

    private String content;

    private String figure;

    private LocalDate creatDate;

    private Long like;

    private Long clientUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public LocalDate getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(LocalDate creatDate) {
        this.creatDate = creatDate;
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

        ArticalDTO articalDTO = (ArticalDTO) o;
        if(articalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), articalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArticalDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", figure='" + getFigure() + "'" +
            ", creatDate='" + getCreatDate() + "'" +
            "}";
    }

    public Long getLike() {
        return like;
    }

    public void setLike(Long like) {
        this.like = like;
    }
}
