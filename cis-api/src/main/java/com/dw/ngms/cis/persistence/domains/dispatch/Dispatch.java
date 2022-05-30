package com.dw.ngms.cis.persistence.domains.dispatch;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DISPATCH")
public class Dispatch {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "dispatch_id_Sequence")
    @SequenceGenerator(name = "dispatch_id_Sequence",sequenceName = "dispatch_seq", allocationSize = 1)
    @Column(name = "DISPATCH_ID")
    private Long dispatchId;

    @Column(name = "WORKFLOW_ID")
    private Long workflowId;

    @Column(name = "DISPATCH_DETAILS")
    @Lob
    private String dispatchDetails;

}
