package chat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ChatListServlet")
@MultipartConfig(location="", 
fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*5, 
maxRequestSize=1024*1024*5*5)
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String listType = request.getParameter("listType");
		if(listType == null || listType.equals("")) response.getWriter().write("");
		else if(listType.equals("today")) response.getWriter().write(getToday());
		else if(listType.equals("twenty")) response.getWriter().write(getCount());
		else {
			try {
				Integer.parseInt(listType);
				response.getWriter().write(getID(listType));
			} catch (Exception e) {
				response.getWriter().write("");
			}
		}
	}
	
	public String getToday() {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<Chat> chatList = chatDAO.getChatList(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		for(int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \""+chatList.get(i).getNo()+"\"},");
			result.append("{\"value\": \""+chatList.get(i).getChatName()+"\"},");
			result.append("{\"value\": \""+chatList.get(i).getChatContent()+"\"},");
			result.append("{\"value\": \""+chatList.get(i).getChatTime()+"\"}]");
			if(i != chatList.size() - 1) result.append(",");
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size()-1).getNo()+"\"}");
		return result.toString();
	}
	
	public String getCount() {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<Chat> chatList = chatDAO.getChatListByRecent(20);
		for(int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \""+chatList.get(i).getNo()+"\"},");
			result.append("{\"value\": \""+chatList.get(i).getChatName()+"\"},");
			result.append("{\"value\": \""+chatList.get(i).getChatContent()+"\"},");
			result.append("{\"value\": \""+chatList.get(i).getChatTime()+"\"}]");
			if(i != chatList.size() - 1) result.append(",");
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size()-1).getNo()+"\"}");
		return result.toString();
	}
	
	public String getID(String no) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<Chat> chatList = chatDAO.getChatListByRecent(no);
		for(int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \""+chatList.get(i).getNo()+"\"},");
			result.append("{\"value\": \""+chatList.get(i).getChatName()+"\"},");
			result.append("{\"value\": \""+chatList.get(i).getChatContent()+"\"},");
			result.append("{\"value\": \""+chatList.get(i).getChatTime()+"\"}]");
			if(i != chatList.size() - 1) result.append(",");
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size()-1).getNo()+"\"}");
		return result.toString();
	}

}
