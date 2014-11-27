package de.inmotion.findme.servlet.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.inmotion.findme.servlet.resources.Position;
import de.inmotion.findme.servlet.utils.ParamChecker;

/**
 * Implementierung des CoordinateDbServices.
 * @author Tobias Wochinger
 *
 */
public class CoordinateDbServiceImpl implements CoordinateDbService {
	
	private Connection connection;

	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String TIMESTAMP = "timestamp";
	public static final String FOREIGN_USER = "user_id";
	public static final String COORDINATES_TABLE = "coordinates";
	public static final String INSERT_COORDINATE = "INSERT INTO "
			+ COORDINATES_TABLE + " (" + FOREIGN_USER + ", " + LATITUDE + ", "
			+ LONGITUDE + ") VALUES (?, ?, ?);";
	public static final String SELECT_MAX_TIMESTAMP = "SELECT MAX(" + TIMESTAMP
			+ ") FROM " + COORDINATES_TABLE + " WHERE " + FOREIGN_USER + " = ?";
	public static final String SELECT_LATEST_USER_COORDINATE = "SELECT "
			+ TIMESTAMP + ", " + LATITUDE + ", " + LONGITUDE + " FROM "
			+ COORDINATES_TABLE + " WHERE " + FOREIGN_USER + " = ? AND "
			+ TIMESTAMP + " = (" + SELECT_MAX_TIMESTAMP + ");";

	/**
	 * Erstellt einen Koordinatendatenbankverbindung mit 
	 * gegebener Datenbankverbindung.
	 * @param connection vorhandene Datenbankverbindung
	 */
	public CoordinateDbServiceImpl(final Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * Erstellt einen Koordinatendatenbankverbindung ohne 
	 * gegebene Datenbankverbindung.
	 */
	public CoordinateDbServiceImpl() {
		this(new MySQLDatabase().getConnection());
	}
	
	@Override
	public void insertPosition(final Position position, final String username) throws SQLException {
		if (ParamChecker.isParamNull(position)) {
			throw new IllegalArgumentException("No valid position given");
		}
		
		int userId = getUserId(username);
		if (userId == UserDbServiceImpl.ILLEGAL_USERID) {
			throw new IllegalArgumentException("User " + username + " does not exist");
		}

		PreparedStatement statement = connection.prepareStatement(INSERT_COORDINATE);
		statement.setInt(1, userId);
		statement.setDouble(2, position.getLatitude());
		statement.setDouble(3, position.getLongitude());
		statement.executeUpdate();
	}

	@Override
	public Position getLatestPosition(final String username) throws SQLException {	
		int userid = getUserId(username);
		
		if (userid == UserDbServiceImpl.ILLEGAL_USERID) {
			return null;
		}
		
		PreparedStatement statement = connection.prepareStatement(SELECT_LATEST_USER_COORDINATE);
		statement.setInt(1, userid);
		statement.setInt(2, userid);
		ResultSet result = statement.executeQuery();
		
		Position latestPosition = null;
		while (result.next()) {
			latestPosition = new Position();
			latestPosition.setTimestamp(result.getTimestamp(TIMESTAMP).getTime() / 1000);
			latestPosition.setLatitude(result.getDouble(LATITUDE));
			latestPosition.setLongitude(result.getDouble(LONGITUDE));
		}
		return latestPosition;
	}

	/**
	 * Ermittelt zum Usernamen die Datenbankid.
	 * @param username Nutzername
	 * @return Datenbankid des nutzers
	 */
	private int getUserId(final String username) {
		UserDbService userService = new UserDbServiceImpl(connection);
		try {
			return userService.getUserId(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return UserDbServiceImpl.ILLEGAL_USERID;
	}

	@Override
	public void closeService() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
