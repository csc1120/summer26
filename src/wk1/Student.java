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
        gpa = 2.0;
    }

    public Student(String firstName) {
       this(firstName, null);
    }

    /**
     * Set the gpa for this student and returns the prior gpa
     * @param gpa desired gpa
     * @return previous gpa
     *
     * @throws IllegalArgumentException When an invalid gpa is passed to the method
     */
    public double setGpa(double gpa) throws IllegalArgumentException{
        if (gpa < 0 || gpa > 4) {
            throw new IllegalArgumentException("The gpa must be between 0 and 4. " + gpa + " is invalid");
        }
        double returnValue = this.gpa;
        this.gpa = gpa;
        return returnValue;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + gpa + ")";
    }
}
