package solvas.rest.invoices.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.stereotype.Component;
import solvas.service.models.Invoice;
import solvas.service.models.InvoiceItem;

import java.io.IOException;
import java.text.NumberFormat;

/**
 * View for the payment invoice in PDF.
 */
@Component
public class PaymentPdfView extends InvoicePdfView {

    public PaymentPdfView() throws DocumentException, IOException {
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
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(FULL_WIDTH);

        // Add the title
        table.addCell(getCell("Nummerplaat", Element.ALIGN_LEFT, font12b));
        table.addCell(getCell("Nettopremie", Element.ALIGN_LEFT, font12b));
        table.addCell(getCell("Taksen", Element.ALIGN_LEFT, font12b));
        table.addCell(getCell("Totaal", Element.ALIGN_LEFT, font12b));

        NumberFormat formatter = NumberFormat.getPercentInstance();
        NumberFormat euroFormat = NumberFormat.getCurrencyInstance();

        for(InvoiceItem item: invoice.getItems()) {
            table.addCell(item.getContract().getFleetSubscription().getVehicle().getLicensePlate() + " #" + item.getContract().getId());
            table.addCell(getCell(euroFormat.format(item.getContract().getPremium()), Element.ALIGN_RIGHT, font12));

            table.addCell(getCell("TODO", Element.ALIGN_LEFT, font12b));
            //table.addCell(getCell(formatter.format(cost.getTax().getTax().doubleValue()), Element.ALIGN_RIGHT, font12));

            table.addCell(getCell("TODO", Element.ALIGN_LEFT, font12b));
            //table.addCell(getCell(euroFormat.format(cost.getTotal().doubleValue()), Element.ALIGN_RIGHT, font12));
        }

        PdfPCell cell = getCell("Totaal:", Element.ALIGN_LEFT, font12);
        cell.setColspan(3);
        table.addCell(cell);

        table.addCell(getCell("TODO", Element.ALIGN_LEFT, font12b));
        //table.addCell(getCell(euroFormat.format(invoice.getTotal().doubleValue()), Element.ALIGN_RIGHT, font12b));
        document.add(table);

        // step 5
        document.close();
    }
}
