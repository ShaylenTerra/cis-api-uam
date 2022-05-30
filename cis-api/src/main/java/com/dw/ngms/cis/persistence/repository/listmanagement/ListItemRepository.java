package com.dw.ngms.cis.persistence.repository.listmanagement;

import com.dw.ngms.cis.persistence.domains.listmanagement.ListItem;
import com.dw.ngms.cis.persistence.domains.listmanagement.ListMapping;
import com.dw.ngms.cis.persistence.projection.ListItemMasterListCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by nirmal on 2020/11/09.
 */
@Repository
public interface ListItemRepository extends JpaRepository<ListItem, Long> {

    List<ListItem> findAll(Specification<ListItem> specification);

    Page<ListItem> findAllByListCodeAndIsActive(final Long listCode, final Long isActive, final Pageable pageable);

    @Query("select mli.itemCode as itemCode from ListItem mli where mli.listCode =:listCode ")
    List<ListItemMasterListCode> findAllByItemCode(final Long listCode, Sort order);

    @Modifying
    @Query(" update ListItem MLI set MLI.isActive =:isActive where MLI.itemId = :itemId")
    int updateStatusByItemId(final Long isActive, final Long itemId);

    @Modifying
    @Query(" update ListItem MLI set MLI.isDefault =:setDefault where MLI.itemId = :itemId")
    int updateStatusByIsDefault(final Long setDefault, final Long itemId);

    ListItem findByItemId(Long itemID);

    Collection<ListItem> findByListCode(final Long listCode);

    @Query(value = "Select itm.* \n" +
            "from M_LIST_MAPPING M\n" +
            "         inner join M_LIST_ITEM itm on M.CHILD_ITEMID = itm.ITEMID\n" +
            "where M.ISACTIVE = 1\n" +
            "  and M.PARENT_ITEMID = :parentItemId\n" +
            "  and M.CHILD_LISTCODE = :lisCode", nativeQuery = true)
    Collection<ListItem> getListItemByParentItemIdAndListCode(final Long parentItemId, final Long lisCode);
}
