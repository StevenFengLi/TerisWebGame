package HTTPServer.DataAccessLayer.Entity.DAOImplement;

import HTTPServer.DataAccessLayer.Entity.DAO.FriendshipDAO;
import HTTPServer.DataAccessLayer.Entity.DAODatabaseConnection.DatabaseConnection;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;
import com.sun.deploy.util.SyncAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class FriendshipDAOImplement implements FriendshipDAO {
    private final String FRIENDSHIP_TABLE_NAME = "`friendship`";

    private Connection connection = DatabaseConnection.getConnection();

    public boolean isFriendshipExisted(long userID1, long userID2) throws DatabaseException {
        boolean friendshipExisted;

        try {
            String SQL = "select 1 " +
                         "from " + FRIENDSHIP_TABLE_NAME + " " +
                         "where (user_id_1 = ? and user_id_2 = ?) or (user_id_1 = ? and user_id_2 = ?) " +
                         "limit 1;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, userID1);
            preparedStatement.setLong(2, userID2);
            preparedStatement.setLong(3, userID2);
            preparedStatement.setLong(4, userID1);

            ResultSet resultSet =  preparedStatement.executeQuery();
            friendshipExisted = resultSet.next();

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return friendshipExisted;
    }

    public List<Long> getFriendUserIDListByUserID(long userID) throws DatabaseException {
        List<Long> friendUserIDList = new ArrayList<>();

        try {
            String SQL = "select user_id_2 " +
                         "from " + FRIENDSHIP_TABLE_NAME + " " +
                         "where user_id_1 = ? " +
                         "union " +
                         "select user_id_1 " +
                         "from " + FRIENDSHIP_TABLE_NAME + " " +
                         "where user_id_2 = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, userID);
            preparedStatement.setLong(2, userID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()) {
                friendUserIDList.add(resultSet.getLong(1));
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return friendUserIDList;
    }

    public void addFriendship(long userID1, long userID2) throws DatabaseException {
        try {
            String SQL = "insert into " + FRIENDSHIP_TABLE_NAME + " " +
                         "(user_id_1, user_id_2) " +
                         "values (?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, userID1);
            preparedStatement.setLong(2, userID2);

            if (preparedStatement.executeUpdate() < 1) {
                throw new DatabaseException();
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }
    }

    public void deleteFriendship(long userID1, long userID2) throws DatabaseException {
        try {
            String SQL = "delete from " + FRIENDSHIP_TABLE_NAME + " " +
                         "where (user_id_1 = ? and user_id_2 = ?) or (user_id_1 = ? and user_id_2 = ?) ;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, userID1);
            preparedStatement.setLong(2, userID2);
            preparedStatement.setLong(3, userID2);
            preparedStatement.setLong(4, userID1);

            if (preparedStatement.executeUpdate() < 1) {
                throw new DatabaseException();
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }
    }
}
