package main;
import models.Answer;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/")
public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public IndexServlet(){
		
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletOutputStream out = response.getOutputStream();
		DBConnector database = new DBConnector();
		
	try {
		database.connect();
		database.load();
		ArrayList<Answer> answers = database.getAnswers();
		
		out.println("<html>");
		out.println("<head><title>Boston Celtics Quiz</title></head>");
		
        
        out.println("<body bgcolor = \"#D3D3D3\">");
        out.println("<center>");
        out.println("<form action=\"res\" method=\"POST\">\n");
        out.println("<h3>Wecome to quiz dedicated to Boston Celtics NBA team</h3>");
        out.println("</center>");
        out.println("<b>Name:</b> <input type=\"text\" required=\"\" name=\"username\" placeholder=\"Your name\"><br>");
        for (int i=1; i<7; i++){
        	out.println("<br><br>");
        	if(i == 1){
                out.print("How many NBA championships did Boston Celtics won (before the 2017-18 season)?<br>");
                out.println("<select name=\"q" + i + "\">");
                for (Answer answer : answers){	
                    if (answer.getQuestionNumb() == i){
                        out.print("<option value=\"" + answer.getAnswerVariant() + "\">" + answer.getAnswerVariant() + "</option>");
              }
             }
                out.println("</select><br>");
            }
        	
        	if(i == 2){
                out.print("What is the full name of Boston Celtics official mascot?<br>");
                out.print("<textarea name=\"q" + i + "\" rows=\"4\" cols=\"20\" placeholder=\"Your answer...\"></textarea><br>");
            }
        	
        	if(i == 3){
            out.print("Which Boston Celtics player never participated in the Three Point contest?<br>");
            for (Answer answer : answers){	
                if (answer.getQuestionNumb() == i){
                    out.print("<input type=\"radio\" name=\"q" + i + "\" value=\"" + answer.getAnswerVariant() + "\">" + answer.getAnswerVariant() + "<br>");
          }
         }
        }
        	if(i == 4){
                out.print("What is the name of Boston Celtics home arena?<br>");
                out.print("<input type=\"text\" name=\"q" + i + "\" placeholder=\"Your answer...\"></input><br>");

            }
        	
        	if(i == 5){
                out.print("Which NBA players formed \"Big Three\" in seasons 2007 - 2012?<br>");
                for (Answer answer : answers){
                    if (answer.getQuestionNumb() == i){
                        out.print("<input type=\"checkbox\" name=\"q" + i + "\" value=\"" + answer.getAnswerVariant() + "\">" + answer.getAnswerVariant() + "<br>");
              }
             }
            }
        	
        	if(i == 6){
                out.print("How does Boston Celtics \"Away Jersey\" looks like (select by clicking on it)?<br>");
                out.print("<img src=\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSXcg-qGZnQJl1Iqj9BX2na5b_fSDEhxaC0V5eU8YmLzcHSvCM5wQ\" width=\"307\" height=\"164\" alt=\"Boston Jerseys\" usemap=\"#jerseys\" />");
                out.print("<input type=\"text\" name=\"q" + i + "\" id=\"jerseys\" style=\"visibility: hidden; height: 0; width: 0;\">");
                out.print("<map name =\"jerseys\">");
                for (Answer answer : answers){
                    if (answer.getQuestionNumb() == i){
                        out.print("<area target=\"_self\" alt=\"variant" + answer.getId() + "\" title=\"" + answer.getAnswerVariant() + "\" href=\"#\" coords=\"" + answer.getAnswerVariant() + "\" shape=\"rect\" onclick=\"getAnswer(event)\">");
              }
             }
                out.print("</map>");
            }
       }
        out.println("<script>");
        out.println("function getAnswer(e) {e.preventDefault(); document.getElementById(\"jerseys\").value = e.target.title;}");
        out.println("</script>");
        out.println("<br><center>");
        out.println("<button type=\"submit\">Finish</submit>");
        out.println("</center></br>");
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