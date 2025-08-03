# 🏢 Meeting Room Booking System (Java Swing)

A **desktop-based Java application** built using **Java Swing** to manage the booking of meeting rooms in a college. This system solves the problem of manual room scheduling by providing a simple, interactive, and offline GUI interface for users and administrators.

---

## 📌 Features

- 🔐 **Login & Registration** for Users and Admins  
- 🧑‍💼 **Role-Based Dashboards**  
  - Users: Book rooms, view and cancel their bookings  
  - Admins: View all bookings, cancel any user's booking  
- 📅 **Date & Time Slot Selection** with Dropdowns  
- 🖼️ GUI built using **Java Swing**  
- 💾 **File-based Storage** (No DB required)  
- ✅ **Conflict-Free Booking Validation**  
- 🚪 **Logout Confirmation**  
- 🎓 Designed for college seminar rooms / club meetings  

---

## 🛠️ Technologies Used

- Java  
- Java Swing (GUI)  
- OOP (Object-Oriented Programming)  
- File Handling (For Data Persistence)  

---

## 🔄 How It Works

1. 🧑‍🎓 User **registers or logs in** from the GUI  
2. 📅 Selects **date, time slot, and room** from dropdowns and books a slot  
3. ✅ System checks for booking conflicts and confirms the slot  
4. 📋 User can **view or cancel** their bookings from a table window  
5. 🚪 User can **logout with confirmation**  
6. 🧑‍💼 Admin logs in to **view all bookings**  
7. ❌ Admin can **cancel any booking** made by users  

---

## 📚 Real-Life College Use Case

This system solves a common college problem — managing room bookings for student clubs, faculty meetings, workshops, or guest lectures.

Instead of using registers or sending emails, students and staff can **book rooms instantly via the GUI**, ensuring no conflicts and better management.  
Admins (HODs or faculty coordinators) get full visibility and control over room usage.

---

## 💡 Future Enhancements

- 🗃️ MySQL/SQLite integration for database storage  
- ✅ Admin-based **approval system** before confirming bookings  
- 📧 Email notifications to users after booking/cancellation  
- 📤 Export bookings to Excel/PDF  

---

## ▶️ How to Run

To Compile: javac -encoding UTF-8 models\*.java services\*.java gui\*.java Main.java

To Run the application: java -cp . Main


## 👤 Author

**Mounika Malineni**  
🎓 College Project | 💻 Java Developer  
🔗 [GitHub Profile](https://github.com/mounikamalineni26)
