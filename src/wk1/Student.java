package wk1;

public class Student extends Person {
    private double gpa;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
        gpa = 0;
    }

    public Student(String firstName) {
        this(firstName, null);
    }

    public void setGpa(double gpa) {
        if (gpa >= 0 && gpa <= 4) {
            this.gpa = gpa;
        }
    }

    @Override
    public String toString() {
        return super.toString() + " (" + gpa + ")";
    }
}
