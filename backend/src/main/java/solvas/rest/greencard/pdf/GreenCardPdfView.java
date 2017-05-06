package solvas.rest.greencard.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import solvas.rest.invoices.pdf.AbstractITextPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Class for generating a green card a vehicle as PDF from.
 * Created by domien on 6/05/2017.
 */
@Component
public class GreenCardPdfView extends AbstractITextPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PdfPTable card = new PdfPTable(4);
        for(int i=1;i<=11;i++) {
            Method m = getClass().getDeclaredMethod("make"+i);
            m.setAccessible(true);
            card.addCell((PdfPCell) m.invoke(this));
            m.setAccessible(false);
        }
        document.add(card);
    }

    /*********************************************************************************
     * Each section on a green card has a number.
     * These methods each generate a section and returns it as
     * a PDfPCell.
     * All the methods don't require any arguments and must have its name like makeX.
     *********************************************************************************/

    private PdfPCell make1() {
        PdfPCell c = new PdfPCellBuilder().setColSpan(2).build();
        c.addElement(new Paragraph("1. INTERNATIONALE MOTORRIJTUIGVERZEKERINGSKAART"));
        c.addElement(new Paragraph("1. INTERNATIONAL MOTOR INSURANCE CARD"));
        c.addElement(new Paragraph("1. CARTE INTERNATIONALE D'ASSURANCE AUTOMOBILE"));
        return c;

    }

    private PdfPCell make2() {
        return new PdfPCellBuilder(new Paragraph("2. UITGEGEVEN OP HET GEZAG VAN HET BELGISCH BUREAU VAN DE AUTOVERZEKERAARS"))
                .setColSpan(2).build();
    }

    private PdfPCell make3() {
        PdfPCell title = new PdfPCellBuilder(new Paragraph("3.         VAN            GELDIG          TOT"))
                .setColSpan(2).build();
        PdfPTable data = new PdfPTable(6);
        data.addCell(new PdfPCell(new Paragraph("Dag:")));
        data.addCell(new PdfPCell(new Paragraph("Maand:")));
        data.addCell(new PdfPCell(new Paragraph("Jaar:")));
        data.addCell(new PdfPCell(new Paragraph("Dag:")));
        data.addCell(new PdfPCell(new Paragraph("Maand:")));
        data.addCell(new PdfPCell(new Paragraph("Jaar:")));
        PdfPTable c = new PdfPTable(1);
        c.addCell(title);
        c.addCell(data);
        return new PdfPCell(c);
    }

    private PdfPCell make4() {
        return new PdfPCellBuilder(new Paragraph("4. Landcode/code verzekeraar/Nummer\n"+ "BE"))
                .setColSpan(2).build();
    }

    private PdfPCell make5() {
        return new PdfPCellBuilder(new Paragraph("5. Kenteken\n"+ "todo"))
                .setColSpan(2).build();
    }

    private PdfPCell make6() {
        return new PdfPCellBuilder(new Paragraph("6. Soort Motorruittuig\n"+ "todo")).build();
    }

    private PdfPCell make7() {
        return new PdfPCellBuilder(new Paragraph("7. Merk motorrijtuig\n"+ "todo")).build();
    }

    private PdfPCell make8() {
        PdfPCell c = new PdfPCellBuilder(new Paragraph("8. Dekkingsgebied\n"+ "todo"))
                .setColSpan(4).build();
        c.addElement(new Paragraph("" +
                "BE | BG | CZ | DK | DE | EE | IE | EL | ES | FR | HR | IT | CY | LV | LT | LU |" +
                "HU | MT | NL | AT | PL | PT | RO | SI | SK | FI | SE | UK | IS | NO | LI"));
        return c;
    }

    private PdfPCell make9() {
        return new PdfPCellBuilder(new Paragraph("9. Naam en adres van verzekeringsnemer\n"+ "todo"))
                .setColSpan(4).build();
    }

    private PdfPCell make10() {
        return new PdfPCellBuilder(new Paragraph("10. Deze kaart is afgegeven door:\n"+ "todo"))
                .setColSpan(2).build();
    }

    private PdfPCell make11() {
        return new PdfPCellBuilder(new Paragraph("11. Ondertekening van de verzekeraar\n"+ "todo"))
                .setColSpan(2).build();
    }
}
