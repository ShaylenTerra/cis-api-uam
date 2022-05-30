package com.dw.ngms.cis.persistence.projection.lodgment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author prateek on 20-04-2022
 */
public interface LodgementDocumentSummary {

     Long getDocumentItemId();

     Integer getCount();

     String getDocumentType();

     String getPurposeType();

     Long getPurposeItemId();

}
