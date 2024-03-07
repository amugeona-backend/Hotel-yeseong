package service;

import constant.RoomType;
import model.Hotel;
import model.ProductRoom;

import java.time.LocalDate;
import java.util.List;
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
        for ( int i = 0; i < 7; i++ ) {
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

    public List<ProductRoom> findProductRoomByDate(LocalDate date) {
        return hotel.getProductRooms().stream()
                .filter(room->room.getReservedDate().equals(date)).toList();
    }
}
