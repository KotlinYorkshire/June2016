package domain;

/**
 * Java Person Class
 */
public class PersonJ {

    private final String name;
    private final String gender;

    public PersonJ(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }
}
