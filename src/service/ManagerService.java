package service;

public class ManagerService {
    private final HotelService hotelService;

    public ManagerService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    public void displayManagerMode() {
        while (true) {
            System.out.println("----------Manager Mode----------");
            System.out.println("1. 예약 현황");
            System.out.println("2. 자산 현황");
            System.out.println("9. 시스템 종료");
            backMessage();
            int command = sc.nextInt();
            if (command == 0) {
                break;
            } else if (command == 1) {
                reservationStatus();
            } else if (command == 2) {
                assertStatus();
            } else if (command == 9) {
                errorMessage();
            }
        }
    }

    public void reservationStatus() { // 1. 예약 현황
        System.out.println();
        System.out.println("메뉴를 선택해 주세요.");
        System.out.println("1. 빈객실 찾기");
        System.out.println("2. 예약 찾기");
        System.out.println("3. 오늘 객실 현황");
        backMessage();
        int command = sc.nextInt();
        switch (command) {
            case 1 -> findProductRoomByDate();
            case 2 -> findReservation();
            case 3 -> findReservationByToday();
            case 0 -> {
            }
            default -> {
                errorMessage();
                reservationStatus();
            }
        }
    }
}
