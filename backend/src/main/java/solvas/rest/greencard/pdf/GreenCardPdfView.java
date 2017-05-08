package solvas.rest.greencard.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.persistence.api.dao.FleetSubscriptionDao;
import solvas.rest.invoices.pdf.AbstractITextPdfView;
import solvas.service.models.Company;
import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

/**
 * Class for generating a green card a vehicle as PDF from.
 * Created by domien on 6/05/2017.
 */
@Component
public class GreenCardPdfView extends AbstractITextPdfView {

    @Autowired
    private FleetSubscriptionDao fsdao;

    private Vehicle vehicle;

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        vehicle = (Vehicle) model.get(GreenCardPdfView.class.getCanonicalName());
        PdfPTable card = new PdfPTable(4);
        card.setWidthPercentage(100);
        for(int i=1;i<=12;i++) {
            Method m = getClass().getDeclaredMethod("make"+i, PdfPTable.class);
            m.setAccessible(true);
            m.invoke(this,card);
            m.setAccessible(false);
        }
        document.add(card);
    }

    private static final int normalSize = 8;
    private static final Font normal = new Font(Font.FontFamily.HELVETICA,normalSize);
    private static final Font normalB = new Font(Font.FontFamily.HELVETICA,normalSize, Font.BOLD);
    private static final int largeSize = 11;
    private static final Font largeB = new Font(Font.FontFamily.HELVETICA,largeSize, Font.BOLD);

    /*********************************************************************************
     * Each section on a green card has a number.
     * These methods each generate a section and returns it as
     * a PDfPCell.
     * All the methods don't require any arguments and must have its name like makeX.
     *********************************************************************************/

    private void make1(PdfPTable card) {
        PdfPCell c = new PdfPCellBuilder().setColSpan(2).build();
        c.addElement(new Paragraph("1. INTERNATIONALE MOTORRIJTUIGVERZEKERINGSKAART",normalB));
        c.addElement(new Paragraph("1. INTERNATIONAL MOTOR INSURANCE CARD",normalB));
        c.addElement(new Paragraph("1. CARTE INTERNATIONALE D'ASSURANCE AUTOMOBILE",normalB));
        card.addCell(c);

    }

    private void make2(PdfPTable card) {
        card.addCell(new PdfPCellBuilder(new Paragraph("2. UITGEGEVEN OP HET GEZAG VAN HET BELGISCH BUREAU VAN DE AUTOVERZEKERAARS",normalB))
                .setColSpan(2).build());
    }

    private void make3(PdfPTable card) {
        Paragraph p = new Paragraph("3.",normalB);
        p.add(new Chunk(new VerticalPositionMark())); // glue
        p.add("GELDIG");
        p.add(new Chunk(new VerticalPositionMark())); // glue
        PdfPCell title = new PdfPCellBuilder(p)
                .setBorder(Rectangle.NO_BORDER).build();

        PdfPTable data = new PdfPTable(6);
        data.addCell(new PdfPCellBuilder(new Paragraph("VAN", normal)).setColSpan(3).setBorder(Rectangle.NO_BORDER)
                .setHorizontalAlignment(Element.ALIGN_CENTER).build());
        data.addCell(new PdfPCellBuilder(new Paragraph("TOT", normal)).setColSpan(3).setBorder(Rectangle.NO_BORDER)
                .setHorizontalAlignment(Element.ALIGN_CENTER).build());
        String[] dates = getSubscriptionDates();
        data.addCell(new PdfPCell(new Paragraph("Dag\n" + dates[0],normal)));
        data.addCell(new PdfPCell(new Paragraph("Maand\n" + dates[1],normal)));
        data.addCell(new PdfPCell(new Paragraph("Jaar\n" + dates[2],normal)));
        data.addCell(new PdfPCell(new Paragraph("Dag\n" + dates[3],normal)));
        data.addCell(new PdfPCell(new Paragraph("Maand\n" + dates[4],normal)));
        data.addCell(new PdfPCell(new Paragraph("Jaar\n" + dates[5],normal)));
        PdfPTable c = new PdfPTable(1);
        c.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        c.addCell(title);
        c.addCell(data);
        c.setWidthPercentage(100);
        card.addCell(new PdfPCellBuilder().addElement(c).setColSpan(2).setMinimumHeight(20).build());
    }

    /**
     * Retrieves dates of active subscription for this vehicle.
     * If no active subscription is present then all array values will be a slash.
     * @return array of length 6 with format : startday,startmonth,startyear,endday,endmonth,endyear
     */
    private String[] getSubscriptionDates() {
        String[] res = new String[] {"/","/","/","/","/","/"};
        Optional<FleetSubscription> fsOpt = fsdao.activeForVehicle(vehicle);
        if (fsOpt.isPresent()) {
            FleetSubscription fs = fsOpt.get();
            LocalDate start = fs.getStartDate(), end = fs.getEndDate();
            if(start!= null) {
                res[0] = String.valueOf(start.getDayOfMonth());
                res[1] = String.valueOf(start.getMonth().getValue());
                res[2] = String.valueOf(start.getYear());
            }
            if(end!= null) {
                res[3] = String.valueOf(end.getDayOfMonth());
                res[4] = String.valueOf(end.getMonth().getValue());
                res[5] = String.valueOf(end.getYear());
            }
        }
        return res;
    }

    private void make4(PdfPTable card) { // minimum height set in make3
        card.addCell(new PdfPCellBuilder(simpleFieldAndValue("4. Landcode/code verzekeraar/Nummer\n","BE"))
                .setColSpan(2).build());
    }

    private void make5(PdfPTable card) {
        String value = vehicle.getLicensePlate() != null? vehicle.getLicensePlate() : vehicle.getChassisNumber();
        card.addCell(new PdfPCellBuilder(simpleFieldAndValue("5. Kenteken (of, indien geen kenteken) chassisnummer\n", value))
                .setMinimumHeight(15).setColSpan(2).build());
    }

    private void make6(PdfPTable card) { // minimum height set in make5
        card.addCell(new PdfPCellBuilder(simpleFieldAndValue("6. Soort Motorruittuig\n", vehicle.getType().getName())).build());
    }

    private void make7(PdfPTable card) { // minimum height set in make5
        card.addCell(new PdfPCellBuilder(simpleFieldAndValue("7. Merk motorrijtuig\n", vehicle.getBrand())).build());
    }

    private void make8(PdfPTable card) {
        PdfPTable c = new PdfPTable(1);
        c.addCell(new PdfPCellBuilder(new Paragraph("8. Dekkingsgebied\n",normalB))
                .setBorder(Rectangle.NO_BORDER).build());
        PdfPTable countries = new PdfPTable(18);
        countries.setWidthPercentage(100);
        for(String code : "BE,BG,CZ,DK,DE,EE,IE,EL,ES,FR,HR,IT,CY,LV,LT,LU,HU,MT,NL,AT,PL,PT,RO,SI,SK,FI,SE,UK,IS,NO,LI".split(",")) {
            countries.addCell(new PdfPCellBuilder(new Paragraph(code,largeB)).setBorder(Rectangle.NO_BORDER)
                    .setFixedHeight(Utilities.millimetersToPoints(10)).build());
        }
        c.addCell(new PdfPCellBuilder().addElement(countries).setFixedHeight(Utilities.millimetersToPoints(10))
                .setVerticalAlignment(Element.ALIGN_MIDDLE).build());
        c.setWidthPercentage(100);
        card.addCell(new PdfPCellBuilder().setColSpan(4).setFixedHeight(Utilities.millimetersToPoints(35))
                .addElement(c).build()); // table wrapped in cell to be able to set some layout constraints.
    }

    private void make9(PdfPTable card) {
        Optional<FleetSubscription> fsOpt = fsdao.activeForVehicle(vehicle);
        String value;
        value = fsOpt.map(fleetSubscription -> buildAddressParagraph(fleetSubscription.getFleet().getCompany())).orElse("Dit voertuig is niet aangesloten bij een vloot");
        card.addCell(new PdfPCellBuilder(simpleFieldAndValue("9. Naam en adres van verzekeringsnemer\n",value))
                .setColSpan(4).setMinimumHeight(Utilities.millimetersToPoints(35)).build());
    }

    private void make10(PdfPTable card) {
        String value = vehicle.getLeasingCompany() != null ? buildAddressParagraph(vehicle.getLeasingCompany()) :
                "Niet verbonden met een verzekeringsmaatschappij";
        card.addCell(new PdfPCellBuilder(simpleFieldAndValue("10. Deze kaart is afgegeven door:\n",
                value))
                .setColSpan(2).build());
    }

    private void make11(PdfPTable card) {
        card.addCell(new PdfPCellBuilder(new Paragraph("11. Ondertekening van de verzekeraar\n",normal))
                .setColSpan(2).setMinimumHeight(Utilities.millimetersToPoints(35)).build());
    }

    private void make12(PdfPTable card) {
        card.addCell(new PdfPCellBuilder(new Paragraph("Nuttige inlichtingen",normal))
                .setColSpan(4).setBorder(Rectangle.NO_BORDER).build());
    }

    /*****************
     * Help functions
     *****************/

    /**
     * Creates a paragraph with a field in bold and its value in normal font.
     */
    private Paragraph simpleFieldAndValue(String field, String value) {
        Paragraph p = new Paragraph();
        p.add(new Chunk(field, normalB));
        p.add(new Chunk(value,normal));
        return p;
    }

    /**
     * Builds the company name and its address in a specific format.
     * @param company the company
     * @return the formatted address and name
     */
    private String buildAddressParagraph(Company company) {
        String a = "%s\n%s, %s\n%s %s\n%s";
        return String.format(a,company.getName(),company.getAddressHouseNumber(), company.getAddressStreet(),
        company.getAddressPostalCode(), company.getAddressCity(),company.getAddressCountry());
    }
}
