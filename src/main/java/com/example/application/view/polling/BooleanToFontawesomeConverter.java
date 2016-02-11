package com.example.application.view.polling;

import com.vaadin.data.util.converter.*;
import com.vaadin.server.*;

import java.util.*;

public class BooleanToFontawesomeConverter extends StringToBooleanConverter {
    @Override
    public String convertToPresentation(Boolean value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        if (value == null || value.equals(Boolean.FALSE)) {
            return FontAwesome.STAR_O.getHtml();
        } else {
            return FontAwesome.STAR.getHtml();
        }
    }
}
