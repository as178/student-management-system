/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import concrete_classes.other.PopUpUtil;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * This class provides the methods to create all the necessary tables for our
 * program as well as the insertion of initial data that comes with the program.
 * If any tables already exist, there is a method in place to ensure they are
 * dropped before being created.
 *
 */
public class DatabaseTablesCreation {

    private Connection connection;
    private Statement statement;

    //Constructor to pass in the connection from the DatabaseManager
    public DatabaseTablesCreation(Connection connection) {
        this.connection = connection;
    }

    /*
    This method will create our tables, calling each create method in
    appropriate order, such that no foreign key mappings cause errors
    due to tables not existing.
     */
    public void createTables() {
        try {
            this.statement = this.connection.createStatement();

            if (!isExistingTable("Major")) {
                createMajorTable();
                insertIntoMajor();
            }
            if (!isExistingTable("Admin")) {
                createAdminTable();
                insertIntoAdmin();
            }
            if (!isExistingTable("Lecturer")) {
                createLecturerTable();
                insertIntoLecturer();
            }
            if (!isExistingTable("Course")) {
                createCourseTable();
                insertIntoCourse();
            }
            if (!isExistingTable("Student")) {
                createStudentTable();
                insertIntoStudent();
            }
            if (!isExistingTable("EnrolledCourse")) {
                createEnrolledCourseTable();
                insertIntoEnrolledCourse();
            }
            if (!isExistingTable("PreviousCourse")) {
                createPreviousCourseTable();
                insertIntoPreviousCourse();
            }

        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }
    }

    /*
    Below are methods for creating individual tables.
     */
    private void createMajorTable() {
        try {
            this.statement.executeUpdate(
                    "CREATE TABLE Major (\n"
                    + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n"
                    + "name VARCHAR(50) UNIQUE NOT NULL\n"
                    + ")"
            );
        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }
    }

    private void createAdminTable() {
        try {
            this.statement.executeUpdate(
                    "CREATE TABLE Admin (\n"
                    + "id INT PRIMARY KEY CHECK (id BETWEEN 10500000 AND 11000000),\n"
                    + "password VARCHAR(30) NOT NULL CHECK (LENGTH(password) BETWEEN 8 AND 30),\n"
                    + "first_name VARCHAR(50) NOT NULL,\n"
                    + "last_name VARCHAR(50) NOT NULL,\n"
                    + "date_of_birth DATE NOT NULL,\n"
                    + "personal_email VARCHAR(50) UNIQUE NOT NULL,\n"
                    + "university_email VARCHAR(50) UNIQUE NOT NULL,\n"
                    + "phone_number VARCHAR(14) UNIQUE NOT NULL,\n"
                    + "gender CHAR(1) CHECK (gender IN ('M', 'F')) NOT NULL,\n"
                    + "address VARCHAR(255) NOT NULL\n"
                    + ")"
            );
        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }
    }

    private void createLecturerTable() {
        try {
            this.statement.executeUpdate(
                    "CREATE TABLE Lecturer (\n"
                    + "id INT PRIMARY KEY CHECK (id BETWEEN 14000000 AND 16000000),\n"
                    + "password VARCHAR(30) NOT NULL CHECK (LENGTH(password) BETWEEN 8 AND 30),\n"
                    + "first_name VARCHAR(50) NOT NULL,\n"
                    + "last_name VARCHAR(50) NOT NULL,\n"
                    + "date_of_birth DATE NOT NULL,\n"
                    + "personal_email VARCHAR(50) UNIQUE NOT NULL,\n"
                    + "university_email VARCHAR(50) UNIQUE NOT NULL,\n"
                    + "phone_number VARCHAR(14) UNIQUE NOT NULL,\n"
                    + "gender CHAR(1) CHECK (gender IN ('M', 'F')) NOT NULL,\n"
                    + "address VARCHAR(255) NOT NULL,\n"
                    + "faculty VARCHAR(50) NOT NULL,\n"
                    + "FOREIGN KEY (faculty) REFERENCES Major(name)\n"
                    + ")"
            );
        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }
    }

    private void createCourseTable() {
        try {
            this.statement.executeUpdate(
                    "CREATE TABLE Course (\n"
                    + "code VARCHAR(7) PRIMARY KEY,\n"
                    + "name VARCHAR(46) NOT NULL CHECK (LENGTH(name) BETWEEN 4 AND 46),\n"
                    + "major VARCHAR(50) NOT NULL,\n"
                    + "prerequisite_code VARCHAR(7),\n"
                    + "estimated_hours INT NOT NULL CHECK (estimated_hours BETWEEN 1 AND 150),\n"
                    + "lecturer_id INT,\n"
                    + "description VARCHAR(70) NOT NULL CHECK (LENGTH(description) BETWEEN 5 AND 70),\n"
                    + "FOREIGN KEY (lecturer_id) REFERENCES Lecturer(id),\n"
                    + "FOREIGN KEY (prerequisite_code) REFERENCES Course(code),\n"
                    + "FOREIGN KEY (major) REFERENCES Major(name)\n"
                    + ")"
            );
        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }
    }

    private void createStudentTable() {
        try {
            this.statement.executeUpdate(
                    "CREATE TABLE Student (\n"
                    + "id INT PRIMARY KEY CHECK (id BETWEEN 20000000 AND 29999999),\n"
                    + "password VARCHAR(30) NOT NULL CHECK (LENGTH(password) BETWEEN 8 AND 30),\n"
                    + "first_name VARCHAR(50) NOT NULL,\n"
                    + "last_name VARCHAR(50) NOT NULL,\n"
                    + "date_of_birth DATE NOT NULL,\n"
                    + "personal_email VARCHAR(50) UNIQUE NOT NULL,\n"
                    + "university_email VARCHAR(50) UNIQUE NOT NULL,\n"
                    + "phone_number VARCHAR(14) UNIQUE NOT NULL,\n"
                    + "gender CHAR(1) CHECK (gender IN ('M', 'F')) NOT NULL,\n"
                    + "address VARCHAR(255) NOT NULL,\n"
                    + "major VARCHAR(50) NOT NULL,\n"
                    + "FOREIGN KEY (major) REFERENCES Major(name)\n"
                    + ")"
            );
        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }
    }

    private void createEnrolledCourseTable() {
        try {
            this.statement.executeUpdate(
                    "CREATE TABLE EnrolledCourse (\n"
                    + "student_id INT NOT NULL,\n"
                    + "course_code VARCHAR(7) NOT NULL,\n"
                    + "grade FLOAT CHECK (grade IS NULL OR grade = -1 OR (grade >= 0.0 AND grade <= 100.0)),\n"
                    + "PRIMARY KEY (student_id, course_code),\n"
                    + "FOREIGN KEY (student_id) REFERENCES Student(id),\n"
                    + "FOREIGN KEY (course_code) REFERENCES Course(code)\n"
                    + ")"
            );
        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }
    }

    private void createPreviousCourseTable() {
        try {
            this.statement.executeUpdate(
                    "CREATE TABLE PreviousCourse (\n"
                    + "student_id INT NOT NULL,\n"
                    + "course_code VARCHAR(7) NOT NULL,\n"
                    + "grade FLOAT CHECK (grade IS NULL OR grade = -1 OR (grade >= 0.0 AND grade <= 100.0)),\n"
                    + "PRIMARY KEY (student_id, course_code),\n"
                    + "FOREIGN KEY (student_id) REFERENCES Student(id),\n"
                    + "FOREIGN KEY (course_code) REFERENCES Course(code)\n"
                    + ")"
            );
        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }
    }

    /*
    A method to check whether a table exists in the program database already,
    appropriate boolean value is returned.
     */
    private boolean isExistingTable(String givenTableName) {
        try {
            DatabaseMetaData metaData = this.connection.getMetaData();
            String[] stringArray = {"TABLE"};
            ResultSet resultSet = metaData.getTables(null, null, null, stringArray);

            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(givenTableName) == 0) {
                    return true;
                }
            }

            if (resultSet != null) {
                resultSet.close();
            }

        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }

        return false;
    }

    /*
    Below are methods for default data insertion, which execute after each of the
    respective tables is made.
     */
    private void insertIntoMajor() {
        try {
            this.statement.executeUpdate(
                    "INSERT INTO Major VALUES\n"
                    + "(DEFAULT, 'Computer Science'),\n"
                    + "(DEFAULT, 'Data Science'),\n"
                    + "(DEFAULT, 'Networks & Cybersecurity'),\n"
                    + "(DEFAULT, 'Digital Services'),\n"
                    + "(DEFAULT, 'Software Development')"
            );
        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }
    }

    private void insertIntoAdmin() {
        try {
            this.statement.executeUpdate(
                    "INSERT INTO Admin VALUES\n"
                    + "(10567001, 'adminpassword123', 'Isla', 'Roberts', '1999-08-21', 'robertsIsla@gmail.com', 'isla.roberts@aut.ac.nz', '0213456789', 'F', '32 Hillcrest Way Greenlane Auckland 1061'),\n"
                    + "(10567002, 'bravoSecure', 'Leo', 'Martinez', '2002-12-03', 'leoZ2002@icloud.com', 'leo.martinez@aut.ac.nz', '0214567890', 'M', '88 Pinewood Dr Mount Roskill Auckland 1041'),\n"
                    + "(10567003, 'charlieDev', 'Ava', 'Kumar', '1998-04-15', 'kumara2@yahoo.com', 'ava.kumar@aut.ac.nz', '0215678901', 'F', '7 Willow Ct Epsom Auckland 1023')"
            );
        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }
    }

    private void insertIntoLecturer() {
        try {
            this.statement.executeUpdate("INSERT INTO Lecturer VALUES\n"
                    + "(14568912, 'BrightTiger7', 'Melissa', 'Williams', '1975-04-12', 'wg97j7x@gmail.com', 'melissa.williams@aut.ac.nz', '0217896541', 'F', '25 Oak Grove Road Remuera Auckland 1050', 'Computer Science'),\n"
                    + "(14259876, 'lectPass098', 'Richard', 'Nguyen', '1980-09-30', 'tigerwave@icloud.com', 'richard.nguyen@aut.ac.nz', '+64211234567', 'M', '84 Sunset Valley Trail Mount Eden Auckland 1024', 'Data Science'),\n"
                    + "(14678943, 'StormDragon33', 'Susan', 'Patel', '1983-02-14', 'snowdrop78@yahoo.com', 'susan.patel@aut.ac.nz', '0224681357', 'F', '91 Maple Blossom Street Onehunga Auckland 1061', 'Networks & Cybersecurity'),\n"
                    + "(14321597, 'GreenStar91', 'James', 'Lee', '1978-07-09', 'greenjungle@gmail.com', 'james.lee@aut.ac.nz', '+64219873456', 'M', '12 Ocean Crest Avenue Mission Bay Auckland 1071', 'Digital Services'),\n"
                    + "(15234891, 'NightWolf24', 'Amy', 'Clark', '1982-10-21', 'nwvyxz77@icloud.com', 'amy.clark@aut.ac.nz', '0209876543', 'F', '33 River Glen Parkway Newmarket Auckland 1023', 'Software Development'),\n"
                    + "(15369752, 'SwiftFalcon17', 'Brian', 'Walker', '1979-05-03', 'techwarrior@gmail.com', 'brian.walker@aut.ac.nz', '0219988776', 'M', '55 Thunder Hill Lane Mt Wellington Auckland 1060', 'Computer Science'),\n"
                    + "(15489762, 'CrystalSky89', 'Rachel', 'Ng', '1985-12-18', 'crystalgirl@yahoo.com', 'rachel.ng@aut.ac.nz', '+64210001112', 'F', '76 Whisper Hollow Road Parnell Auckland 1052', 'Networks & Cybersecurity'),\n"
                    + "(15512344, 'BlazingWind55', 'Jason', 'Hall', '1981-03-25', 'winterfox@icloud.com', 'jason.hall@aut.ac.nz', '0224567890', 'M', '14 Emerald Springs Avenue St Heliers Auckland 1071', 'Data Science'),\n"
                    + "(14653278, 'OceanRay42', 'Linda', 'Martinez', '1977-08-29', 'lrmzxd22@gmail.com', 'linda.martinez@aut.ac.nz', '0215678349', 'F', '101 Meadow Ridge Trail Avondale Auckland 1026', 'Digital Services'),\n"
                    + "(15194623, 'IronLeaf39', 'Thomas', 'Brown', '1984-11-06', 'ironmanleaf@yahoo.com', 'thomas.brown@aut.ac.nz', '0211239876', 'M', '67 Crystal Bay Court Glen Innes Auckland 1072', 'Software Development'),\n"
                    + "(14476589, 'GoldenSun12', 'Natalie', 'Kaur', '1986-01-04', 'goldflare@icloud.com', 'natalie.kaur@aut.ac.nz', '+64213245768', 'F', '19 Breeze Ridge Lane Blockhouse Bay Auckland 0600', 'Computer Science'),\n"
                    + "(14591234, 'HiddenStream88', 'Edward', 'Thompson', '1976-06-19', 'hstream88@gmail.com', 'edward.thompson@aut.ac.nz', '0217654321', 'M', '108 Forest Hills Trail Mount Albert Auckland 1025', 'Data Science'),\n"
                    + "(15567890, 'SilentComet60', 'Laura', 'Murphy', '1980-02-23', 'silentcomet@yahoo.com', 'laura.murphy@aut.ac.nz', '0221112244', 'F', '42 Autumn Ridge Parkway Henderson Auckland 0610', 'Networks & Cybersecurity'),\n"
                    + "(15123489, 'QuantumNova77', 'Steven', 'Young', '1983-09-11', 'quantnova@gmail.com', 'steven.young@aut.ac.nz', '0206547891', 'M', '38 Twilight Grove Street Ellerslie Auckland 1051', 'Digital Services'),\n"
                    + "(14367890, 'ElectricWave19', 'Karen', 'Davis', '1987-07-07', 'electricwave19@yahoo.com', 'karen.davis@aut.ac.nz', '0214561237', 'F', '90 Tranquil Waters Boulevard Meadowbank Auckland 1072', 'Software Development'),\n"
                    + "(14577881, 'ShiningFalcon99', 'Isabelle', 'Jones', '1975-06-21', 'shinefalcon99@gmail.com', 'isabelle.jones@aut.ac.nz', '0219876543', 'F', '76 Beachwood Drive Point Chevalier Auckland 1022', 'Computer Science'),\n"
                    + "(15181239, 'FireStorm01', 'Daniel', 'White', '1980-03-29', 'stormdaniel@icloud.com', 'daniel.white@aut.ac.nz', '0223456788', 'M', '101 Starview Crescent Howick Auckland 2014', 'Data Science'),\n"
                    + "(14453392, 'LightningBolt11', 'Anna', 'Taylor', '1978-08-17', 'lightningbolt11@yahoo.com', 'anna.taylor@aut.ac.nz', '0212345678', 'F', '56 Forest Ridge Avenue Grey Lynn Auckland 1021', 'Networks & Cybersecurity'),\n"
                    + "(15343112, 'SilverFlash22', 'Christopher', 'Adams', '1986-02-14', 'silverflash22@icloud.com', 'christopher.adams@aut.ac.nz', '0223456789', 'M', '23 Crescent Street Mt Roskill Auckland 1041', 'Digital Services'),\n"
                    + "(14558713, 'testingIt@#1', 'Sharon', 'Base', '1970-10-20', 'shazzz@gmail.com', 'sharon.base@aut.ac.nz', '0224569782', 'F', '16 Rogers Avenue Pakuranga Auckland 2010', 'Software Development')"
            );
        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }
    }

    private void insertIntoCourse() {
        try {
            this.statement.executeUpdate("INSERT INTO Course VALUES\n"
                    + "('DATA604', 'Big Data Technologies', 'Data Science', 'DATA501', 150, 15181239, 'Introduces Hadoop, Spark, and NoSQL data systems.'),\n"
                    + "('DATA605', 'Data Visualization and Storytelling', 'Data Science', 'STAT602', 145, 15512344, 'Transforms data into compelling visual insights.'),\n"
                    + "('DATA606', 'Machine Learning in Practice', 'Data Science', 'DATA610', 150, 14591234, 'Hands-on ML projects with real datasets and Python libs.'),\n"
                    + "('DATA607', 'Natural Language Processing', 'Data Science', 'DATA606', 145, 15181239, 'Focuses on text analysis, sentiment, and language models.'),\n"
                    + "('INFS603', 'User-Centered Design', 'Digital Services', 'DIGI501', 140, 14653278, 'Designing services tailored to user needs and experience.'),\n"
                    + "('INFS604', 'Information Systems Strategy', 'Digital Services', 'DIGI604', 150, 15343112, 'Aligns info systems with organizational goals and growth.'),\n"
                    + "('DIGI610', 'IT Project Management', 'Digital Services', NULL, 145, 14321597, 'Covers planning, execution, and monitoring of projects.'),\n"
                    + "('DIGI611', 'Design Thinking in IT', 'Digital Services', 'DIGI501', 140, 14653278, 'Applies design thinking to solve service challenges.'),\n"
                    + "('STAT602', 'Applied Statistics', 'Data Science', 'DATA501', 145, 15512344, 'Real-world applications of stats analysis and visualization.'),\n"
                    + "('COMP610', 'Human-Computer Interaction', 'Computer Science', 'COMP500', 140, 14476589, 'Design and evaluation of user interfaces and systems.'),\n"
                    + "('DIGI612', 'Emerging Technologies and Innovation', 'Digital Services', 'DIGI604', 145, 15123489, 'Studies AI, blockchain, and XR impact on digital services.'),\n"
                    + "('COMP611', 'Parallel and Distributed Systems', 'Computer Science', 'COMP603', 150, 14577881, 'Intro to parallel computing and distributed architectures.'),\n"
                    + "('SOFT612', 'Advanced Software Design Patterns', 'Software Development', 'SOFT603', 150, 14558713, 'Focuses on reusable solutions and architecture patterns.'),\n"
                    + "('CYBR601', 'Principles of Cybersecurity', 'Networks & Cybersecurity', 'NETS501', 145, 14678943, 'Covers core security concepts: risks, threats, controls.'),\n"
                    + "('SOFT611', 'Software Testing & QA', 'Software Development', 'SOFT602', 140, 14367890, 'Explores unit, integration, and system testing strategies.'),\n"
                    + "('CYBR604', 'Cryptography and Network Security', 'Networks & Cybersecurity', 'CYBR601', 150, 15567890, 'Explores encryption, SSL/TLS, and authentication.'),\n"
                    + "('DATA501', 'Introduction to Data Science', 'Data Science', NULL, 140, 14259876, 'Covers tools and basic methods used in data workflows.'),\n"
                    + "('CYBR605', 'Security Risk Management', 'Networks & Cybersecurity', 'CYBR601', 145, 14678943, 'Frameworks and tools for managing cybersecurity risks.'),\n"
                    + "('CYBR606', 'Digital Forensics', 'Networks & Cybersecurity', 'CYBR604', 150, 15567890, 'Investigates crime scenes, recovery, and legal aspects.'),\n"
                    + "('SOFT610', 'Secure Coding Practices', 'Software Development', 'SOFT602', 145, 15194623, 'Best practices for writing secure and resilient software.'),\n"
                    + "('SOFT608', 'Mobile App Development', 'Software Development', 'SOFT603', 150, 15234891, 'Designs and builds apps for Android and iOS platforms.'),\n"
                    + "('SOFT605', 'Version Control and DevOps', 'Software Development', 'SOFT501', 140, 14558713, 'Teaches Git, CI/CD pipelines, and automation principles.'),\n"
                    + "('NETS603', 'Wireless and Mobile Networks', 'Networks & Cybersecurity', 'NETS501', 140, 15489762, 'Examines 4G/5G, mobile IP, and security challenges.'),\n"
                    + "('NETS501', 'Networking Fundamentals', 'Networks & Cybersecurity', NULL, 140, 14453392, 'Intro to OSI model, protocols, and packet transmission.'),\n"
                    + "('DIGI501', 'Introduction to Digital Services', 'Digital Services', NULL, 135, 14321597, 'Foundations of digital services in modern IT ecosystems.'),\n"
                    + "('NETS606', 'IoT Network Design', 'Networks & Cybersecurity', 'NETS501', 140, 15489762, 'Builds secure, scalable IoT systems and protocols.'),\n"
                    + "('NETS605', 'Cloud Infrastructure', 'Networks & Cybersecurity', 'NETS501', 145, 14453392, 'Covers cloud architecture, virtualization, deployment.'),\n"
                    + "('DIGI606', 'Service-Oriented Architectures', 'Digital Services', 'DIGI604', 150, 15343112, 'Explores SOA principles and their use in digital platforms.'),\n"
                    + "('COMP500', 'Introduction to Computer Science', 'Computer Science', NULL, 140, 14568912, 'Foundations in computing including logic and programming.'),  \n"
                    + "('COMP501', 'Discrete Structures in Computing', 'Computer Science', 'COMP500', 145, 15369752, 'Intro to discrete math, logic, and set theory in computing.'),\n"
                    + "('DIGI604', 'Digital Transformation Strategies', 'Digital Services', 'DIGI501', 145, 15123489, 'Study of innovation, agility, and transformation.'),\n"
                    + "('COMP603', 'Operating Systems Concepts', 'Computer Science', 'COMP601', 145, 14577881, 'Covers memory management, processes, and system calls.'),\n"
                    + "('SOFT501', 'Foundations of Software Development', 'Software Development', NULL, 140, 15234891, 'Introduces software lifecycles and coding basics.'),\n"
                    + "('COMP601', 'Object-Oriented Programming', 'Computer Science', 'COMP500', 150, 14476589, 'Principles of OOP using Java with practical focus.'),\n"
                    + "('SOFT603', 'Web Application Development', 'Software Development', 'SOFT501', 150, 14367890, 'Develops full-stack apps using HTML, CSS, JS, frameworks.'),\n"
                    + "('DATA610', 'Ethics in Data Science', 'Data Science', NULL, 130, 14259876, 'Analyzes data privacy, fairness, and ethics.'),\n"
                    + "('SOFT602', 'Agile Development Techniques', 'Software Development', 'SOFT501', 145, 15194623, 'Focuses on Agile, Scrum, and iterative delivery.'),\n"
                    + "('COMP607', 'Data Mining and Machine Learning', 'Data Science', 'STAT602', 150, 14591234, 'Focuses on algorithms like clustering and classification.'),\n"
                    + "('COMP608', 'Algorithms and Data Structures II', 'Computer Science', 'COMP601', 150, 15369752, 'In-depth study of algorithms and data structures.'),\n"
                    + "('COMP606', 'Computer Architecture', 'Computer Science', 'COMP500', 140, 14568912, 'Explores CPU design, memory hierarchy, and logic.')"
            );
        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }
    }

    private void insertIntoStudent() {
        try {
            this.statement.executeUpdate("INSERT INTO Student VALUES\n"
                    + "(24229618, 'mySchoolPass', 'John', 'Doe', '1999-06-13', 'driftshot88@yahoo.com', 'john.doe@aut.ac.nz', '0211234567', 'M', '123 Main St Mount Albert Auckland 1025', 'Computer Science'),\n"
                    + "(24237573, 'myPassStudent', 'Jane', 'Smith', '2000-09-20', 'icecloud47@gmail.com', 'jane.smith@aut.ac.nz', '+64213456789', 'F', '40 Smith Rd Epsom Auckland 1051', 'Computer Science'),\n"
                    + "(23123456, 'omegaFlash91', 'Alan', 'Brown', '2001-11-30', 'stormfire99@icloud.com', 'alan.brown@aut.ac.nz', '0203456789', 'M', '789 Pine St Sandringham Auckland 1025', 'Networks & Cybersecurity'),\n"
                    + "(21478523, 'crimsonLeaf7', 'Emily', 'White', '2000-02-11', 'berryjuice7@yahoo.com', 'emily.white@aut.ac.nz', '0229876543', 'F', '321 Birch St Northcote Auckland 0627', 'Data Science'),\n"
                    + "(22245678, 'moonriseStar22', 'Michael', 'Green', '1998-12-05', 'foxtrack92@gmail.com', 'michael.green@aut.ac.nz', '+64213456712', 'M', '654 Elm St Mount Eden Auckland 1024', 'Digital Services'),\n"
                    + "(23546789, 'orchidTree99', 'Sarah', 'Black', '1999-07-14', 'lunabeam33@icloud.com', 'sarah.black@aut.ac.nz', '0216543210', 'F', '987 Maple St Mission Bay Auckland 1071', 'Networks & Cybersecurity'),\n"
                    + "(24689123, 'zebraRunner42', 'David', 'Lee', '2000-09-18', 'rockwolf78@gmail.com', 'david.lee@aut.ac.nz', '+64219827364', 'M', '135 Cedar St Greenlane Auckland 1051', 'Digital Services'),\n"
                    + "(21746392, 'deltaRacer12', 'Laura', 'Harris', '2001-01-23', 'silverflame22@yahoo.com', 'laura.harris@aut.ac.nz', '0221453987', 'F', '246 Redwood St Glen Innes Auckland 1072', 'Software Development'),\n"
                    + "(23457981, 'violetTiger55', 'James', 'Wilson', '2000-03-29', 'gearboost18@gmail.com', 'james.wilson@aut.ac.nz', '0218761234', 'M', '369 Fir St New Lynn Auckland 0600', 'Software Development'),\n"
                    + "(24197635, 'sunsetWave88', 'Anna', 'Clark', '1998-10-17', 'orbitalspin44@icloud.com', 'anna.clark@aut.ac.nz', '0212468135', 'F', '741 Willow St Manukau Auckland 2104', 'Software Development'),\n"
                    + "(21487953, 'novaSparkle1', 'Ethan', 'Nguyen', '1999-06-02', 'stormwheel29@yahoo.com', 'ethan.nguyen@aut.ac.nz', '+64214738219', 'M', '81 Riverbank St Pakuranga Auckland 2010', 'Computer Science'),\n"
                    + "(22431975, 'shadowPath77', 'Alice', 'King', '2000-04-13', 'dawnflash35@gmail.com', 'alice.king@aut.ac.nz', '0217439268', 'F', '92 Moonrise St Mount Roskill Auckland 1041', 'Digital Services'),\n"
                    + "(23315642, 'rapidLion99', 'Brian', 'Wu', '1998-12-20', 'skytide76@icloud.com', 'brian.wu@aut.ac.nz', '0201112456', 'M', '58 Forest Hill Hillcrest Auckland 0627', 'Networks & Cybersecurity'),\n"
                    + "(21247698, 'winterGlow33', 'Chloe', 'Rose', '2001-03-27', 'wildtree55@yahoo.com', 'chloe.rose@aut.ac.nz', '0213928172', 'F', '19 Thunder Ln Henderson Auckland 0610', 'Data Science'),\n"
                    + "(24538976, 'falconDash25', 'Daniel', 'Kaur', '2000-08-16', 'nightarrow38@gmail.com', 'daniel.kaur@aut.ac.nz', '0221984736', 'M', '27 Summit Hill Howick Auckland 2014', 'Software Development'),\n"
                    + "(22158793, 'braveEcho66', 'Olivia', 'Turner', '1999-11-04', 'lightpath79@icloud.com', 'olivia.turner@aut.ac.nz', '+64213489174', 'F', '73 Horizon Rd Meadowbank Auckland 1072', 'Computer Science'),\n"
                    + "(23214768, 'cosmicDrift7', 'Noah', 'Wong', '2000-07-10', 'darkflame18@yahoo.com', 'noah.wong@aut.ac.nz', '0203948721', 'M', '64 Sunset Blvd Point Chevalier Auckland 1022', 'Networks & Cybersecurity'),\n"
                    + "(24357692, 'risingComet93', 'Mia', 'Patel', '1998-09-01', 'gearcore92@gmail.com', 'mia.patel@aut.ac.nz', '0219834751', 'F', '31 Galaxy Rise Newmarket Auckland 1023', 'Data Science'),\n"
                    + "(22678912, 'jetBlazer52', 'Liam', 'Evans', '2001-05-25', 'arcstream88@icloud.com', 'liam.evans@aut.ac.nz', '0228365912', 'M', '50 Volcano Rd Mount Wellington Auckland 1060', 'Digital Services'),\n"
                    + "(21837645, 'blazeHawk34', 'Ava', 'Scott', '1999-10-08', 'nimbleshade64@gmail.com', 'ava.scott@aut.ac.nz', '+64217894567', 'F', '86 Crystal Grove Parnell Auckland 1052', 'Computer Science')"
            );
        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }
    }

    private void insertIntoEnrolledCourse() {
        try {
            this.statement.executeUpdate("INSERT INTO EnrolledCourse VALUES\n"
                    + "(24229618, 'COMP501', NULL),\n"
                    + "(24229618, 'COMP601', 97.67),\n"
                    + "(24229618, 'COMP606', 98.34),\n"
                    + "(21487953, 'COMP611', 86.46),\n"
                    + "(21487953, 'COMP608', NULL),\n"
                    + "(22158793, 'COMP500', NULL),\n"
                    + "(21837645, 'COMP500', NULL),\n"
                    + "(21478523, 'DATA610', 55.40),\n"
                    + "(21247698, 'DATA606', 100.0),\n"
                    + "(21247698, 'DATA501', 97.84),\n"
                    + "(24357692, 'DATA605', 90.10),\n"
                    + "(24357692, 'DATA607', 84.20),\n"
                    + "(22245678, 'DIGI610', NULL),\n"
                    + "(24689123, 'DIGI610', NULL),\n"
                    + "(22431975, 'DIGI604', 96.30),\n"
                    + "(22678912, 'DIGI501', NULL),\n"
                    + "(23123456, 'NETS501', NULL),\n"
                    + "(23546789, 'NETS501', 65.70),\n"
                    + "(23315642, 'CYBR601', 89.79),\n"
                    + "(23214768, 'CYBR606', 97.86),\n"
                    + "(23214768, 'NETS606', 100.0),\n"
                    + "(23214768, 'NETS605', 78.49),\n"
                    + "(21746392, 'SOFT501', 98.99),\n"
                    + "(23457981, 'SOFT602', 76.50),\n"
                    + "(23457981, 'SOFT603', 80.0),\n"
                    + "(24197635, 'SOFT501', NULL),\n"
                    + "(24538976, 'SOFT612', 93.70),\n"
                    + "(24538976, 'SOFT610', 90.93)"
            );
        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }
    }

    private void insertIntoPreviousCourse() {
        try {
            this.statement.executeUpdate("INSERT INTO PreviousCourse VALUES\n"
                    + "(24229618, 'COMP500', 100.0),\n"
                    + "(21487953, 'COMP601', 89.89),\n"
                    + "(21487953, 'COMP603', 76.54),\n"
                    + "(21247698, 'DATA610', 97.55),\n"
                    + "(24357692, 'DATA610', 100.0),\n"
                    + "(24357692, 'DATA606', 69.70),\n"
                    + "(24357692, 'STAT602', 88.50),\n"
                    + "(24689123, 'DIGI501', -1.0),\n"
                    + "(22431975, 'DIGI501', 55.60),\n"
                    + "(22431975, 'DIGI610', 78.90),\n"
                    + "(23546789, 'NETS501', 90.00),\n"
                    + "(23315642, 'NETS501', 90.00),\n"
                    + "(23214768, 'CYBR604', 62.30),\n"
                    + "(23214768, 'CYBR601', 85.40),\n"
                    + "(23214768, 'NETS501', 97.60),\n"
                    + "(23457981, 'SOFT501', 56.50),\n"
                    + "(24538976, 'SOFT501', 100.0),\n"
                    + "(24538976, 'SOFT603', 87.88),\n"
                    + "(24538976, 'SOFT602', 91.20)"
            );
        } catch (SQLException ex) {
            PopUpUtil.displayError(ex.getMessage());
        }
    }
}
