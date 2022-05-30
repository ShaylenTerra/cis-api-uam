package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.cart.Cart;
import com.dw.ngms.cis.service.dto.cart.CartDto;
import org.mapstruct.Mapper;

/**
 * @author : prateekgoel
 * @since : 09/03/21, Tue
 **/
@Mapper(componentModel = "spring")
public interface CartMapper {

    Cart cartDtoToCart(CartDto cartDto);

    CartDto cartToCartDto(Cart cart);


}
