package com.dw.ngms.cis.service;

import com.lowagie.text.Image;
import com.lowagie.text.pdf.Barcode128;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextReplacedElementFactory;
import org.xhtmlrenderer.render.BlockBox;

import java.awt.*;

/**
 * @author : prateekgoel
 * @since : 26/06/21, Sat
 **/
public class BarcodeReplacedElementFactory extends ITextReplacedElementFactory {


    public BarcodeReplacedElementFactory(ITextOutputDevice outputDevice) {
        super(outputDevice);
    }

    @Override
    public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box,
                                                 UserAgentCallback uac, int cssWidth, int cssHeight) {

        Element e = box.getElement();
        if (e == null) {
            return null;
        }

        String nodeName = e.getNodeName();
        if (nodeName.equals("img")) {
            if ("code128".equals(e.getAttribute("data-type"))) {
                try {
                    Barcode128 code = new Barcode128();
                    code.setCode(e.getAttribute("src"));
                    FSImage fsImage = new ITextFSImage(Image.getInstance(
                            code.createAwtImage(Color.BLACK, Color.WHITE),
                            Color.WHITE));
                    if (cssWidth != -1 || cssHeight != -1) {
                        fsImage.scale(cssWidth, cssHeight);
                    }
                    return new ITextImageElement(fsImage);
                } catch (Throwable e1) {
                    return null;
                }
            }
        }

        return super.createReplacedElement(c, box, uac, cssWidth, cssHeight);
    }

}
