package com.dw.ngms.cis.service.workflow;

import com.dw.ngms.cis.enums.SystemConfigurationType;
import com.dw.ngms.cis.persistence.domains.Payment;
import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.domains.cart.Cart;
import com.dw.ngms.cis.persistence.domains.cart.CartItems;
import com.dw.ngms.cis.persistence.domains.cart.Requester;
import com.dw.ngms.cis.persistence.domains.system.SystemConfiguration;
import com.dw.ngms.cis.persistence.domains.workflow.Workflow;
import com.dw.ngms.cis.persistence.repository.PaymentRepository;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.repository.cart.CartItemRepository;
import com.dw.ngms.cis.persistence.repository.cart.CartRepository;
import com.dw.ngms.cis.persistence.repository.system.SystemConfigurationRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowRepository;
import com.dw.ngms.cis.service.BarcodeReplacedElementFactory;
import com.dw.ngms.cis.utilities.CartUtils;
import com.dw.ngms.cis.utilities.FileUtils;
import com.dw.ngms.cis.utilities.TemplateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 23/01/21, Sat
 **/
@Service
@Slf4j
@AllArgsConstructor
public class PdfGeneratorService {


    private static final String DATA = "data";

    private static final String REQUESTER_INFO = "requester";

    private static final String REQUESTER_ADDRESS = "address";

    private static final String PROVINCE = "province";

    private static final String WORKFLOW_DATA = "workflow";

    private static final String PAYMENT = "payment";


    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProvinceRepository provinceRepository;

    private final SpringTemplateEngine templateEngine;

    private final WorkflowRepository workflowRepository;

    private final PaymentRepository paymentRepository;

    private final ObjectMapper mapper;

    private final SystemConfigurationRepository systemConfigurationRepository;

    private final CartUtils cartUtils;

    /**
     * @param workflowId workflowId
     * @return path of the file generated
     */
    public String generatePdf(final Long workflowId, final Boolean isFinalVersion) {

        Collection<CartItems> cartItems = cartItemRepository.findAllByWorkflowIdOrderBySno(workflowId);
        Cart byWorkflowId = cartRepository.findByWorkflowId(workflowId);

        Context context = new Context();

        try {

            final Requester requesterDetails = cartUtils.getRequesterDetails(workflowId);

            context.setVariable("isFinalVersion", isFinalVersion);
            context.setVariable(REQUESTER_INFO, requesterDetails);


            Workflow workflow = workflowRepository.findByWorkflowId(byWorkflowId.getWorkflowId());

            if (null != workflow) {
                context.setVariable(WORKFLOW_DATA, workflow);
            }

            Province province = provinceRepository.findByProvinceId(byWorkflowId.getProvinceId());

            if (null != province) {

                context.setVariable(PROVINCE, province);

            }

            if (null != cartItems && cartItems.size() > 0) {

                context.setVariable(DATA, cartItems);

            }

            Payment payment = paymentRepository.findByWorkflowId(workflowId);

            if (null != payment) {

                context.setVariable(PAYMENT, payment);

            }

            final SystemConfiguration byTag = systemConfigurationRepository
                    .findByTag(SystemConfigurationType.INVOICE_PAYMENT_DUE_DAYS.getSystemConfigurationType());

            final String tagValue = byTag.getTagValue();

            context.setVariable("dueDate", LocalDateTime.now().plusDays(Long.parseLong(tagValue)));

            String processedHtml = templateEngine.process("INFO- Invoice Generation", context);

            String xhtml = TemplateUtils.convertToXhtml(processedHtml);

            String filePath = FileUtils.ROOT_PATH + FileUtils.PATH_SEPARATOR +
                    "Invoice-" + workflow.getReferenceNo() + ".pdf";

            return generatePdfFile(xhtml, filePath);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error(" error occurred while processing address information ");
        }

        return null;

    }

    public String generatePdfFile(final String processedHtml, final String filePath) {

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {

            ITextOutputDevice outputDevice = new ITextOutputDevice(ITextRenderer.DEFAULT_DOTS_PER_POINT);

            ITextRenderer renderer = new ITextRenderer(ITextRenderer.DEFAULT_DOTS_PER_POINT,
                    ITextRenderer.DEFAULT_DOTS_PER_PIXEL, outputDevice,
                    new CustomTextUserAgent(outputDevice));

            renderer.getSharedContext().setReplacedElementFactory(new BarcodeReplacedElementFactory(
                    renderer.getOutputDevice()
            ));

            renderer.setDocumentFromString(processedHtml, "file:/");

            renderer.layout();

            renderer.createPDF(fileOutputStream, false);

            renderer.finishPDF();

            return filePath;

        } catch (Exception e) {
            log.error(" error occurred while generating pdf file ");
        }

        return filePath;
    }

    public static byte[] toByteArray(BufferedImage bi, String format){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            ImageIO.write(bi, format, baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
