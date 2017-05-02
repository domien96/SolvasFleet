package solvas.rest.invoices.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.stereotype.Component;
import solvas.service.invoices.billing.BillingInvoice;
import solvas.service.invoices.billing.Correction;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * View for the billing invoice in PDF.
 */
@Component
public class BillingPdfView extends InvoicePdfView<BillingInvoice> {

    public BillingPdfView() throws DocumentException, IOException {
    }

    @Override
    protected void createPdf(BillingInvoice invoice, Document document) throws DocumentException, IOException {

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
        p = new Paragraph("Invoice #" + invoice.getInvoice().getId(), font14);
        p.setAlignment(Element.ALIGN_RIGHT);
        document.add(p);
        p = new Paragraph(String.format("Period of invoice: %s to %s",
                convertDate(invoice.getInvoice().getStartDate()),
                convertDate(invoice.getInvoice().getEndDate())), font12);
        p.setAlignment(Element.ALIGN_RIGHT);
        document.add(p);

        p = new Paragraph("Invoice type: " + invoice.getInvoice().getType().getText());
        document.add(p);

        document.add( Chunk.NEWLINE );

        // grand totals
        //document.add(getTotalsTable(String.valueOf(invoice.getTotalAmount()),"€"));
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(FULL_WIDTH);

        // Add the title
        table.addCell(getCell("Nummerplaat", Element.ALIGN_LEFT, font12b));
        table.addCell(getCell("Nettopremie", Element.ALIGN_LEFT, font12b));
        table.addCell(getCell("Taksen", Element.ALIGN_LEFT, font12b));
        table.addCell(getCell("Type verbetering", Element.ALIGN_LEFT, font12b));
        table.addCell(getCell("Aantal dagen actief", Element.ALIGN_LEFT, font12b));
        table.addCell(getCell("Totaal", Element.ALIGN_LEFT, font12b));

        NumberFormat formatter = NumberFormat.getPercentInstance();
        NumberFormat euroFormat = NumberFormat.getCurrencyInstance();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Correction correction: invoice.getCosts()) {

            table.addCell(correction.getContract().getFleetSubscription().getVehicle().getLicensePlate() + " #" + correction.getContract().getId());
            table.addCell(getCell(euroFormat.format(correction.getContract().getPremium()), Element.ALIGN_RIGHT, font12));
            table.addCell(getCell(formatter.format(correction.getTax().getTax().doubleValue()), Element.ALIGN_RIGHT, font12));
            table.addCell(getCell(correction.getCorrectionName() + " op " + timeFormatter.format(correction.getEventDate()), Element.ALIGN_LEFT, font12));
            table.addCell(getCell(String.valueOf(correction.daysActive()) + " (" + formatter.format(correction.percentageActive()) + ")", Element.ALIGN_RIGHT, font12));
            table.addCell(getCell(euroFormat.format(correction.getTotal().doubleValue()), Element.ALIGN_RIGHT, font12));
        }

        PdfPCell cell = getCell("Totaal:", Element.ALIGN_LEFT, font12);
        cell.setColspan(5);
        table.addCell(cell);
        table.addCell(getCell(euroFormat.format(invoice.getTotal().doubleValue()), Element.ALIGN_RIGHT, font12b));
        document.add(table);

        document.add( Chunk.NEWLINE );

        String start = "As a reference, you have received following payment invoices in this period: ";
        Collection<String> invoiceIds = invoice.getPayments().stream()
                .map(i -> "#" + String.valueOf(i.getId()))
                .collect(Collectors.toList());
        String joined = String.join(", ", invoiceIds);
        p = new Paragraph(start + joined);
        document.add(p);

        // step 5
        document.close();
    }
}