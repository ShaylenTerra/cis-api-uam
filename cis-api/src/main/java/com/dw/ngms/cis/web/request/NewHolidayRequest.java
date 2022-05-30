package com.dw.ngms.cis.web.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by nirmal on 2020/11/18.
 */
@Data
public class NewHolidayRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "holiday year must not be empty")
	private long holidayYear;

	@NotNull(message = "holiday date must not be empty")
	private String holidayDate;

	private String description;

}
