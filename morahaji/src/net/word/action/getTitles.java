package net.word.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import net.Action.Action;
import net.Action.ActionForward;
import net.word.db.WordDAO;

public class getTitles implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      WordDAO wordDAO = new WordDAO();
      request.setCharacterEncoding("UTF-8");
      response.setCharacterEncoding("UTF-8");
      String title = request.getParameter("param");
      List<String> list = new ArrayList<String>();
      list = wordDAO.getTitles(title);
      System.out.println(list);
      
      Gson gson = new Gson();
      String jsonList = gson.toJson(list);
      response.getWriter().write(jsonList);

      return null;
   }

}