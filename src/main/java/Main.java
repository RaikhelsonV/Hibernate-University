import account.Account;
import account.FinalFields;
import account.InputWord;
import exception.*;
import service.AdministrationService;
import service.DepartmentService;
import service.FacultyService;
import service.UniversityService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        AdministrationService administrationService = new AdministrationService();
        UniversityService universityService = new UniversityService();
        FacultyService facultyService = new FacultyService();
        DepartmentService departmentService = new DepartmentService();
        Account account = new Account();
        InputWord inputWord = new InputWord();
        FinalFields finalFields = new FinalFields();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String enter = "";
        try {
            while (!enter.equalsIgnoreCase("stop")) {
                System.out.println("Select account: univer, admin, fac or dep:" +
                        "\nTo go to the next account enter 'stop'.");
                enter = reader.readLine();
                String input = "";
                if (enter.equals("univer")) {
                    account.universityAccount();
                    input = reader.readLine();
                    switch (input) {
                        case ("1"):
                            universityService.createUniversity(inputWord.university());
                            break;
                        case ("2"):
                            universityService.openFaculty(inputWord.faculty(), finalFields.UNIVERSITY_NAME);
                            break;
                        case ("3"):
                            System.out.println("Enter faculty id and faculty name:");
                            universityService.renameFaculty(inputWord.readInt(), inputWord.readString());
                            break;
                        case ("4"):
                            System.out.println("Enter faculty id:");
                            universityService.closeFaculty(inputWord.readInt());
                            break;
                        case ("5"):
                            universityService.getAllFacultiesFromUniversity(finalFields.UNIVERSITY_NAME);
                            break;
                        case ("6"):
                            System.out.println("Enter faculty id:");
                            universityService.getFaculty(inputWord.readInt());
                            break;
                        case ("7"):
                            System.out.println("Enter faculty id:");
                            universityService.getAllStudentsFromFaculty(inputWord.readInt());
                            break;

                    }
                } else if (enter.equals("admin")) {
                    account.administrationAccount();
                    input = reader.readLine();
                    switch (input) {
                        case ("21"):
                            administrationService.appointAdministration(inputWord.administration());
                            break;
                        case ("22"):
                            administrationService.fireAdministration(finalFields.UNIVERSITY_NAME);
                            break;
                        case ("23"):
                            System.out.println("Enter administration id:");
                            administrationService.updateAdministration(inputWord.readInt(), inputWord.administration());
                            break;
                        case ("24"):
                            administrationService.matriculatedStudent(inputWord.student(), finalFields.UNIVERSITY_NAME);
                            break;
                        case ("25"):
                            System.out.println("Enter student id:");
                            administrationService.expelStudent(inputWord.readInt());
                            break;

                    }
                } else if (enter.equals("fac")) {
                    account.facultyAccount();
                    input = reader.readLine();
                    switch (input) {
                        case ("31"):
                            System.out.println("Enter faculty id:");
                            facultyService.openDepartment(inputWord.readInt(), inputWord.department());
                            break;
                        case ("32"):
                            System.out.println("Enter department id:");
                            facultyService.addScheduleToDepartment(inputWord.readInt(), inputWord.schedule());
                            break;
                        case ("33"):
                            System.out.println("Enter faculty id:");
                            facultyService.hireTeacher(inputWord.readInt(), inputWord.teacher());
                            break;
                        case ("34"):
                            System.out.println("Enter teacher id:");
                            facultyService.updateTeacher(inputWord.readInt(), inputWord.teacher());
                            break;
                        case ("35"):
                            System.out.println("Enter teacher id:");
                            facultyService.fireTeacher(inputWord.readInt());
                            break;
                        case ("36"):
                            System.out.println("Enter teacher id and scheule id:");
                            facultyService.addLectureToSchedule(inputWord.readInt(), inputWord.readInt());
                            break;
                        case ("37"):
                            System.out.println("Enter department id and department name:");
                            facultyService.renameDepartment(inputWord.readInt(), inputWord.readString());
                            break;
                        case ("38"):
                            System.out.println("Enter department id:");
                            facultyService.closeDepartment(inputWord.readInt());
                            break;
                        case ("39"):
                            System.out.println("Enter faculty id:");
                            facultyService.getAllDepartmentsFromFaculty(inputWord.readInt());
                            break;
                        case ("40"):
                            System.out.println("Enter department id:");
                            facultyService.getDepartment(inputWord.readInt());
                            break;
                        case ("41"):
                            System.out.println("Enter department id:");
                            facultyService.getAllStudentsFromDepartment(inputWord.readInt());
                            break;
                        case ("42"):
                            System.out.println("Enter faculty id:");
                            facultyService.getAllTeachersFromFaculty(inputWord.readInt());
                            break;
                        case ("43"):
                            System.out.println("Enter teacher id:");
                            facultyService.getTeacher(inputWord.readInt());
                            break;
                        case ("44"):
                            System.out.println("Enter teacher id:");
                            facultyService.getLectureOfTeacher(inputWord.readInt());
                            break;
                    }
                } else if (enter.equals("dep")) {
                    account.departmentAccount();
                    input = reader.readLine();
                    switch (input) {
                        case ("51"):
                            System.out.println("Enter department id:");
                            departmentService.openGroup(inputWord.readInt(), inputWord.studyGroup());
                            break;
                        case ("52"):
                            System.out.println("Enter student id and department id:");
                            facultyService.assignStudentToDepartment(inputWord.readInt(), inputWord.readInt());
                            break;
                        case ("53"):
                            System.out.println("Enter group id and group name:");
                            departmentService.renameGroup(inputWord.readInt(), inputWord.readString());
                            break;
                        case ("54"):
                            System.out.println("Enter group id:");
                            departmentService.closeGroup(inputWord.readInt());
                            break;
                        case ("55"):
                            departmentService.getAllStudents(finalFields.UNIVERSITY_NAME);
                            break;
                        case ("56"):
                            System.out.println("Enter group id:");
                            departmentService.getAllStudentsFromGroup(inputWord.readInt());
                            break;
                        case ("57"):
                            System.out.println("Enter student id, last name and first name :");
                            departmentService.renameStudent(inputWord.readInt(), inputWord.readString(), inputWord.readString());
                            break;
                        case ("58"):
                            System.out.println("Enter department id:");
                            departmentService.getAllGroupsFromDepartment(inputWord.readInt());
                            break;
                        case ("59"):
                            System.out.println("Enter group id:");
                            departmentService.getStudyGroup(inputWord.readInt());
                            break;
                        case ("60"):
                            System.out.println("Enter schedule id:");
                            departmentService.updateSchedule(inputWord.readInt(), inputWord.schedule());
                            break;

                    }
                }
            }
        } catch (IOException | NoSuchDepartmentException | NoSuchTeacherException | NoSuchGroupException | NoSuchFacultyException | NoSuchAdministrationException | NoSuchStudentException | NoSuchScheduleException e) {
            e.printStackTrace();
        }

    }


}
