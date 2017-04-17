package solvas.rest.invoices.pdf;


import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import solvas.service.models.Invoice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class InvoicePdfView extends AbstractITextPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("sdqjg");
        Invoice invoice = (Invoice) model.get("invoice");
        // get data model which is passed by the Spring container

        document.add(new Paragraph("Recommended books for Spring framework"));
    }
}
