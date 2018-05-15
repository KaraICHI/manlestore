package com.sxu.yusa.web.vo;

import com.sxu.yusa.service.dto.ArticalCommentDTO;
import com.sxu.yusa.service.dto.ArticalDTO;
import com.sxu.yusa.service.dto.ClientUserDTO;

public class CommunityVO {

    private ClientUserDTO clientUserDTO;
    private ArticalDTO articalDTO;
    private ArticalCommentDTO articalCommentDTO;

    public ClientUserDTO getClientUserDTO() {
        return clientUserDTO;
    }

    public void setClientUserDTO(ClientUserDTO clientUserDTO) {
        this.clientUserDTO = clientUserDTO;
    }

    public ArticalDTO getArticalDTO() {
        return articalDTO;
    }

    public void setArticalDTO(ArticalDTO articalDTO) {
        this.articalDTO = articalDTO;
    }

    public ArticalCommentDTO getArticalCommentDTO() {
        return articalCommentDTO;
    }

    public void setArticalCommentDTO(ArticalCommentDTO articalCommentDTO) {
        this.articalCommentDTO = articalCommentDTO;
    }
}
