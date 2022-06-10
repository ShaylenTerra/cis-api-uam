package com.dw.ngms.cis.service.mapper.examination.dockets;
/**
 * @author Shaylen Budhu on 08-06-2022
 */

import com.dw.ngms.cis.persistence.domains.examination.dockets.DiagramDocket;
import com.dw.ngms.cis.service.dto.examination.dockets.DiagramDocketDto;

public class DiagramDocketDecorator implements DiagramDocketMapper{

    @Override
    public DiagramDocketDto DiagramDocketToDiagramDocketDto(DiagramDocket diagramDocket) {
        DiagramDocketDto diagramDocketDto = new DiagramDocketDto();

        diagramDocketDto.setExaminerName(diagramDocket.getExaminerName());
        diagramDocketDto.setScrutinizerName(diagramDocket.getScrutinizerName());
        diagramDocketDto.setPaName(diagramDocket.getPaName());
        diagramDocketDto.setExaminerChecklist(diagramDocket.getExaminerChecklist());
        diagramDocketDto.setScrutinizerChecklist(diagramDocket.getScrutinizerChecklist());
        diagramDocketDto.setPaCheckList(diagramDocket.getPaCheckList());
        diagramDocketDto.setDesignationNumbering(diagramDocket.getDesignationNumbering());
        diagramDocketDto.setComputing(diagramDocket.getComputing());
        diagramDocketDto.setRegistryDispatch(diagramDocket.getRegistryDispatch());
        diagramDocketDto.setPostApproval(diagramDocket.getPostApproval());
        diagramDocketDto.setStatus(diagramDocket.getStatus());
        diagramDocketDto.setExaminationId(diagramDocket.getExaminationId());

        return diagramDocketDto;
    }

    @Override
    public DiagramDocket DiagramDocketDtoToDiagramDocket(DiagramDocketDto diagramDocketDto) {
        DiagramDocket diagramDocket = new DiagramDocket();

        diagramDocket.setExaminerName(diagramDocketDto.getExaminerName());
        diagramDocket.setScrutinizerName(diagramDocketDto.getScrutinizerName());
        diagramDocket.setPaName(diagramDocketDto.getPaName());
        diagramDocket.setExaminerChecklist(diagramDocketDto.getExaminerChecklist());
        diagramDocket.setScrutinizerChecklist(diagramDocketDto.getScrutinizerChecklist());
        diagramDocket.setPaCheckList(diagramDocketDto.getPaCheckList());
        diagramDocket.setDesignationNumbering(diagramDocketDto.getDesignationNumbering());
        diagramDocket.setComputing(diagramDocketDto.getComputing());
        diagramDocket.setRegistryDispatch(diagramDocketDto.getRegistryDispatch());
        diagramDocket.setPostApproval(diagramDocketDto.getPostApproval());
        diagramDocket.setStatus(diagramDocketDto.getStatus());
        diagramDocket.setExaminationId(diagramDocketDto.getExaminationId());

        return diagramDocket;
    }
}
