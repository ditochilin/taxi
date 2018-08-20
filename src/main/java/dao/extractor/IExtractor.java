package dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Fulfill fields of entity with resultSet data
 * @param <T> gets extractor of concrete entity
 * @author Dmitry Tochilin
 */
public interface IExtractor<T> {

    T extractEntityData(ResultSet resultSet) throws SQLException;

    //todo : delete  ... ?
//    default List<T> extractAll(ResultSet resultSet) throws SQLException {
//        List<T> list = new ArrayList<>();
//        while (resultSet.next()) {
//            list.add(extractEntityData(resultSet));
//        }
//        return list;
//    }

}
