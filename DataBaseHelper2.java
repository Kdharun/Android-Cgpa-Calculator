package com.example.academictracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper2 extends SQLiteOpenHelper {
    public static final String COLUMN_CGPA_GPA = "gpa";
    public static final String CGPA_TABLE = "studentcgpa";
    public static final String COLUMN_SG_SEMESTER = "semester";
    public static final String COLUMN_SUBJECT_SEMESTER = COLUMN_SG_SEMESTER;
    public static final String COLUMN_CGPA_SEMESTER = COLUMN_SUBJECT_SEMESTER;
    public static final String COLUMN_CGPA_TOTALCREDIT = "totalcredit";
    public static final String COLUMN_CGPA_CGPA = "cgpa";
    public static final String SUBJECT_TABLE = "subject";
    public static final String COLUMN_SUBJECT_COURSE = "course";
    public static final String COLUMN_SUBJECT_DEPT = "dept";
    public static final String COLUMN_SG_SUBCODE = "subcode";
    public static final String COLUMN_SUBJECT_SUBCODE = COLUMN_SG_SUBCODE;
    public static final String COLUMN_SG_SUBNAME = "subname";
    public static final String COLUMN_SUBJECT_SUBNAME = COLUMN_SG_SUBNAME;
    public static final String COLUMN_SG_SUBCREDIT = "subcredit";
    public static final String COLUMN_SUBJECT_SUBCREDIT = COLUMN_SG_SUBCREDIT;
    public static final String COLUMN_SG_GRADE = "grade";
    public static final String TABLE_STUDENTGRADE = "student" + COLUMN_SG_GRADE;
    Context c;

    public DataBaseHelper2(@Nullable Context context) {
        super(context, "student.db", null, 1);
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableDepartmentName = "CREATE TABLE departmentname(deptname TEXT, dept TEXT);";
        String insertDepartmentName = "INSERT INTO departmentname (deptname,dept)  " +
                "VALUES('Computer Science & Engineering','CSE'),  " +
                "('Information Technology','IT'),  " +
                "('Civil Engineering','CE'),  " +
                "('Mechanical Engineering','ME'),  " +
                "('Electronics & Communication Engineering','ECE'),  " +
                "('Electrical & Electronics Engineering','EEE'),  " +
                "('Electronic & Instrumentation Engineering','EIE'),  " +
                "('Chemical Engineering','CH'),  " +
                "('Mechatronics','MH'), " +
                "('No Department','NODEPT') ; ";
        String createTableCgpa = "CREATE TABLE " + CGPA_TABLE + " ( " + COLUMN_CGPA_SEMESTER + " INTEGER PRIMARY KEY , " + COLUMN_CGPA_TOTALCREDIT + " REAL, " + COLUMN_CGPA_GPA + " REAL, " + COLUMN_CGPA_CGPA + " REAL );";
        String createTableSubject = "CREATE TABLE " + SUBJECT_TABLE + " ( " + COLUMN_SUBJECT_COURSE + " TEXT, " + COLUMN_SUBJECT_DEPT + " TEXT, " + COLUMN_SUBJECT_SEMESTER + " INTEGER, " + COLUMN_SUBJECT_SUBCODE + " TEXT, " + COLUMN_SUBJECT_SUBNAME + " TEXT, " + COLUMN_SUBJECT_SUBCREDIT + " REAL, PRIMARY KEY ( "+COLUMN_SUBJECT_COURSE+" , "+COLUMN_SUBJECT_DEPT+" , "+COLUMN_CGPA_SEMESTER+" , "+COLUMN_SUBJECT_SUBCODE+") );";
        String createTableGrade = "CREATE TABLE gradepoints ( grade TEXT, gradepoint INTEGER);";
        String createTableStudentGrade = "CREATE TABLE " + TABLE_STUDENTGRADE + " ( " + COLUMN_SG_SEMESTER + " INTEGER, " + COLUMN_SG_SUBCODE + " TEXT, " + COLUMN_SG_SUBNAME + " TEXT, " + COLUMN_SG_SUBCREDIT + " TEXT, " + COLUMN_SG_GRADE + " TEXT );";
        String createTableSelDept = "CREATE TABLE seldept ( dept TEXT )";
        String insertSelDept = "INSERT INTO seldept (dept) VALUES ('NODEPT') ; ";
        String insertTableGrade = "INSERT INTO gradepoints ( grade , gradepoint )" +
                "VALUES ( 'S' , 10 ), ( 'A' , 9 ), ( 'B' , 8 ), ( 'C' , 7 ), ( 'D' , 6 ), ( 'E' , 5 ), ( 'F' , 0 );";
        String insertTableSubjectCSE ="INSERT INTO "+ SUBJECT_TABLE +" ("+COLUMN_SUBJECT_COURSE+","+COLUMN_SUBJECT_DEPT+","+COLUMN_CGPA_SEMESTER+","+COLUMN_SUBJECT_SUBCODE+","+COLUMN_SUBJECT_SUBNAME+","+COLUMN_SUBJECT_SUBCREDIT+") " +
                "VALUES ('B.Tech','CSE',1,'MA201','Mathematics I', 4), " +
                "('B.Tech','CSE',1,'PH201','Physics', 4), " +
                "('B.Tech','CSE',1,'CY201','Chemistry', 4), " +
                "('B.Tech','CSE',1,'HS201','English for Communication', 3), " +
                "('B.Tech','CSE',1,'ME201','Workshop and Manufacturing Practice', 1.5), " +
                "('B.Tech','CSE',1,'PH202','Physics Laboratory', 1.5), " +
                "('B.Tech','CSE',1,'CY202','Chemistry Laboratory', 1.5), " +
                "('B.Tech','CSE',2,'MA202','Mathematics II', 4), " +
                "('B.Tech','CSE',2,'EE201','Basic Electrical Engineering', 4), " +
                "('B.Tech','CSE',2,'CS201','Programming for Problem Solving', 3), " +
                "('B.Tech','CSE',2,'ME202','Engineering Graphics and Computer Aided Drawing', 3), " +
                "('B.Tech','CSE',2,'EE202','Basic Electrical Engineering Laboratory', 1.5), " +
                "('B.Tech','CSE',2,'CS202','Programming Laboratory', 1.5), " +
                "('B.Tech','CSE',3,'SH201','Biology for Engineers', 3), " +
                "('B.Tech','CSE',3,'EC235','Electronic Devices and Digital Systems', 3), " +
                "('B.Tech','CSE',3,'CS203','Computer Organization and Architecture', 4), " +
                "('B.Tech','CSE',3,'CS204','Data Structures', 3), " +
                "('B.Tech','CSE',3,'CS205','Object Oriented Programming Languages', 3), " +
                "('B.Tech','CSE',3,'EC236','Electronic Devices and Digital Systems Laboratory', 1.5), " +
                "('B.Tech','CSE',3,'CS206','Data Structures Laboratory', 1.5), " +
                "('B.Tech','CSE',3,'CS207','Object Oriented Programming Languages Laboratory', 1.5), " +
                "('B.Tech','CSE',4,'MA206','Mathematics for Computing', 4), " +
                "('B.Tech','CSE',4,'CS208','Operating Systems', 3), " +
                "('B.Tech','CSE',4,'CS209','Design and Analysis of Algorithms', 3), " +
                "('B.Tech','CSE',4,'CS210','Database Management Systems', 3), " +
                "('B.Tech','CSE',4,'CS211','Software Engineering', 4), " +
                "('B.Tech','CSE',4,'CS212','Operating System Laboratory', 1.5), " +
                "('B.Tech','CSE',4,'CS213','Design and Analysis of Algorithms Laboratory', 1.5), " +
                "('B.Tech','CSE',4,'CS214','Database Management Systems Laboratory', 1.5), " +
                "('B.Tech','CSE',5,'HS202','Industrial Economics and Management', 3), " +
                "('B.Tech','CSE',5,'CS215','Platform Technologies', 3), " +
                "('B.Tech','CSE',5,'CS216','Computer Networks', 3), " +
                "('B.Tech','CSE',5,'CS217','Automata Theory and Compiler Design', 4), " +
                "('B.Tech','CSE',5,'CSY51','Professional Elective Course - I', 3), " +
                "('B.Tech','CSE',5,'CS218','Platform Technologies Laboratory', 1.5), " +
                "('B.Tech','CSE',5,'CS219','Computer Networks Laboratory', 1.5), " +
                "('B.Tech','CSE',6,'EP201','Entrepreneurship', 2), " +
                "('B.Tech','CSE',6,'CS220','Microprocessors and Microcontrollers', 3), " +
                "('B.Tech','CSE',6,'CS221','Web Technologies', 3), " +
                "('B.Tech','CSE',6,'CS222','Information Security', 4), " +
                "('B.Tech','CSE',6,'CSY61','Professional Elective Course - II', 3), " +
                "('B.Tech','CSE',6,'CSY62','Professional Elective Course - III', 3), " +
                "('B.Tech','CSE',6,'CS223','Microprocessors and Microcontrollers Laboratory', 1.5), " +
                "('B.Tech','CSE',6,'CS224','Web Technologies Laboratory', 1.5), " +
                "('B.Tech','CSE',7,'CS225','Artificial Intelligence', 3), " +
                "('B.Tech','CSE',7,'CS226','Parallel and Distributed Systems', 4), " +
                "('B.Tech','CSE',7,'CS227','Data Science Essentials', 4), " +
                "('B.Tech','CSE',7,'CSY71','Professional Elective Course - IV', 3), " +
                "('B.Tech','CSE',7,'CSY72','Professional Elective Course - V', 3), " +
                "('B.Tech','CSE',7,'CS228','Artificial Intelligence Laboratory', 1.5), " +
                "('B.Tech','CSE',7,'CS229','Seminar', 1), " +
                "('B.Tech','CSE',8,'SWO1','Open Elective through SWAYAM', 2), " +
                "('B.Tech','CSE',8,'SWO2','Open Elective through SWAYAM', 2), " +
                "('B.Tech','CSE',8,'CS231','Comprehensive Test', 1), " +
                "('B.Tech','CSE',8,'CS232','Internship', 2), " +
                "('B.Tech','CSE',8,'CS233','Project Work', 8);";

        String insertTableSubjectIT = "INSERT INTO subject(course,dept,semester,subcode,subname,subcredit) " +
                "VALUES ('B.Tech','IT',1,'MA201','Mathematics I', 4), " +
                "('B.Tech','IT',1,'PH201','Physics', 4), " +
                "('B.Tech','IT',1,'CY201','Chemistry', 4), " +
                "('B.Tech','IT',1,'HS201','English for Communication', 3), " +
                "('B.Tech','IT',1,'ME201','Workshop and Manufacturing Practice', 1.5), " +
                "('B.Tech','IT',1,'PH202','Physics Laboratory', 1.5), " +
                "('B.Tech','IT',1,'CY202','Chemistry Laboratory', 1.5), " +
                "('B.Tech','IT',2,'MA202','Mathematics II', 4), " +
                "('B.Tech','IT',2,'EE201','Basic Electrical Engineering', 4), " +
                "('B.Tech','IT',2,'CS201','Programming for Problem Solving', 3), " +
                "('B.Tech','IT',2,'ME202','Engineering Graphics and Computer Aided Drawing', 3), " +
                "('B.Tech','IT',2,'EE202','Basic Electrical Engineering Laboratory', 1.5), " +
                "('B.Tech','IT',2,'CS202','Programming Laboratory', 1.5), " +
                "('B.Tech','IT',3,'EC233','Electronic Circuits', 3), " +
                "('B.Tech','IT',3,'IT201','Digital System Design', 3), " +
                "('B.Tech','IT',3,'IT202','Data Structures', 3), " +
                "('B.Tech','IT',3,'IT203','Object Oriented Programming using C++ & Java', 3), " +
                "('B.Tech','IT',3,'SH201','Biology for Engineers', 2), " +
                "('B.Tech','IT',3,'IT204','Digital Laboratory', 1.5), " +
                "('B.Tech','IT',3,'IT205','Data Structures Laboratory', 1.5), " +
                "('B.Tech','IT',3,'IT206','Object Oriented Programming Laboratory (C++ & Java)', 1.5), " +
                "('B.Tech','IT',4,'MA206','Mathematics for Computing', 4), " +
                "('B.Tech','IT',4,'IT207','Operating Systems', 3), " +
                "('B.Tech','IT',4,'IT208','Computer Architecture', 3), " +
                "('B.Tech','IT',4,'IT209','Microprocessors and Applications', 3), " +
                "('B.Tech','IT',4,'IT210','Design and Analysis of Algorithms', 3), " +
                "('B.Tech','IT',4,'IT211','Operating Systems Laboratory with UNIX / Linux', 1.5), " +
                "('B.Tech','IT',4,'IT212','Microprocessor Laboratory', 1.5), " +
                "('B.Tech','IT',4,'IT213','Design and Analysis of Algorithms Laboratory', 1.5), " +
                "('B.Tech','IT',5,'IT214','Database Management System', 3), " +
                "('B.Tech','IT',5,'IT215','Resource Management and Graph Theory', 4), " +
                "('B.Tech','IT',5,'IT216','Computer Networks', 4), " +
                "('B.Tech','IT',5,'IT217','Information Coding Techniques', 3), " +
                "('B.Tech','IT',5,'ITY51','Program Elective – I', 3), " +
                "('B.Tech','IT',5,'IT218','Database Management System Laboratory', 1.5), " +
                "('B.Tech','IT',5,'IT219','Computer Networks Laboratory', 1.5), " +
                "('B.Tech','IT',5,'IT220','Information Coding Techniques Laboratory', 1.5), " +
                "('B.Tech','IT',6,'IT221','Software Engineering', 3), " +
                "('B.Tech','IT',6,'IT222','Automata and Formal Languages', 4), " +
                "('B.Tech','IT',6,'IT223','Web Technology', 3), " +
                "('B.Tech','IT',6,'ITY61','Program Elective – II', 3), " +
                "('B.Tech','IT',6,'ITY62','Program Elective – II', 3), " +
                "('B.Tech','IT',6,'EP201','Entrepreneurship', 2), " +
                "('B.Tech','IT',6,'IT224','Web Technology Laboratory', 1.5), " +
                "('B.Tech','IT',6,'IT225','Software Engineering Laboratory', 1.5), " +
                "('B.Tech','IT',7,'IT226','Artificial Intelligence', 4), " +
                "('B.Tech','IT',7,'HS202','Industrial Economics and Management', 3), " +
                "('B.Tech','IT',7,'ITY71','Program Elective – IV', 3), " +
                "('B.Tech','IT',7,'ITY72','Program Elective – V', 3), " +
                "('B.Tech','IT',7,'IT227','Artificial Intelligence Laboratory', 1.5), " +
                "('B.Tech','IT',7,'IT228','Seminar', 1), " +
                "('B.Tech','IT',7,'IT229','Mini Project', 1.5), " +
                "('B.Tech','IT',8,'SWO81','Open Elective through SWAYAM', 2), " +
                "('B.Tech','IT',8,'SWO82','Open Elective through SWAYAM', 2), " +
                "('B.Tech','IT',8,'IT231','Comprehensive Test', 1), " +
                "('B.Tech','IT',8,'IT232','Internship', 2), " +
                "('B.Tech','IT',8,'IT233','Project Work', 8);";

        String insertTableSubjectCE = "INSERT INTO subject(course,dept,semester,subcode,subname,subcredit) " +
                "VALUES ('B.Tech','CE',1,'MA201','Mathematics I', 4), " +
                "('B.Tech','CE',1,'EE201','Basic Electrical Engineering', 4), " +
                "('B.Tech','CE',1,'CS201','Programming for Problem Solving', 3), " +
                "('B.Tech','CE',1,'ME202','Engineering Graphics and Computer Aided Drawing', 3), " +
                "('B.Tech','CE',1,'EE202','Basic Electrical Engineering Laboratory', 1.5), " +
                "('B.Tech','CE',1,'CS202','Programming Laboratory', 1.5), " +
                "('B.Tech','CE',2,'MA202','Mathematics II', 4), " +
                "('B.Tech','CE',2,'PH201','Physics', 4), " +
                "('B.Tech','CE',2,'CY201','Chemistry', 4), " +
                "('B.Tech','CE',2,'HS201','English for Communication', 3), " +
                "('B.Tech','CE',2,'ME201','Workshop and Manufacturing Practice', 1.5), " +
                "('B.Tech','CE',2,'PH202','Physics Laboratory', 1.5), " +
                "('B.Tech','CE',2,'CY202','Chemistry Laboratory', 1.5), " +
                "('B.Tech','CE',3,'SH201','Biology for Engineers', 2), " +
                "('B.Tech','CE',3,'CE202','Engineering Mechanics', 4), " +
                "('B.Tech','CE',3,'CE203','Engineering Geology', 2), " +
                "('B.Tech','CE',3,'CE204','Fluid Mechanics', 3), " +
                "('B.Tech','CE',3,'CE205','Building Technology', 3), " +
                "('B.Tech','CE',3,'CE206','Surveying and Geomatics', 3), " +
                "('B.Tech','CE',3,'CE207','Computer-Aided Civil Engineering Drawing', 1.5), " +
                "('B.Tech','CE',3,'CE208','Surveying Laboratory', 1.5), " +
                "('B.Tech','CE',4,'MA203','Numerical Methods and Statistics', 4), " +
                "('B.Tech','CE',4,'CE209','Disaster Management', 3), " +
                "('B.Tech','CE',4,'CE210','Hydraulics Engineering', 3), " +
                "('B.Tech','CE',4,'EI212','Instrumentation and Sensor Technologies for Civil Engineering Applications', 3), " +
                "('B.Tech','CE',4,'CE211','Concrete Technology', 3), " +
                "('B.Tech','CE',4,'CE212','Basics of Solid Mechanics', 4), " +
                "('B.Tech','CE',4,'CE213','Materials Testing and Evaluation Laboratory-I', 1.5), " +
                "('B.Tech','CE',4,'CE214','Fluid mechanics Laboratory', 1.5), " +
                "('B.Tech','CE',5,'CE215','Mechanics of Materials', 4), " +
                "('B.Tech','CE',5,'CE216','Environmental Engineering', 3), " +
                "('B.Tech','CE',5,'CE217','Transportation Engineering', 3), " +
                "('B.Tech','CE',5,'CE218','Hydrology Water Resources and Irrigation Engineering', 4), " +
                "('B.Tech','CE',5,'CEY51','Professional Elective-I', 3), " +
                "('B.Tech','CE',5,'CE219','Material Testing and Evaluation Laboratory-II (Highway and Concrete Laboratory)', 1.5), " +
                "('B.Tech','CE',5,'CE220','Environmental Engineering Laboratory', 1.5), " +
                "('B.Tech','CE',5,'EP201','Entrepreneurship', 2), " +
                "('B.Tech','CE',6,'CE221','Structural Analysis', 4), " +
                "('B.Tech','CE',6,'CE222','Structural Concrete Design', 4), " +
                "('B.Tech','CE',6,'CE223','Geotechnical Engineering', 3), " +
                "('B.Tech','CE',6,'CE224','Estimation costing and Valuation', 3), " +
                "('B.Tech','CE',6,'CEY61','Professional Elective-II', 3), " +
                "('B.Tech','CE',6,'CE225','Geotechnical Engg. Laboratory', 1.5), " +
                "('B.Tech','CE',6,'CE226','Structural Mechanics Laboratory', 1.5), " +
                "('B.Tech','CE',7,'HS202','Industrial Economics & Management', 3), " +
                "('B.Tech','CE',7,'CEY71','Professional Elective -III', 3), " +
                "('B.Tech','CE',7,'CEY72','Professional Elective -IV', 3), " +
                "('B.Tech','CE',7,'CEY73','Professional Elective -V', 3), " +
                "('B.Tech','CE',7,'CEY74','Professional Elective -VI', 3), " +
                "('B.Tech','CE',7,'CE227','Computer Aided Analysis and Design of Structures', 1.5), " +
                "('B.Tech','CE',7,'CE228','Seminar', 1), " +
                "('B.Tech','CE',8,'SWO81','Open Elective through SWAYAM', 2), " +
                "('B.Tech','CE',8,'SWO82','Open Elective through SWAYAM', 2), " +
                "('B.Tech','CE',8,'CE230','Comprehensive Test', 1), " +
                "('B.Tech','CE',8,'CE231','Internship', 2), " +
                "('B.Tech','CE',8,'CE232','Project Work', 8);";

        String insertTableSubjectME = "INSERT INTO subject(course,dept,semester,subcode,subname,subcredit) " +
                "VALUES ('B.Tech','ME',1,'MA201','Mathematics I', 4), " +
                "('B.Tech','ME',1,'PH201','Physics', 4), " +
                "('B.Tech','ME',1,'CY201','Chemistry', 4), " +
                "('B.Tech','ME',1,'HS201','English for Communication', 3), " +
                "('B.Tech','ME',1,'ME201','Workshop and Manufacturing Practice', 1.5), " +
                "('B.Tech','ME',1,'PH202','Physics Laboratory', 1.5), " +
                "('B.Tech','ME',1,'CY202','Chemistry Laboratory', 1.5), " +
                "('B.Tech','ME',2,'MA202','Mathematics II', 4), " +
                "('B.Tech','ME',2,'EE201','Basic Electrical Engineering', 4), " +
                "('B.Tech','ME',2,'CS201','Programming for Problem Solving', 3), " +
                "('B.Tech','ME',2,'ME202','Engineering Graphics and Computer Aided Drawing', 3), " +
                "('B.Tech','ME',2,'EE202','Basic Electrical Engineering Laboratory', 1.5), " +
                "('B.Tech','ME',2,'CS202','Programming Laboratory', 1.5), " +
                "('B.Tech','ME',3,'MA204','Transforms, PDE and Statistics', 4), " +
                "('B.Tech','ME',3,'ME203','Engineering Mechanics', 4), " +
                "('B.Tech','ME',3,'ME204','Fluid Mechanics and Hydraulic Machines', 4), " +
                "('B.Tech','ME',3,'ME205','Engineering Thermodynamics', 4), " +
                "('B.Tech','ME',3,'ME206','Materials Technology', 3), " +
                "('B.Tech','ME',3,'ME207','Machine Drawing', 3), " +
                "('B.Tech','ME',4,'SH201','Biology for Engineers', 2), " +
                "('B.Tech','ME',4,'EC234','Elements of Electronics', 3), " +
                "('B.Tech','ME',4,'ME208','Mechanics of Solids', 4), " +
                "('B.Tech','ME',4,'ME209','Thermal Engineering – I', 4), " +
                "('B.Tech','ME',4,'ME210','Machining Technology', 3), " +
                "('B.Tech','ME',4,'ME211','Kinematics of Machines', 4), " +
                "('B.Tech','ME',4,'ME212','Mechanical Engineering Lab –I', 1.5), " +
                "('B.Tech','ME',5,'ME213','Heat and Mass Transfer', 4), " +
                "('B.Tech','ME',5,'ME214','Manufacturing Processes', 4), " +
                "('B.Tech','ME',5,'ME215','Dynamics of Machines', 4), " +
                "('B.Tech','ME',5,'MEY51','Professional Elective –I', 3), " +
                "('B.Tech','ME',5,'MEY52','Professional Elective –II', 3), " +
                "('B.Tech','ME',5,'ME216','Mechanical Engineering Lab –II', 1.5), " +
                "('B.Tech','ME',6,'HS202','Industrial Economics and Management', 3), " +
                "('B.Tech','ME',6,'ME217','Thermal Engineering – II', 4), " +
                "('B.Tech','ME',6,'ME218','Metrology and Measurements', 4), " +
                "('B.Tech','ME',6,'ME219','Design of Machine Elements', 4), " +
                "('B.Tech','ME',6,'MEY61','Professional Elective –III', 3), " +
                "('B.Tech','ME',6,'ME220','Seminar', 1), " +
                "('B.Tech','ME',6,'ME221','Mechanical Engineering Lab –III', 1.5), " +
                "('B.Tech','ME',7,'ME222','Operations Research', 4), " +
                "('B.Tech','ME',7,'ME223','Industrial Engineering and Management', 3), " +
                "('B.Tech','ME',7,'ME224','Advanced Manufacturing Technology', 4), " +
                "('B.Tech','ME',7,'MEY71','Professional Elective –IV', 3), " +
                "('B.Tech','ME',7,'MEY72','Professional Elective –V', 3), " +
                "('B.Tech','ME',7,'EP201','Entrepreneurship', 2), " +
                "('B.Tech','ME',8,'SWO81','Open Elective through SWAYAM', 2), " +
                "('B.Tech','ME',8,'SWO82','Open Elective through SWAYAM', 2), " +
                "('B.Tech','ME',8,'ME226','Comprehensive Test', 1), " +
                "('B.Tech','ME',8,'ME227','Internship', 2), " +
                "('B.Tech','ME',8,'ME228','Project Work', 8);";

        String insertTableSubjectECE = "INSERT INTO subject(course,dept,semester,subcode,subname,subcredit) " +
                "VALUES ('B.Tech','ECE',1,'MA201','Mathematics I', 4), " +
                "('B.Tech','ECE',1,'EE201','Basic Electrical Engineering', 4), " +
                "('B.Tech','ECE',1,'CS201','Programming for Problem Solving', 3), " +
                "('B.Tech','ECE',1,'ME202','Engineering Graphics and Computer Aided Drawing', 3), " +
                "('B.Tech','ECE',1,'EE202','Basic Electrical Engineering Laboratory', 1.5), " +
                "('B.Tech','ECE',1,'CS202','Programming Laboratory', 1.5), " +
                "('B.Tech','ECE',2,'MA202','Mathematics II', 4), " +
                "('B.Tech','ECE',2,'PH201','Physics', 4), " +
                "('B.Tech','ECE',2,'CY201','Chemistry', 4), " +
                "('B.Tech','ECE',2,'HS201','English for Communication', 3), " +
                "('B.Tech','ECE',2,'ME201','Workshop and Manufacturing Practice', 1.5), " +
                "('B.Tech','ECE',2,'PH202','Physics Laboratory', 1.5), " +
                "('B.Tech','ECE',2,'CY202','Chemistry Laboratory', 1.5), " +
                "('B.Tech','ECE',3,'MA205','Linear Algebra, Numerical Methods and Random Processes', 4), " +
                "('B.Tech','ECE',3,'EC201','Circuits and Networks', 3), " +
                "('B.Tech','ECE',3,'EC202','Electronic Devices and Circuits', 3), " +
                "('B.Tech','ECE',3,'EC203','Electromagnetic Waves and Fields', 3), " +
                "('B.Tech','ECE',3,'EC204','Digital System Design', 3), " +
                "('B.Tech','ECE',3,'CS234','Data Structures and Object- Oriented Programming', 3), " +
                "('B.Tech','ECE',3,'EC205','Electronic Devices and Networks Laboratory', 1.5), " +
                "('B.Tech','ECE',3,'CS235','Data Structures and Object- Oriented Programming Laboratory', 1.5), " +
                "('B.Tech','ECE',4,'EC206','Transmission Lines and Waveguides', 3), " +
                "('B.Tech','ECE',4,'EC207','Electronic Circuit Design', 3), " +
                "('B.Tech','ECE',4,'EC208','Signals and Systems', 4), " +
                "('B.Tech','ECE',4,'EC209','Analog Communication', 3), " +
                "('B.Tech','ECE',4,'ECY41','Professional Elective – I', 3), " +
                "('B.Tech','ECE',4,'SH201','Biology for Engineers', 2), " +
                "('B.Tech','ECE',4,'EC210','Digital System Design Laboratory', 1.5), " +
                "('B.Tech','ECE',4,'EC211','Electronic Circuit Design Laboratory', 1.5), " +
                "('B.Tech','ECE',4,'EC212','Analog Communication Laboratory', 1.5), " +
                "('B.Tech','ECE',5,'EC213','Digital Signal processing and DSP Processors', 4), " +
                "('B.Tech','ECE',5,'EC214','Digital Communication', 3), " +
                "('B.Tech','ECE',5,'ECY51','Professional Elective – II', 3), " +
                "('B.Tech','ECE',5,'CS236','Microprocessors and Microcontrollers', 3), " +
                "('B.Tech','ECE',5,'EP201','Entrepreneurship', 2), " +
                "('B.Tech','ECE',5,'EC215','Digital Signal Processing Laboratory', 1.5), " +
                "('B.Tech','ECE',5,'EC216','Digital Communication Laboratory', 1.5), " +
                "('B.Tech','ECE',5,'CS237','Microprocessors and Microcontrollers Laboratory', 1.5), " +
                "('B.Tech','ECE',6,'EC217','Microwave and Optical Engineering', 3), " +
                "('B.Tech','ECE',6,'EC218','Data Communication Networks', 3), " +
                "('B.Tech','ECE',6,'EC219','VLSI Design', 3), " +
                "('B.Tech','ECE',6,'ECY61','Professional Elective - III', 3), " +
                "('B.Tech','ECE',6,'HS202','Industrial Management and Economics', 3), " +
                "('B.Tech','ECE',6,'EC220','Microwave and Optical Engineering Laboratory', 1.5), " +
                "('B.Tech','ECE',6,'EC221','Data Communication Networks Laboratory', 1.5), " +
                "('B.Tech','ECE',6,'EC222','VLSI Design Laboratory', 1.5), " +
                "('B.Tech','ECE',7,'EC223','Wireless Communication', 3), " +
                "('B.Tech','ECE',7,'EC224','Information Theory and Coding', 3), " +
                "('B.Tech','ECE',7,'EC225','Embedded System', 3), " +
                "('B.Tech','ECE',7,'ECY71','Professional Elective - IV', 3), " +
                "('B.Tech','ECE',7,'ECY72','Professional Elective - V', 3), " +
                "('B.Tech','ECE',7,'EC226','Wireless Communication Laboratory', 1.5), " +
                "('B.Tech','ECE',7,'EC227','Embedded System Laboratory', 1.5), " +
                "('B.Tech','ECE',7,'EC228','Mini Project', 1), " +
                "('B.Tech','ECE',8,'SWO81','Open Elective through SWAYAM', 2), " +
                "('B.Tech','ECE',8,'SWO82','Open Elective through SWAYAM', 2), " +
                "('B.Tech','ECE',8,'EC230','Comprehensive Test', 1), " +
                "('B.Tech','ECE',8,'EC231','Internship', 2), " +
                "('B.Tech','ECE',8,'EC232','Project Work', 8);";

        String insertTableSubjectEEE = "INSERT INTO subject(course,dept,semester,subcode,subname,subcredit) " +
                "VALUES ('B.Tech','EEE',1,'MA201','Mathematics I', 4), " +
                "('B.Tech','EEE',1,'EE201','Basic Electrical Engineering', 4), " +
                "('B.Tech','EEE',1,'CS201','Programming for Problem Solving', 3), " +
                "('B.Tech','EEE',1,'ME202','Engineering Graphics and Computer Aided Drawing', 3), " +
                "('B.Tech','EEE',1,'EE202','Basic Electrical Engineering Laboratory', 1.5), " +
                "('B.Tech','EEE',1,'CS202','Programming Laboratory', 1.5), " +
                "('B.Tech','EEE',2,'MA202','Mathematics II', 4), " +
                "('B.Tech','EEE',2,'PH201','Physics', 4), " +
                "('B.Tech','EEE',2,'CY201','Chemistry', 4), " +
                "('B.Tech','EEE',2,'HS201','English for Communication', 3), " +
                "('B.Tech','EEE',2,'ME201','Workshop and Manufacturing Practice', 1.5), " +
                "('B.Tech','EEE',2,'PH202','Physics Laboratory', 1.5), " +
                "('B.Tech','EEE',2,'CY202','Chemistry Laboratory', 1.5), " +
                "('B.Tech','EEE',3,'MA204','Transforms, Partial Differential Equations and Statistics', 4), " +
                "('B.Tech','EEE',3,'EE203','Electrical Circuit Analysis', 4), " +
                "('B.Tech','EEE',3,'EE204','Electromagnetic Fields', 3), " +
                "('B.Tech','EEE',3,'EE205','Electronic Devices and Circuits', 3), " +
                "('B.Tech','EEE',3,'EE206','Electrical Machines - I', 3), " +
                "('B.Tech','EEE',3,'EE207','Signals and Systems', 3), " +
                "('B.Tech','EEE',3,'EE208','Electronics Laboratory-I', 1.5), " +
                "('B.Tech','EEE',3,'EE209','Electrical Machines Laboratory - I', 1.5), " +
                "('B.Tech','EEE',4,'SH201','Biology for Engineers', 2), " +
                "('B.Tech','EEE',4,'EE210','Analog Electronics', 3), " +
                "('B.Tech','EEE',4,'EE211','Pulse and Digital Circuits', 3), " +
                "('B.Tech','EEE',4,'EE212','Electrical Machines - II', 3), " +
                "('B.Tech','EEE',4,'CS234','Data Structures and Object Oriented Programming', 3), " +
                "('B.Tech','EEE',4,'EE213','Electronics Laboratory - II', 1.5), " +
                "('B.Tech','EEE',4,'EE214','Electrical Machines Laboratory - II', 1.5), " +
                "('B.Tech','EEE',4,'CS235','Data Structures and Object Oriented Programming Laboratory', 1.5), " +
                "('B.Tech','EEE',5,'EE215','Analog and Digital Integrated circuits', 3), " +
                "('B.Tech','EEE',5,'EE216','Power Electronics', 3), " +
                "('B.Tech','EEE',5,'EE217','Measurement and Instrumentation', 3), " +
                "('B.Tech','EEE',5,'EE218','Transmission and Distribution', 3), " +
                "('B.Tech','EEE',5,'EE219','Control Systems', 4), " +
                "('B.Tech','EEE',5,'HS202','Industrial Economics and Management', 3), " +
                "('B.Tech','EEE',5,'EE220','Electronics laboratory - III', 1.5), " +
                "('B.Tech','EEE',5,'EE221','Measurement and Control Laboratory', 1.5), " +
                "('B.Tech','EEE',6,'EE222','Power System Analysis', 4), " +
                "('B.Tech','EEE',6,'EE223','Microprocessors and Microcontrollers', 3), " +
                "('B.Tech','EEE',6,'EEY61','Program Elective -I', 3), " +
                "('B.Tech','EEE',6,'EEY62','Program Elective – II', 3), " +
                "('B.Tech','EEE',6,'EP201','Entrepreneurship', 2), " +
                "('B.Tech','EEE',6,'EE224','Microprocessors and Microcontrollers Laboratory', 1.5), " +
                "('B.Tech','EEE',6,'EE225','Power Electronics Laboratory', 1.5), " +
                "('B.Tech','EEE',7,'EE226','Power System Operation and Control', 3), " +
                "('B.Tech','EEE',7,'EE227','Protection and Switchgear', 3), " +
                "('B.Tech','EEE',7,'EE228','Solid State Drives', 3), " +
                "('B.Tech','EEE',7,'EEY71','Program Elective – III', 3), " +
                "('B.Tech','EEE',7,'EEY72','Program Elective – IV', 3), " +
                "('B.Tech','EEE',7,'EEY73','Program Elective – V', 3), " +
                "('B.Tech','EEE',7,'EE229','Power Systems Laboratory', 2), " +
                "('B.Tech','EEE',7,'EE230','Seminar', 1), " +
                "('B.Tech','EEE',8,'SWX81','Open Elective through SWAYAM', 2), " +
                "('B.Tech','EEE',8,'SWX82','Open Elective through SWAYAM', 2), " +
                "('B.Tech','EEE',8,'EE232','Comprehensive Test', 1), " +
                "('B.Tech','EEE',8,'EE233','Internship', 2), " +
                "('B.Tech','EEE',8,'EE234','Project Work', 8);";

        String insertTableSubjectEIE = "INSERT INTO subject(course,dept,semester,subcode,subname,subcredit) " +
                "VALUES ('B.Tech','EIE',1,'MA201','Mathematics I', 4), " +
                "('B.Tech','EIE',1,'PH201','Physics', 4), " +
                "('B.Tech','EIE',1,'CY201','Chemistry', 4), " +
                "('B.Tech','EIE',1,'HS201','English for Communication', 3), " +
                "('B.Tech','EIE',1,'ME201','Workshop and Manufacturing Practice', 1.5), " +
                "('B.Tech','EIE',1,'PH202','Physics Laboratory', 1.5), " +
                "('B.Tech','EIE',1,'CY202','Chemistry Laboratory', 1.5), " +
                "('B.Tech','EIE',2,'MA202','Mathematics II', 4), " +
                "('B.Tech','EIE',2,'EE201','Basic Electrical Engineering', 4), " +
                "('B.Tech','EIE',2,'CS201','Programming for Problem Solving', 3), " +
                "('B.Tech','EIE',2,'ME202','Engineering Graphics and Computer Aided Drawing', 3), " +
                "('B.Tech','EIE',2,'EE202','Basic Electrical Engineering Laboratory', 1.5), " +
                "('B.Tech','EIE',2,'CS202','Programming Laboratory', 1.5), " +
                "('B.Tech','EIE',3,'MA204','Transforms, Partial Differential Equations and Statistics', 4), " +
                "('B.Tech','EIE',3,'SH201','Biology for Engineers', 2), " +
                "('B.Tech','EIE',3,'EI201','Circuit Theory', 4), " +
                "('B.Tech','EIE',3,'EI202','Electronic Circuits', 3), " +
                "('B.Tech','EIE',3,'EI203','Electronic Design and fabrication', 4), " +
                "('B.Tech','EIE',3,'EI204','Digital Logic Theory and Design', 3), " +
                "('B.Tech','EIE',3,'EI205','Electronic Circuits Lab', 1.5), " +
                "('B.Tech','EIE',3,'EI206','Electronic Design and fabrication lab', 1), " +
                "('B.Tech','EIE',4,'EI207','Linear Integrated Circuits', 3), " +
                "('B.Tech','EIE',4,'EI208','Electrical and Electronics Instruments', 3), " +
                "('B.Tech','EIE',4,'EI209','Transducers and Measurements System', 3), " +
                "('B.Tech','EIE',4,'CS234','Data Structures and OOPS', 3), " +
                "('B.Tech','EIE',4,'EIY41','Professional Elective Course -I', 3), " +
                "('B.Tech','EIE',4,'EI210','Linear and Integrated Circuits Lab', 1.5), " +
                "('B.Tech','EIE',4,'CS235','Data structures and OOPS Lab', 1.5), " +
                "('B.Tech','EIE',4,'EI211','Transducers and Measurements System Lab', 1.5), " +
                "('B.Tech','EIE',5,'EI213','Industrial Instrumentation', 4), " +
                "('B.Tech','EIE',5,'EI214','Microprocessors and Applications', 3), " +
                "('B.Tech','EIE',5,'EI215','Control Systems', 4), " +
                "('B.Tech','EIE',5,'EP201','Entrepreneurship', 2), " +
                "('B.Tech','EIE',5,'EIY51','Professional Elective Course -II', 3), " +
                "('B.Tech','EIE',5,'EI216','Instrumentation System design Lab', 1.5), " +
                "('B.Tech','EIE',5,'EI217','VLSI Lab', 1.5), " +
                "('B.Tech','EIE',5,'EI218','Microprocessors and Applications Lab', 1.5), " +
                "('B.Tech','EIE',6,'EI219','Process Control', 4), " +
                "('B.Tech','EIE',6,'EI220','Embedded System Design', 4), " +
                "('B.Tech','EIE',6,'EI221','Robotics and Automation', 3), " +
                "('B.Tech','EIE',6,'HS202','Industrial Economics and Management', 3), " +
                "('B.Tech','EIE',6,'EIY61','Professional Elective Course -III', 3), " +
                "('B.Tech','EIE',6,'EI222','Process Control Lab', 1.5), " +
                "('B.Tech','EIE',6,'EI223','Virtual Instrumentation Lab', 1.5), " +
                "('B.Tech','EIE',6,'EI224','Embedded System Design Lab', 1.5), " +
                "('B.Tech','EIE',7,'EI225','PLC and DCS', 4), " +
                "('B.Tech','EIE',7,'EI226','Analytical Instruments', 4), " +
                "('B.Tech','EIE',7,'EIY71','Professional Elective Course -IV', 3), " +
                "('B.Tech','EIE',7,'EIY72','Professional Elective Course -IV', 3), " +
                "('B.Tech','EIE',7,'EI227','Industrial Measurements and Control Lab', 1.5), " +
                "('B.Tech','EIE',7,'EI228','Seminar', 1), " +
                "('B.Tech','EIE',7,'EI229','Mini Project', 2), " +
                "('B.Tech','EIE',8,'SWO81','Open Elective through SWAYAM', 2), " +
                "('B.Tech','EIE',8,'SWO82','Open Elective through SWAYAM', 2), " +
                "('B.Tech','EIE',8,'EI231','Comprehensive Test', 1), " +
                "('B.Tech','EIE',8,'EI232','Internship', 2), " +
                "('B.Tech','EIE',8,'EI233','Project Work', 8);";

        String insertTableSubjectCH = "INSERT INTO subject(course,dept,semester,subcode,subname,subcredit) " +
                "VALUES('B.Tech','CH',1,'MA201','Mathematics I', 4), " +
                "('B.Tech','CH',1,'PH201','Physics', 4), " +
                "('B.Tech','CH',1,'CY201','Chemistry', 4), " +
                "('B.Tech','CH',1,'HS201','English for Communication', 3), " +
                "('B.Tech','CH',1,'ME201','Workshop and Manufacturing Practice', 1.5), " +
                "('B.Tech','CH',1,'PH202','Physics Laboratory', 1.5), " +
                "('B.Tech','CH',1,'CY202','Chemistry Laboratory', 1.5), " +
                "('B.Tech','CH',2,'MA202','Mathematics II', 4), " +
                "('B.Tech','CH',2,'EE201','Basic Electrical Engineering', 4), " +
                "('B.Tech','CH',2,'CS201','Programming for Problem Solving', 3), " +
                "('B.Tech','CH',2,'ME202','Engineering Graphics and Computer Aided Drawing', 3), " +
                "('B.Tech','CH',2,'EE202','Basic Electrical Engineering Laboratory', 1.5), " +
                "('B.Tech','CH',2,'CS202','Programming Laboratory', 1.5), " +
                "('B.Tech','CH',3,'CY203','Chemistry for Chemical Engineering', 4), " +
                "('B.Tech','CH',3,'CE233','Engineering Mechanics and Mechanics ofSolids', 4), " +
                "('B.Tech','CH',3,'CH201','Momentum Transfer', 4), " +
                "('B.Tech','CH',3,'CH202','Process Calculations', 4), " +
                "('B.Tech','CH',3,'SH201','Biology for Engineers', 2), " +
                "('B.Tech','CH',3,'CY204','Chemistry Lab for Chemical Engineering', 1.5), " +
                "('B.Tech','CH',4,'MA204','Transforms, Partial Differential Equations and Statistics', 4), " +
                "('B.Tech','CH',4,'CH203','Process Heat Transfer', 4), " +
                "('B.Tech','CH',4,'CH204','Mechanical Operations', 3), " +
                "('B.Tech','CH',4,'CH205','Chemical Engineering Thermodynamics', 4), " +
                "('B.Tech','CH',4,'CH206','Mass Transfer – I', 4), " +
                "('B.Tech','CH',4,'EC234','Elements of Electronics', 3), " +
                "('B.Tech','CH',4,'CH207','Chemical Engineering Lab – I', 1.5), " +
                "('B.Tech','CH',5,'CH208','Mass Transfer – II', 4), " +
                "('B.Tech','CH',5,'CH209','Chemical Reaction Engineering – I', 4), " +
                "('B.Tech','CH',5,'CH210','Chemical Process Industries', 3), " +
                "('B.Tech','CH',5,'CHY51','Program Elective – I', 3), " +
                "('B.Tech','CH',5,'CHY52','Program Elective – II', 3), " +
                "('B.Tech','CH',5,'CH211','Chemical Engineering Lab – II', 1.5), " +
                "('B.Tech','CH',5,'EP201','Entrepreneurship', 2), " +
                "('B.Tech','CH',6,'CH212','Chemical Reaction Engineering – II', 4), " +
                "('B.Tech','CH',6,'CH213','Computational Methods and Practice inChemical Engineering', 4), " +
                "('B.Tech','CH',6,'CHY61','Program Elective –III', 3), " +
                "('B.Tech','CH',6,'CHY62','Program Elective –IV', 3), " +
                "('B.Tech','CH',6,'HS202','Industrial Economics and Management', 3), " +
                "('B.Tech','CH',6,'CH214','Chemical Engineering Lab – III', 1.5), " +
                "('B.Tech','CH',7,'CH215','Transport Phenomena', 4), " +
                "('B.Tech','CH',7,'CH216','Process Dynamics & Control', 4), " +
                "('B.Tech','CH',7,'CH217','Process Equipment Design & Practice', 4), " +
                "('B.Tech','CH',7,'CH218','Process Engineering Economics', 3), " +
                "('B.Tech','CH',7,'CHY71',' Program Elective – V', 3), " +
                "('B.Tech','CH',7,'CH219','Chemical Engineering Lab – IV', 1.5), " +
                "('B.Tech','CH',7,'CH220','Seminar', 1), " +
                "('B.Tech','CH',8,'SWO81','Open Elective through SWAYAM', 2), " +
                "('B.Tech','CH',8,'SWO82','Open Elective through SWAYAM', 2), " +
                "('B.Tech','CH',8,'CH222','Comprehensive Test', 1), " +
                "('B.Tech','CH',8,'CH223','Internship', 2), " +
                "('B.Tech','CH',8,'CH224','Project Work', 8);";
        
        String insertTableSubjectMT = "INSERT INTO subject(course,dept,semester,subcode,subname,subcredit) " +
                "VALUES ('B.Tech','MT',1,'MA201','Mathematics I', 4), " +
                "('B.Tech','MT',1,'EE201','Basic Electrical Engineering', 4), " +
                "('B.Tech','MT',1,'CS201','Programming for Problem Solving', 3), " +
                "('B.Tech','MT',1,'ME202','Engineering Graphics and Computer Aided Drawing', 3), " +
                "('B.Tech','MT',1,'EE202','Basic Electrical Engineering Laboratory', 1.5), " +
                "('B.Tech','MT',1,'CS202','Programming Laboratory', 1.5), " +
                "('B.Tech','MT',2,'MA202','Mathematics II', 4), " +
                "('B.Tech','MT',2,'PH201','Physics', 4), " +
                "('B.Tech','MT',2,'CY201','Chemistry', 4), " +
                "('B.Tech','MT',2,'HS201','English for Communication', 3), " +
                "('B.Tech','MT',2,'ME201','Workshop and Manufacturing Practice', 1.5), " +
                "('B.Tech','MT',2,'PH202','Physics Laboratory', 1.5), " +
                "('B.Tech','MT',2,'CY202','Chemistry Laboratory', 1.5), " +
                "('B.Tech','MT',3,'MA204','Transforms, PDE and Statistics', 4), " +
                "('B.Tech','MT',3,'MT201','Manufacturing Processes', 3), " +
                "('B.Tech','MT',3,'MT202','Hydraulic and Pneumatic Systems', 3), " +
                "('B.Tech','MT',3,'MT203','Mechanics of Solids and Design', 3), " +
                "('B.Tech','MT',3,'EC233','Analog and Digital Electronics', 3), " +
                "('B.Tech','MT',3,'MT204','Material Testing & Manufacturing Processes Laboratory', 1.5), " +
                "('B.Tech','MT',3,'MT205','Hydraulic and Pneumatic Systems Laboratory', 1.5), " +
                "('B.Tech','MT',3,'EC234','Analog and Digital Electronics Laboratory', 1.5), " +
                "('B.Tech','MT',4,'SH201','Biology for Engineers', 2), " +
                "('B.Tech','MT',4,'MT206','Sensors and Actuators', 3), " +
                "('B.Tech','MT',4,'CS220','Microprocessor and Micro Controllers', 3), " +
                "('B.Tech','MT',4,'EI203','Electronic Design and fabrication (TCP)', 4), " +
                "('B.Tech','MT',4,'CS234','Data Structures and Object Oriented Programming', 3), " +
                "('B.Tech','MT',4,'MT207','Sensors and Actuators Laboratory', 1.5), " +
                "('B.Tech','MT',4,'CS223','Microprocessor and Micro Controller Laboratory', 1.5), " +
                "('B.Tech','MT',4,'CS235','Data Structures and Object Oriented Programming Laboratory', 1.5), " +
                "('B.Tech','MT',4,'MT221','Innovative Design Thinking', 1.5), " +
                "('B.Tech','MT',5,'MT208','Control System', 4), " +
                "('B.Tech','MT',5,'MT209','Industrial Automation', 3), " +
                "('B.Tech','MT',5,'EEM04','Power Electronics and Drives', 3), " +
                "('B.Tech','MT',5,'MTP51','Professional Elective –I', 3), " +
                "('B.Tech','MT',5,'MTP52','Professional Elective –II', 3), " +
                "('B.Tech','MT',5,'MT210','Industrial Automation Laboratory', 1.5), " +
                "('B.Tech','MT',5,'MT211','Dynamics and Control Laboratory', 1.5), " +
                "('B.Tech','MT',5,'CS236','Python Programming Laboratory', 1.5), " +
                "('B.Tech','MT',6,'HS202','Industrial Economics and Management', 3), " +
                "('B.Tech','MT',6,'MT212','CAD/CAM Technology', 4), " +
                "('B.Tech','MT',6,'MT213','Industrial Robotics', 3), " +
                "('B.Tech','MT',6,'MT214','Embedded System Design', 3), " +
                "('B.Tech','MT',6,'MTP61','Professional Elective –III', 3), " +
                "('B.Tech','MT',6,'MT222','Engineering Design Project', 3), " +
                "('B.Tech','MT',6,'MT215','CAD / CAM/ Robotics Laboratory', 1.5), " +
                "('B.Tech','MT',6,'MT216','Embedded System Design Laboratory', 1.5), " +
                "('B.Tech','MT',6,'CS237','Java Programming Laboratory', 1.5), " +
                "('B.Tech','MT',7,'MT217','Mechatronics System Design', 4), " +
                "('B.Tech','MT',7,'MT218','Modeling and Simulation', 3), " +
                "('B.Tech','MT',7,'MTP71','Professional Elective –IV', 3), " +
                "('B.Tech','MT',7,'MTP72','Professional Elective –V', 3), " +
                "('B.Tech','MT',7,'MT219','System Integration Laboratory', 1.5), " +
                "('B.Tech','MT',7,'MT220','Modeling and Simulation Laboratory', 1.5), " +
                "('B.Tech','MT',7,'MT223','Mini Project', 2), " +
                "('B.Tech','MT',8,'SWO81','Open Elective through SWAYAM', 2), " +
                "('B.Tech','MT',8,'SWO82','Open Elective through SWAYAM', 2), " +
                "('B.Tech','MT',8,'MT224','Comprehensive Test', 1), " +
                "('B.Tech','MT',8,'MT225','Internship', 2), " +
                "('B.Tech','MT',8,'MT226','Project Work', 8);";

        db.execSQL(createTableDepartmentName);
        db.execSQL(insertDepartmentName);
        db.execSQL(createTableCgpa);
        db.execSQL(createTableSubject);
        db.execSQL(insertTableSubjectCSE);
        db.execSQL(insertTableSubjectIT);
        db.execSQL(insertTableSubjectCE);
        db.execSQL(insertTableSubjectME);
        db.execSQL(insertTableSubjectECE);
        db.execSQL(insertTableSubjectEEE);
        db.execSQL(insertTableSubjectEIE);
        db.execSQL(insertTableSubjectCH);
        db.execSQL(insertTableSubjectMT);
        db.execSQL(createTableGrade);
        db.execSQL(insertTableGrade);
        db.execSQL(createTableStudentGrade);
        db.execSQL(createTableSelDept);
        db.execSQL(insertSelDept);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String getDeptName(String dept)
    {
        SQLiteDatabase db = getWritableDatabase();
        String getDeptName = "SELECT deptname FROM departmentname WHERE dept = '"+dept+"' ;";
        Cursor c = db.rawQuery(getDeptName,null);
        c.moveToFirst();
        String d = c.getString(0);
        return d;
    }

    public String getDeptfromName(String deptName)
    {
        SQLiteDatabase db = getWritableDatabase();
        String getDeptName = "SELECT dept FROM departmentname WHERE deptname = '"+deptName+"' ;";
        Cursor c = db.rawQuery(getDeptName,null);
        c.moveToFirst();
        String d = c.getString(0);
        return d;
    }

    public void setDept(String dept)
    {
        SQLiteDatabase db = getWritableDatabase();
        String delDept = "DELETE FROM seldept ; ";
        db.execSQL(delDept);
        String insertDept = "INSERT INTO seldept (dept) VALUES ('"+dept+"')";
        db.execSQL(insertDept);
    }

    public String getDept()
    {
        SQLiteDatabase db = getReadableDatabase();
        String s = "SELECT * FROM seldept ; ";
        Cursor c = db.rawQuery(s,null);
        //if(c.moveToFirst());
        c.moveToFirst();
        String dept = c.getString(0);
            return dept;
    }
    public void destroy()
    {
        SQLiteDatabase db = getWritableDatabase();
        String del1 = "DELETE FROM studentcgpa ; ";
        String del2 = "DELETE FROM studentgrade ; ";
        String del3 = "DELETE FROM seldept ; ";
        db.execSQL(del1);
        db.execSQL(del2);
        db.execSQL(del3);
        String insertSelDept = "INSERT INTO seldept (dept) VALUES ('NODEPT') ; ";
        db.execSQL(insertSelDept);
    }

    public void addSem()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String getdept = "SELECT * FROM seldept";
        Cursor cur = db.rawQuery(getdept,null);
        cur.moveToFirst();
        String dept = cur.getString(0);

        ContentValues cv = new ContentValues();
        Cursor pk = db.rawQuery("SELECT COUNT(*)+1 FROM studentcgpa",null);
        pk.moveToFirst();
        int sem =pk.getInt(0);
        cv.put(COLUMN_CGPA_SEMESTER, sem);

        String cgpaSelectQuery = "SELECT MAX("+COLUMN_CGPA_SEMESTER+") FROM " + CGPA_TABLE;
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery(cgpaSelectQuery,null);
        cursor.moveToFirst();
        int val = cursor.getInt(0);
        if(val < 8) {
            db.insert(CGPA_TABLE, null, cv);
            String insertGradeTable ="INSERT INTO "+TABLE_STUDENTGRADE+" ( "+COLUMN_SG_SEMESTER+" , "+COLUMN_SG_SUBCODE+" , "+COLUMN_SG_SUBNAME+" , "+COLUMN_SG_SUBCREDIT+" ) " +
                    " SELECT "+COLUMN_SUBJECT_SEMESTER+" , "+COLUMN_SUBJECT_SUBCODE+" , "+COLUMN_SUBJECT_SUBNAME+" , "+COLUMN_SUBJECT_SUBCREDIT+" FROM "+SUBJECT_TABLE+" " +
                    " WHERE "+COLUMN_SUBJECT_SEMESTER+" = '"+sem+"' AND "+COLUMN_SUBJECT_DEPT+" = '"+dept+"' ; ";
            db.execSQL(insertGradeTable);
        }
        else {
            Toast.makeText(c, "Semester limit reached", Toast.LENGTH_LONG).show();
        }

    }

    public void remSem()
    {
        SQLiteDatabase db =this.getWritableDatabase();
        String deleteSem = "DELETE FROM "+CGPA_TABLE+" WHERE "+COLUMN_CGPA_SEMESTER+" = ( SELECT MAX( "+COLUMN_CGPA_SEMESTER+" ) FROM "+CGPA_TABLE+" ) ;";
        String deleteStuG = "DELETE FROM "+TABLE_STUDENTGRADE+" WHERE "+COLUMN_SG_SEMESTER+"= ( SELECT MAX("+COLUMN_SG_SEMESTER+") FROM "+TABLE_STUDENTGRADE+" );";
        db.execSQL(deleteSem);
        db.execSQL(deleteStuG);
    }

    public List<CgpaModel> showSemList()
    {
        List<CgpaModel> returnList = new ArrayList<>();

        String cgpaSelectQuery = "SELECT * FROM " + CGPA_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(cgpaSelectQuery,null);

        if(cursor.moveToFirst())
        {
            do {
                int semester = cursor.getInt(0);
                int credit = cursor.getInt(1);
                double gpa = cursor.getDouble(2);
                double cgpa = cursor.getDouble(3);

                CgpaModel newCgpaModel = new CgpaModel(semester, credit, gpa, cgpa);
                returnList.add(newCgpaModel);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<GradeModel> showGrade(int sem)
    {
        List<GradeModel> returnList = new ArrayList<>();

        String gradeSelectQuery = "SELECT "+COLUMN_SUBJECT_SUBCODE+" , "+COLUMN_SUBJECT_SUBNAME+" , "+COLUMN_SUBJECT_SUBCREDIT+" , "+COLUMN_SG_GRADE+" FROM "+TABLE_STUDENTGRADE+" WHERE "+COLUMN_SG_SEMESTER+" = '"+sem+"';";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(gradeSelectQuery,null);

        if(cursor.moveToFirst())
        {
            do {
                String subCode = cursor.getString(0);
                String subName = cursor.getString(1);
                double subCredit = cursor.getDouble(2);
                String grade = cursor.getString(3);

                GradeModel newGradeModel = new GradeModel(subCode,subName,subCredit,grade);
                returnList.add(newGradeModel);

            }while(cursor.moveToNext());
        }

        return returnList;
    }

    public void updateGrade(List<GradeModel> updList, int sem)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        for(int position = 0 ; position < updList.size() ; position++)
        {
            GradeModel model = updList.get(position);
            String subCode = model.getSubCode();
            String grade = model.getGrade();

            String upt = "UPDATE "+TABLE_STUDENTGRADE+" SET "+COLUMN_SG_GRADE+" = '"+grade+"' WHERE "+COLUMN_SG_SEMESTER+" = "+sem+" AND "+COLUMN_SG_SUBCODE+" = '"+subCode+"' ;";
            db.execSQL(upt);
        }
        String updateGpa = "UPDATE studentcgpa SET gpa = (SELECT sum(subcredit*gradepoint)/sum(subcredit) FROM studentgrade NATURAL JOIN gradepoints WHERE semester = "+sem+" ) WHERE semester = "+sem+" ;";
        db.execSQL(updateGpa);
        for(int i=sem;i<=8;i++)
        {
            String updateCgpa = "UPDATE studentcgpa SET cgpa = ( SELECT sum(subcredit*gradepoint)/sum(subcredit) FROM studentgrade NATURAL JOIN gradepoints WHERE semester BETWEEN 1 AND " + i + " ) WHERE semester = " + i + " ; ";
            db.execSQL(updateCgpa);
        }
        String totalCredit = "UPDATE "+CGPA_TABLE+" set "+COLUMN_CGPA_TOTALCREDIT+" = ( SELECT sum(subcredit) FROM "+TABLE_STUDENTGRADE+" WHERE semester = "+sem+" ) WHERE semester = "+sem+" ;";
        db.execSQL(totalCredit);
    }

    public void inOpen(int sem)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertOpenQuery = "INSERT INTO "+TABLE_STUDENTGRADE+" ( "+COLUMN_SG_SEMESTER+" , "+COLUMN_SG_SUBCODE+" , "+COLUMN_SG_SUBNAME+" , "+COLUMN_SG_SUBCREDIT+" ) VALUES( "+sem+" , 'OPN0"+sem+"' , 'Open Elective Course' , '3.0' );";
        db.execSQL(insertOpenQuery);
    }

    public void remOpen(int sem)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteOpenQuery = "DELETE FROM "+TABLE_STUDENTGRADE+" WHERE "+COLUMN_SG_SEMESTER+" = "+sem+" AND "+COLUMN_SG_SUBCODE+" like 'OPN0%' ; ";
        db.execSQL(deleteOpenQuery);
    }

    public void inHonour(int sem)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertHonourQuery = "INSERT INTO "+TABLE_STUDENTGRADE+" ( "+COLUMN_SG_SEMESTER+" , "+COLUMN_SG_SUBCODE+" , "+COLUMN_SG_SUBNAME+" , "+COLUMN_SG_SUBCREDIT+" ) VALUES( "+sem+" , 'HOM0"+sem+"' , 'Honour/Minor Course' , '4.0' );";
        db.execSQL(insertHonourQuery);
    }

    public void remHonour(int sem)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteHonourQuery = "DELETE FROM "+TABLE_STUDENTGRADE+" WHERE "+COLUMN_SG_SEMESTER+" = "+sem+" AND "+COLUMN_SG_SUBCODE+" like 'HOM0%' ; ";
        db.execSQL(deleteHonourQuery);
    }
    public boolean checkOpen(int sem)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String checkOpen = "SELECT COUNT(*) FROM "+TABLE_STUDENTGRADE+" WHERE "+COLUMN_SG_SEMESTER+" = "+sem+" AND "+COLUMN_SG_SUBCODE+" like 'OPN0%' ; ";
        Cursor cursor = db.rawQuery(checkOpen,null);
        cursor.moveToFirst();
        int n = cursor.getInt(0);
        if (n == 1)
            return true;
        else
            return false;
    }
    public boolean checkHonour(int sem)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String checkHonour = "SELECT COUNT(*) FROM "+TABLE_STUDENTGRADE+" WHERE "+COLUMN_SG_SEMESTER+" = "+sem+" AND "+COLUMN_SG_SUBCODE+" like 'HOM0%' ; ";
        Cursor cursor = db.rawQuery(checkHonour,null);
        cursor.moveToFirst();
        int n = cursor.getInt(0);
        if (n ==1)
            return true;
        else
            return false;
    }

    public void insertCgpa(CgpaModel cgpa)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CGPA_SEMESTER,cgpa.getSemester());
        cv.put(COLUMN_CGPA_TOTALCREDIT,cgpa.getTotal_credits());
        cv.put(COLUMN_CGPA_GPA,cgpa.getGpa());
        cv.put(COLUMN_CGPA_CGPA,cgpa.getCgpa());
        db.insert(CGPA_TABLE,null, cv);
    }
    public double getcgpa()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String qry = "Select "+COLUMN_CGPA_CGPA+" from "+CGPA_TABLE;
        Cursor c = db.rawQuery(qry,null);
        double d = 0;
        if(c.moveToLast())
        {
            d = c.getDouble(c.getColumnIndexOrThrow(COLUMN_CGPA_CGPA));
        }
        return d;

    }

    public List<GradeModel> showGradeTable()
    {
        List<GradeModel> returnList = new ArrayList<>();

        String gradeQuery = "SELECT " +COLUMN_SG_SEMESTER+ " , " +COLUMN_SG_SUBCODE+" , "+COLUMN_SG_SUBNAME+" , "+COLUMN_SG_SUBCREDIT+" , "+COLUMN_SG_GRADE+" FROM "+TABLE_STUDENTGRADE+ " ;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(gradeQuery,null);

        if(cursor.moveToFirst())
        {
            do {
                int sem = cursor.getInt(0);
                String subCode = cursor.getString(1);
                String subName = cursor.getString(2);
                double subCredit = cursor.getDouble(3);
                String grade = cursor.getString(4);

                GradeModel newGradeModel = new GradeModel(sem,subCode,subName,subCredit,grade);
                returnList.add(newGradeModel);

            }while(cursor.moveToNext());
        }
        return returnList;
    }

    public void insertGrade(GradeModel grade)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SG_SEMESTER,grade.getSemester());
        cv.put(COLUMN_SG_SUBCODE,grade.getSubCode());
        cv.put(COLUMN_SG_SUBNAME,grade.getSubName());
        cv.put(COLUMN_SG_SUBCREDIT,grade.getSubCredit());
        cv.put(COLUMN_SG_GRADE,grade.getGrade());
        db.insert(TABLE_STUDENTGRADE,null, cv);
    }

}
