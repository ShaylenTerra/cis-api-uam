package com.dw.ngms.cis.persistence.domains;

import com.dw.ngms.cis.enums.OfficeTimingType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by nirmal on 2020/11/18.
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "M_OFFICE_TIMINGS")
public class OfficeTimings {

	@Id
	@Column(name = "TIMINGID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timing_id_sequence")
	@SequenceGenerator(name = "timing_id_sequence", sequenceName = "M_OFFICE_TIMINGS_SEQ", allocationSize = 1)
	private Long officeTimeId;

	@Column(name = "PROVINCEID")
	private Long provinceId;

	@Column(name = "USERID")
	private Long userId;

	@Column(name = "FROMTIME")
	@DateTimeFormat(pattern = "hh:mm a")
	private LocalTime fromTime;

	@Column(name = "TOTIME")
	@DateTimeFormat(pattern = "hh:mm a")
	private LocalTime toTime;

	@Column(name = "FROMDATE")
	private LocalDate fromDate;

	@Column(name = "TODATE")
	private LocalDate toDate;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "OFFICETIMINGTYPE")
	private OfficeTimingType officeTimingType;

	@Column(name = "STATUS")
	private Long isActive;

}
