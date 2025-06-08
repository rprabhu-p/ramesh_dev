import java.util.*;

class Student {
    Integer rollNo;
    String name;

    //define constructor
    Student(String name, Integer rollNo) {
        this.name = name;
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }
    public Integer getRollNo() {
        return rollNo;
    }

    //method to print statement
    @Override
    public String toString(){
        return rollNo + " => " + name;
    }
}

//comparator class
class SortbyRoll implements Comparator <Student> {
    //compare all rollNo by asc order
    public int compare(Student a, Student b) {
        //return a.rollNo - b.rollNo;
        //compare by name
        int nameCompare = a.getName().compareTo(b.getName());
        //compare by rollNo
        int rollCompare = a.getRollNo().compareTo(b.getRollNo());

        return  (nameCompare == 0) ? rollCompare : nameCompare;
    }

}

public class ComparatorExample {
    public static void main(String[] args) {

        //list of students
        List<Student> stds = new ArrayList<Student>();

        stds.add(new Student("ram", 121));
        stds.add(new Student("prabhu", 111));
        stds.add(new Student("raj", 154));
        stds.add(new Student("balu", 130));

        System.out.println("Before Sort");

        for(Student t : stds){
            System.out.println(t);
        }

        //sort Student by RollNo
        Collections.sort(stds, new SortbyRoll());

        System.out.println("After Sort");

        for(Student t : stds){
            System.out.println(t);
        }
    }
}
