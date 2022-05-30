package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.cart.CartData;
import com.dw.ngms.cis.persistence.domains.cart.CartStageData;
import com.dw.ngms.cis.service.dto.cart.CartStageDataDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CartStageDataMapper {

	@Mappings({
			@Mapping(target = "id", source = "id"),
			@Mapping(target = "userId", source = "userId"),
			@Mapping(target = "provinceId", source = "provinceId"),
			@Mapping(target = "cartId", source = "cartId"),
			@Mapping(target = "dataKey", source = "dataKey"),
			@Mapping(target = "dataKeyValue", source = "dataKeyValue"),
			@Mapping(target = "dated", source = "dated"),
			@Mapping(target = "jsonData", source = "jsonData"),
			@Mapping(target = "searchTypeId", source = "searchTypeId"),
	})
	CartStageData cartStageDataDtoToCartStageData(CartStageDataDto cartStageDataDto);

	@InheritInverseConfiguration
	CartStageDataDto cartStageDataToCartStageDataDto(CartStageData cartStageData);
	
	@Mappings({
		@Mapping(target = "dated", source = "dated"),
		@Mapping(target = "provinceId", source = "provinceId"),
		@Mapping(target = "userId", source = "userId"),
		@Mapping(target = "searchTypeId", source = "searchTypeId"),
		@Mapping(target = "dataKey", source = "dataKey"),
		@Mapping(target = "jsonData", source = "jsonData")
	})
	CartData cartStageDataToCartData(CartStageData cartStageData);
	
	@InheritInverseConfiguration
	CartStageData cartDataToCartStageData(CartData cartData);

}
