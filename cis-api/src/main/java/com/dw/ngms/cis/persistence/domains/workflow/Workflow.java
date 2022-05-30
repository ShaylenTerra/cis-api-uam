package com.dw.ngms.cis.persistence.domains.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "WORKFLOW")
public class Workflow {

	@Id
	@Column(name = "WORKFLOWID")
	@GeneratedValue(generator = "workflow_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "workflow_seq", sequenceName = "WORKFLOW_SEQ", allocationSize = 1)
	private Long workflowId;

	@Column(name = "PROCESSID")
	private Long processId;

	@Column(name = "TRIGGEREDON")
	private Date triggeredOn;

	@Column(name = "PROCESSDATA")
	private String processData;

	@Column(name = "STATUSID")
	private Long statusId;

	@Column(name = "REFERENCE_NO")
	private String referenceNo;

	@Column(name = "TRANSACTIONID")
	private Long transactionId;

	@Column(name = "PARENT_WORKFLOWID")
	private Long parentWorkflowId;

	@Column(name = "COMPLETEDON")
	private Date completedOn;

	@Column(name = "PROVINCEID")
	private Long provinceId;

	@Column(name = "INTERNAL_STATUS_ID")
	private Long internalStatusId;

	@Column(name = "EXTERNAL_STATUS_ID")
	private Long externalStatusId;

	@Column(name = "TRUNAROUND_DURATION")
	private Long turnAroundDuration;

	@Column(name = "PRIORITY_FLAG")
	private Long priorityFlag;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "WORKFLOWID")
	private Set<WorkflowAction> workflowAction;
}
