package com.dw.ngms.cis.mappers;

import com.dw.ngms.cis.configuration.SearchProperties;
import com.dw.ngms.cis.dto.SearchDocumentDto;
import com.dw.ngms.cis.persistence.domain.SgdataDocuments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author : prateekgoel
 * @since : 11/05/21, Tue
 **/
public abstract class SearchDocumentMapperDecorator implements SearchDocumentMapper {

    @Autowired
    @Qualifier("delegate")
    private SearchDocumentMapper delegate;

    @Autowired
    private SearchProperties searchProperties;

    @Override
    public SearchDocumentDto sgdataDocumentToSearchDocumentDto(SgdataDocuments sgdataDocuments) {
        final SearchDocumentDto searchDocumentDto = delegate.sgdataDocumentToSearchDocumentDto(sgdataDocuments);
        final SearchProperties.ImagePrefixPath imagePrefixPath = searchProperties.getImagePrefixPath();
        searchDocumentDto.setPreview(imagePrefixPath.getPreviewPrefix() + sgdataDocuments.getPreview());
        searchDocumentDto.setThumbnail(imagePrefixPath.getThumbnailPrefix() + sgdataDocuments.getThumbnail());
        searchDocumentDto.setUrl(imagePrefixPath.getUrlPrefix() + sgdataDocuments.getUrl());
        return searchDocumentDto;
    }
}
