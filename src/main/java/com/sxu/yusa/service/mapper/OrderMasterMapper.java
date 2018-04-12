package com.sxu.yusa.service.mapper;

import com.sxu.yusa.domain.*;
import com.sxu.yusa.service.dto.OrderMasterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderMaster and its DTO OrderMasterDTO.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class, ClientUserMapper.class})
public interface OrderMasterMapper extends EntityMapper<OrderMasterDTO, OrderMaster> {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "clientUser.id", target = "clientUserId")
    OrderMasterDTO toDto(OrderMaster orderMaster);

    @Mapping(source = "addressId", target = "address")
    @Mapping(source = "clientUserId", target = "clientUser")
    OrderMaster toEntity(OrderMasterDTO orderMasterDTO);

    default OrderMaster fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setId(id);
        return orderMaster;
    }
}
