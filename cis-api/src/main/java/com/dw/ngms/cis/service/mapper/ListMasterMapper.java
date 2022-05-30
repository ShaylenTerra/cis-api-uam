package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.listmanagement.ListMaster;
import com.dw.ngms.cis.service.dto.listmanagement.ListMasterDto;
import org.mapstruct.Mapper;

/**
 * @author : prateekgoel
 * @since : 06/12/20, Sun
 **/
@Mapper(componentModel = "spring")
public interface ListMasterMapper {

    ListMasterDto managementListMasterToManagementListMasterDto(ListMaster listMaster);

    ListMaster managementListMasterDtoToManagementListMaster(ListMasterDto listMasterDto);

}
