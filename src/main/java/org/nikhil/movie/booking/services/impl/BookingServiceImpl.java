package org.nikhil.movie.booking.services.impl;

import org.nikhil.movie.booking.entities.Booking;
import org.nikhil.movie.booking.entities.Seat;
import org.nikhil.movie.booking.entities.Show;
import org.nikhil.movie.booking.enums.BookingStatus;
import org.nikhil.movie.booking.services.BookingService;

import java.util.*;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {

    private static Map<String, List<Booking>> showIdToBookingMap;
    private static Map<String, Booking> bookingIdToBookingMap;

    public BookingServiceImpl() {
        showIdToBookingMap = new HashMap<>();
        bookingIdToBookingMap = new HashMap<>();
    }

    @Override
    public Booking createBooking(Show show, List<Seat> seats, String bookedBy) {
        List<Seat> existingBookedSeats = getBookedSeatsByShowId(show.getId());
        seats.forEach(seat -> {
            if (existingBookedSeats.contains(seat)) {
                throw new RuntimeException("Seat already booked");
            }
        });
        Booking booking = new Booking(show, seats, bookedBy);
        showIdToBookingMap.computeIfAbsent(show.getId(), k -> new ArrayList<>()).add(booking);
        bookingIdToBookingMap.put(booking.getId(), booking);
        return booking;
    }

    @Override
    public List<Booking> getActiveBookingsByShowId(String showId) {
        return Optional.ofNullable(showIdToBookingMap.get(showId)).orElse(new ArrayList<>()).stream()
                .filter(booking -> booking.getBookingStatus() == BookingStatus.BOOKED || booking.getBookingStatus() == BookingStatus.CREATED)
                .collect(Collectors.toList());
    }

    @Override
    public List<Seat> getBookedSeatsByShowId(String showId) {
        List<Booking> activeBookings = getActiveBookingsByShowId(showId);
        return activeBookings.stream().map(Booking::getSeats).flatMap(List::stream).collect(Collectors.toList());
    }

    @Override
    public Booking getBookingById(String bookingId) {
        return bookingIdToBookingMap.get(bookingId);
    }

}
