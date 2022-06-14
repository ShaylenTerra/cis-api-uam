package com.dw.ngms.cis.persistence.repository.examination.dockets;

import com.dw.ngms.cis.persistence.domains.examination.dockets.DocketList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocketListRepository extends JpaRepository<DocketList,Long> {

    @Query("SELECT D FROM DocketList D WHERE D.parentId=?1")
    List<DocketList> getDocketListByParentId(Long parentId);
}
