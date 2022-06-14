package com.dw.ngms.cis.service.examination.dockets;

import com.dw.ngms.cis.persistence.domains.examination.dockets.DiagramDocket;
import com.dw.ngms.cis.persistence.domains.examination.dockets.Docket;
import com.dw.ngms.cis.persistence.domains.examination.dockets.DocketList;
import com.dw.ngms.cis.persistence.domains.examination.dockets.MasterDocket;
import com.dw.ngms.cis.persistence.domains.user.SecurityUser;
import com.dw.ngms.cis.persistence.repository.examination.dockets.DiagramDocketRepository;
import com.dw.ngms.cis.persistence.repository.examination.dockets.DocketListRepository;
import com.dw.ngms.cis.persistence.repository.examination.dockets.MasterDocketRepository;
import com.dw.ngms.cis.security.CurrentLoggedInUser;
import com.dw.ngms.cis.service.dto.examination.dockets.DiagramDocketDto;
import com.dw.ngms.cis.service.dto.examination.dockets.DocketListDto;
import com.dw.ngms.cis.service.dto.examination.dockets.MasterDocketDto;
import com.dw.ngms.cis.service.mapper.examination.dockets.DiagramDocketMapper;
import com.dw.ngms.cis.service.mapper.examination.dockets.DocketListMapper;
import com.dw.ngms.cis.service.mapper.examination.dockets.MasterDocketMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class DocketService {

    private final MasterDocketRepository masterDocketRepository;

    private final CurrentLoggedInUser currentLoggedInUser;

    private final MasterDocketMapper masterDocketMapper ;

    private final DiagramDocketRepository diagramDocketRepository;

    private final DiagramDocketMapper diagramDocketMapper;

    private final DocketListRepository docketListRepository;

    private final DocketListMapper docketListMapper;


   /* Master Docket */
   public Page<MasterDocketDto> getAllMasterDocket(final Pageable pageable){
       SecurityUser user = currentLoggedInUser.getUser();
       Page<MasterDocket> masterDockets = masterDocketRepository.findAll(pageable);
       return masterDockets.map(masterDocketMapper::masterDocketToMasterDocketDto);

   }

    public MasterDocketDto getMasterDocketById(final long docketId)
    {
        MasterDocket masterDocket= masterDocketRepository.findById(docketId).get();
        return masterDocketMapper.masterDocketToMasterDocketDto(masterDocket);
    }

   /* Docket information */
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

   /* Docket List */

    public Stream<DocketListDto> getDocketList(){
         return docketListRepository.findAll().stream().map(docketListMapper::DocketListToDocketListDto);
    }

    public List<DocketList> getDocketByParentList(Long parentId){
        //DocketListDto docketListDto =
        return docketListRepository.getDocketListByParentId(parentId);
    }

}
