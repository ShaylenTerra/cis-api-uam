package com.dw.ngms.cis.service;

import com.dw.ngms.cis.persistence.domains.listmanagement.ListItem;
import com.dw.ngms.cis.persistence.domains.listmanagement.ListItemData;
import com.dw.ngms.cis.persistence.domains.user.SecurityUser;
import com.dw.ngms.cis.persistence.projection.ListItemMasterListCode;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemDataRepository;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemRepository;
import com.dw.ngms.cis.security.CurrentLoggedInUser;
import com.dw.ngms.cis.service.dto.listmanagement.ListItemDataDto;
import com.dw.ngms.cis.service.dto.listmanagement.ListItemDto;
import com.dw.ngms.cis.service.mapper.ListItemDataMapper;
import com.dw.ngms.cis.service.mapper.ListItemMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by nirmal on 2020/11/09.
 */
@Service
@Slf4j
@AllArgsConstructor
public class ListItemService {


    private static final Long default_active = 1L;

    private final ListItemRepository listItemRepository;

    private final ListItemMapper listItemMapper;

    private  final ListItemDataRepository   listItemDataRepository;

    private final ListItemDataMapper    listItemDataMapper;

    private final CurrentLoggedInUser currentLoggedInUser;
    /**
     * @param listCode listCode
     * @param pageable {@link Pageable}
     * @return Page<MasterListItemDto>
     */
    public Page<ListItemDto> listItemsByListCode(final Long listCode, final Pageable pageable) {
        return listItemRepository.findAllByListCodeAndIsActive(listCode, 1L, pageable)
                .map(listItemMapper::masterListItemToMasterListItemDto);
    }

    /**
     * @param listItemDto {@link ListItemDto}
     * @return MasterListItemDto
     */
    public ListItemDto createListItem(ListItemDto listItemDto) {
        ListItem listItem = listItemMapper.masterListItemDtoToMasterListItem(listItemDto);
        listItem.setItemCode(generateItemCodeForListCode(listItem.getListCode()));
        listItem.setIsActive(default_active);
        return listItemMapper.masterListItemToMasterListItemDto(listItemRepository
                .save(listItem));
    }

    /**
     *
     * @param listItemDto {@link ListItemDto}
     * @return {@link ListItemDto}
     */
    public ListItemDto updateListItem(final ListItemDto listItemDto) {
        ListItem listItem = listItemMapper.masterListItemDtoToMasterListItem(listItemDto);
        ListItem save = listItemRepository.save(listItem);
        return listItemMapper.masterListItemToMasterListItemDto(save);
    }

    /**
     * @param isActive 0/1
     * @param itemId   itemId
     * @return how many rows get updated
     */
    @Transactional
    public Boolean updateItemListStatus(final Long isActive, final Long itemId) {
        return 1 == listItemRepository.updateStatusByItemId(isActive, itemId);
    }

    /**
     *
     * @param setDefault setDefault
     * @param itemId itemId
     * @return Collection<ListItemDto>
     */
    @Transactional
    public Collection<ListItemDto> updateItemListDefault(final Long setDefault, final Long listCode, final Long itemId) {
        List<ListItem> collect = listItemRepository.findByListCode(listCode).stream().peek(listItem -> {
            if (!listItem.getItemId().equals(itemId)) {
                listItem.setIsDefault(0L);
            } else {
                listItem.setIsDefault(setDefault);
            }

        }).collect(Collectors.toList());

        return listItemRepository.saveAll(collect)
                .stream()
                .map(listItemMapper::masterListItemToMasterListItemDto).collect(Collectors.toList());
    }

    /**
     * private method to generate itemCode from listCode
     *
     * @param listCode list code of listMaster
     * @return itemCode for listMaster
     */
    private String generateItemCodeForListCode(final Long listCode) {
        Optional<ListItemMasterListCode> first = listItemRepository
                .findAllByItemCode(listCode, Sort.by(Sort.Direction.DESC, "itemCode"))
                .stream()
                .findFirst();
        if (first.isPresent()) {

            String[] split = StringUtils.split(first.get().getItemCode(), "0", 2);
            long l = Long.parseLong(split[1]) + 1L;
            String increment = String.format("%03d", l);
            String finalCode = split[0] + increment;
            log.debug(" code generated {} for listCode {}  ", finalCode, listCode);
            return finalCode;
        } else {
            return null;
        }
    }


    /**
     * @param listCode listCode
     * @param pageable {@link Pageable}
     * @return Page<MasterListItemDto>
     */
    public Page<ListItemDataDto> listItemsDataByListCodeAndDataTypeItemId(final Long listCode, final long dataTypeItemId, final Pageable pageable) {
//        List<ListItemDataDto> collectionListItemDataDto = Arrays.asList();
        return listItemRepository.findAllByListCodeAndIsActive(listCode, 1L, pageable)
                .map(listItem -> {
                    ListItemData listItemData = listItemDataRepository.findByItemIdAndDataTypeItemId(listItem.getItemId(),dataTypeItemId);
                    ListItemDataDto listItemDataDto = new ListItemDataDto();
                    listItemDataDto.setListCode(listItem.getListCode());
                    listItemDataDto.setItemCode(listItem.getItemCode());
                    listItemDataDto.setCaption(listItem.getCaption());
                    listItemDataDto.setDescription(listItem.getDescription());
                    listItemDataDto.setIsDefault(listItem.getIsDefault());
                    listItemDataDto.setIsActive(listItem.getIsActive());
                    listItemDataDto.setItemId(listItem.getItemId());
                    listItemDataDto.setDataTypeItemId(dataTypeItemId);
                    if(listItemData!=null){
                        listItemDataDto.setData1(listItemData.getData1());
                        listItemDataDto.setDataId(listItemData.getDataId());
                    }
//                    collectionListItemDataDto.add(listItemDataDto);

                    return listItemDataDto;
                });
    }

    /**
     * @param listItemDataDtos {@link ListItemDto}

     */
    public Collection<ListItemDataDto> saveListItemDataColl(Collection<ListItemDataDto> listItemDataDtos) {
        for (ListItemDataDto listItemDataDto:listItemDataDtos) {
            litsItemDtoListItemPostSave(listItemDataDto);
        }
        return listItemDataDtos;
    }

    /**
     * @param listItemDataDto {@link ListItemDto}

     */
    public ListItemDataDto saveListItemData(ListItemDataDto listItemDataDto) {
        litsItemDtoListItemPostSave(listItemDataDto);
        return listItemDataDto;
    }

    private void litsItemDtoListItemPostSave(ListItemDataDto listItemDataDto) {
        ListItemData listItemData = listItemDataMapper.listItemDataDtoToListItemData(listItemDataDto);
        SecurityUser user = currentLoggedInUser.getUser();
        listItemData.setUserId(user.getUserId());
        listItemData.setTransactionDate(LocalDate.now());
        listItemData = listItemDataRepository.save(listItemData);
        listItemDataDto.setDataId(listItemData.getDataId());
        listItemDataDto.setData1(listItemData.getData1());
    }

}
