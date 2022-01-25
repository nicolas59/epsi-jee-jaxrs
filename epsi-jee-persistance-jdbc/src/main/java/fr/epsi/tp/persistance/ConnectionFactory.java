package fr.epsi.tp.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

  private static ConnectionFactory INSTANCE = new ConnectionFactory();

  private ConnectionFactory() {
  }

  public Connection getConnection(String url, String login, String password) throws SQLException {
    return DriverManager.getConnection(url, login, password);
  }

  public Connection getConnection() throws SQLException {
    return this.getConnection("jdbc:h2:mem:boutique", "sa", "");

  }

  public static ConnectionFactory getInstance() {
    return INSTANCE;
  }
}
