package solvas.rest.greencard.pdf;

import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

/**
 * Helps making cells with fewer lines.
 * Created by domien on 6/05/2017.
 */
public class PdfPCellBuilder {

    private PdfPCell c;

    public PdfPCellBuilder() {
        c = new PdfPCell();
    }

    public PdfPCellBuilder(Phrase e) {
        c = new PdfPCell(e);
    }

    public PdfPCellBuilder(PdfPCell style) {
        c = new PdfPCell(style);
    }

    public PdfPCellBuilder setColSpan(int x) {
        c.setColspan(x);
        return this;
    }

    public PdfPCellBuilder setFixedheight(float x) {
        c.setFixedHeight(x);
        return this;
    }

    public PdfPCellBuilder addElement(Element e) {
        c.addElement(e);
        return this;
    }

    public PdfPCell build() {
        return c;
    }
}
