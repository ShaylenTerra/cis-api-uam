package com.dw.ngms.cis.persistence.domains.examination;

/**
 * @author Shaylen Budhu on 30-05-2022
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="EXAM_ALLOCATED_USERS")
public class ExaminationAllocatedUsers {

    @Id
    @GeneratedValue(generator = "exam_allocated_user_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "exam_allocated_user_seq",sequenceName = "ISEQ$$_101852", allocationSize = 1)
    @Column(name="EXAM_ALLOCATED_ID")
    private Long examAllocatedId;

    @NotNull
    @Column(name="SCRUTINIZER_ID")
    private Long scrutinizerId;

    @NotNull
    @Column(name="EXAMINER_ID")
    private Long examinerId;

    @NotNull
    @Column(name="UPDATEDBY_USERID")
    private Long updatedByUserId;

    @NotNull
    @Column(name="MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @NotNull
    @Column(name="CREATE_DATE",updatable = false)
    private LocalDateTime createDate;

    @NotNull
    @Column(name="COMMENTS")
    private String comments;

    @NotNull
    @Column(name="EXAMID")
    private Long examId;

}
