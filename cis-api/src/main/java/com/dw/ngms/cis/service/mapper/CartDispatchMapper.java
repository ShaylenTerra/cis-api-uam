package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.cart.CartDispatch;
import com.dw.ngms.cis.service.dto.cart.CartDispatchDto;
import org.mapstruct.*;

/**
 * @author : prateekgoel
 * @since : 11/02/21, Thu
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartDispatchMapper {

    @Mappings({
            @Mapping(target = "id", source = "cartDispatchId"),
            @Mapping(target = "dispatchDetails", source = "dispatchDetails"),
            @Mapping(target = "workflowId", source = "workflowId"),
    })
    CartDispatch cartDispatchDtoToCartDispatch(CartDispatchDto cartDispatchDto);

    @InheritInverseConfiguration
    CartDispatchDto cartDispatchToCartDispatchDto(CartDispatch cartDispatch);

}
