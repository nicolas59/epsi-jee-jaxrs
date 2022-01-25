package fr.epsi.tp.persistance.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class SQLUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(SQLUtils.class);

  private static final String SQL_FILE = "/init-db-h2.sql";

  public static void initDatabase(Connection conn) {
    LOGGER.info("Initialisation base de données");

    String lines;
    ResultSet rs = null;
    try {
      DatabaseMetaData metadata = conn.getMetaData();
      rs = metadata.getTables(null, null, "MARQUE", null);
      if(rs.next()) {
        LOGGER.info("Tables already created");
        return;
      }
      lines = Files.readAllLines(Paths.get(CommandeDAOTest.class.getResource(SQL_FILE)
        .toURI()))
        .stream()
        .collect(Collectors.joining());

      Statement st =null;
      for (String line : lines.split(";")) {
        st = conn.createStatement();
        st.executeUpdate(line);
        st.close();
      }
    } catch (IOException | URISyntaxException | SQLException e) {
      LOGGER.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }finally {
      close(rs);
    }
  }
  
  public static void close(AutoCloseable close) {
    if(close != null) {
      try {
        close.close();
      } catch (Exception ignore) {

      }
    }
  }
}
