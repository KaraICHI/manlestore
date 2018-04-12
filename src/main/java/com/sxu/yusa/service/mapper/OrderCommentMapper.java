package com.sxu.yusa.service.mapper;

import com.sxu.yusa.domain.*;
import com.sxu.yusa.service.dto.OrderCommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderComment and its DTO OrderCommentDTO.
 */
@Mapper(componentModel = "spring", uses = {OrderItemMapper.class, ClientUserMapper.class})
public interface OrderCommentMapper extends EntityMapper<OrderCommentDTO, OrderComment> {

    @Mapping(source = "orderItem.id", target = "orderItemId")
    @Mapping(source = "clientUser.id", target = "clientUserId")
    OrderCommentDTO toDto(OrderComment orderComment);

    @Mapping(source = "orderItemId", target = "orderItem")
    @Mapping(source = "clientUserId", target = "clientUser")
    OrderComment toEntity(OrderCommentDTO orderCommentDTO);

    default OrderComment fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderComment orderComment = new OrderComment();
        orderComment.setId(id);
        return orderComment;
    }
}
