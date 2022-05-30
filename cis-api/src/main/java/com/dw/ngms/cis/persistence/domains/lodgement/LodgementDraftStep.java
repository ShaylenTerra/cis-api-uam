package com.dw.ngms.cis.persistence.domains.lodgement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LDG_DRAFT_STEPS")
public class LodgementDraftStep {

    @Id
    @GeneratedValue(generator = "ldg_draft_steps_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ldg_draft_steps_seq",sequenceName = "ldg_draft_steps_seq", allocationSize = 1)
    @Column(name = "STEP_ID")
    private Long stepId;

    @Column(name = "STEP_NO")
    private Long stepNo;

    @Column(name = "DOCUMENT_ITEMID")
    private Long documentItemId;

    @Column(name = "PURPOSE_ITEMID")
    private Long purposeItemId;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DRAFT_ID", nullable = false)
    private LodgementDraft lodgementDraft;

    @OneToMany(mappedBy = "lodgementDraftStep",
            fetch=FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy(value = "requestId ASC")
    private Set<LodgementDraftRequest> lodgementDraftRequests;

}
