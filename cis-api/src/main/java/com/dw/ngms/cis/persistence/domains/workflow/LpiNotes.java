package com.dw.ngms.cis.persistence.domains.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LPI_NOTES")
public class LpiNotes {

    @Id
    @GeneratedValue(generator = "lpi_notes_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "lpi_notes_seq", sequenceName = "LPI_NOTES_SEQ", allocationSize = 1)
    @Column(name = "NOTEID")
    private Long noteId;

    @Column(name = "LPI")
    private String lpi;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "NOTETYPE")
    private Long noteType;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "DATED")
    private LocalDateTime dated;


}
