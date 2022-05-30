package com.dw.ngms.cis.cisworkflow.request;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Data
public class NodeActionBits {

    private int action;

    private int nextNode;

    private String xref;

    private int blank;

    private int internalStatus;

    private int externalStatus;

    private int templateId;
}
