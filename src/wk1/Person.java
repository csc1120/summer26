package wk1;

/*
 +-------------------------------------------------+
 |                      Person                     |
 +-------------------------------------------------+
 | -firstNameFirst: boolean <<static>> {= true}    |
 | -firstName: String                              |
 | -lastName: String                               |
 +-------------------------------------------------+
 | +Person(firstName: String, lastName: String)    |
 | +setDisplayFirst(name: String): void <<static>> |
 | +setFirstName(name: String): void               |
 | +setLastName(name: String): void                |
 | +toString(): String                             |
 +-------------------------------------------------+

 */
public class Person {
    private static boolean firstNameFirst = true;
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static void setDisplayFirst(String name) {
        firstNameFirst = name.equalsIgnoreCase("first");
    }

    public void setFirstName(String name) {
        firstName = name;
    }

    public void setLastName(String name) {
        lastName = name;
    }

    @Override
    public String toString() {
        String result = firstName + " " + lastName;
        if (!firstNameFirst) {
            result = lastName + ", " + firstName;
        }
        return result;
    }
}
