package com.dw.ngms.cis.persistence.domains;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by nirmal on 2020/11/18.
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "M_HOLIDAYCALENDAR")
public class HolidayCalendar {

	@Id
	@Column(name = "HOLIDAYID", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "holiday_id_sequence")
	@SequenceGenerator(name = "holiday_id_sequence", sequenceName = "HOLIDAY_SEQ", allocationSize = 1)
	private Long holidayId;

	@Temporal(TemporalType.DATE)
	@Column(name = "HOLIDAY_DATE", nullable = false)
	private Date holidayDate = new Date();

	@Column(name = "DESCRIPTION", nullable = true, length = 4000)
	private String description;

	@Column(name = "STATUS", nullable = true)
	private Integer status;

}
