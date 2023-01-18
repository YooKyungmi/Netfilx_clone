package com.example.demo.src.users;

import com.example.demo.src.users.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UsersDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public  void setDataSource(DataSource dataSource){this.jdbcTemplate = new JdbcTemplate(dataSource);}
    public GetUsersRes getUser(int userId){
        String getUserQuery = "select userId, email, membershipType, pwd, phoneNum from User where userId = ?";
        int getUserParams = userId;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs,rowNum)  -> new GetUsersRes(
                    rs.getInt("userId"),
                    rs.getString("email"),
                    rs.getString("membershipType"),
                    rs.getString("pwd"),
                    rs.getString("phoneNum")), getUserParams);

    }

    public int createUser(PostUsersReq postUsersReq){
        String createUserQuery = "insert into User VALUES (null,?,?,?,?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,1)";
        Object[] createUserParams = new Object[]{postUsersReq.getEmail(),postUsersReq.getMembership(),postUsersReq.getPassword(),postUsersReq.getPhoneNum()};
        this.jdbcTemplate.update(createUserQuery,createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int modifyUser(PatchUsersReq patchUsersReq){
        String modifyUserQuery = "update User set "+patchUsersReq.getModItem()+" = ?, updateAt=CURRENT_TIMESTAMP where userId = ?";
        Object[] modifyUserParams = new Object[]{patchUsersReq.getValue(),patchUsersReq.getUserId()};

        return this.jdbcTemplate.update(modifyUserQuery, modifyUserParams);
    }

    public void deleteUser(int userId){
        String deleteUserQuery = "delete from User where userId=?";
        int deleteUserParams = userId;
        this.jdbcTemplate.update(deleteUserQuery, deleteUserParams);
    }
}
