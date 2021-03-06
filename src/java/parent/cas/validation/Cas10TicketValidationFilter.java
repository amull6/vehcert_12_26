/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/index.html
 */
package parent.cas.validation;


import javax.servlet.FilterConfig;

/**
 * Implementation of AbstractTicketValidatorFilter that instanciates a Cas10TicketValidator.
 * <p>Deployers can provide the "casServerPrefix" and the "renew" attributes via the standard context or filter init
 * parameters.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.1
 */
public class Cas10TicketValidationFilter extends parent.cas.validation.AbstractTicketValidationFilter {

    protected final TicketValidator getTicketValidator(final FilterConfig filterConfig) {
        final String casUrlServerPrefix = getPropertyFromInitParams(filterConfig, "casUrlServerPrefix", null);
        final Cas10TicketValidator validator = new Cas10TicketValidator(casUrlServerPrefix);
        validator.setRenew(Boolean.parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));

        return validator;
    }
}
