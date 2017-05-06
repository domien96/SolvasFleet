package solvas.rest.greencard.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import solvas.rest.invoices.pdf.AbstractITextPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * class for generating a green card as PDF from an vehicle.
 * Created by domien on 6/05/2017.
 */
public class GreenCardPdfView extends AbstractITextPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        document.open();
        document.add(new Paragraph("hallo"));
        document.close();
    }
}
