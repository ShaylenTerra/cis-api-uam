package com.dw.ngms.cis.persistence.domains.examination.dockets;

import com.dw.ngms.cis.service.dto.examination.dockets.DiagramDocketDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Docket {

    private String type;
    private DiagramDocketDto diagramDocketDto;

}
