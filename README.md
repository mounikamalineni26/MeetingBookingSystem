# 🏢 Meeting Room Booking System (Java Console App)

A simple Java console-based application to **book**, **view**, and **cancel** meeting rooms. It supports both **admin** and **user** roles, with all data saved to a local file.

---

## 👨‍💻 Features
- ✅ Book rooms with date and time slot
- ✅ Cancel and view your own bookings
- ✅ Admin can view all users' bookings
- ✅ Bookings saved persistently in `bookings.txt`
- 🔐 Case-insensitive user recognition

---

## 🔧 Technologies Used
- Java (OOP)
- File I/O (`BufferedReader`, `BufferedWriter`)
- Collections (`ArrayList`)
- CLI (Console-Based)

---

## 📁 Project Structure

```
src/
├── Main.java
├── models/
│   └── Booking.java
├── services/
│   └── BookingService.java
bookings.txt
```

---

## ▶️ How to Run

```bash
cd src
javac Main.java models/Booking.java services/BookingService.java
java -cp . Main
 ```

---

## 🧾 Sample Output

```
Are you a user or admin? (user/admin): user
Enter your name: mounika

--- Meeting Room Booking ---
1. Book a room
2. View bookings
3. Cancel booking
4. Exit
Choose option: 1
Enter room name: BoardRoom
Enter date (YYYY-MM-DD): 2025-07-12
Enter time slot (e.g. 10:00-11:00): 10:00-11:00
[✅] Booking successful!

Choose option: 2
Booking: mounika | BoardRoom | 2025-07-12 | 10:00-11:00
```

---

## 💼 Example Use Case

Mounika, an employee, wants to book a meeting room for a client call. She opens the console app, logs in as a user, selects "Book a room", and confirms the time slot. Admins can later view or manage all bookings.

---

## 📚 What I Learned

- Working with Java File I/O and text-based persistence
- Implementing clean user flows in console applications
- Managing modular logic using OOP (Model + Service architecture)
- Handling role-based access (Admin vs User)

---








