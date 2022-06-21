package com.dw.ngms.cis.persistence.repository.examination.dockets;

/**
 * @author Shaylen Budhu on 08-06-2022
 */

import com.dw.ngms.cis.persistence.domains.examination.dockets.DiagramDocket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiagramDocketRepository extends JpaRepository<DiagramDocket,Long> {

    @Query("SELECT D FROM DiagramDocket D WHERE D.examinationId=?1")
    DiagramDocket getDiagramDocketByExaminationId(Long examinationId);

    @Query("SELECT D FROM DiagramDocket D WHERE D.diagramId=?1")
    DiagramDocket getDiagramDocketByDiagramId(Long diagramId);




}
