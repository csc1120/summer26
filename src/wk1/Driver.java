package wk1;

public class Driver {
    public static void main(String[] args) {
        Person tom = new Person("Tom", "Jones");
        Person ed = new Person("Edward", "Jones");
        Student jane = new Student("Jane", "Jones");
        System.out.println(tom);
        System.out.println(jane);
        Person.setDisplayFirst("last");
        jane.setGpa(8);
        System.out.println(tom);
        System.out.println(ed);
        System.out.println(jane);
    }
}
