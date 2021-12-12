package org.example.dao;

import org.example.models.Person;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class PersonDAO {

    private static final String URL ="jdbc:mysql://localhost:3306/lr1";
    private static final String USER ="root";
    private static final String PASSWORD ="1111";

    private static Connection connection;
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static final String CREATE_USER = "INSERT INTO users (login, password)VALUES (?, ?)";

    public List<Person> index(){
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM USERS";

            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setLogin(resultSet.getString("login"));
                person.setPassword(resultSet.getString("password"));
                people.add(person);
            }
        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }
        return people;
    }

    public boolean save(Person person){
        boolean result = false;

        try {
            PreparedStatement statement = connection.prepareStatement(CREATE_USER);
            statement.setString(1, person.getLogin());
            statement.setString(2, person.getPassword());

            result = statement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }


}