package com.dw.ngms.cis.service.workflow;

import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextUserAgent;

import java.io.InputStream;

/**
 * @author : prateekgoel
 * @since : 25/06/21, Fri
 **/
public class CustomTextUserAgent extends ITextUserAgent {

    public CustomTextUserAgent(ITextOutputDevice outputDevice) {
        super(outputDevice);
    }

    @Override
    protected InputStream resolveAndOpenStream(String uri) {
        if (uri.startsWith("file:")) {
            String path = uri.substring("file:".length());

            InputStream is = getClass().getClassLoader().getResourceAsStream(String.format("reports%s", path));
            if (is != null) {
                return is;
            }
        }
        return super.resolveAndOpenStream(uri);
    }
}
