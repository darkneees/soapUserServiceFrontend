import com.darkneees.soapfrontservice.entity.Role;
import com.darkneees.soapfrontservice.entity.Social;
import com.darkneees.soapfrontservice.entity.User;
import com.darkneees.soapfrontservice.service.RoleServiceImpl;
import com.darkneees.soapfrontservice.service.UserServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MainTest {

    UserServiceImpl userService = new UserServiceImpl();
    RoleServiceImpl roleService = new RoleServiceImpl();

    @Test
    public void getAllUsers(){
        List<User> users = userService.getAllUsers().join();
        System.out.println(users);
    }

    @Test
    public void getUserByUsername() {
        User user = userService.getUserByUsername("dark").join();
        System.out.println(user);
    }

    @Test
    public void addUser() {
        User user = new User();

        user.setUsername("dark11");
        user.setPassword("pass");
        user.setFirstName("Andrey11");
        user.setSecondName("Nesterov11");
        user.setAge(10);

        user.getRoleSet().add(new Role(1L));
        user.getSocialSet().add(new Social("VK", "VK"));

        userService.addUser(user).join();
    }

    @Test
    public void deleteRoleUser() {
        userService.deleteRoleUser("darkneees", 1).join();
    }

    @Test
    public void addRoleUser(){
        userService.addRoleUser("darkneees", 1).join();
        userService.addRoleUser("darkneees", 2).join();
    }

    @Test
    public void editUser() {
        User user = new User();

        user.setUsername("dark11");
        user.setPassword("Nesterov");
        user.setFirstName("dsadas");
        user.setSecondName("pass");
        user.setAge(20);

        userService.editUser(user).join();
    }

    @Test
    public void deleteSocial() {
        userService.deleteSocialUser("dark11", "VK").join();
    }

    @Test
    public void addSocial() {
        userService.addSocialUser("dark11", new Social("VK", "VK")).join();
    }

    @Test
    public void deleteUser() {
        userService.deleteUserByUsername("dark11").join();
    }

    @Test
    public void addRole(){
        Role role = new Role();

        role.setId(5L);
        role.setNameRole("PAR");
        role.setPrettyNameRole("PARROT");
        roleService.addRole(role).join();
    }

    @Test
    public void getAllRoles() {
        List<Role> roles = roleService.getAllRoles().join();
        System.out.println(roles);
    }


}
