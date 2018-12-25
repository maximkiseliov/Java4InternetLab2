package main;
import models.Answer;

import java.sql.*;
import java.util.ArrayList;

public class DBConnector {
	
	private final static String DB_url = "jdbc:mysql://localhost:3306/";
	private final static String DB_name = "myquiz";
	private final static String DB_user = "root";
	private final static String DB_pass = "";

	private Connection connection;
	private ArrayList<Answer> answers;

	public DBConnector() {
		answers = new ArrayList<Answer>();
	}

	public void connect() throws SQLException {
		if (connection != null) {
			return;
		}

		try {
			Driver driver = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(driver);
		} catch (Exception e) {
			System.out.println("NO Driver...");
		}

		connection = DriverManager.getConnection(DB_url+DB_name, DB_user, DB_pass);
		if (!connection.isClosed()) {
			System.out.println("Connected successfully...");
		}
	}

	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Can't be closed...");
			}
		}
		connection = null;
	}

	public void load() throws SQLException {
		answers.clear();

		String questionsSql = "SELECT * FROM answers";
		Statement selectAnswersStatement = connection.createStatement();
		
		ResultSet answersResults = selectAnswersStatement.executeQuery(questionsSql);

		while (answersResults.next()){ 
			int id = answersResults.getInt("id");
			int question_numb = answersResults.getInt("question_numb");
			String answer_variant = answersResults.getString("answer_variant");
			String correct_y_n = answersResults.getString("correct_y_n");

			Answer answer = new Answer(id, question_numb, answer_variant, correct_y_n);
			answers.add(answer);

		
		}
		answersResults.close();
		selectAnswersStatement.close();
}	

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

}
