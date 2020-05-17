import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Bun venit! Introduceti numarul de locuri disponibile: ");
		int locuriDisponibile = sc.nextInt();
		GuestList aGuest = new GuestList(locuriDisponibile);

		System.out.println("Va rugam selectati o comanda. Pentru lista comenzilor tastati \"help\".");
		String userInput = sc.next();

		while (userInput != "quit") {

			switch (userInput) {
			case "help":
				aGuest.printMenu();
				break;
			case "add":
				aGuest.addGuest();
				System.out.println("Va rugam selectati o comanda. Pentru lista comenzilor tastati \"help\".");
				break;
			case "available":
				aGuest.slotsAvailable();
				break;
			case "guests":
				aGuest.getGuestsList();
				break;
			case "guests_no":
				aGuest.participantsNo();
				break;
			case "waitlist_no":
				aGuest.waitlistNo();
				break;
			case "remove":
				aGuest.removeGuest();
				break;
			case "search":
				System.out.println("Introduceti sirul de caractere dupa care sa fie executata cautarea: ");
				String userSearchInput = sc.next();
				aGuest.search(userSearchInput);
				break;
			case "subscribe_no":
				aGuest.subscribeNo();
				break;
			case "update":
				aGuest.guestUpdate();
				break;
			case "waitlist":
				aGuest.getWaitingList();
				break;
			default:
				System.out.println("Nu ati introdus o comanda valida. Incercati din nou: ");
				aGuest.printMenu();
				break;
			}
			userInput = sc.next();

		}

		sc.close();
	}
}
