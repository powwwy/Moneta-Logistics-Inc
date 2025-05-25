package System;

import System.managers.*;
import System.handler.*;
import java.sql.*;


public class Main {
    public static void main(String[] args) throws SQLException {

        DBUtil.getConnection();
        Connection conn = DBUtil.getConnection();

        UserManager manager = new UserManager();
        manager.selectUserRole();
    }
}