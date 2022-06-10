package com.dw.ngms.cis.service.examination.dockets;

import com.dw.ngms.cis.persistence.domains.examination.dockets.DiagramDocket;
import com.dw.ngms.cis.persistence.domains.examination.dockets.Docket;
import com.dw.ngms.cis.persistence.repository.examination.dockets.DiagramDocketRepository;
import com.dw.ngms.cis.service.dto.examination.dockets.DiagramDocketDto;
import com.dw.ngms.cis.service.mapper.examination.dockets.DiagramDocketMapper;
import com.dw.ngms.cis.utilities.Messages;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.net.ns.Message;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class DocketService {

    /* Diagram Docket */
   private final DiagramDocketRepository diagramDocketRepository;

   private final DiagramDocketMapper diagramDocketMapper;

   private final Messages messages;

   public DiagramDocketDto getDocketByExaminationId(Long examinationId){
       DiagramDocket diagramDocket = diagramDocketRepository.getDiagramDocketByExaminationId(examinationId);
       DiagramDocketDto diagramDocketDto = diagramDocketMapper.DiagramDocketToDiagramDocketDto(diagramDocket);
       return diagramDocketDto;
   }

   public Docket saveDocket(DiagramDocketDto diagramDocketDto){
       DiagramDocket diagramDocket = diagramDocketMapper.DiagramDocketDtoToDiagramDocket(diagramDocketDto);
       diagramDocketRepository.save(diagramDocket);
       Docket docket = new Docket();
       docket.setType("Diagram");
       docket.setDiagramDocketDto(diagramDocketDto);
       return docket;
   }

   public Docket updateDocket(DiagramDocketDto diagramDocketDto){
       DiagramDocket diagramDocket = diagramDocketRepository.getDiagramDocketByExaminationId(diagramDocketDto.getExaminationId());

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

       diagramDocketRepository.save(diagramDocket);

       Docket docket = new Docket();
       docket.setType("Diagram");
       docket.setDiagramDocketDto(diagramDocketDto);
       return docket;
   }


}
