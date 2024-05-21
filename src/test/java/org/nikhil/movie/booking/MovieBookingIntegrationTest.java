package org.nikhil.movie.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nikhil.movie.booking.entities.*;
import org.nikhil.movie.booking.enums.BookingStatus;
import org.nikhil.movie.booking.facade.AdminManager;
import org.nikhil.movie.booking.facade.BookingManager;
import org.nikhil.movie.booking.facade.impl.AdminManagerImpl;
import org.nikhil.movie.booking.facade.impl.BookingManagerImpl;
import org.nikhil.movie.booking.services.*;
import org.nikhil.movie.booking.services.impl.*;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MovieBookingIntegrationTest {

    private AdminManager adminManager;
    private BookingManager bookingManager;
    private TheatreService theatreService;
    private ScreenService screenService;
    private SeatService seatService;
    private MovieService movieService;
    private ShowService showService;
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        adminManager = new AdminManagerImpl();
        bookingManager = new BookingManagerImpl();
        theatreService = new TheatreServiceImpl();
        screenService = new ScreenServiceImpl();
        seatService = new SeatServiceImpl();
        movieService = new MovieServiceImpl();
        showService = new ShowServiceImpl();
        bookingService = new BookingServiceImpl();

        System.out.println("Start theatre setup.");

        String theatreName = "Theatre 1";
        Theatre theatre = adminManager.createTheatre(theatreName);
        assertEquals(theatreName, theatre.getName());
        List<Theatre> allTheatres = theatreService.getAllTheatres();
        assertEquals(1, allTheatres.size());
        assertEquals(theatre, allTheatres.get(0));
        String theatreId = theatre.getId();
        Theatre theatreById = theatreService.getTheatreById(theatre.getId());
        assertEquals(theatre, theatreById);

        String screenName = "Screen 1";
        Screen screen = adminManager.addScreenToTheatre(theatreId, screenName);
        assertEquals(screenName, screen.getName());
        List<Screen> screensByTheatreId = theatreService.getScreensByTheatreId(theatreId);
        assertEquals(1, screensByTheatreId.size());
        assertEquals(screen, screensByTheatreId.get(0));
        String screenId = screen.getId();
        Screen screenById = screenService.getScreenById(screenId);
        assertEquals(screen, screenById);

        int noOfSeats = 15;
        for (int i = 1; i <= noOfSeats; i++) {
            adminManager.addSeatToScreen(screenId, 1, i);
        }

        String movieName = "Movie 1";
        Movie movie = adminManager.createMovie(movieName);
        assertEquals(movieName, movie.getName());
        String movieId = movie.getId();
        Movie movieById = movieService.getMovieById(movieId);
        assertEquals(movie, movieById);

        LocalTime startTime = LocalTime.of(18, 0);
        Integer durationInSeconds = 150000;
        Show show = adminManager.createShow(movieId, screenId, startTime, durationInSeconds);
        assertEquals(movie, show.getMovie());
        assertEquals(screen, show.getScreen());
        assertEquals(startTime, show.getStartTime());
        assertEquals(durationInSeconds, show.getDurationInSeconds());
        Show showById = showService.getShowById(show.getId());
        assertEquals(show, showById);
        System.out.println("Theatre setup complete");
    }

    // Test Case 1:
    // Say U1 and U2 select same show.
    // U1 requests for and gets all Available Seats for this show.
    // U1 selects group of seats and proceeds to pay.
    // U2 requests for and gets all Available Seats for this show. U2 should not see the seats selected by U1 as AVAILABLE. 5 .Payment succeeded for U1.
    // U1 receives Ticket with Seats confirmed.
    @Test
    void testBookingCase1(){
        System.out.println("Start booking case 1.");

        List<Show> shows = showService.getAllShows();
        Show show = shows.get(0);
        String showId = show.getId();
        List<Seat> availableSeats = bookingManager.getAvailableSeats(showId);
        int noOfSeats = availableSeats.size();

        String user1 = "Nikhil";
        List<String> seatIds1 = availableSeats.subList(0, 5).stream().map(Seat::getId).collect(Collectors.toList());
        Booking booking1 = bookingManager.createBooking(showId, seatIds1, user1);
        assertEquals(show, booking1.getShow());
        assertEquals(5, booking1.getSeats().size());
        assertEquals(user1, booking1.getBookedBy());
        assertNotNull(booking1.getId());
        assertEquals(booking1.getBookingStatus(), BookingStatus.CREATED);

        List<Seat> availableSeatsAfterBooking1 = bookingManager.getAvailableSeats(showId);
        assertEquals(noOfSeats - 5, availableSeatsAfterBooking1.size());
        assertTrue(availableSeatsAfterBooking1.stream().noneMatch(seat -> seatIds1.contains(seat.getId())));

        String bookedBy2 = "Rahul";
        List<String> seatIds2 = availableSeatsAfterBooking1.subList(0, 5).stream().map(Seat::getId).collect(Collectors.toList());
        Booking booking2 = bookingManager.createBooking(showId, seatIds2, bookedBy2);
        assertEquals(show, booking2.getShow());
        assertEquals(5, booking2.getSeats().size());
        assertEquals(bookedBy2, booking2.getBookedBy());
        assertNotNull(booking2.getId());
        assertEquals(booking2.getBookingStatus(), BookingStatus.CREATED);
    }

    // Test Case 2:
    // Say U1 and U2 select same show.
    // U1 and U2 requests for and gets all Available Seats for this show.
    // U1 selects group of seats.
    // U1 proceeds to pay.
    // U2 requests for and gets all Available Seats for this show. U2 should NOT see the seats selected by U1 as AVAILABLE.
    // Payment failed for U1. Assume maximum retries as zero just for the demo. Also show in another scenario where U1’s UserBookingSession is explicitly closed by U1 before payment is completed.
    // U2 again requests for and gets all Available Seats for this show. U2 should now see the seats previously selected by U1 as AVAILABLE.
    @Test
    void testBookingCase2(){
        System.out.println("Start booking case 2.");

        List<Show> shows = showService.getAllShows();
        Show show = shows.get(0);
        String showId = show.getId();
        List<Seat> availableSeats = bookingManager.getAvailableSeats(showId);
        int noOfSeats = availableSeats.size();

        String user1 = "Nikhil";
        List<String> seatIds1 = availableSeats.subList(0, 5).stream().map(Seat::getId).collect(Collectors.toList());
        Booking booking1 = bookingManager.createBooking(showId, seatIds1, user1);
        assertEquals(show, booking1.getShow());
        assertEquals(5, booking1.getSeats().size());
        assertEquals(user1, booking1.getBookedBy());
        assertNotNull(booking1.getId());
        assertEquals(booking1.getBookingStatus(), BookingStatus.CREATED);

        List<Seat> availableSeatsAfterBooking1 = bookingManager.getAvailableSeats(showId);
        assertEquals(noOfSeats - 5, availableSeatsAfterBooking1.size());
        assertTrue(availableSeatsAfterBooking1.stream().noneMatch(seat -> seatIds1.contains(seat.getId())));

        String bookedBy2 = "Rahul";
        List<String> seatIds2 = availableSeatsAfterBooking1.subList(0, 5).stream().map(Seat::getId).collect(Collectors.toList());
        Booking booking2 = bookingManager.createBooking(showId, seatIds2, bookedBy2);
        assertEquals(show, booking2.getShow());
        assertEquals(5, booking2.getSeats().size());
        assertEquals(bookedBy2, booking2.getBookedBy());
        assertNotNull(booking2.getId());
        assertEquals(booking2.getBookingStatus(), BookingStatus.CREATED);

        bookingManager.markPaymentFailed(booking1.getId());
        Booking booking1AfterPaymentFailed = bookingService.getBookingById(booking1.getId());
        assertEquals(BookingStatus.FAILED, booking1AfterPaymentFailed.getBookingStatus());

        List<Seat> availableSeatsAfterPaymentFailed = bookingManager.getAvailableSeats(showId);
        assertEquals(noOfSeats-5, availableSeatsAfterPaymentFailed.size());

        bookingManager.markPaymentFailed(booking2.getId());
        Booking booking2AfterPaymentFailed = bookingService.getBookingById(booking2.getId());
        assertEquals(BookingStatus.FAILED, booking2AfterPaymentFailed.getBookingStatus());

        List<Seat> availableSeatsAfterPaymentFailed2 = bookingManager.getAvailableSeats(showId);
        assertEquals(noOfSeats, availableSeatsAfterPaymentFailed2.size());
    }

    // Test Case 3:
    // Say U1 and U2 select same show.
    // U1 and U2 request for and get all Available Seats for this show.
    // U1 selects group of seats and proceeds to pay.
    // U2 selects overlapping group of seats and proceeds to pay.
    // U2 should be notified that “one or more of the selected seats are not available at this moment”.
    @Test
    void testBookingCase3(){
        System.out.println("Start booking case 3.");

        List<Show> shows = showService.getAllShows();
        Show show = shows.get(0);
        String showId = show.getId();
        List<Seat> availableSeats = bookingManager.getAvailableSeats(showId);
        int noOfSeats = availableSeats.size();

        String user1 = "Nikhil";
        List<String> seatIds1 = availableSeats.subList(0, 5).stream().map(Seat::getId).collect(Collectors.toList());
        Booking booking1 = bookingManager.createBooking(showId, seatIds1, user1);
        assertEquals(show, booking1.getShow());
        assertEquals(5, booking1.getSeats().size());
        assertEquals(user1, booking1.getBookedBy());
        assertNotNull(booking1.getId());
        assertEquals(booking1.getBookingStatus(), BookingStatus.CREATED);

        String bookedBy2 = "Rahul";
        List<String> seatIds2 = availableSeats.subList(3, 8).stream().map(Seat::getId).collect(Collectors.toList());
        assertThrows(RuntimeException.class, () -> bookingManager.createBooking(showId, seatIds2, bookedBy2));
    }
}