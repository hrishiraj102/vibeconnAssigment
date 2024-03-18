import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Employee implements Serializable {
    private int id;
    private String name;
    private String position;
    private double salary; // double so that it can handle decimal too

    public Employee(int id, String name, String position, double salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    // Keeping id,name,position,salary private and using getter/setter implementing
    // Encapsulation
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: "
                + name + ", Position: " + position + ", Salary: " + salary;
    }

}

class EmployeeManagementSystem {

    private ArrayList<Employee> employees;

    private Scanner sc;

    public EmployeeManagementSystem() {
        employees = new ArrayList<>();
        sc = new Scanner(System.in);
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void viewAllEmployees() {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    public void updateEmployee(int id, String name, String position, double salary) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employee.setName(name);
                employee.setPosition(position);
                employee.setSalary(salary);
                return;
            }
        }
        System.out.println("Employee with ID " + id + " do not Exit.");
    }

    public void deleteEmployee(int id) {
        employees.removeIf(employee -> employee.getId() == id);
    }

    public void saveData() {

        try {
            FileOutputStream file = new FileOutputStream("employees.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(employees);
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked") // I am ignoring any warning when loading the data
    public void loadData() {
        try {
            FileInputStream fileIn = new FileInputStream("employees.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            employees = (ArrayList<Employee>) in.readObject(); //getting back the data to object employees
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void displayMenu() {
        System.out.println("\nEmployee Management System Menu:");
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. Update Employee");
        System.out.println("4. Delete Employee");
        System.out.println("5. Save Data");
        System.out.println("6. Load Data");
        System.out.println("7. Exit");
    }

    public void run() {
        int option;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            option = sc.nextInt();
            sc.nextLine(); 

            switch (option) {
                case 1:
                    int id;

                    // Handling Inputmismatch if user type non int number
                    while (true) {
                        try {

                            System.out.print("Enter employee ID: ");
                            id = sc.nextInt();
                            break; // If no error break the loop
                        } catch (InputMismatchException e) {
                            // TODO: handle exception
                            System.out.println("Invalid input. Please enter a valid integer for employee ID.");
                            sc.nextLine();
                        }
                    }
                    sc.nextLine(); // Consume newline 
                    System.out.print("Enter employee name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter employee position: ");
                    String position = sc.nextLine();
                    System.out.print("Enter employee salary: ");
                    double salary = sc.nextDouble();
                    // Handling negative input by user just by while loop
                    while (salary < 0.0) {
                        System.out.print("Enter positive employee salary: ");
                        salary = sc.nextDouble();
                    }
                    addEmployee(new Employee(id, name, position, salary));
                    break;
                case 2:
                    viewAllEmployees();
                    break;
                case 3:
                    int updateId;
                    // Handling Inputmismatch if user type non int number
                    while (true) {
                        try {

                            System.out.print("Enter employee ID to update: ");
                            updateId = sc.nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            // TODO: handle exception
                            System.out.println("Invalid input. Please enter a valid integer for employee ID.");
                            sc.nextLine();
                        }
                    }

                    sc.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = sc.nextLine();
                    System.out.print("Enter new position: ");
                    String newPosition = sc.nextLine();
                    System.out.print("Enter new salary: ");
                    double newSalary = sc.nextDouble();
                    // Handling negative input by user just by while loop
                    while (newSalary < 0.0) {
                        System.out.print("Enter positive employee salary: ");
                        newSalary = sc.nextDouble();
                    }
                    updateEmployee(updateId, newName, newPosition, newSalary);
                    break;
                case 4:

                    int deleteId;
                    while (true) {
                        // Handling Inputmismatch if user type non int number
                        try {

                            System.out.print("Enter employee ID for deletion: ");
                            deleteId = sc.nextInt();
                            deleteEmployee(deleteId);
                            break;
                        } catch (InputMismatchException e) {
                            // TODO: handle exception
                            System.out.println("Invalid input. Please enter a valid integer for employee ID.");
                            sc.nextLine();
                        }
                    }

                case 5:
                    saveData();
                    System.out.println("Data saved successfully.");
                    break;
                case 6:
                    loadData();
                    System.out.println("Data loaded successfully.");
                    break;
                case 7:
                    saveData();
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (option != 7);

    }

}

public class Main {
    public static void main(String[] args) {
        EmployeeManagementSystem emsSystem = new EmployeeManagementSystem();
        emsSystem.run();
    }
}
