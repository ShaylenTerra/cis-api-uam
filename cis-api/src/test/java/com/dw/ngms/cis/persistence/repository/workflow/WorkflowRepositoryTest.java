package com.dw.ngms.cis.persistence.repository.workflow;

import com.dw.ngms.cis.persistence.projection.DashboardRequestProjection;
import com.dw.ngms.cis.persistence.projection.InboxProjection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author : prateekgoel
 * @since : 22/12/20, Tue
 **/
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WorkflowRepositoryTest {

    @Autowired
    private WorkflowRepository workflowRepository;

    @Test
    public void loadInbox() {
        Page<InboxProjection> inboxProjections = workflowRepository.loadInboxForNationalAdmin(PageRequest.of(0, 2));
        Assertions.assertThat(inboxProjections.getContent()).hasSize(2);
    }

    @Test
    public void loadDashboardRequest() {
        Page<DashboardRequestProjection> dashboardRequestProjections = workflowRepository.loadRequestForUser(0L, PageRequest.of(0, 2));
        Assertions.assertThat(dashboardRequestProjections.getContent()).hasSize(2);
    }


}