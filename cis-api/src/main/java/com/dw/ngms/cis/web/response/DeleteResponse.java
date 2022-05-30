package com.dw.ngms.cis.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : prateekgoel
 * @since : 21/12/20, Mon
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteResponse {

    public Boolean isDeleted;
}
