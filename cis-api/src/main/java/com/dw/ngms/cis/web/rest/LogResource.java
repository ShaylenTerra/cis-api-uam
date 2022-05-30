package com.dw.ngms.cis.web.rest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.vm.LoggerVm;
import io.micrometer.core.annotation.Timed;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 05/12/20, Sat
 **/
@RestController
@BaseResponse
@RequestMapping(AppConstants.API_BASE_MAPPING + "/management")
public class LogResource {


    @GetMapping("/logs")
    @Timed
    public List<LoggerVm> getList() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        return context.getLoggerList()
                .stream()
                .map(LoggerVm::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/logs")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Timed
    public void changeLevel(@RequestBody LoggerVm jsonLogger) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getLogger(jsonLogger.getName()).setLevel(Level.valueOf(jsonLogger.getLevel()));
    }
}
