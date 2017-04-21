package solvas.rest.invoices.pdf;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import solvas.rest.api.models.ApiInvoice;

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

/**
 * Abstract class for generating a PDF from an invoice.
 *
 * @param <M> The model of the invoice.
 */
public abstract class InvoicePdfView<M> extends AbstractITextPdfView {

    public final static String MODEL_NAME = InvoicePdfView.class.getCanonicalName();

    private final static int BASE_FONT_SIZE = 12;
    private Font dataFont = new Font(Font.FontFamily.HELVETICA,BASE_FONT_SIZE,Font.NORMAL);
    private Font dataFontBold = new Font(Font.FontFamily.HELVETICA,BASE_FONT_SIZE,Font.BOLD);

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        createPdf((M) model.get(MODEL_NAME), document);
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

    /**
     * Default constructor.
     * @throws DocumentException When bad things happen.
     * @throws IOException Other bad things.
     */
    public InvoicePdfView() throws DocumentException, IOException {
        font10 = new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL);
        font10b = new Font(Font.FontFamily.HELVETICA,10,Font.BOLD);
        font12 = new Font(Font.FontFamily.HELVETICA,12,Font.NORMAL);
        font12b = new Font(Font.FontFamily.HELVETICA,12,Font.BOLD);
        font14 = new Font(Font.FontFamily.HELVETICA,14,Font.NORMAL);
    }

    protected abstract void createPdf(M invoice, Document document) throws DocumentException, IOException;

    protected PdfPCell getCell(String value, int alignment, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        Paragraph p = new Paragraph(value, font);
        p.setAlignment(alignment);
        cell.addElement(p);
        return cell;
    }

    protected String convertDate(LocalDateTime d, String newFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(newFormat);
        return sdf.format(Date.from(d.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
}