package solvas.rest.invoices.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.service.invoices.InvoiceCorrector;
import solvas.service.models.Invoice;
import solvas.service.models.InvoiceItem;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * View for the payment invoice in PDF.
 */
@Component
public class PaymentPdfView extends InvoicePdfView {
    private final InvoiceCorrector invoiceCorrector;

    @Autowired
    public PaymentPdfView(InvoiceCorrector invoiceCorrector) throws DocumentException, IOException {
        this.invoiceCorrector = invoiceCorrector;
    }

    @Override
    protected void createPdf(Invoice invoice, Document document) throws DocumentException, IOException {

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
        p = new Paragraph("Invoice #" + invoice.getId(), font14);
        p.setAlignment(Element.ALIGN_RIGHT);
        document.add(p);
        p = new Paragraph(String.format("Period of invoice: %s to %s",
                convertDate(invoice.getStartDate()),
                convertDate(invoice.getEndDate())), font12);
        p.setAlignment(Element.ALIGN_RIGHT);
        document.add(p);

        p = new Paragraph("Invoice type: " + invoice.getType().getText());
        document.add(p);

        document.add( Chunk.NEWLINE );

        // grand totals
        //document.add(getTotalsTable(String.valueOf(invoice.getTotalAmount()),"€"));
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(FULL_WIDTH);

        // Add the title
        table.addCell(getCell("Nummerplaat", Element.ALIGN_LEFT, font12b));
        table.addCell(getCell("Nettopremie", Element.ALIGN_LEFT, font12b));
        table.addCell(getCell("Taksen", Element.ALIGN_LEFT, font12b));
        table.addCell(getCell("Aantal dagen actief", Element.ALIGN_LEFT, font12b));
        table.addCell(getCell("Totaal", Element.ALIGN_LEFT, font12b));

        NumberFormat formatter = NumberFormat.getPercentInstance();
        formatter.setMaximumFractionDigits(2);
        NumberFormat euroFormat = NumberFormat.getCurrencyInstance(new Locale("nl", "BE"));

        for(InvoiceItem item: invoice.getItems()) {
            table.addCell(item.getContract().getFleetSubscription().getVehicle().getLicensePlate() + " #" + item.getContract().getId());
            table.addCell(getCell(euroFormat.format( invoiceCorrector.getNettoPremium(item.getContract())), Element.ALIGN_RIGHT, font12));

            table.addCell(getCell(formatter.format(item.getTax()), Element.ALIGN_LEFT, font12b));
            table.addCell(getCell(String.valueOf(item.getNumberOfDays()), Element.ALIGN_RIGHT, font12));

            table.addCell(getCell(euroFormat.format(item.getTotalAmount()), Element.ALIGN_LEFT, font12b));
        }

        PdfPCell cell = getCell("Netto totaal:", Element.ALIGN_LEFT, font12);
        cell.setColspan(2);
        table.addCell(cell);
        table.addCell(getCell(euroFormat.format(invoice.getNetAmount()), Element.ALIGN_LEFT, font12b));

        cell = getCell("Totaal:", Element.ALIGN_LEFT, font12);
        cell.setColspan(1);
        table.addCell(cell);

        table.addCell(getCell(euroFormat.format(invoice.getTotalAmount()), Element.ALIGN_LEFT, font12b));
        document.add(table);

        // step 5
        document.close();
    }
}
