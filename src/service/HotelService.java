package service;

import constant.RoomType;
import model.Guest;
import model.Hotel;
import model.ProductRoom;
import model.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HotelService {
    Hotel hotel = new Hotel();
    private static HotelService hotelService;

    public static HotelService getHotelService() {
        if ( hotelService == null ) {
            hotelService = new HotelService();
        }
        return hotelService;
    }

    public void initRoom() {
        LocalDate localDate = LocalDate.now();
        for ( int i = 0; i < 7; i++ ) { // 일주일치 방 만들기. (4개 방)X(7일)
            hotel.getProductRooms().add(new ProductRoom
                    (RoomType.STANDARD, 30000, 101, localDate.plusDays(i)));
            hotel.getProductRooms().add(new ProductRoom
                    (RoomType.SUPERIOR, 40000, 102, localDate.plusDays(i)));
            hotel.getProductRooms().add(new ProductRoom
                    (RoomType.DELUXE, 50000, 103, localDate.plusDays(i)));
            hotel.getProductRooms().add(new ProductRoom
                    (RoomType.SUITE, 60000, 104, localDate.plusDays(i)));
        }
    }

    public String getHotelPassword() {
        return hotel.getPassword();
    }

    public List<LocalDate> findAvailableDays() {
        return hotel.getProductRooms().stream().map(ProductRoom::getReservedDate)
                .distinct().collect(Collectors.toList());
    }

    public List<ProductRoom> findProductRoomByDate(LocalDate date) { // 날짜로 룸 찾기
        return hotel.getProductRooms().stream()
                .filter(room->room.getReservedDate().equals(date)).toList();
    }

    public Guest findGuestByPhoneNumber(String phoneNumber) { // 전화번호로 예약자 찾기
        return hotel.getGuests().stream().filter(u -> u.getPhoneNumber().equals(phoneNumber))
                .findFirst().orElse(null);
    }

    public List<Reservation> findReservationByDate(LocalDate date) { // 예약날짜로 예약 찾기
        return hotel.getReservations().stream().filter(reservation -> reservation.productRoom()
                .getReservedDate().equals(date)).toList();
    }

    public List<Reservation> findReservationByName(String name) { // 예약자 이름으로 예약 찾기
        return hotel.getReservations().stream().filter(reservation -> reservation.guestName()
                        .equals(name)).toList();
    }

    public List<Reservation> findReservationByPhoneNumber(String phoneNumber) { // 전화번호로 예약 찾기
        return hotel.getReservations().stream().filter(reservation -> reservation.guestPhoneNumber()
                        .equals(phoneNumber))
                .toList();
    }

    public void addReservation(Reservation reservation) { // 예약 추가
        hotel.getReservations().add(reservation);
    }

    public void cancelReservation(Reservation reservation) { // 예약 취소
        hotel.getReservations().add(reservation);
    }

    public void addGuest(Guest guest) {
        hotel.getGuests().add(guest);
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        String pattern = "^\\d{3}-\\d{3,4}-\\d{4}$";
        return Pattern.matches(pattern, phoneNumber);
    }

    public boolean existPhoneNumber(String phoneNumber) {
        return hotel.getGuests().stream().anyMatch(u -> u.getPhoneNumber().equals(phoneNumber));
    }

    public void addAsset(int price) {
        hotel.setAsset(hotel.getAsset() + price);
    }

    public void deductAsset(int price) {
        hotel.setAsset(hotel.getAsset() - price);
    }

    public int getAsset() {
        return hotel.getAsset();
    }

    public List<ProductRoom> findEmptyProductRoomByDate(LocalDate date) {
        return hotel.getProductRooms().stream().filter(room -> room.getReservedDate()
                .equals(date)).filter(empty -> empty.isReserved()).toList();
    }

    public boolean findReservationByExistingName(String name) {
        return hotel.getReservations().stream().anyMatch(reservation -> reservation.guestName()
                .equals(name));
    }

    public boolean findReservationByExistingPhoneNumber(String phone) {
        return hotel.getReservations().stream().anyMatch(reservation -> reservation.guestPhoneNumber()
                .equals(phone));
    }

    public boolean findReservationByExistingDate(LocalDate date) {
        return hotel.getReservations().stream().anyMatch(reservation -> reservation.productRoom()
                .equals(date));
    }
}
