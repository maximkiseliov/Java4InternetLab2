package main;
import models.Answer;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/res")
public class ResServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletOutputStream out = response.getOutputStream();
		DBConnector database = new DBConnector();

		try {
			database.connect();
			database.load();
			ArrayList<Answer> answers = database.getAnswers();
			int accumPoints = 0;
			int accumPointsQuestionFive = 0;
			String username = request.getParameter("username");

			out.println("<html>");
			out.println("<head><title>Boston Celtics Quiz - Results</title></head>");
			
	        out.println("<body bgcolor = \"#D3D3D3\">");
	        out.println("<center>");
	        out.println("<h3>Boston Celtics Quiz Results</h3>");
	        out.println("</center>");
	        
	        for (int i=1; i<7; i++){
	        	if (i == 1){
    				String userAnswer = request.getParameter("q" + i);
	        		for (Answer answer : answers){
	        			if (answer.getQuestionNumb() == i && answer.checkCorrect().equals("Y") && (answer.getAnswerVariant().equals(userAnswer))){
	        					accumPoints++;
	        			}
	        		}
	        	}
	        	
	        	if (i == 2){
	        		String userAnswer = request.getParameter("q" + i);
	        		for (Answer answer : answers){
	        			if (answer.getQuestionNumb() == i && answer.getAnswerVariant().equals(userAnswer)){
	        					accumPoints++;
	        			}
	        		}
	        	}
	        	
	        	if (i == 3){
	        		String userAnswer = request.getParameter("q" + i);
	        		for (Answer answer : answers){
	        			if (answer.getQuestionNumb() == i && answer.checkCorrect().equals("Y") && answer.getAnswerVariant().equals(userAnswer)){
	        				accumPoints++;			
	        			}
	        		}
	        	}
	        	
	        	if (i == 4){
    				String userAnswer = request.getParameter("q" + i);
	        		for (Answer answer : answers){
	        			if (answer.getQuestionNumb() == i && answer.getAnswerVariant().equals(userAnswer)){
	        				accumPoints++;			
	        			}
	        		}
	        	}
	        	
	        	if (i == 5){
	        		String[] checkedValues = request.getParameterValues("q" + i);
	        		ArrayList<String> correctValues = new ArrayList<String>();
	        		
	        		for (Answer answer : answers){
	        			if (answer.getQuestionNumb() == i && answer.checkCorrect().equals("Y")){
	        				correctValues.add(answer.getAnswerVariant());
	        			}
	        		}
	        		
	        		for (String checkedValue:checkedValues){
	        			if (correctValues.contains(checkedValue)){
	        				accumPointsQuestionFive++;
	        			}
	        			else{
	        				accumPointsQuestionFive--;
	        			}
	        		}
	        		
	        		if (accumPointsQuestionFive == 3){
	        			accumPoints++;
	        		};
	        	}
	        	
	        	if (i == 6){
	        		String userAnswer = request.getParameter("q" + i);
	        		for (Answer answer : answers){
	        			if (answer.getQuestionNumb() == i && answer.checkCorrect().equals("Y") && answer.getAnswerVariant().equals(userAnswer)){
	        				accumPoints++;			
	        			}
	        		}
	        	}
	        	
	        }
	        
	        out.println("Dear <b>"+ username + "</b>, you've earned <b>" + accumPoints + "</b> of <b>6</b> possible points<br>");
	        
	        if (accumPoints == 6){
	        	out.println("Well done! Looks like you are the real Boston Celtics Fan!");
	        }
	        else if (accumPoints == 4 || accumPoints == 5){
	        	out.println("Almost there! Little push to prove that you are the real Boston Celtics Fan!");
	        }
	        
	        else if (accumPoints == 2 || accumPoints == 3){
	        	out.println("Hmm... That's not enough to prove that you are the real Boston Celtincs Fan. Gain knowledge and come back!");
	        }
	        
	        else {
	        	out.println("Most likely it was an accident and you do not know what you are doing here.");
	        }
	        
	        out.println("</body>");
	        out.println("<html>");
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(500);
		} finally {
			database.disconnect();
			out.close();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
}