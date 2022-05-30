package com.dw.ngms.cis.persistence.domains.lodgement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LDG_DRAFT_ANNEXURE")
public class LodgementDraftAnnexure {

    @Id
    @GeneratedValue(generator = "ldg_draft_annexure_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ldg_draft_annexure_seq",sequenceName = "ldg_draft_annexure_seq", allocationSize = 1)
    @Column(name = "ANNEXURE_ID")
    private Long annexureId;


    @Column(name = "TYPE_IITEMD")
    private Long typeItemId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "DATED")
    private LocalDateTime dated;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DRAFT_ID", nullable = false)
    private LodgementDraft lodgementDraft;

}
