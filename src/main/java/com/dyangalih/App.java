package com.dyangalih;

import com.dyangalih.student.Student;
import com.dyangalih.student.StudentJDBCTemplate;
import com.github.javafaker.Faker;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        StudentJDBCTemplate studentJDBCTemplate = (StudentJDBCTemplate)
                context.getBean("studentJDBCTemplate");

        System.out.println("------Delete All Record-------");

        studentJDBCTemplate.deleteAllData();

        System.out.println("------Records Creation--------" );
        Faker faker = new Faker();
        int maxStudent = faker.number().numberBetween(10,50);
        for (int i = 0; i < maxStudent; i++) {
            studentJDBCTemplate.create(faker.name().firstName(), faker.number().numberBetween(10,50));
        }

        System.out.println("------Listing Multiple Records--------" );
        List<Student> students = studentJDBCTemplate.listStudents();

        int firstRecord = 0, lastRecord=0;

        for (Student record : students) {
            if(firstRecord==0){
                firstRecord = record.getId();
            }
            System.out.print("ID : " + record.getId() );
            System.out.print(", Name : " + record.getName() );
            System.out.println(", Age : " + record.getAge());
            lastRecord = record.getId();
        }

        List<Student> studentsLast = studentJDBCTemplate.getStudent(lastRecord);

        System.out.println("The old name is '" + studentsLast.get(0).getName() + "' And the old Age is " + studentsLast.get(0).getAge());

        studentJDBCTemplate.updateDataById(faker.name().firstName(), faker.number().numberBetween(10,50),lastRecord);

        studentsLast = studentJDBCTemplate.getStudent(lastRecord);

        System.out.println("The new name is '" + studentsLast.get(0).getName()+ "' And the new Age is " + studentsLast.get(0).getAge());
    }
}
