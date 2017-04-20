package solvas.rest.invoices.pdf;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import solvas.rest.api.models.ApiInvoice;
import solvas.service.models.Invoice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

@Component
public class InvoicePdfView extends AbstractITextPdfView {

    public final int BASE_FONT_SIZE = 12;
    private Font dataFont = new Font(Font.FontFamily.HELVETICA,BASE_FONT_SIZE,Font.NORMAL);
    private Font dataFontBold = new Font(Font.FontFamily.HELVETICA,BASE_FONT_SIZE,Font.BOLD);

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ApiInvoice invoice = (ApiInvoice) model.get("invoice");
        // get data model which is passed by the Spring container
        Paragraph p = new Paragraph(String.format("Invoice overview (id : %s)\n", invoice.getId()));
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(2);
        for (Field f : invoice.getClass().getDeclaredFields()) {
            table.addCell(new PdfPCell(new Phrase(f.getName(), dataFontBold)));
            table.addCell(new PdfPCell(new Phrase(getGetterMethodByField(f).invoke(invoice).toString(), dataFont)));
        }
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        document.add(table);
    }

    private Method getGetterMethodByField(Field f) throws IntrospectionException {
        PropertyDescriptor pd = new PropertyDescriptor(f.getName(), ApiInvoice.class);
        return pd.getReadMethod();
    }
}
