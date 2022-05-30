package com.dw.ngms.cis.service.report;

import com.dw.ngms.cis.web.vm.report.ReportingVm;
import org.springframework.core.io.Resource;

/**
 * @author : prateekgoel
 * @since : 15/06/21, Tue
 **/
public interface GenericReport {

    String DATA = "data";

    String REQUEST = "request";

    String COLUMNS = "columns";

    Resource generateReport(ReportingVm reportingVm);

}
