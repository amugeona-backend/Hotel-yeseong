package model;

import java.time.LocalDateTime;
public record Reservation(ProductRoom productRoom, String guestName, String guestPhoneNumber, LocalDateTime createdAt) {
}
