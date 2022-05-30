package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.cart.Cart;
import com.dw.ngms.cis.persistence.domains.cart.CartStage;
import com.dw.ngms.cis.service.dto.cart.CartStageDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
		unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartStageMapper {

	CartStage cartStageDtoToCartStage(CartStageDto cartStageDto);

	CartStageDto cartStageToCartStageDto(CartStage cartStage);

	@Mappings({
			@Mapping(target = "cartId", source = "cartId"),
			@Mapping(target = "provinceId", source = "provinceId"),
			@Mapping(target = "userId", source = "userId"),
			@Mapping(target = "dated", source = "dated"),
			@Mapping(target = "requesterInformation", source = "requesterInformation"),
			@Mapping(target = "workflowId", source = "workflowId")
	})
	Cart cartStageToCart(CartStage cartStage);
	
	@InheritInverseConfiguration
	CartStage cartToCartStage(Cart cart);
}
