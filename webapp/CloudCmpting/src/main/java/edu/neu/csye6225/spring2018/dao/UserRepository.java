package edu.neu.csye6225.spring2018.dao;

import edu.neu.csye6225.spring2018.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.repository.CrudRepository;
=======
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
>>>>>>> 5c60689443bb356a3518688f529ba947a5f22eee

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findByEmailAndPassword(String email, String password);
    public boolean existsByEmail(String email);
<<<<<<< HEAD
    //public List<User> findAllByOrderByUpdatedAtDesc();
    List<User> findAll();




=======
    public String getByPassword(String password);
    public User findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE user c SET c.aboutMe = :aboutMe, c.imageFilePath = :imageFilePath WHERE c.id = :id")
    public int updateUser(@Param("id") int id, @Param("aboutMe") String aboutMe, @Param("imageFilePath") String imageFilePath);

//    @Query("delete from user u where u.imageFilePath = :imageFilePath")
//    void delete(int id);
>>>>>>> 5c60689443bb356a3518688f529ba947a5f22eee

}
