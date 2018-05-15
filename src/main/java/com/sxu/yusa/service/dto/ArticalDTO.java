package com.sxu.yusa.service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A DTO for the Artical entity.
 */
public class ArticalDTO implements Serializable {

    private Long id;

    private String title;

    private String content;

    private String figure;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatDate;

    private Long favorite;

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

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
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
            ", favorite='" + getFavorite()+ "'" +
            ", clientUserId='" + getClientUserId() + "'" +
            "}";
    }

    public Long getFavorite() {
        return favorite;
    }

    public void setFavorite(Long favorite) {
        this.favorite = favorite;
    }
}
