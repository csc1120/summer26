package wk1;

public class Driver {
    public static void main(String[] args) {
        Person tom = new Person("Tom", "Jones");
        Person ed = new Person("Edward", "Jones");
        Student jane = new Student("Jane", "Jones");
        System.out.println(tom);
        System.out.println(jane);
        Person.setDisplayFirst("last");
        try {
            jane.setGpa(8);
        } catch (IndexOutOfBoundsException | ArithmeticException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Made it out of the try block");
        }
        System.out.println(tom);
        System.out.println(ed);
        System.out.println(jane);
    }
}
