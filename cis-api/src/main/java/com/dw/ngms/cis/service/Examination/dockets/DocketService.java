package com.dw.ngms.cis.service.examination.dockets;

import com.dw.ngms.cis.persistence.domains.examination.dockets.*;
import com.dw.ngms.cis.persistence.domains.user.SecurityUser;
import com.dw.ngms.cis.persistence.repository.examination.dockets.DiagramDocketRepository;
import com.dw.ngms.cis.persistence.repository.examination.dockets.DocketListRepository;
import com.dw.ngms.cis.persistence.repository.examination.dockets.DocketListValueRepository;
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

import javax.print.Doc;
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

    private final DocketListValueRepository docketListValueRepository;

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

   /*public Docket updateDocket(DiagramDocketDto diagramDocketDto){
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
       DiagramDocketDto diagramDocketDtoReturn = diagramDocketMapper.DiagramDocketToDiagramDocketDto(diagramDocket);
       docket.setDiagramDocketDto(diagramDocketDtoReturn);
       return docket;
   }*/
    public Docket updateDocket(Long examId, String examList){
        DiagramDocket diagramDocket = diagramDocketRepository.getDiagramDocketByExaminationId(examId);
        diagramDocket.setExaminerChecklist(examList);
        diagramDocket.setExaminationId(examId);
        diagramDocketRepository.save(diagramDocket);
        Docket docket = new Docket();
        docket.setType("Diagram");
       // docket.setDiagramDocketDto(diagramDocketDto);
        return docket;
    }

   /* Docket List */
    public Stream<DocketListDto> getDocketList(){
         return docketListRepository.findAll().stream().map(docketListMapper::DocketListToDocketListDto);
    }

    public List<DocketList> getDocketByParentList(Long parentId){
        return docketListRepository.getDocketListByParentId(parentId);
    }

    public List<DocketList>getDocketListByType(Long typeId){
        return docketListRepository.getDocketListByTypeId(typeId);
    }

   // public List<DocketList>updateDocketList(String DList){

   // }

  public DocketListValues saveDocketList(Long examinationid,Long typeId){
        String value = generateDocketListString(typeId);
        DocketListValues docketListValues = new DocketListValues();

        docketListValues.setStringvalue(value);
        docketListValues.setExaminationid(examinationid);
        docketListValues.setDocketypeid(typeId);

        docketListValueRepository.save(docketListValues);
       return docketListValues;
  }

  //utility methods

    /*
    This method is used to create a string format of a docketList structure but with empty values, this string can be manipulated.
    This will impact the decorator as the unfinished idea is to create a string that can then be broken down and added to a custom DTO method and returned on Angular.
    The mapper/decorator is where the retrieval of values and the breaking down of the string takes place.
    */

    public String generateDocketListString(Long typeId){
        List<DocketList> list = docketListRepository.getDocketListByTypeId(typeId);
        String ids = "";
        String values = "";
        for(int i = 0 ; i < list.size() ; i++){
            if(list.get(i).getParentId()==0) {
                ids += "{";
                ids += list.get(i).getDocketListId() + ":";
                values += "{";
                values += list.get(i).getDocketListId() + ":";
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(i).getDocketListId() == list.get(j).getParentId()) {
                        ids += list.get(j).getDocketListId() + ",";
                        values += " " + ",";
                    }
                }
                ids = ids.substring(0,ids.length()-1)+ "};";
                values = values.substring(0,values.length()-1) + "};";
            }
        }
        ids = ids.substring(0,ids.length()-1);
        values = values.substring(0,values.length()-1);

        values = ids + "=" + values;
        return values;
    }

}
