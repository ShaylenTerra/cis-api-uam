package com.dw.ngms.cis.service.mapper.examination.dockets;

import com.dw.ngms.cis.persistence.domains.examination.dockets.DocketList;
import com.dw.ngms.cis.service.dto.examination.dockets.DocketListDto;

public class DocketListDecorator implements DocketListMapper{

    @Override
    public DocketList DocketListDtoToDocketList(DocketListDto docketListDto) {
        DocketList docketList = new DocketList();

        docketList.setItemCode(docketListDto.getItemCode());
        docketList.setName(docketListDto.getName());
        docketList.setDescription(docketListDto.getDescription());
        docketList.setParentId(docketListDto.getParentId());
        docketList.setDocketTypeId(docketListDto.getDocketTypeId());
        docketList.setStatus(docketListDto.getStatus());
        docketList.setValue(docketListDto.getValue());

        return docketList;
    }

    @Override
    public DocketListDto DocketListToDocketListDto(DocketList docketList) {
        DocketListDto docketListDto = new DocketListDto();

        docketListDto.setItemCode(docketList.getItemCode());
        docketListDto.setName(docketList.getName());
        docketListDto.setDescription(docketList.getDescription());
        docketListDto.setParentId(docketList.getParentId());
        docketListDto.setDocketTypeId(docketList.getDocketTypeId());
        docketListDto.setStatus(docketList.getStatus());
        docketListDto.setValue(docketList.getValue());

        return docketListDto;
    }

}
