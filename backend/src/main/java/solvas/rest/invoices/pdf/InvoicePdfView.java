package solvas.rest.invoices.pdf;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import solvas.rest.api.models.ApiInvoice;
import solvas.service.models.Invoice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import static com.itextpdf.text.html.HtmlTags.FONT;

@Component
public class InvoicePdfView extends AbstractITextPdfView {

    public final int BASE_FONT_SIZE = 12;
    private Font dataFont = new Font(Font.FontFamily.HELVETICA,BASE_FONT_SIZE,Font.NORMAL);
    private Font dataFontBold = new Font(Font.FontFamily.HELVETICA,BASE_FONT_SIZE,Font.BOLD);

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        /**ApiInvoice invoice = (ApiInvoice) model.get("invoice");
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
        document.add(table);*/
        createPdf((ApiInvoice) model.get("invoice"), document);
    }

    private Method getGetterMethodByField(Field f) throws IntrospectionException {
        PropertyDescriptor pd = new PropertyDescriptor(f.getName(), ApiInvoice.class);
        return pd.getReadMethod();
    }

    protected Font font10;
    protected Font font10b;
    protected Font font12;
    protected Font font12b;
    protected Font font14;
    protected String logoPath = "/main/java/solvas/rest/invoices/logo-solvas-1.png";

    public InvoicePdfView() throws DocumentException, IOException {
        font10 = new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL);
        font10b = new Font(Font.FontFamily.HELVETICA,10,Font.BOLD);
        font12 = new Font(Font.FontFamily.HELVETICA,12,Font.NORMAL);
        font12b = new Font(Font.FontFamily.HELVETICA,12,Font.BOLD);
        font14 = new Font(Font.FontFamily.HELVETICA,14,Font.NORMAL);
    }

    public void createPdf(ApiInvoice invoice, Document document) throws DocumentException, IOException {

        // step 3
        document.open();
        // step 4
        /*System.out.println(getClass());
        Image image = Image.getInstance(ClassLoader.getSystemResource(logoPath));
        image.scalePercent(200f);
        image.setAbsolutePosition(0, (float) (PageSize.A4.getHeight() - 20.0));
        System.out.println(image.getScaledHeight());
        document.add(image);*/
        // header
        Paragraph p;
        p = new Paragraph("Invoice id" + invoice.getId(), font14);
        p.setAlignment(Element.ALIGN_RIGHT);
        document.add(p);
        p = new Paragraph(String.format("Period of invoice: %s to %s",
                convertDate(invoice.getStartDate(), "MMM dd, yyyy"),
                convertDate(invoice.getEndDate(), "MMM dd, yyyy")), font12);
        p.setAlignment(Element.ALIGN_RIGHT);
        document.add(p);
        document.add( Chunk.NEWLINE );

        // grand totals
        //document.add(getTotalsTable(String.valueOf(invoice.getTotalAmount()),"€"));
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(50);
        table.addCell(getCell("Factuurtype",Element.ALIGN_LEFT,font12));
        table.addCell(getCell(invoice.getType(),Element.ALIGN_LEFT,font12));
        table.addCell(getCell("Verzekeringsmaatschappij",Element.ALIGN_LEFT,font12));
        table.addCell(getCell("AXA",Element.ALIGN_LEFT,font12));
        table.addCell(getCell("Klant",Element.ALIGN_LEFT,font12));
        table.addCell(getCell("Universiteit Gent",Element.ALIGN_LEFT,font12));
        table.addCell(getCell("Fleet id"+invoice.getFleet(),Element.ALIGN_LEFT,font12));
        table.addCell(getCell(String.valueOf(invoice.getFleet()),Element.ALIGN_LEFT,font12));
        table.addCell(getCell("Premie"+invoice.getTotalAmount(),Element.ALIGN_LEFT,font12));
        table.addCell(getCell(String.valueOf(invoice.getTotalAmount()),Element.ALIGN_LEFT,font12));
        document.add(table);

        // step 5
        document.close();
    }

    public PdfPTable getTotalsTable(String tTotal, String tCurrency) throws DocumentException {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 1, 3, 3, 3, 1});
        table.addCell(getCell("Total:", Element.ALIGN_LEFT, font12b));
        table.addCell(getCell("", Element.ALIGN_LEFT, font12b));
        PdfPCell cell = getCell("", Element.ALIGN_LEFT, font12b);
        cell.setColspan(2);
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
        table.addCell(getCell(tTotal, Element.ALIGN_RIGHT, font12b));
        table.addCell(getCell(tCurrency, Element.ALIGN_LEFT, font12b));
        return table;
    }

    public PdfPCell getCell(String value, int alignment, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        Paragraph p = new Paragraph(value, font);
        p.setAlignment(alignment);
        cell.addElement(p);
        return cell;
    }

    public String convertDate(LocalDateTime d, String newFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(newFormat);
        return sdf.format(Date.from(d.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
}
