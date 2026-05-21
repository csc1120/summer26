package wk1;

public class Person {
    private static boolean firstNameFirst = true;
    private String lastName;
    private String firstName;


    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static void setDisplayFirst(String name) {
        firstNameFirst = name.equalsIgnoreCase("first");
    }

    @Override
    public String toString() {
        String result = firstName + " " + lastName;
        if (!firstNameFirst) {
            result = lastName + ", " + firstName;
        }
        return result;
    }

    public void setFirstName(String name) {
        firstName = name;
    }

    public void setLastName(String name) {
        lastName = name;
    }
}
