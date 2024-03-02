package model;

import java.time.LocalDateTime;
public record Reservation(String guestName, String guestPhoneNumber, ProductRoom productRoom, LocalDateTime createdAt) {
}
