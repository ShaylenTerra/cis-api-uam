package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.cart.*;
import com.dw.ngms.cis.persistence.projection.fee.InvoiceItemCostProjection;
import com.dw.ngms.cis.persistence.repository.fee.FeeMasterRepository;
import com.dw.ngms.cis.web.request.InvoiceItemCostVm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author : prateekgoel
 * @since : 09/02/21, Tue
 **/
@Component
@AllArgsConstructor
@Slf4j
public class CartJsonToCartItemConvertor {

    private final ObjectMapper mapper;

    private final FeeMasterRepository feeMasterRepository;

    /**
     * @param cartData Collection<CartData> cartData
     * @return Collection<CartItemInvoiceView>
     */
    public Collection<CartItems> cartJsonDataToCartItemInvoiceView(Collection<CartData> cartData) {
        Collection<CartItems> cartItems = new LinkedList<>();
        cartData.forEach(cartData1 -> {
            String jsonData = cartData1.getJsonData();
            Long workflowId = cartData1.getWorkflowId();
            Long cartDataId = cartData1.getId();

            try {

                CartJsonData cartJsonData = mapper.readerFor(CartJsonData.class)
                        .readValue(jsonData);

                if (null != cartJsonData) {

                    CartItemData cartItemData = cartJsonData.getCartItemData();
                    SearchDetails searchDetails = cartJsonData.getSearchDetails();

                    if (null != cartItemData) {

                        Integer counter = 0;

                        Collection<CartItemDiagramData> cartItemDiagramData = cartItemData.getCartItemDiagramData();

                        if (null != cartItemDiagramData && cartItemDiagramData.size() > 0) {
                            int subCounter = 1;
                            counter++;
                            for (CartItemDiagramData cartItemDiagramData1 : cartItemDiagramData) {
                                CartItems cartItem = new CartItems();
                                cartItem.setLpicode(searchDetails.getLpi());
                                cartItem.setCartDataId(cartDataId);
                                cartItem.setWorkflowId(workflowId);
                                cartItem.setItem("Diagram");

                                cartItem.setSno(counter + "." + subCounter);

                                CartItemDataType cartItemDataType = cartItemDiagramData1.getCartItemDataType();

                                InvoiceItemCostVm invoiceItemCostVm = new InvoiceItemCostVm();

                                if (null != cartItemDataType) {
                                    cartItem.setDetails(cartItemDataType.getCaption());
                                    invoiceItemCostVm.setPaperSizeListItemId(cartItemDataType.getItemId());
                                }

                                CartItemDataFormat cartItemDataFormat = cartItemDiagramData1.getCartItemDataFormat();

                                if (null != cartItemDataFormat) {
                                    cartItem.setType(cartItemDataFormat.getCaption());
                                    invoiceItemCostVm.setFormatListItemId(cartItemDataFormat.getItemId());
                                }


                                invoiceItemCostVm.setSearchDataTypeId(1L);
                                invoiceItemCostVm.setSubTypeListItemId(0L);
                                invoiceItemCostVm.setDataTypeListItemId(0L);
                                prepareCostingForCartItems(cartItem, invoiceItemCostVm);

                                cartItems.add(cartItem);

                                subCounter++;

                            }
                        }

                        String cartItemAdvisoryData = cartItemData.getCartItemAdvisoryData();
                        if (null != cartItemAdvisoryData) {
                            counter++;
                            CartItems cartItem = new CartItems();
                            cartItem.setLpicode(searchDetails.getLpi());
                            cartItem.setCartDataId(cartDataId);

                            cartItem.setWorkflowId(workflowId);
                            cartItem.setItem("Advisory");
                            cartItem.setSno(counter + ".1");
                            cartItem.setDetails(cartItemAdvisoryData);

                            InvoiceItemCostVm invoiceItemCostVm = new InvoiceItemCostVm();
                            invoiceItemCostVm.setDataTypeListItemId(0L);
                            invoiceItemCostVm.setPaperSizeListItemId(0L);
                            invoiceItemCostVm.setSubTypeListItemId(0L);
                            invoiceItemCostVm.setFormatListItemId(0L);
                            invoiceItemCostVm.setSearchDataTypeId(6L);
                            prepareCostingForCartItems(cartItem, invoiceItemCostVm);

                            cartItems.add(cartItem);
                        }

                        Collection<CartItemGeneralData> cartItemGeneralData = cartItemData.getCartItemGeneralData();
                        if (null != cartItemGeneralData && cartItemGeneralData.size() > 0) {

                            counter++;
                            int subCounter = 1;
                            for (CartItemGeneralData cartItemGeneralDatum : cartItemGeneralData) {

                                CartItems cartItem = new CartItems();

                                cartItem.setLpicode(searchDetails.getLpi());

                                cartItem.setCartDataId(cartDataId);

                                cartItem.setWorkflowId(workflowId);

                                cartItem.setItem("General Data");

                                cartItem.setSno(counter + "." + subCounter);

                                CartItemDataType cartItemDataType = cartItemGeneralDatum.getCartItemDataType();

                                InvoiceItemCostVm invoiceItemCostVm = new InvoiceItemCostVm();

                                if (null != cartItemDataType) {
                                    cartItem.setDetails(cartItemDataType.getCaption());
                                    invoiceItemCostVm.setPaperSizeListItemId(cartItemDataType.getItemId());
                                }

                                CartItemDataFormat cartItemDataFormat = cartItemGeneralDatum.getCartItemDataFormat();
                                if (null != cartItemDataFormat) {
                                    cartItem.setType(cartItemDataFormat.getCaption());
                                    invoiceItemCostVm.setFormatListItemId(cartItemDataFormat.getItemId());
                                }

                                invoiceItemCostVm.setDataTypeListItemId(0L);
                                invoiceItemCostVm.setSearchDataTypeId(7L);
                                invoiceItemCostVm.setSubTypeListItemId(0L);
                                prepareCostingForCartItems(cartItem, invoiceItemCostVm);

                                cartItems.add(cartItem);
                                subCounter++;
                            }
                        }

                        String cartItemMiscellaneousData = cartItemData.getCartItemMiscellaneousData();
                        if (StringUtils.isNotBlank(cartItemMiscellaneousData)) {
                            counter++;
                            CartItems cartItem = new CartItems();
                            cartItem.setLpicode(searchDetails.getLpi());
                            cartItem.setCartDataId(cartDataId);
                            cartItem.setWorkflowId(workflowId);
                            cartItem.setItem("Miscellaneous");
                            cartItem.setSno(counter + ".1");
                            cartItem.setDetails(cartItemMiscellaneousData);

                            InvoiceItemCostVm invoiceItemCostVm = new InvoiceItemCostVm();
                            invoiceItemCostVm.setDataTypeListItemId(0L);
                            invoiceItemCostVm.setPaperSizeListItemId(0L);
                            invoiceItemCostVm.setSubTypeListItemId(0L);
                            invoiceItemCostVm.setFormatListItemId(0L);
                            invoiceItemCostVm.setSearchDataTypeId(8L);
                            prepareCostingForCartItems(cartItem, invoiceItemCostVm);


                            cartItems.add(cartItem);
                        }


                        Collection<CartItemCertificateData> cartItemCertificateData = cartItemData
                                .getCartItemCertificateData();
                        if (null != cartItemCertificateData && cartItemCertificateData.size() > 0) {
                            counter++;
                            int subCounter = 1;
                            for (CartItemCertificateData cartItemCertificateDatum : cartItemCertificateData) {

                                CartItems cartItem = new CartItems();
                                cartItem.setItem("Certificate");
                                cartItem.setLpicode(searchDetails.getLpi());
                                cartItem.setCartDataId(cartDataId);
                                cartItem.setWorkflowId(workflowId);
                                cartItem.setSno(counter + "." + subCounter);
                                CartItemDataCertType cartItemDataCertType = cartItemCertificateDatum.getCartItemDataCertType();

                                InvoiceItemCostVm invoiceItemCostVm = new InvoiceItemCostVm();

                                if (null != cartItemDataCertType) {
                                    cartItem.setDetails(cartItemDataCertType.getCaption());
                                    invoiceItemCostVm.setSubTypeListItemId(cartItemDataCertType.getItemId());
                                }

                                CartItemDataType cartItemDataType = cartItemCertificateDatum.getCartItemDataType();
                                if (null != cartItemDataType) {
                                    cartItem.setDetails(cartItem.getDetails() + " " + cartItemDataType.getCaption());
                                    invoiceItemCostVm.setPaperSizeListItemId(cartItemDataType.getItemId());
                                }

                                CartItemDataFormat cartItemDataFormat = cartItemCertificateDatum.getCartItemDataFormat();
                                if (null != cartItemDataFormat) {
                                    cartItem.setType(cartItemDataFormat.getCaption());
                                    invoiceItemCostVm.setFormatListItemId(cartItemDataFormat.getItemId());
                                }

                                invoiceItemCostVm.setSearchDataTypeId(4L);
                                invoiceItemCostVm.setDataTypeListItemId(0L);
                                prepareCostingForCartItems(cartItem, invoiceItemCostVm);

                                cartItems.add(cartItem);
                                subCounter++;

                            }

                        }

                        Collection<CartItemAlphaNumericData> cartItemAlphaNumericData = cartItemData
                                .getCartItemAlphaNumericData();
                        if (null != cartItemAlphaNumericData && cartItemAlphaNumericData.size() > 0) {
                            counter++;
                            int subCounter = 1;
                            for (CartItemAlphaNumericData cartItemAlphaNumericDatum : cartItemAlphaNumericData) {

                                CartItems cartItem = new CartItems();
                                cartItem.setLpicode(searchDetails.getLpi());
                                cartItem.setCartDataId(cartDataId);
                                cartItem.setWorkflowId(workflowId);
                                cartItem.setItem("Alphanumeric");
                                cartItem.setSno(counter + "." + subCounter);
                                InvoiceItemCostVm invoiceItemCostVm = new InvoiceItemCostVm();
                                CartItemDataType cartItemDataType = cartItemAlphaNumericDatum.getCartItemDataType();
                                if (null != cartItemDataType) {
                                    cartItem.setDetails(cartItemDataType.getCaption());
                                    invoiceItemCostVm.setDataTypeListItemId(cartItemDataType.getItemId());
                                }

                                invoiceItemCostVm.setFormatListItemId(0L);
                                invoiceItemCostVm.setSearchDataTypeId(3L);
                                invoiceItemCostVm.setPaperSizeListItemId(0L);
                                invoiceItemCostVm.setSubTypeListItemId(0L);
                                prepareCostingForCartItems(cartItem, invoiceItemCostVm);

                                cartItems.add(cartItem);
                                subCounter++;

                            }

                        }

                        Collection<CartItemSpatialData> cartItemSpatialData = cartItemData.getCartItemSpatialData();
                        if (null != cartItemSpatialData && cartItemSpatialData.size() > 0) {
                            counter++;
                            int subCounter = 1;
                            for (CartItemSpatialData cartItemSpatialDatum : cartItemSpatialData) {

                                CartItems cartItem = new CartItems();

                                cartItem.setCartDataId(cartDataId);

                                cartItem.setLpicode(searchDetails.getLpi());

                                cartItem.setCartDataId(cartDataId);

                                cartItem.setWorkflowId(workflowId);

                                cartItem.setItem("Spatial");

                                cartItem.setSno(counter + "." + subCounter);

                                CartItemDataType cartItemDataType = cartItemSpatialDatum.getCartItemDataType();
                                InvoiceItemCostVm invoiceItemCostVm = new InvoiceItemCostVm();

                                if (null != cartItemDataType) {
                                    cartItem.setDetails(cartItemDataType.getCaption());
                                    invoiceItemCostVm.setDataTypeListItemId(cartItemDataType.getItemId());
                                }

                                invoiceItemCostVm.setSearchDataTypeId(2L);
                                invoiceItemCostVm.setFormatListItemId(0L);
                                invoiceItemCostVm.setSubTypeListItemId(0L);
                                invoiceItemCostVm.setPaperSizeListItemId(0L);
                                prepareCostingForCartItems(cartItem, invoiceItemCostVm);
                                cartItems.add(cartItem);
                                subCounter++;
                            }
                        }

                        final Collection<CartItemIncludeCoordinate> cartItemIncludeCoordinates = cartItemData
                                .getCartItemIncludeCoordinates();
                        if (null != cartItemIncludeCoordinates && cartItemIncludeCoordinates.size() > 0) {
                            counter++;
                            int subCounter = 1;
                            for (CartItemIncludeCoordinate cartItemIncludeCoordinate : cartItemIncludeCoordinates) {
                                CartItems cartItem = new CartItems();
                                cartItem.setLpicode(searchDetails.getLpi());
                                cartItem.setCartDataId(cartDataId);
                                cartItem.setWorkflowId(workflowId);
                                cartItem.setItem("Incl. Coordinate");
                                cartItem.setSno(counter + "." + subCounter);
                                InvoiceItemCostVm invoiceItemCostVm = new InvoiceItemCostVm();
                                CartItemDataType cartItemDataType = cartItemIncludeCoordinate.getCartItemDataType();
                                if (null != cartItemDataType) {
                                    cartItem.setDetails(cartItemDataType.getCaption());
                                    invoiceItemCostVm.setDataTypeListItemId(cartItemDataType.getItemId());
                                }

                                invoiceItemCostVm.setFormatListItemId(0L);
                                invoiceItemCostVm.setSearchDataTypeId(3L);
                                invoiceItemCostVm.setPaperSizeListItemId(0L);
                                invoiceItemCostVm.setSubTypeListItemId(0L);
                                prepareCostingForCartItems(cartItem, invoiceItemCostVm);

                                cartItems.add(cartItem);
                                subCounter++;
                            }
                        }

                        CartItemNgiData cartItemNgiData = cartItemData.getCartItemNgiData();
                        if(null != cartItemNgiData) {
                            counter++;
                            int subCounter = 1;

                            CartItems cartItem = new CartItems();
                            cartItem.setLpicode(searchDetails.getCategoryName());
                            cartItem.setCartDataId(cartDataId);
                            cartItem.setWorkflowId(workflowId);
                            cartItem.setItem("Ngi Data");
                            cartItem.setSno(counter + "." + subCounter);
                            cartItem.setNotes(cartItemNgiData.getNotes());

                            InvoiceItemCostVm invoiceItemCostVm = new InvoiceItemCostVm();
                            CartItemDataFormat cartItemDataFormat = cartItemNgiData.getCartItemDataFormat();
                            if (null != cartItemDataFormat) {
                                cartItem.setType(cartItemDataFormat.getCaption());
                                invoiceItemCostVm.setFormatListItemId(cartItemDataFormat.getItemId());
                            }

                            invoiceItemCostVm.setSubTypeListItemId(searchDetails.getRecordId());
                            invoiceItemCostVm.setPaperSizeListItemId(0L);
                            invoiceItemCostVm.setDataTypeListItemId(0L);
                            invoiceItemCostVm.setSearchDataTypeId(16L);
                            prepareCostingForCartItems(cartItem, invoiceItemCostVm);

                            cartItems.add(cartItem);
                            subCounter++;
                        }
                    }


                }

            } catch (JsonProcessingException e) {
                e.printStackTrace();
                log.error(" exception occur while parsing cart data for workflowId {}", workflowId);
            }

        });
        return cartItems;
    }

    private void prepareCostingForCartItems(CartItems cartItem, InvoiceItemCostVm invoiceItemCostVm) {

        InvoiceItemCostProjection invoiceItemCost = feeMasterRepository.getItemCost(invoiceItemCostVm.getSearchDataTypeId(),
                invoiceItemCostVm.getFormatListItemId(),
                invoiceItemCostVm.getPaperSizeListItemId(),
                invoiceItemCostVm.getSubTypeListItemId(),
                invoiceItemCostVm.getDataTypeListItemId());

        cartItem.setComments(StringUtils.defaultIfBlank("", ""));
        cartItem.setDispatchStatus(0);
        cartItem.setDispatchComment("");
        if (null != invoiceItemCost) {
            cartItem.setTimeRequired(StringUtils.defaultIfBlank(invoiceItemCost.getType(), ""));
            cartItem.setSystemEstimate(invoiceItemCost.getFee());
            cartItem.setFinalCost(invoiceItemCost.getFee());
        }


    }
}
