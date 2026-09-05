# Dental Clinic Management System — UML Diagrams

These diagrams are derived from the Java models, JSP pages, servlets, services,
DAOs, and `schema.sql` in this repository. GitHub and many Markdown editors can
render the Mermaid blocks directly.

## 1. Use-case diagram

The application exposes a single authenticated staff interface. The database
contains an `admin` role, so the primary actor is shown as clinic staff/admin.
Patients and dentists are managed records rather than direct system users.

```mermaid
flowchart LR
    Staff["Clinic Staff / Admin"]

    subgraph DCMS["Dental Clinic Management System"]
        Login([Log in])
        Logout([Log out])
        Dashboard([View dashboard])
        Patient([Register and view patients])
        Dentist([Register and view dentists])
        Treatment([Register and view treatments])
        Appointment([Book appointment])
        ExistingPatient([Select existing patient])
        NewPatient([Register new patient])
        SelectDentist([Select dentist])
        SelectTreatment([Select treatment])
        Search([Search appointments])
        Bill([Generate bill])
        Calculate([Calculate total and discount])
        Help([View help guide])
    end

    Staff --> Login
    Staff --> Logout
    Staff --> Dashboard
    Staff --> Patient
    Staff --> Dentist
    Staff --> Treatment
    Staff --> Appointment
    Staff --> Search
    Staff --> Bill
    Staff --> Help

    Appointment -. "includes" .-> SelectDentist
    Appointment -. "includes" .-> SelectTreatment
    ExistingPatient -. "extends" .-> Appointment
    NewPatient -. "extends" .-> Appointment
    Appointment -. "leads to" .-> Bill
    Bill -. "includes" .-> Calculate
```

## 2. Sequence diagram — book an appointment and generate its bill

This is the main end-to-end workflow implemented by `AppointmentServlet` and
`BillServlet`. The optional block represents choosing **New patient** in the
appointment form.

```mermaid
sequenceDiagram
    autonumber
    actor Staff as Clinic Staff / Admin
    participant UI as appointment.jsp
    participant AS as AppointmentServlet
    participant PS as PatientService
    participant PD as PatientDAO
    participant APS as AppointmentService
    participant AD as AppointmentDAO
    participant DB as MySQL Database
    participant BSv as BillServlet
    participant BS as BillService
    participant BD as BillDAO
    participant BUI as bill.jsp

    Staff->>UI: Open appointment page
    UI->>AS: GET /appointment
    AS->>PS: getAllPatients()
    PS->>PD: getAllPatients()
    PD->>DB: SELECT patients
    DB-->>PD: Patient rows
    PD-->>PS: List of patients
    PS-->>AS: List of patients
    Note over AS,DB: DentistService and TreatmentService similarly load dentists and treatments
    AS-->>UI: Forward lists to appointment.jsp

    Staff->>UI: Enter appointment details and submit
    UI->>AS: POST /appointment (action=add)

    opt Staff selected a new patient
        AS->>PS: addPatient(patient)
        PS->>PD: addPatient(patient)
        PD->>DB: INSERT patient
        DB-->>PD: Generated patient_id
        PD-->>AS: Success and patient_id
    end

    AS->>APS: addAppointment(appointment)
    APS->>AD: addAppointment(appointment)
    AD->>AD: Generate appointment number
    AD->>DB: INSERT appointment
    DB-->>AD: Generated appointment_id
    AD-->>APS: appointment_id
    APS-->>AS: appointment_id

    alt Appointment saved
        AS-->>BSv: Redirect GET /bill?appointmentId=...
        BSv->>APS: getAppointmentById(id)
        APS->>AD: getAppointmentById(id)
        AD->>DB: SELECT appointment with patient, dentist, treatment
        DB-->>AD: Appointment details
        AD-->>BSv: Appointment
        BSv-->>BUI: Forward appointment to bill.jsp

        Staff->>BUI: Enter fees/discount and submit
        BUI->>BSv: POST /bill
        BSv->>BS: calculateBill(consultation, treatment, discount)
        BS-->>BSv: total amount
        BSv->>BS: saveBill(bill)
        BS->>BD: saveBill(bill)
        BD->>DB: INSERT bill
        DB-->>BD: Rows affected
        BD-->>BSv: Success/failure
        BSv-->>BUI: Forward bill and status message
    else Appointment failed
        AS-->>UI: Reload page with error message
    end
```

## 3. Class diagram

The diagram shows the domain entities and the implemented Servlet–Service–DAO
layers. Routine getters/setters and servlet lifecycle methods are omitted to
keep it readable.

```mermaid
classDiagram
    direction LR

    class User {
        -int userId
        -String username
        -String password
        -String role
    }
    class Patient {
        -int patientId
        -String patientName
        -int age
        -String gender
        -String contactNumber
        -String email
        -String address
    }
    class Dentist {
        -int dentistId
        -String dentistName
        -String specialization
        -String contactNumber
        -String email
    }
    class Treatment {
        -int treatmentId
        -String treatmentName
    }
    class Appointment {
        -int appointmentId
        -int patientId
        -int dentistId
        -int treatmentId
        -String appointmentDate
        -String appointmentTime
        -String patientName
        -String dentistName
        -String treatmentName
    }
    class Bill {
        -int billId
        -int appointmentId
        -double consultationFee
        -double treatmentFee
        -double discount
        -double totalAmount
    }

    Patient "1" <-- "0..*" Appointment : booked for
    Dentist "1" <-- "0..*" Appointment : assigned to
    Treatment "1" <-- "0..*" Appointment : provides
    Appointment "1" <-- "0..1" Bill : billed by

    class LoginServlet
    class PatientServlet
    class DentistServlet
    class TreatmentServlet
    class AppointmentServlet
    class SearchAppointmentServlet
    class BillServlet
    class LogoutServlet

    class LoginService {
        +boolean login(username, password)
    }
    class PatientService {
        +List~Patient~ getAllPatients()
        +boolean addPatient(patient)
        +Patient getPatientById(id)
    }
    class DentistService {
        +List~Dentist~ getAllDentists()
        +boolean addDentist(dentist)
    }
    class TreatmentService {
        +List~Treatment~ getAllTreatments()
        +boolean addTreatment(treatment)
    }
    class AppointmentService {
        +int addAppointment(appointment)
        +boolean updateAppointment(appointment)
        +boolean deleteAppointment(id)
        +Appointment getAppointmentById(id)
        +List~Appointment~ searchAppointments(keyword)
        +List~Appointment~ getAllAppointments()
    }
    class BillService {
        +double calculateBill(consultationFee)
        +double calculateBill(consultationFee, treatmentFee)
        +double calculateBill(consultationFee, treatmentFee, discount)
        +boolean saveBill(bill)
    }

    class UserDAO {
        +boolean login(username, password)
        +User getUserByUsername(username)
    }
    class PatientDAO
    class DentistDAO
    class TreatmentDAO
    class AppointmentDAO
    class BillDAO
    class DBConnection {
        -Connection connection$
        +Connection getConnection()$
    }
    class ValidationUtil

    LoginServlet --> LoginService
    PatientServlet --> PatientService
    DentistServlet --> DentistService
    TreatmentServlet --> TreatmentService
    AppointmentServlet --> AppointmentService
    AppointmentServlet --> PatientService
    AppointmentServlet --> DentistService
    AppointmentServlet --> TreatmentService
    SearchAppointmentServlet --> AppointmentService
    BillServlet --> BillService
    BillServlet --> AppointmentService

    LoginService --> UserDAO
    PatientService --> PatientDAO
    DentistService --> DentistDAO
    TreatmentService --> TreatmentDAO
    AppointmentService --> AppointmentDAO
    BillService --> BillDAO

    UserDAO --> User
    PatientDAO --> Patient
    DentistDAO --> Dentist
    TreatmentDAO --> Treatment
    AppointmentDAO --> Appointment
    BillDAO --> Bill

    UserDAO ..> DBConnection
    PatientDAO ..> DBConnection
    DentistDAO ..> DBConnection
    TreatmentDAO ..> DBConnection
    AppointmentDAO ..> DBConnection
    BillDAO ..> DBConnection
```

## Implementation notes discovered during analysis

- `AppointmentDAO` expects an `appointments.appointment_number` column, but it
  is absent from `schema.sql`.
- `BillDAO` inserts `consultation_fee`, `treatment_fee`, and `total_amount`, while
  `schema.sql` defines `patient_id`, `amount`, `bill_date`, and `status` instead.
- The `Bill` model contains `discount`, but `BillDAO` does not persist it.
- Appointment update/delete operations exist in the service and DAO layers, but
  the current `AppointmentServlet` only exposes appointment creation.
- `AppointmentAPI.java` is empty, so no external web-service actor is included.

