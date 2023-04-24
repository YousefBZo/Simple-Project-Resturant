package ViewControl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class ConnectionDB {

    public static Connection connect() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/resturant", "root", "");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public static int count(String col, String table) throws SQLException {
        Connection con = connect();
        PreparedStatement ps = con.prepareStatement("select count(" + col + ")from " + table);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        con.close();
        return 0;
    }

    public static boolean insert(String table, int id, String name, String type, double cost) {
        Connection con = connect();
        try {
            PreparedStatement ps = con.prepareStatement("insert into " + table + " values (?, ?, ?, ?)");
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, type);
            ps.setDouble(4, cost);
            ps.executeUpdate();
        } catch (SQLException ex) {
            if (table.equals("Drinks")) {
                JOptionPane.showMessageDialog(null, "Id or Name already exists");
            } else {
                JOptionPane.showMessageDialog(null, "Id or Name already exists");
            }
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public static ObservableList<Drinks> getDrinks() {
        Connection con = connect();
        ObservableList<Drinks> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = con.prepareStatement("select * from Dranks");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Drinks(rs.getString("nameD"), rs.getString("typeD"), rs.getInt("numD"), rs.getDouble("costD")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
            }
        }
        return list;
    }

    public static ObservableList<Meals> getMeals() {
        Connection con = connect();
        ObservableList<Meals> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = con.prepareStatement("select * from Meals");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Meals(rs.getString("nameM"), rs.getString("typeM"), rs.getInt("numM"), rs.getDouble("costM")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
            }
        }
        return list;
    }

    public static boolean update(String table, String where, String name, String type, double cost) {
        Connection con = connect();
        String sql;
        if (table.equals("Dranks")) {
            sql = "update " + table + " set nameD='" + name + "', typeD='" + type + "', costD=" + cost + " " + where;
        } else {
            sql = "update " + table + " set nameM='" + name + "', typeM='" + type + "', costM=" + cost + " " + where;
        }

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            return !ps.execute();
        } catch (Exception e) {
            if (table.equals("Dranks")) {
                JOptionPane.showMessageDialog(null, "Id or Name already exists");
            } else {
                JOptionPane.showMessageDialog(null, "Id or Name already exists");
            }
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public static boolean delete(String table, String where) {
        Connection con = connect();
        try {
            PreparedStatement ps = con.prepareStatement("delete from " + table + " where " + where);
            return !ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
