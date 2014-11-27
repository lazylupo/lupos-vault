package de.inmotion.findme.servlet.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.inmotion.findme.servlet.pojo.Registration;
import de.inmotion.findme.servlet.utils.ParamChecker;

/**
 * Implementierung des UserDbServices.
 * @author Tobias Wochinger
 *
 */
public class UserDbServiceImpl implements UserDbService {

	private Connection connection;

	public static final String USERNAME = "name";
	public static final String USER_PASSWORD = "password";
	public static final String USER_ID = "id";
	public static final String USER_TABLE = "user";
	
	public static final int ILLEGAL_USERID = 0;

	public static final String SELECT_USERNAMES = "SELECT " + USERNAME
			+ " FROM " + USER_TABLE + " ORDER BY " + USERNAME + ";";
	public static final String SELECT_USER = "SELECT " + USER_ID
			+ " FROM " + USER_TABLE + " WHERE " + USERNAME + " = ?;";


	public static final String INSERT_USER = "INSERT INTO " + USER_TABLE + " ("
			+ USERNAME + ", " + USER_PASSWORD + ") VALUES (?, ?);";

	public static final String CHECK_USER_LOGIN = "SELECT 1 FROM " + USER_TABLE
			+ " WHERE " + USERNAME + " = ? AND " + USER_PASSWORD + " = ?;";

	/**
	 * Erstellt einen Datenbankservice ohne gegebene Datenbankverbindung.
	 */
	public UserDbServiceImpl() {
		this(new MySQLDatabase().getConnection());
	}
	
	/**
	 * Erstellt einen Datenbankservice mit gegebener Datenbankverbindung.
	 * @param connection Vorhandene Datanbankverbindung
	 */
	public UserDbServiceImpl(final Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insertUser(final Registration registration) throws SQLException {
		if (registration == null) {
			throw new IllegalArgumentException("Registration is null");
		}
		
		if (ParamChecker.isParamNull(registration.getUsername())) {
			throw new IllegalArgumentException("Username is null");
		}
		
		if (ParamChecker.isParamNull(registration.getPassword())) {
			throw new IllegalArgumentException("Password is null");
		}
		
		
		if (getUserId(registration.getUsername()) > ILLEGAL_USERID) {
			throw new IllegalArgumentException("User " + registration.getUsername() 
					+ " already exists!");
		}

		PreparedStatement statement = connection.prepareStatement(INSERT_USER);
		statement.setString(1, registration.getUsername());
		statement.setString(2, registration.getPassword());
		statement.executeUpdate();
	}

	@Override
	public List<String> getUsernames() throws SQLException {
		List<String> usernames = new ArrayList<String>();
		PreparedStatement statement = connection.prepareStatement(SELECT_USERNAMES);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			usernames.add(result.getString(USERNAME));
		}
		return usernames;
	}

	@Override
	public int getUserId(final String username) throws SQLException {
		if (ParamChecker.isParamNull(username)) {
			return ILLEGAL_USERID;
		}
		
		int userId = 0;
		PreparedStatement statement = connection.prepareStatement(SELECT_USER);
		statement.setString(1, username);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			userId = result.getInt(USER_ID);
		}
		
		return userId;
	}
	
	@Override
	public boolean isLoginCorrect(final String username, final String password)
			throws SQLException {
		if (ParamChecker.isParamNull(username)) {
			return false;
		}
		
		if (ParamChecker.isParamNull(password)) {
			return false;
		}
		
		PreparedStatement statement = connection.prepareStatement(CHECK_USER_LOGIN);
		statement.setString(1, username);
		statement.setString(2, password);
		ResultSet result = statement.executeQuery();
		boolean isLoginCorrect = result.next();
		
		return isLoginCorrect;
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
