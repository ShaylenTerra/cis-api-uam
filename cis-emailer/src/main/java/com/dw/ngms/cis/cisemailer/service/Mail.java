package com.dw.ngms.cis.cisemailer.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.Collection;
import java.util.List;

/**
 * @author : prateekgoel
 * @since : 27/05/21, Thu
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

    private String subject;
    private String body;
    private String to;
    private String[] cc;
    private boolean isMultipart;
    private boolean isHtml;
    private Collection<Resource> resources;

}
