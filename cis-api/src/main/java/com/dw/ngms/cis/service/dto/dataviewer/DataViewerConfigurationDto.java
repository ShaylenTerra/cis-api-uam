package com.dw.ngms.cis.service.dto.dataviewer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : prateekgoel
 * @since : 18/03/21, Thu
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataViewerConfigurationDto {

    private String objectName;

    private String description;

}
