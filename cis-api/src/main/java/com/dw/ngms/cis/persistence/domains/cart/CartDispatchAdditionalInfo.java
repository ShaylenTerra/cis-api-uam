package com.dw.ngms.cis.persistence.domains.cart;

import com.dw.ngms.cis.enums.DeliveryMedium;
import com.dw.ngms.cis.enums.DeliveryMethod;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 01/06/21, Tue
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartDispatchAdditionalInfo {

    private Long cartDispatchId;

    private String primaryEmail;

    private String secondaryEmail;

    private String referenceNumber;

    private String dataDispatched;

    private String collectorName;

    private String collectorSurname;

    private String collectorContactNumber;

    private String postalAddressLine1;

    private String postalAddressLine2;

    private String postalAddressLine3;

    private String postalCode;

    private String courierName;

    private String contactPerson;

    private String ftpDetails;

    private DeliveryMethod deliveryMethod;

    private DeliveryMedium deliveryMedium;


}
