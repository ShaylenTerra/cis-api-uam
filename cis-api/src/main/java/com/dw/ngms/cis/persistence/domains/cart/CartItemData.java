package com.dw.ngms.cis.persistence.domains.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 09/02/21, Tue
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemData {

    @JsonProperty("diagramsData")
    private Collection<CartItemDiagramData> cartItemDiagramData;

    @JsonProperty("generalData")
    private Collection<CartItemGeneralData> cartItemGeneralData;

    @JsonProperty("advisoryData")
    private String cartItemAdvisoryData;

    @JsonProperty("certificatesData")
    private Collection<CartItemCertificateData> cartItemCertificateData;

    @JsonProperty("miscData")
    private String cartItemMiscellaneousData;

    @JsonProperty("alphaNumericsData")
    private Collection<CartItemAlphaNumericData> cartItemAlphaNumericData;

    @JsonProperty("spatialData")
    private Collection<CartItemSpatialData> cartItemSpatialData;

    @JsonProperty("coordinateData")
    private Collection<CartItemIncludeCoordinate> cartItemIncludeCoordinates;

    @JsonProperty("ngiData")
    private CartItemNgiData cartItemNgiData;



}
