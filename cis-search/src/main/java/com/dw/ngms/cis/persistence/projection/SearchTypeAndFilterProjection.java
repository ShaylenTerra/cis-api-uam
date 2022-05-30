package com.dw.ngms.cis.persistence.projection;

public interface SearchTypeAndFilterProjection {

	Long getProvinceId();

	String getUserType();

	Long getSearchTypeId();

	String getName();

	String getTag();

	Long getParentSearchTypeId();

	String getDescription();

	String getControlType();

	Long getIsActive();

	Long getTemplateListItemId();

	Long getConfig();

}
