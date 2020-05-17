
import java.util.ArrayList;
import java.util.Scanner;

public class GuestList {

	private int locuriDispnibile;
	private ArrayList<Guest> guestsList = new ArrayList<Guest>(locuriDispnibile);
	private ArrayList<Guest> waitingList = new ArrayList<Guest>();

	int nrOrdine = 1;

	public GuestList(int locuriDisponibile) {
		this.locuriDispnibile = locuriDisponibile;

	}


	public int getLocuriDisponibile() {
		return this.locuriDispnibile;
	}

	// 1. Adaugarea unei noi persoane
	public int addGuest() {

		Scanner sc = new Scanner(System.in);
		System.out.println("Se adauga o noua persoana…  ");
		System.out.println("Introduceti numele de familie:  ");
		String userInputFirstName = sc.next();
		System.out.println("Introduceti prenumele: ");
		String userInputLastName = sc.next();
		System.out.println("Introduceti email: ");
		String userInputEmail = sc.next();
		System.out.println("Introduceti numar de telefon (format „0733386463“): ");
		String userInputPhoneNumber = sc.next();

		Guest newGuest = new Guest(userInputFirstName, userInputLastName, userInputEmail, userInputPhoneNumber);

		// cautam in guestList
		if (listSearchByName(guestsList, userInputFirstName, userInputLastName)
				|| (listSearchByEmail(guestsList, userInputEmail)
						|| (listSearchByPhone(guestsList, userInputPhoneNumber)))) {
			System.out.println("[" + userInputFirstName + " " + userInputLastName
					+ "] Ai fost deja inscris la eveniment si locul tau este confirmat. Te asteptam!");
			
			return -1;

			// cautam in waiting list
		} else if (listSearchByName(waitingList, userInputFirstName, userInputLastName)
				|| (listSearchByEmail(waitingList, userInputEmail)
						|| (listSearchByPhone(waitingList, userInputPhoneNumber)))) {

			System.out.println("[" + userInputFirstName + " " + userInputLastName
					+ "] Esti dejs inscris in lista de asteptare si ai primit numarul de ordine "
					+ waitingList.indexOf(newGuest) + 1 + ". Te vom notifica daca un loc devine disponibil");
			
			return waitingList.indexOf(newGuest);

			// daca lista guest nu a atins limita maxima de ocupare, se adauga persoana
		} else if (guestsList.size() < locuriDispnibile) {
			guestsList.add(newGuest);
			System.out.println("[" + userInputFirstName + " " + userInputLastName
					+ "] Felicitari! Locul tau la eveniment este confirmat. Te asteptam!.");
			
			return 0;
		} else {
			waitingList.add(newGuest);
			System.out.println("[" + userInputFirstName + " " + userInputLastName
					+ "] Te-ai inscris cu succes in lista de asteptare si ai primit numarul de ordine " + this.nrOrdine
					+ ". Te vom notifica daca un loc devine disponibil");
			this.nrOrdine++;
			
			return this.nrOrdine;
		}
	}

	// 2. Determina daca o persoana este inscrisa la eveniment (in oricare lista)

	// cautarea in guestList si waitingList

	public boolean listSearch(Guest newGuest) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Introduceti criteriul de cautare\r\n1    - Cautare dupa nume si preume\r\n"
				+ "2    - Cautare dupa email\r\n" + "3    - Cautare dupa numarul de telefon\r\n");

		String userInput = sc.next();

		switch (userInput) {
		case "1":
			System.out.println("Introduceti numele: ");
			String userInputFirstName = sc.next();
			System.out.println("Introduceti prenumele: ");
			String userInputLastName = sc.next();
			System.out.println("Se cauta…  ");
			if (listSearchByName(guestsList, userInputFirstName, userInputLastName)) {
				System.out.println("[" + userInputFirstName + " " + userInputLastName
						+ "] Ai fost deja inscris la eveniment si locul tau este confirmat. Te asteptam!");
			} else if (listSearchByName(waitingList, userInputFirstName, userInputLastName)) {
				System.out.println("[" + userInputFirstName + " " + userInputLastName
						+ "] Esti inscris in lista de asteptare cu numarul de ordine " + waitingList.indexOf(newGuest)
						+ 1 + ". Te vom notifica daca un loc devine disponibil");
			} else {
				System.out.println("[" + userInputFirstName + " " + userInputLastName
						+ "] Nu te afli pe lista celor inscrisi la eveniment sau pe cea de asteptare. "
						+ "Te invitam sa te inscrii la eveniment prin comanda - add - din menul principal");
			}
			break;

		case "2":
			System.out.println("Introduceti emailul: ");
			String userInputEmail = sc.next();
			System.out.println("Se cauta…  ");
			if (listSearchByEmail(guestsList, userInputEmail)) {
				System.out.println("Ai fost deja inscris la eveniment si locul tau este confirmat. Te asteptam!");
			} else if (listSearchByEmail(waitingList, userInputEmail)) {
				System.out.println("Esti inscris in lista de asteptare cu numarul de ordine "
						+ waitingList.indexOf(newGuest) + 1 + ". Te vom notifica daca un loc devine disponibil");
			} else {
				System.out.println("Nu te afli pe lista celor inscrisi la eveniment sau pe cea de asteptare. "
						+ "Te invitam sa te inscrii la eveniment prin comanda - add - din menul principal");
				
				return false;
			}
			break;

		case "3":
			System.out.println("Introduceti numarul de telefon: ");
			String userInputPhNo = sc.next();
			System.out.println("Se cauta…  ");
			if (listSearchByPhone(guestsList, userInputPhNo)) {
				System.out.println("Ai fost deja inscris la eveniment si locul tau este confirmat. Te asteptam!");
				
				return true;
			} else if (listSearchByPhone(waitingList, userInputPhNo)) {
				System.out.println("Esti inscris in lista de asteptare si ai primit numarul de ordine "
						+ waitingList.indexOf(newGuest) + ". Te vom notifica daca un loc devine disponibil");
				
				return true;
			} else {
				System.out.println("Nu te afli pe lista celor inscrisi la eveniment sau pe cea de asteptare. "
						+ "Te invitam sa te inscrii la eveniment prin comanda - add - din menul principal");
				
				return false;
			}

		default:
			System.out.println("Nu ati introdus o comanda valida. Incercati din nou: ");
			System.out.println("Introduceti criteriul de cautare\r\n1    - Cautare dupa nume si preume\r\n"
					+ "2    - Cautare dupa email\r\n" + "3    - Cautare dupa numarul de telefon\r\n");
			break;
		}
		userInput = sc.next();
		return false;
	}

	// 3. Eliminarea unei persoane (inscrise)

	public boolean removeGuest() {

		Scanner sc = new Scanner(System.in);
		System.out.println("Introduceti criteriul de cautare\r\n1    - Cautare dupa nume si preume\r\n"
				+ "2    - Cautare dupa email\r\n" + "3    - Cautare dupa numarul de telefon\r\n");

		int userInput = sc.nextInt();

		switch (userInput) {

		case 1:
			System.out.println("Introduceti numele: ");
			String userInputFirstName = sc.next();
			System.out.println("Introduceti prenumele: ");
			String userInputLastName = sc.next();
			System.out.println("Se cauta…  ");
			if (listRemoveByName(guestsList, userInputFirstName, userInputLastName)) {
				System.out.println("Stergerea persoanei s-a realizat cu succes.");
				System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");

				if (waitingList.size() > 0) {
					guestsList.add(waitingList.get(0));
					System.out.println("Participantul " + waitingList.get(0).getFirstName() + " "
							+ waitingList.get(0).getLastName() + " are acum locul confirmat la eveniment");
					System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
					waitingList.remove(0);
					this.nrOrdine--;
					
					return true;
				}
			} else if (listRemoveByName(waitingList, userInputFirstName, userInputLastName)) {
				System.out.println("Stergerea persoanei s-a realizat cu succes.");
				System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
				
				return true;
			} else {
				System.out.println("Persoana nu era inscrisa la eveniment sau in lista de asteptare.");
				System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
				
				return true;
			}
			break;

		case 2:
			System.out.println("Introduceti emailul: ");
			String userInputEmail = sc.next();
			System.out.println("Se cauta…  ");
			if (listRemoveByEmail(guestsList, userInputEmail)) {
				System.out.println("Stergerea persoanei s-a realizat cu succes.");
				System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");

				if (waitingList.size() > 0) {
					guestsList.add(waitingList.get(0));
					System.out.println("Participantul " + waitingList.get(0).getFirstName() + " "
							+ waitingList.get(0).getLastName() + " are acum locul confirmat la eveniment");
					System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
					waitingList.remove(0);
					this.nrOrdine--;
					
					return true;
				}
			} else if (listRemoveByEmail(waitingList, userInputEmail)) {
				System.out.println("Stergerea persoanei s-a realizat cu succes.");
				System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
				
				return true;
			} else {
				System.out.println("Persoana nu era inscrisa la eveniment sau in lista de asteptare.");
				System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
				
				return true;
			}
			break;

		case 3:
			System.out.println("Introduceti numarul de telefon: ");
			String userInputPhNo = sc.next();
			System.out.println("Se cauta…  ");
			if (listRemoveByPhone(guestsList, userInputPhNo)) {
				System.out.println("Stergerea persoanei s-a realizat cu succes. ");
				System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");

				if (waitingList.size() > 0) {
					guestsList.add(waitingList.get(0));
					System.out.println("Participantul " + waitingList.get(0).getFirstName() + " "
							+ waitingList.get(0).getLastName() + " are acum locul confirmat la eveniment");
					System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
					waitingList.remove(0);
					this.nrOrdine--;
					
					return true;
				}
			} else if (listRemoveByPhone(waitingList, userInputPhNo)) {
				System.out.println("Stergerea persoanei s-a realizat cu succes.");
				System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
				
				return true;
			} else {
				System.out.println("Persoana nu era inscrisa la eveniment sau in lista de asteptare.");
				System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
				
				return true;
			}
			break;

		default:
			break;

		}
		
		return false;

	}

	// 4. Actualizarea detaliilor unei persoane inscrise --> obs: cautare apoi
	// actualizare

	public boolean guestUpdate() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduceti criteriul de cautare\r\n1    - Cautare dupa nume si preume\r\n"
				+ "2    - Cautare dupa email\r\n" + "3    - Cautare dupa numarul de telefon\r\n");

		String userInput = sc.next();

		switch (userInput) {
		case "1":
			System.out.println("Introduceti numele: ");
			String userInputFirstN = sc.next();
			System.out.println("Introduceti prenumele: ");
			String userInputLastN = sc.next();
			if (returnGuestByName(guestsList, userInputFirstN, userInputLastN) != null) {
				Guest updateGuest = returnGuestByName(guestsList, userInputFirstN, userInputLastN);

				System.out.println(
						"Alege campul de actualizat, tastand\r\n1    - pentru nume \r\n" + "2    - pentru prenume\r\n"
								+ "3    - pentru email\r\n" + "4    - pentru numarul de telefon\r\n");

				String userInput2 = sc.next();
			
				updateAction(updateGuest, userInput2);

			} else if (returnGuestByName(waitingList, userInputFirstN, userInputLastN) != null) {
				Guest updateGuest = returnGuestByName(guestsList, userInputFirstN, userInputLastN);

				System.out.println(
						"Alege campul de actualizat, tastand\r\n1    - pentru nume \r\n" + "2    - pentru prenume\r\n"
								+ "3    - pentru email\r\n" + "4    - pentru numarul de telefon\r\n");

				String userInput2 = sc.next();
				
				updateAction(updateGuest, userInput2);

			}

			System.out.println("Actualizarea s-a realizat cu succes. ");
			System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
			
			return true;

		case "2":
			System.out.println("Introduceti emailul: ");
			String userInputEmail = sc.next();
			if (returnGuestByEmail(guestsList, userInputEmail) != null) {
				Guest updateGuest = returnGuestByEmail(guestsList, userInputEmail);

				System.out.println(
						"Alege campul de actualizat, tastand\r\n1    - pentru nume \r\n" + "2    - pentru prenume\r\n"
								+ "3    - pentru email\r\n" + "4    - pentru numarul de telefon\r\n");

				String userInput2 = sc.next();
				
				updateAction(updateGuest, userInput2);

			} else if (returnGuestByEmail(waitingList, userInputEmail) != null) {
				Guest updateGuest = returnGuestByEmail(guestsList, userInputEmail);

				System.out.println(
						"Alege campul de actualizat, tastand\r\n1    - pentru nume \r\n" + "2    - pentru prenume\r\n"
								+ "3    - pentru email\r\n" + "4    - pentru numarul de telefon\r\n");

				String userInput2 = sc.next();
				
				updateAction(updateGuest, userInput2);

			}

			System.out.println("Actualizarea s-a realizat cu succes. ");
			System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
			
			return true;

		case "3":

			System.out.println("Introduceti numarul de telefon: ");
			String userInputPhone = sc.next();
			if (returnGuestByPhone(guestsList, userInputPhone) != null) {
				Guest updateGuest = returnGuestByPhone(guestsList, userInputPhone);

				System.out.println(
						"Alege campul de actualizat, tastand\r\n1    - pentru nume \r\n" + "2    - pentru prenume\r\n"
								+ "3    - pentru email\r\n" + "4    - pentru numarul de telefon\r\n");

				String userInput2 = sc.next();
				
				updateAction(updateGuest, userInput2);

			} else if (returnGuestByPhone(waitingList, userInputPhone) != null) {
				Guest updateGuest = returnGuestByPhone(guestsList, userInputPhone);

				System.out.println(
						"Alege campul de actualizat, tastand\r\n1    - pentru nume \r\n" + "2    - pentru prenume\r\n"
								+ "3    - pentru email\r\n" + "4    - pentru numarul de telefon\r\n");

				String userInput2 = sc.next();
			
				updateAction(updateGuest, userInput2);

			}

			System.out.println("Actualizarea s-a realizat cu succes. ");
			System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
			
			return true;

		default:
			System.out.println(
					"Datele nu au fost gasite. Incercati din nou din meniul principal. Pentru detalii apasati help.");
			break;
		}
		
		return false;

	}

	public boolean updateAction(Guest guest, String modifier) {
		Scanner sc = new Scanner(System.in);

		switch (modifier) {
		case "1":
			System.out.println("Introduceti numele actualizat: ");
			String userInputNewFirstN = sc.next();
			guest.setFirstName(userInputNewFirstN);
			break;
		case "2":
			System.out.println("Introduceti prenumele actualizat: ");
			String userInputNewLastN = sc.next();
			guest.setLastName(userInputNewLastN);
			break;
		case "3":
			System.out.println("Introduceti noul email: ");
			String userInputNewEmail = sc.next();
			guest.setEmail(userInputNewEmail);
			break;
		case "4":
			System.out.println("Introduceti noul numar de telefon: ");
			String userInputNewPhone = sc.next();
			guest.setPhoneNumber(userInputNewPhone);
			break;
		}
		
		return true;
	}

	// 5. Obtinerea listei de persoane care au loc la eveniment (lista de
	// participare)
	public void getGuestsList() {
		if (guestsList.size() > 0) {
			System.out.println("In lista de participare sunt inscrisi urmatorii: ");
			for (int i = 0; i < guestsList.size(); i++) {
				System.out.println(guestsList.get(i).getFirstName() + " " + guestsList.get(i).getLastName() + " "
						+ guestsList.get(i).getEmail() + " " + guestsList.get(i).getPhoneNumber());
				System.out.println();
			}
		} else {
			System.out.println("Nu exista niciun participant inscris.");
			System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
		}
	}

	// 6. Obtinerea listei de persoane din lista de asteptare
	public void getWaitingList() {
		if (waitingList.size() > 0) {
			System.out.println("In lista de asteptare sunt inscrisi urmatorii: ");
			for (int i = 0; i < waitingList.size(); i++) {
				System.out.println(waitingList.get(i).getFirstName() + " " + waitingList.get(i).getLastName() + " "
						+ waitingList.get(i).getEmail() + " " + waitingList.get(i).getPhoneNumber());
				System.out.println();

			}
			System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
		} else {
			System.out.println("Nu exista nicio persoana in lista de asteptare. ");
			System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
		}
	}

	// 7. Obtinerea numarului de locuri disponibile in lista de participare

	public int slotsAvailable() {
		if (guestsList.size() == locuriDispnibile) {
			System.out.println("Nu mai exista locuri disponibile in lista de participanti.");
			System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
			return 0;
		}
		System.out.println(
				"Mai sunt " + (locuriDispnibile - guestsList.size()) + " locuri disponibile in lista de participanti.");
		System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
		return locuriDispnibile - guestsList.size();
	}

	// 8. Obtinerea numarului de persoane participante (aflate in lista de
	// participare)

	public int participantsNo() {
		System.out.println("Numarul participantilor este: " + guestsList.size());
		System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
		return guestsList.size();
	}

	// 9. Obtinerea numarului de persoane din lista de asteptare

	public int waitlistNo() {
		System.out.println("Numarul persoanelor din lista de asteptare este: " + waitingList.size());
		System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
		return waitingList.size();
	}

	// 10. Obtinerea numarului total de persoane (din ambele liste)

	public int subscribeNo() {
		System.out.println("Numarul persoanelor inscrise este: " + (guestsList.size() + waitingList.size()));
		System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
		return guestsList.size() + waitingList.size();
	}

	// 11. Cautare partiala, dupa un subsir de caractere (case insensitive, cautare
	// in toate campurile)

	public void search(String characters) {

		partialSearch(characters, guestsList);
		System.out.println("in lista de participanti. ");
		partialSearch(characters, waitingList);
		System.out.println("in lista de asteptare. ");
		System.out.println("Ati revenit in meniul principal. Pentru detalii apasati help.");
	}

	public Guest partialSearch(String characters, ArrayList<Guest> aList) {

		String caseIns = characters.toLowerCase();

		for (Guest guest : aList) {
			if (guest.getFirstName().contains(caseIns) || guest.getLastName().contains(caseIns)) {
				System.out.println("Rezultatele cautarii sunt: ");
				System.out.println(guest.getFirstName() + " " + guest.getLastName());
				return guest;
			} else if (guest.getEmail().contains(caseIns)) {
				System.out.println("Rezultatele cautarii sunt: ");
				System.out.println(guest.getEmail());
				return guest;
			} else if (guest.getPhoneNumber().contains(caseIns)) {
				System.out.println("Rezultatele cautarii sunt: ");
				System.out.println(guest.getPhoneNumber());
				return guest;

			} else {
				System.out.print("Cautarea nu a generat nicun rezultat ");
			}
		}

		return null;
	}

	// Metode adiacente

	// Metoda de cautare in liste dupa nume
	public boolean listSearchByName(ArrayList<Guest> aList, String firstN, String lastN) {

		for (Guest guest : aList) {
			if (guest.getFirstName().equalsIgnoreCase(firstN) && guest.getLastName().equalsIgnoreCase(lastN)) {
				return true;
			}
		}
		return false;
	}

	// Metoda de cautare in liste dupa email
	public boolean listSearchByEmail(ArrayList<Guest> aList, String email) {

		for (Guest guest : aList) {
			if (guest.getEmail().equalsIgnoreCase(email)) {
				return true;
			}
		}
		return false;
	}

	// Metoda de cautare in liste dupa numarul de telefon
	public boolean listSearchByPhone(ArrayList<Guest> aList, String arg) {

		for (Guest guest : aList) {
			if (guest.getPhoneNumber().equalsIgnoreCase(arg)) {
				return true;
			}
		}
		return false;
	}

	// Metoda de stergere in liste dupa nume
	public boolean listRemoveByName(ArrayList<Guest> aList, String firstN, String lastN) {

		for (Guest guest : aList) {
			if (guest.getFirstName().equalsIgnoreCase(firstN) && guest.getLastName().equalsIgnoreCase(lastN)) {
				aList.remove(guest);
				return true;
			}
		}
		return false;
	}

	// Metoda de stergere in liste dupa email
	public boolean listRemoveByEmail(ArrayList<Guest> aList, String email) {

		for (Guest guest : aList) {
			if (guest.getEmail().equalsIgnoreCase(email)) {
				aList.remove(guest);
				return true;
			}
		}
		return false;
	}

	// Metoda de stergere in liste dupa numarul de telefon
	public boolean listRemoveByPhone(ArrayList<Guest> aList, String phoneNo) {

		for (Guest guest : aList) {
			if (guest.getPhoneNumber().equalsIgnoreCase(phoneNo)) {
				aList.remove(guest);
				return true;
			}
		}
		return false;
	}

	// Metoda de returnare guest by name
	public Guest returnGuestByName(ArrayList<Guest> aList, String firstN, String lastN) {

		for (Guest guest : aList) {
			if (guest.getFirstName().equals(firstN) && guest.getLastName().equals(lastN)) {
				return guest;
			}
		}
		return null;
	}

	// Metoda de returnare guest by email
	public Guest returnGuestByEmail(ArrayList<Guest> aList, String email) {

		for (Guest guest : aList) {
			if (guest.getEmail().equals(email)) {
				return guest;
			}
		}
		return null;
	}

	// Metoda de returnare guest by phone number
	public Guest returnGuestByPhone(ArrayList<Guest> aList, String phNo) {

		for (Guest guest : aList) {
			if (guest.getPhoneNumber().equals(phNo)) {
				return guest;
			}
		}
		return null;
	}

	// Metoda de afisare a meniului principal
	public void printMenu() {
		System.out.println("help         - Afiseaza aceasta lista de comenzi\r\n"
				+ "add          - Adauga o noua persoana (inscriere)\r\n"
				+ "check        - Verifica daca o persoana este inscrisa la eveniment\r\n"
				+ "remove       - Sterge o persoana existenta din lista\r\n"
				+ "update       - Actualizeaza detaliile unei persoane\r\n"
				+ "guests       - Lista de persoane care participa la eveniment\r\n"
				+ "waitlist     - Persoanele din lista de asteptare\r\n" + "available    - Numarul de locuri libere\r\n"
				+ "guests_no    - Numarul de persoane care participa la eveniment\r\n"
				+ "waitlist_no  - Numarul de persoane din lista de asteptare\r\n"
				+ "subscribe_no - Numarul total de persoane inscrise\r\n"
				+ "search       - Cauta toti invitatii conform sirului de caractere introdus\r\n"
				+ "quit         - Inchide aplicatia");
		System.out.println("Va rugam selectati o comanda. Pentru lista comenzilor tastati \"help\".");
	}

}
