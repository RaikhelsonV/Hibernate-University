package account;

public class Account {

    public void universityAccount() {
        System.out.println("\n1 - createUniversity.\n" +
                "2 - openFaculty.\n" +
                "3 - renameFaculty.\n" +
                "4 - closeFaculty.\n" +
                "5 - getAllFacultiesFromUniversity.\n" +
                "6 - getFaculty.\n" +
                "7 - getAllStudentsFromFaculty.\n" +
                "\n" +
                "Please, enter the corresponding number: ");
    }

    public void administrationAccount() {
        System.out.println("\n21 - appointAdministration.\n" +
                "22 - fireAdministration.\n" +
                "23 - update administration.\n" +
                "24 - matriculatedStudent.\n" +
                "25 - expelledStudent.\n" +
                "Please, enter the corresponding number: ");
    }

    public void facultyAccount() {
        System.out.println("\n31 - openDepartment.\n" +
                "32 - addScheduleToDepartment.\n" +
                "33 - hireTeacher.\n" +
                "34 - updateTeacher.\n" +
                "35 - fireTeacher.\n" +
                "36 - addLectureToSchedule.\n" +
                "37 - renameDepartment.\n" +
                "38 - closeDepartment.\n" +
                "39 - getAllDepartmentsFromFaculty.\n" +
                "40 - getDepartment.\n" +
                "41 - getAllStudentsFromDepartment.\n" +
                "42 - getAllTeachersFromFaculty.\n" +
                "43 - getTeacher.\n" +
                "44 - getLectureOfTeacher.\n" +
                "Please, enter the corresponding number: ");
    }

    public void departmentAccount() {
        System.out.println("\n51 - openGroup.\n" +
                "52 - assignStudentToDepartmentAndDistributeToGroup.\n" +
                "53 - renameGroup.\n" +
                "54 - closeGroup.\n" +
                "55 - getAllStudents.\n" +
                "56 - getAllStudentsFromGroup.\n" +
                "57 - renameStudent.\n" +
                "58 - getAllGroupsFromDepartment.\n" +
                "59 - getStudyGroup.\n" +
                "60 - updateSchedule.\n" +

                "Please, enter the corresponding number: ");
    }
}
