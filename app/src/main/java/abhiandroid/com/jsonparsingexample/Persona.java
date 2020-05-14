package abhiandroid.com.jsonparsingexample;

public class Persona {

    private  int id;
    private String name;
    private  String department;
    private int Salary;

    public Persona(){

    }

    public Persona(int id, String name, String department, int salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        Salary = salary;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }
}
