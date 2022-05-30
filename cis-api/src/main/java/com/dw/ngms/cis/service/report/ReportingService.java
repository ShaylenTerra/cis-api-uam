package com.dw.ngms.cis.service.report;

import com.dw.ngms.cis.enums.ReportType;
import com.dw.ngms.cis.persistence.domains.user.SecurityUser;
import com.dw.ngms.cis.persistence.domains.user.UserRole;
import com.dw.ngms.cis.persistence.repository.report.ReportsRepository;
import com.dw.ngms.cis.security.CurrentLoggedInUser;
import com.dw.ngms.cis.service.dto.report.ReportsDto;
import com.dw.ngms.cis.service.mapper.ReportsMapper;
import com.dw.ngms.cis.web.vm.report.ReportingVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 06/02/21, Sat
 **/
@Service
@Slf4j
@AllArgsConstructor
public class ReportingService {

    private final ProductivityReportGenerator productivityReportGenerator;

    private final ReportsRepository reportsRepository;

    private final CurrentLoggedInUser currentLoggedInUser;

    private final ReportsMapper reportsMapper;

    private final GenericReportFactory genericReportFactory;

    /**
     * @param reportsModuleId reportsModuleId
     * @return Collection<ReportsDto>
     */
    public Collection<ReportsDto> getAllReportsBasedOnReportModuleId(final Long reportsModuleId) {
        final SecurityUser user = currentLoggedInUser.getUser();
        if (null != user) {
            final Set<Long> roleIds = user.getUserRoles().stream().map(UserRole::getRoleId).collect(Collectors.toSet());
            return reportsRepository.getAllReportsByRoleId(roleIds, reportsModuleId)
                    .stream()
                    .map(reportsMapper::reportToReportDto)
                    .collect(Collectors.toList());
        }
        return null;
    }

    /**
     * @param reportingVm {@link ReportingVm}
     * @return {@link FileSystemResource}
     */
    public Resource getReport(final ReportingVm reportingVm) {
        final GenericReportFactory genericReportFactory = this.genericReportFactory.buildFactory();
        final GenericReport report = genericReportFactory.getReport(ReportType.of(reportingVm.getReportId()))
                .orElseThrow(() -> new IllegalArgumentException("Wrong Report Type"));

        return report.generateReport(reportingVm);
    }

    @Scheduled(cron = "0 0 0 * * *")
    @SchedulerLock(name = "ReportingService_generateProductivityReport",
            lockAtLeastFor = "PT5M", lockAtMostFor = "PT14M")
    public void generateProductivityReport() {
        productivityReportGenerator.generateWorkflowProductivity();
    }
}
