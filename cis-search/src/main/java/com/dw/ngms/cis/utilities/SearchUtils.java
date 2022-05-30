package com.dw.ngms.cis.utilities;

import org.apache.commons.lang3.StringUtils;

/**
 * @author : prateekgoel
 * @since : 10/05/21, Mon
 **/
public final class SearchUtils {

    public static String padStringWithZero(final String str, final Integer size) {
        return StringUtils.leftPad(str, size, "0");
    }

}
