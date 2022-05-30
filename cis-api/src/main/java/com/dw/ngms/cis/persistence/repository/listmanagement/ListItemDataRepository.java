package com.dw.ngms.cis.persistence.repository.listmanagement;

import com.dw.ngms.cis.persistence.domains.listmanagement.ListItemData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : pragayanshu
 * @since : 2021/12/20, Mon
 **/
@Repository
public interface ListItemDataRepository extends JpaRepository<ListItemData, Long> {
    ListItemData findByItemIdAndDataTypeItemId (final Long itemId, final Long dataTypeItemId);

    ListItemData findByItemId(final Long reasonItemId);
}
