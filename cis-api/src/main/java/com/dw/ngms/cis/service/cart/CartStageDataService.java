package com.dw.ngms.cis.service.cart;

import com.dw.ngms.cis.persistence.domains.cart.CartStageData;
import com.dw.ngms.cis.persistence.repository.cart.CartStageDataRepository;
import com.dw.ngms.cis.service.dto.cart.CartStageDataDto;
import com.dw.ngms.cis.service.mapper.CartStageDataMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartStageDataService {

    private final CartStageDataRepository cartStageDataRepository;

    private final CartStageDataMapper cartStageDataMapper;

    /**
     * method to add cart stage data
     *
     * @param cartStageDataDto {@link CartStageDataDto}
     * @return CreateResponse
     */
    public CartStageDataDto add(CartStageDataDto cartStageDataDto) {
        CartStageData cartStageData = cartStageDataMapper.cartStageDataDtoToCartStageData(cartStageDataDto);
        return cartStageDataMapper.cartStageDataToCartStageDataDto(cartStageDataRepository.save(cartStageData));
    }

}

