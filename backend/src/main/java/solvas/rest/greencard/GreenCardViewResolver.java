package solvas.rest.greencard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import solvas.rest.greencard.pdf.GreenCardPdfView;
import solvas.service.models.Vehicle;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Resolver for green card PDFs.
 * Created by domien on 6/05/2017.
 */
@Component
public class GreenCardViewResolver implements ViewResolver {

    public static final String GREEN_CARD_PDF_VIEW = GreenCardPdfView.class.getCanonicalName();

    private Map<String, View> views = new HashMap<>();

    /**
     * Constructor.
     *
     * @param view autowired
     */
    @Autowired
    public GreenCardViewResolver(GreenCardPdfView view) {
        views.put(GREEN_CARD_PDF_VIEW, view);
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return views.get(viewName);
    }
}
