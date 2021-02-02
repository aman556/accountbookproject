package com.example.javaproject.accountbook;

import com.example.javaproject.accountbook.model.User;
import com.example.javaproject.accountbook.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public  void testCreateUser(){
        User user = new User();
        user.setEmail("namansharma14041998@gmail.com");
        user.setPassword("9971167213");
        user.setFirstName("Naman");
        user.setLastName("Sharma");

        User savedUser = repo.save(user);

       User existUser = entityManager.find(User.class,savedUser.getId());

       Assertions.assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testFindUserByEmail(){
        String email ="50006789@stu.upes.ac.in";

        User user = repo.findByEmail(email);

        Assertions.assertThat(user).isNotNull();

    }
}
