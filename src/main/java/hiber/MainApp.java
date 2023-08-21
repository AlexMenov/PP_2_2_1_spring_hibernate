package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
      UserService userService = context.getBean(UserService.class);

      Car car1 = new Car("Жигули", 1956);
      Car car2 = new Car("Москвич", 1961);
      Car car3 = new Car("Запорожец", 1999);

      User user1 = new User("Alex", "Жигули", "mech@mail.ru");
      User user2 = new User("Alex", "Москвич", "mech@mail.ru");
      User user3 = new User("Alex", "Запорожец", "mech@mail.ru");

      user1.setCar(car1);
      user2.setCar(car2);
      user3.setCar(car3);
      car1.setUser(user1);
      car2.setUser(user2);
      car3.setUser(user3);

      userService.add(user1);
      userService.add(user2);
      userService.add(user3);
      System.out.println("-----------------------users-----------------------");
      userService.listUsers().forEach(System.out::println);

      System.out.println("-----------------------usersbyparams-----------------------");
      User user1956 = userService.getUserByModelAndSeries("Жигули", 1956);
      User user1961 = userService.getUserByModelAndSeries("Москвич", 1961);
      User user1999 = userService.getUserByModelAndSeries("Запорожец", 1999);
      System.out.println(user1956);
      System.out.println(user1961);
      System.out.println(user1999);

/*
      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println();
      }*/

      context.close();
   }
}
