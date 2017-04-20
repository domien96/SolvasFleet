package solvas.rest.invoices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class InvoiceFileViewResolver implements ViewResolver {
    private Map<String, View> views = new HashMap<>();

    @Autowired
    public InvoiceFileViewResolver(View invoicePdfView) {
        views.put("InvoicePdfView", invoicePdfView);
    }
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return views.get(viewName);
    }
}
