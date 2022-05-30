package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.listmanagement.ListItemData;
import com.dw.ngms.cis.service.dto.listmanagement.ListItemDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author : pragayanshu
 * @since : 22/12/21, Sun
 **/
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ListItemDataMapper {

    ListItemDataDto listItemDataToListItemDataDto(ListItemData listItemData);

    ListItemData listItemDataDtoToListItemData(ListItemDataDto listItemDataDto);

    List<ListItemDataDto> collectionListItemDataToCollectionListItemDataDto(List<ListItemData> listItemData);

    List<ListItemData> collectionListItemDataDtoToCollectionListItemData(List<ListItemDataDto> listItemDataDtos);
}
