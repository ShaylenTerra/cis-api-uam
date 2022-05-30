package com.dw.ngms.cis.persistence.repository.cart;

import com.dw.ngms.cis.persistence.domains.cart.CartItemsDispatchDocs;
import com.dw.ngms.cis.persistence.projection.cart.CartDispatchDocView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 11/02/21, Thu
 **/
@Repository
public interface CartItemsDispatchDocsRepository extends JpaRepository<CartItemsDispatchDocs, Long> {

    @Query(value = "SELECT COUNT(*) as noOfDocument, SUM(WD.SIZE_KB) as sizeInKb FROM CART_ITEMS_DISPATCH_DOCS CIDD\n" +
            "    JOIN WORKFLOW_DOCUMENTS WD on CIDD.document_id = WD.DOCUMENTID\n" +
            "WHERE CIDD.WORKFLOW_ID=:workflowId", nativeQuery = true)
    CartDispatchDocView getCartDispatchDocuments(final Long workflowId);

    Collection<CartItemsDispatchDocs> findCartItemsDispatchDocsByWorkflowId(final Long workflowId);

}
