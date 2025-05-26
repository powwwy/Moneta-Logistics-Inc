package System;

import System.managers.*;
import System.handler.DBUtil;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        DBUtil.getConnection();
        Connection conn = DBUtil.getConnection();

        User manager = new User() {
            @Override
            public void showMenu() {}
        };
        manager.selectUserRole();
    }
}