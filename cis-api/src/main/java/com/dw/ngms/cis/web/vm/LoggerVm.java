package com.dw.ngms.cis.web.vm;

import ch.qos.logback.classic.Logger;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : prateekgoel
 * @since : 05/12/20, Sat
 **/
@Getter
@Setter
public class LoggerVm {

    private String name;

    private String level;

    public LoggerVm(Logger logger) {
        this.name = logger.getName();
        this.level = logger.getEffectiveLevel().toString();
    }

    public LoggerVm() {
        // Empty public constructor used by Jackson.
    }

    @Override
    public String toString() {
        return "LoggerVM{" +
                "name='" + name + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
