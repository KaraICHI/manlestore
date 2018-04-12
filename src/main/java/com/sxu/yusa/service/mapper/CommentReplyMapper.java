package com.sxu.yusa.service.mapper;

import com.sxu.yusa.domain.*;
import com.sxu.yusa.service.dto.CommentReplyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommentReply and its DTO CommentReplyDTO.
 */
@Mapper(componentModel = "spring", uses = {OrderCommentMapper.class, ClientUserMapper.class})
public interface CommentReplyMapper extends EntityMapper<CommentReplyDTO, CommentReply> {

    @Mapping(source = "orderComment.id", target = "orderCommentId")
    @Mapping(source = "clientUser.id", target = "clientUserId")
    CommentReplyDTO toDto(CommentReply commentReply);

    @Mapping(source = "orderCommentId", target = "orderComment")
    @Mapping(source = "clientUserId", target = "clientUser")
    CommentReply toEntity(CommentReplyDTO commentReplyDTO);

    default CommentReply fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommentReply commentReply = new CommentReply();
        commentReply.setId(id);
        return commentReply;
    }
}
