package com.dw.ngms.cis.persistence.domains.cart;

import com.dw.ngms.cis.enums.DeliveryMedium;
import com.dw.ngms.cis.enums.DeliveryMethod;
import com.dw.ngms.cis.enums.RequestType;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 30/05/21, Sun
 **/
@Data
public class RequesterInformation {

    private Long userId;

    private Long isMediaToDepartment;

    private RequestType requesterType;

    private DeliveryMethod deliveryMethod;

    private DeliveryMedium deliveryMedium;

    private Requester requestLoggedBy;

    private Requester requesterDetails;
}
