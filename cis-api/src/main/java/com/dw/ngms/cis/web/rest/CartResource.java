package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.service.cart.CartService;
import com.dw.ngms.cis.service.dto.cart.CartDto;
import com.dw.ngms.cis.service.dto.cart.CartStageDataDto;
import com.dw.ngms.cis.service.dto.cart.CartStageDto;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.response.CreateResponse;
import com.dw.ngms.cis.web.response.DeleteResponse;
import com.dw.ngms.cis.web.response.UpdateResponse;
import com.dw.ngms.cis.web.vm.cart.CheckoutVm;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@BaseResponse
@AllArgsConstructor
@RestController
@RequestMapping(AppConstants.API_BASE_MAPPING + "/cart")
public class CartResource {

    private final CartService cartService;

    /**
     * @param userId userId of logged in user
     * @return ResponseEntity<CartStageDto>
     */
    @GetMapping("/")
    public ResponseEntity<CartStageDto> getCartDetails(@RequestParam @NotNull final Long userId) {
        return ResponseEntity.ok().body(cartService.getByUserId(userId));
    }

    /**
     * @param cartStageDataDto {@link CartStageDataDto}
     * @return {@link CreateResponse}
     */
    @PostMapping("/add")
    public ResponseEntity<CartStageDataDto> addToCart(@RequestBody @NotNull final CartStageDataDto cartStageDataDto) {
        return ResponseEntity.ok()
                .body(cartService.addToCart(cartStageDataDto));
    }

    /**
     * @param cartStageDataDto {@link CartStageDataDto}
     * @return {@link CreateResponse}
     */
    @PostMapping("/bulkAdd")
    public ResponseEntity<Collection<CartStageDataDto>> addBulkToCart(@RequestBody @Validated final Collection<CartStageDataDto> cartStageDataDto) {
        return ResponseEntity.ok()
                .body(cartService.addBulkToCart(cartStageDataDto));
    }

    /**
     * @param cartStageDataId cartStageDataId
     * @param cartId          cartId
     * @return {@link DeleteResponse}
     */
    @GetMapping("/removeCartItem")
    public ResponseEntity<DeleteResponse> removeItemsFromCart(@RequestParam @NotNull final Long cartStageDataId,
                                                              @RequestParam final Long cartId) {
        Boolean aBoolean = cartService.removeItemFromCart(cartStageDataId, cartId);

        DeleteResponse build = DeleteResponse.builder()
                .isDeleted(aBoolean)
                .build();

        return ResponseEntity.ok().body(build);
    }

    /**
     * @param cartId userId of logged in user
     * @return {@link DeleteResponse}
     */
    @GetMapping("/empty")
    public ResponseEntity<DeleteResponse> emptyCart(@RequestParam @NotNull final Long cartId) {
        Boolean aBoolean = cartService.emptyCart(cartId);
        DeleteResponse build = DeleteResponse.builder().isDeleted(aBoolean).build();
        return ResponseEntity.accepted().body(build);
    }

    /**
     * @param cartStageDto {@link CartStageDataDto}
     * @return {@link CreateResponse}
     */
    @PostMapping("/update")
    public ResponseEntity<UpdateResponse> updateCart(@RequestBody @NotNull final CartStageDto cartStageDto) {
        return ResponseEntity.ok().body(cartService.updateCart(cartStageDto));
    }

    /**
     * @param checkoutVm {@link CheckoutVm}
     * @return ResponseEntity<CreateResponse>
     */
    @PostMapping("/checkout")
    public ResponseEntity<CreateResponse> checkout(@RequestBody @NotNull final CheckoutVm checkoutVm) {
        return ResponseEntity.ok().body(cartService.checkout(checkoutVm));
    }


    /**
     * @param cartDto {@link CartDto}
     * @return {@link CartDto}
     */
    @PostMapping("/addCartHook")
    public ResponseEntity<CartDto> addToFinalCart(@RequestBody @Valid CartDto cartDto) {
        return ResponseEntity.ok()
                .body(cartService.addToFinalCart(cartDto));
    }

}
