package org.springframework.webflow.samples.booking;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BookingDateRangeValidator implements ConstraintValidator<BookingDateRange, Booking> {

    public void initialize(BookingDateRange bookingDateRange) {
    }

    public boolean isValid(Booking booking, ConstraintValidatorContext context) {
        return !((booking.getCheckinDate() != null) && (booking.getCheckoutDate() != null)
                && booking.getCheckoutDate().before(booking.getCheckinDate()));
    }

}
