package com.dw.ngms.cis.enums;

/**
 * @author : prateekgoel
 * @since : 01/06/21, Tue
 **/
public enum DocumentType {
    POP(133L),
    GENERAL(134L),
    INVOICE(135L),
    DISPATCH(246L);


    DocumentType(Long documentType) {
        this.documentType = documentType;
    }

    private Long documentType;

    public Long getDocumentType(){
        return this.documentType;
    }

}
