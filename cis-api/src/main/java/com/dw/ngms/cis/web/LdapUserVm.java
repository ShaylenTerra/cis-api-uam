package com.dw.ngms.cis.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 28/11/20, Sat
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LdapUserVm {

	@NotNull
	private String username;

	@NotNull
	private String password;

	@NotNull
	private String status;
}
