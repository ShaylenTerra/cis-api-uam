package com.dw.ngms.cis.persistence.repository.examination.dockets;

import com.dw.ngms.cis.persistence.domains.examination.dockets.MasterDocket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterDocketRepository extends JpaRepository<MasterDocket, Long> {

    @Query("SELECT COUNT(BATCH_NO) FROM MasterDocket WHERE name=?1")
    int CheckDocket(String Exam_MasterDocket);

}
