package com.dw.ngms.cis.persistence.repository.document;

import com.dw.ngms.cis.enums.UserDocumentType;
import com.dw.ngms.cis.persistence.domains.document.UserDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserDocumentRepository extends JpaRepository<UserDocument, Long> {

    long countByContextTypeIdAndContextId(final Long contextTypeId, final Long contextId);

    UserDocument findByContextTypeIdAndContextId(final Long contextTypeId, final Long contextId);

    Collection<UserDocument> findUserDocumentByUserIdAndDocumentTypeIdAndIsActive(final Long userId,
                                                                                  final UserDocumentType documentTypeId,
                                                                                  final Long isActive);

    UserDocument findByDocumentId(final Long id);

}

