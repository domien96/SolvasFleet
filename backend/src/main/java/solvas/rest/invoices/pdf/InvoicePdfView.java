package solvas.rest.invoices.pdf;


import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import solvas.rest.api.models.ApiInvoice;
import solvas.service.models.Invoice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.Map;

@Component
public class InvoicePdfView extends AbstractITextPdfView {

    private Font dataFont = new Font(Font.FontFamily.HELVETICA,12,Font.NORMAL);
    private Font dataFontBold = new Font(Font.FontFamily.HELVETICA,12,Font.BOLD);

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ApiInvoice invoice = (ApiInvoice) model.get("invoice");
        // get data model which is passed by the Spring container

        document.add(new Paragraph(String.format("Invoice overview (id : %s", invoice.getId())));
        PdfPTable table = new PdfPTable(2);
        for (Field f : invoice.getClass().getDeclaredFields()) {
            table.addCell(new PdfPCell(new Phrase(f.getName(), dataFontBold)));
            table.addCell(new PdfPCell(new Phrase(f.get(invoice).toString(), dataFont)));
        }
        document.add(table);
    }
}
