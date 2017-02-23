package domain.model;

public class Person {

    private String name;
    private String password;
    private Status status;

    public Person(String name, String password) {
        setName(name);
        setPassword(password);
        status = Status.OFFLINE;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if(name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public boolean passwordMatches(String password) {
        return this.password.equals(password);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
