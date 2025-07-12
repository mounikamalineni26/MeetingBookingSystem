import services.BookingService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookingService bookingService = new BookingService();
        Scanner sc = new Scanner(System.in);

        System.out.print("Are you a user or admin? (user/admin): ");
        String role = sc.nextLine().trim().toLowerCase();

        System.out.print("Enter your name: ");
        String userName = sc.nextLine();

        boolean isAdmin = role.equals("admin");

        while (true) {
            System.out.println("\n--- Meeting Room Booking ---");
            System.out.println("1. Book a room");
            System.out.println("2. View bookings");
            System.out.println("3. Cancel booking");
            if (isAdmin) {
                System.out.println("4. View all bookings");
                System.out.println("5. Exit");
            } else {
                System.out.println("4. Exit");
            }

            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter room name: ");
                    String room = sc.nextLine();
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = sc.nextLine();
                    System.out.print("Enter time slot (e.g. 10:00-11:00): ");
                    String time = sc.nextLine();

                    if (bookingService.bookRoom(userName, room, date, time)) {
                        System.out.println("[✅] Booking successful!");
                    } else {
                        System.out.println("[❌] Slot already booked!");
                    }
                    break;

                case 2:
                    bookingService.showUserBookings(userName, isAdmin);
                    break;

                case 3:
                    System.out.print("Enter room name: ");
                    room = sc.nextLine();
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    date = sc.nextLine();
                    System.out.print("Enter time slot: ");
                    time = sc.nextLine();

                    if (bookingService.cancelBooking(userName, room, date, time)) {
                        System.out.println("[✅] Booking cancelled.");
                    } else {
                        System.out.println("[❌] No matching booking found.");
                    }
                    break;

                case 4:
                    if (isAdmin) {
                        bookingService.showBookings();
                    } else {
                        System.out.println("Goodbye 👋");
                        sc.close();
                        return;
                    }
                    break;

                case 5:
                    if (isAdmin) {
                        System.out.println("Goodbye 👋");
                        sc.close();
                        return;
                    }
                    // fall-through if not admin
                default:
                    System.out.println("[⚠️] Invalid choice. Try again.");
            }
        }
    }
}
