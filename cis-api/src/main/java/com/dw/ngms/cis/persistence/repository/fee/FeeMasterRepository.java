package com.dw.ngms.cis.persistence.repository.fee;

import com.dw.ngms.cis.persistence.domains.fee.FeeMaster;
import com.dw.ngms.cis.persistence.projection.fee.InvoiceItemCostProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Repository
public interface FeeMasterRepository extends JpaRepository<FeeMaster, Long> {

    FeeMaster findByFeeSubCategoryIdAndFeeScaledId(final Long subCategoryId, final Long feeScaleId);


    @Query(value = "SELECT\n" +
            "    FC.NAME as categoryName,\n" +
            "    FSC.NAME as subCategoryName,\n" +
            "    FSC.DESCRIPTION as subCategoryDescription,\n" +
            "    FM.FEE as fee,\n" +
            "    ft.Type as type\n" +
            "from FEE_SCALE_MAP FSM inner join FEE_SUBCATEGORY FSC on FSC.SUBCATID = FSM.FEE_SUBCATID\n" +
            "                      inner join FEE_MASTER  FM on FM.SUBCATEGORYID = FSC.SUBCATID\n" +
            "                      inner join FEE_TYPE FT on FT.TYPEID = FM.TYPEID\n" +
            "                      inner join FEE_CATEGORY FC on FC.CATID = FSC.CATEGORYID\n" +
            "where FM.FEESCHEDULEID = 1\n" +
            "  and FSM.SEARCHDATATYPEID = :searchDataTypeId \n" +
            "  and FSM.FORMAT_LIST_ITEMID = :formatListItemId \n" +
            "  and FSM.PAPERSIZE_LIST_ITEMID = :paperSizeListItemId \n" +
            "  and FSM.SUB_TYPE_LIST_ITEMID = :subTypeListItemId \n" +
            "  and FSM.DATATYPE_LIST_ITEMID = :dataTypeListItemId", nativeQuery = true)
    InvoiceItemCostProjection getItemCost(final Long searchDataTypeId,
                                          final Long formatListItemId,
                                          final Long paperSizeListItemId,
                                          final Long subTypeListItemId,
                                          final Long dataTypeListItemId);


    @Query(value = "SELECT\n" +
            "    FC.NAME as categoryName,\n" +
            "    FSC.NAME as subCategoryName,\n" +
            "    FSC.DESCRIPTION as subCategoryDescription,\n" +
            "    FM.FEE as fee,\n" +
            "    ft.Type as type\n" +
            "from                 FEE_SUBCATEGORY FSC\n" +
            "                      inner join FEE_MASTER  FM on FM.SUBCATEGORYID = FSC.SUBCATID\n" +
            "                      inner join FEE_TYPE FT on FT.TYPEID = FM.TYPEID\n" +
            "                      inner join FEE_CATEGORY FC on FC.CATID = FSC.CATEGORYID\n" +
            "where FM.FEESCHEDULEID = 1\n" +
            "and FSC.SUBCATID = :feeSubCategoryId", nativeQuery = true)
    InvoiceItemCostProjection getLodegementItemCost(final Long feeSubCategoryId);

}
