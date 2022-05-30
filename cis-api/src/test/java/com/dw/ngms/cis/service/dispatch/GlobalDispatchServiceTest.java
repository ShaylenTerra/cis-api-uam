package com.dw.ngms.cis.service.dispatch;

import com.dw.ngms.cis.service.dto.dispatch.DispatchDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GlobalDispatchServiceTest {

    @Autowired
    private GlobalDispatchService globalDispatchService;


    @Test
    void saveDispatch() {
        DispatchDto dispatchDto =  new DispatchDto();
        dispatchDto.setDispatchDetails("test");
        dispatchDto.setWorkflowId(2L);
        DispatchDto response = globalDispatchService.saveDispatch(dispatchDto);
        assertNotNull(response);

    }

     @Test
    void getDispatchTest_success() {
        DispatchDto response = globalDispatchService.getDispatch(1L);
        assertNotNull(response);
    }

    @Test
    void getDispatchTest_noData() {
        DispatchDto response = globalDispatchService.getDispatch(788888888L);
        Assertions.assertNull(response);
    }

    @Test
    void updateDispatch() {
        DispatchDto dispatchDto = new DispatchDto();
        dispatchDto.setDispatchId(1L);
        dispatchDto.setWorkflowId(3L);
        dispatchDto.setDispatchDetails("updated");
        DispatchDto response = globalDispatchService.updateDispatch(dispatchDto);
        assertNotNull(response.getDispatchDetails().equals("updated"));
    }

    @Test
    void deleteDispatch() {
        globalDispatchService.deleteDispatch(3L);
        DispatchDto response = globalDispatchService.getDispatch(3L);
        Assertions.assertNull(response);
    }
}