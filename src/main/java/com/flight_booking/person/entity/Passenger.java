package com.flight_booking.person.entity;

import com.flight_booking.user.entity.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "passengers")
public class Passenger extends Person{

    @OneToMany(mappedBy = "passenger")
    private List<Booking> bookings;

    @OneToOne
    @Column(name = "user_id")
    private User user;

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
