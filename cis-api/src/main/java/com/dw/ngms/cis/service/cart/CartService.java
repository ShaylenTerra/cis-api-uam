package com.dw.ngms.cis.service.cart;

import com.dw.ngms.cis.persistence.domains.cart.Cart;
import com.dw.ngms.cis.persistence.domains.cart.CartData;
import com.dw.ngms.cis.persistence.domains.cart.CartStage;
import com.dw.ngms.cis.persistence.domains.cart.CartStageData;
import com.dw.ngms.cis.persistence.repository.cart.*;
import com.dw.ngms.cis.service.dto.cart.CartDto;
import com.dw.ngms.cis.service.dto.cart.CartStageDataDto;
import com.dw.ngms.cis.service.dto.cart.CartStageDto;
import com.dw.ngms.cis.service.mapper.CartJsonToCartItemConvertor;
import com.dw.ngms.cis.service.mapper.CartMapper;
import com.dw.ngms.cis.service.mapper.CartStageDataMapper;
import com.dw.ngms.cis.service.mapper.CartStageMapper;
import com.dw.ngms.cis.web.response.CreateResponse;
import com.dw.ngms.cis.web.response.UpdateResponse;
import com.dw.ngms.cis.web.vm.cart.CheckoutVm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartService {

    private final CartStageRepository cartStageRepository;

    private final CartStageDataRepository cartStageDataRepository;

    private final CartStageMapper cartStageMapper;

    private final CartStageDataMapper cartStageDataMapper;

    private final CartStageDataService cartStageDataService;

    private final CartRepository cartRepository;

    private final CartDataRepository cartDataRepository;

    private final CartJsonToCartItemConvertor cartJsonToCartItemConvertor;

    private final CartItemRepository cartItemRepository;

    private final CartMapper cartMapper;

    /**
     * method to return cart details by user id
     *
     * @param userId userId of logged in user
     * @return CartStageDto {@link CartStageDto}
     */
    public CartStageDto getByUserId(final Long userId) {
        return cartStageMapper.cartStageToCartStageDto(cartStageRepository.findByUserId(userId));
    }

    /**
     * method to add items to cart
     *
     * @param cartStageDataDto {@link CartStageDataDto}
     * @return CreateResponse
     */
    public CartStageDataDto addToCart(final CartStageDataDto cartStageDataDto) {

        // check if there is a cart data already for a user
        Collection<CartStageData> existingCartStageData = cartStageDataRepository
                .findByUserIdAndProvinceIdAndSearchTypeIdAndDataKey(cartStageDataDto.getUserId(),
                        cartStageDataDto.getProvinceId(), cartStageDataDto.getSearchTypeId(),
                        cartStageDataDto.getDataKey());

        if (existingCartStageData.size() > 0) {
            Long cartId = existingCartStageData.stream().findFirst().get().getCartId();
            cartStageDataDto.setCartId(cartId);
            return cartStageDataService.add(cartStageDataDto);
        } else {

            // get cartId from cart stage object based on userId
            CartStage byUserId = cartStageRepository.findByUserId(cartStageDataDto.getUserId());
            // create dummy cartStage and insert it into db
            if (null == byUserId) {
                CartStage build = CartStage.builder().provinceId(cartStageDataDto.getProvinceId())
                        .userId(cartStageDataDto.getUserId()).dated(new Date()).build();
                byUserId = cartStageRepository.save(build);
            }
            CartStageData cartStageData = cartStageDataMapper.cartStageDataDtoToCartStageData(cartStageDataDto);
            cartStageData.setCartId(byUserId.getCartId()); // set cartId
            return cartStageDataMapper.cartStageDataToCartStageDataDto(cartStageDataRepository.save(cartStageData));
        }

    }


    public Collection<CartStageDataDto> addBulkToCart(final Collection<CartStageDataDto> cartStageDataDtos) {

        return cartStageDataDtos.stream().map(cartStageDataDto -> {
            // check if there is a cart data already for a user
            Collection<CartStageData> existingCartStageData = cartStageDataRepository
                    .findByUserIdAndProvinceIdAndSearchTypeIdAndDataKey(cartStageDataDto.getUserId(),
                            cartStageDataDto.getProvinceId(), cartStageDataDto.getSearchTypeId(),
                            cartStageDataDto.getDataKey());

            if (existingCartStageData.size() > 0) {
                Long cartId = existingCartStageData.stream().findFirst().get().getCartId();
                cartStageDataDto.setCartId(cartId);
                return cartStageDataService.add(cartStageDataDto);
            } else {

                // get cartId from cart stage object based on userId
                CartStage byUserId = cartStageRepository.findByUserId(cartStageDataDto.getUserId());
                // create dummy cartStage and insert it into db
                if (null == byUserId) {
                    CartStage build = CartStage.builder().provinceId(cartStageDataDto.getProvinceId())
                            .userId(cartStageDataDto.getUserId()).dated(new Date()).build();
                    byUserId = cartStageRepository.save(build);
                }
                CartStageData cartStageData = cartStageDataMapper.cartStageDataDtoToCartStageData(cartStageDataDto);
                cartStageData.setCartId(byUserId.getCartId()); // set cartId
                return cartStageDataMapper.cartStageDataToCartStageDataDto(cartStageDataRepository.save(cartStageData));
            }
        }).collect(Collectors.toSet());

    }


    /**
     * @param cartDto {@link CartDto}
     * @return {@link CartDto}
     */
    public CartDto addToFinalCart(final CartDto cartDto) {
        Cart cart = cartMapper.cartDtoToCart(cartDto);
        cart.setCartData(null);
        Cart save = cartRepository.saveAndFlush(cart);

        Cart cart1 = cartMapper.cartDtoToCart(cartDto);

        cart1.getCartData().forEach(cartData1 -> {
            cartData1.setCartId(save.getCartId());
            cartDataRepository.saveAndFlush(cartData1);
        });

        Collection<CartData> byWorkflowId = cartDataRepository.findByWorkflowId(cartDto.getWorkflowId());
        cartItemRepository.saveAll(cartJsonToCartItemConvertor.cartJsonDataToCartItemInvoiceView(byWorkflowId));

        return cartMapper.cartToCartDto(cart1);
    }

    /**
     * @param cartId cartId of logged in user
     * @return boolean value representing is cart become empty or not
     */
    @Transactional
    public Boolean emptyCart(final Long cartId) {
        return 1 == cartStageRepository.deleteCartStageByCartId(cartId);
    }

    /**
     * method to update cart data
     *
     * @param cartStageDto {@link CartStageDto}
     * @return UpdateResponse
     */
    public UpdateResponse updateCart(CartStageDto cartStageDto) {
        CartStage existingCartStage = cartStageRepository.findByCartId(cartStageDto.getCartId());
        if (existingCartStage != null) {
            existingCartStage.setRequesterInformation(cartStageDto.getRequesterInformation());
            cartStageRepository.save(existingCartStage);
            return UpdateResponse.builder().update(true).build();
        }
        return UpdateResponse.builder().update(false).build();
    }

    /**
     * method to move cart data from stage to main cart tables
     *
     * @param checkoutVm {@link CheckoutVm}
     * @return CreateResponse
     */
    public CreateResponse checkout(CheckoutVm checkoutVm) {
        CartStage cartStage = cartStageRepository.findByCartId(checkoutVm.getCartId());
        Cart cart = cartStageMapper.cartStageToCart(cartStage);
        cart.setWorkflowId(checkoutVm.getWorkflowId());
        Cart cartResponse = cartRepository.save(cart);

        // fetch all data based on cartId from cartStageDataRepository
        Collection<CartStageData> cartStageDataList = cartStageDataRepository.findByCartId(checkoutVm.getCartId());
        cartStageDataList.forEach(cartStageData -> {
            CartData cartData = cartStageDataMapper.cartStageDataToCartData(cartStageData);
            cartData.setCartId(cartResponse.getCartId());
            cartData.setWorkflowId(checkoutVm.getWorkflowId());
            cartDataRepository.save(cartData);
        });

        // delete all data from cart stage data and cart stage
        cartStageDataRepository.deleteAll(cartStageDataList);
        cartStageRepository.delete(cartStage);

        // prepare data for cartItem
        // prepare invoice view of cart data
        Collection<CartData> byWorkflowId = cartDataRepository.findByWorkflowId(checkoutVm.getWorkflowId());
        cartItemRepository.saveAll(cartJsonToCartItemConvertor.cartJsonDataToCartItemInvoiceView(byWorkflowId));
        return CreateResponse.builder().created(true).build();
    }

    /**
     * @param cartStageDataId cartStageDataId
     */
    @Transactional
    public Boolean removeItemFromCart(final Long cartStageDataId, final Long cartId) {
        return 1 == cartStageDataRepository.deleteCartStageDataByIdAndCartId(cartStageDataId, cartId);
    }
}
