package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.listmanagement.ListItem;
import com.dw.ngms.cis.service.dto.listmanagement.ListItemDto;
import org.mapstruct.Mapper;

/**
 * @author : prateekgoel
 * @since : 06/12/20, Sun
 **/
@Mapper(componentModel = "spring")
public interface ListItemMapper {

    ListItemDto masterListItemToMasterListItemDto(ListItem listItem);

    ListItem masterListItemDtoToMasterListItem(ListItemDto listItemDto);

}
