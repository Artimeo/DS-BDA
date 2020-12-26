import java.io.Serializable;

/** Person - defines person for data from a .csv input file line
 * fromString - creates class object from String line
 * getters must be defined
 */

public class Person implements Serializable {
    public int passportNumber;
    public int month;
    public int salary;
    public int age;
    public int trips;
    private final String category;

    public Person(int passportNumber, int month, int salary, int age, int trips) {
        if (age <= 0) {
            throw new RuntimeException("Object of class Human can not have age 0 or below");
        }
        this.passportNumber = passportNumber;
        this.month = month;
        this.salary = salary;
        this.age = age;
        this.trips = trips;
        this.category = categoryForAge(age);
    };

    public static Person fromString(String line) {
        String[] data = line.split(",");
        if (data.length != 5) {
            throw new RuntimeException("Person.fromString(): wrong data given");
        }
        return new Person(
                Integer.parseInt(data[0]),
                Integer.parseInt(data[1]),
                Integer.parseInt(data[2]),
                Integer.parseInt(data[3]),
                Integer.parseInt(data[4])
        );
    }

    @Override
    public String toString() {
        return  "passportNumber " + passportNumber +
                ", month " + month +
                ", salary " + salary +
                ", age " + age +
                ", category " + category +
                ", trips " + trips;
    }

    private String categoryForAge(int age) {
        if (0 < age && age < 18) {
            return "teenage";
        } else if (18 <= age && age < 27) {
            return "yong";
        } else if (27 <= age && age < 45) {
            return "average";
        } else if (45 <= age && age < 60) {
            return "mature";
        } else if (age >= 60) {
            return "old";
        }
        return "unknown";
    }

    // getters - must be provided for correct DataFrame schema creating

    public int getAge() { return age; }

    public int getMonth() { return month; }

    public int getPassportNumber() { return passportNumber; }

    public int getSalary() { return salary; }

    public int getTrips() { return trips; }

    public String getCategory() { return category; }
}
