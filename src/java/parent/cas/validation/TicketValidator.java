/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/index.html
 */
package parent.cas.validation;

import javax.servlet.http.HttpServletRequest;

/**
 * Contract for a validator that will confirm the validity of a supplied ticket.
 * <p>
 * Validator makes no statement about how to validate the ticket or the format of the ticket (other than that it must be a String).
 *
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.1
 */
public interface TicketValidator {

    /**
     * Attempts to validate a ticket for the provided service.
     *
     * @param ticket the ticket to attempt to validate.
     * @param service the service this ticket is valid for.
     * @return an assertion from the ticket.
     * @throws parent.cas.validation.TicketValidationException if the ticket cannot be validated.
     *
     */
    parent.cas.validation.Assertion validate(String ticket, String service,HttpServletRequest request) throws TicketValidationException;
}
