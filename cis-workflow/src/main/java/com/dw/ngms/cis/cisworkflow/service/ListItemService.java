package com.dw.ngms.cis.cisworkflow.service;

import com.dw.ngms.cis.cisworkflow.persistence.domain.MListItem;
import com.dw.ngms.cis.cisworkflow.persistence.repository.ListItemRepository;
import com.dw.ngms.cis.cisworkflow.utility.WorkflowUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Service
@AllArgsConstructor
public class ListItemService {

    private final ListItemRepository listItemRepository;

    public ResponseEntity<?> addItem(Map<String, Object> requestMatrix) {
        MListItem listitem = new MListItem();
        listitem.setCaption((String) requestMatrix.get("caption"));
        listitem.setIsActive(1L);
        listitem.setItemId((Long) requestMatrix.get("itemId"));
        listitem.setListCode((Long) requestMatrix.get("listCode"));
        listItemRepository.saveAndFlush(listitem);
        return ResponseEntity.ok("success");
    }

    public ResponseEntity<?> performGetListItem(final Long listcode) {
        List<MListItem> clistItemsList = listItemRepository.findByListCode(listcode);
        return ResponseEntity.status(HttpStatus.OK)
                .body(WorkflowUtility.listItemListTolistItemMap(clistItemsList));
    }

}
