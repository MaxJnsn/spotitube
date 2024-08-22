package nl.MaxJNSN.datasource.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataMapper<T> {

    T mapToDTO(ResultSet resultSet) throws SQLException;
}
