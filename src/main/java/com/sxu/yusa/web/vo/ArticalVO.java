package com.sxu.yusa.web.vo;

import com.sxu.yusa.domain.ClientUser;
import com.sxu.yusa.service.dto.ArticalCommentDTO;

import java.util.Date;
import java.util.List;

public class ArticalVO {
    private Long id;

    private String title;

    private String content;

    private String figure;

    private Date creatDate;

    private Long favorite;

    private ClientUser clientUser;

    private List<ArticalCommentDTO> ArticalCommentList;

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

    public Long getFavorite() {
        return favorite;
    }

    public void setFavorite(Long favorite) {
        this.favorite = favorite;
    }

    public ClientUser getClientUser() {
        return clientUser;
    }

    public void setClientUser(ClientUser clientUser) {
        this.clientUser = clientUser;
    }

    public List<ArticalCommentDTO> getArticalCommentList() {
        return ArticalCommentList;
    }

    public void setArticalCommentList(List<ArticalCommentDTO> articalCommentList) {
        ArticalCommentList = articalCommentList;
    }
}
