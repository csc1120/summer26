package wk1;

/*
 +-----------------------------------------------+
 |                     Person                    |
 +-----------------------------------------------+
                         /\ (open triangle)
                         |
 +-----------------------------------------------+
 |                     Student                   |
 +-----------------------------------------------+
 | -gpa: double                                  |
 +-----------------------------------------------+
 | +Student(firstName: String, lastName: String) |
 | +Student(firstName: String)                   |
 | +setGpa(gpa: double): boolean                 |
 | +toString(): String                           |
 +-----------------------------------------------+
 */
public class Student extends Person {
    private double gpa;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Student(String firstName) {
       this(firstName, null);
    }

    public boolean setGpa(double gpa) {
        boolean changed = false;
        if (gpa >= 0 && gpa <= 4) {
            this.gpa = gpa;
            changed = true;
        }
        return changed;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + gpa + ")";
    }
}
