package university.people;

public class Person {
    private String name;
    protected String id;

    public Person(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    // Method to demonstrate private member access (will be accessed via reflection in Main)
    public String getPrivateName() {
        return name;
    }

    // Method to demonstrate protected member access
    public String getProtectedId() {
        return id;
    }
}
