package com.herokuapp.restfulbooker;

public class BookingId {

    //CHAPTER 24: De-Serialization
    /*
    {
    "bookingid": 1,
    "booking": {
                "firstname": "Jim",
                "lastname": "Brown",
                "totalprice": 111,
                "depositpaid": true,
                "bookingdates": {
                                    "checkin": "2018-01-01",
                                    "checkout": "2019-01-01"
                                },
                "additionalneeds": "Breakfast"
                }
    }
     */

    //Constructor
    public BookingId(int bookingid, Booking booking) {
        this.bookingid = bookingid;
        this.booking = booking;
    }
    //Empty Constructor
    public BookingId() {
    }

    private int bookingid;
    private Booking booking;

    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return "BookingId{" +
                "bookingid=" + bookingid +
                ", booking=" + booking +
                '}';
    }
}
