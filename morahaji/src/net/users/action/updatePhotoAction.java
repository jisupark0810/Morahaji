package net.users.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.Action.Action;
import net.Action.ActionForward;
import net.users.db.USERS;
import net.users.db.UserDAO;

public class updatePhotoAction implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
         throws Exception {
      HttpSession session = request.getSession();
      UserDAO dao = new UserDAO();
      USERS u = new USERS();
      ActionForward forward = new ActionForward();
      
      String realFolder ="";
      
      //WebContent �Ʒ��� �� ���� �����ϱ�
      String saveFolder = "boardupload";
      
      //���ε��� ������ �ִ� ������(5MB)
      int fileSize = 5*1024*1024;
      
      //���� ���� ��θ� ����
      ServletContext sc = request.getServletContext();
      realFolder = sc.getRealPath(saveFolder);
            
      System.out.println("realFolder = " + realFolder);
      boolean result = false;
      
      try {
         MultipartRequest multi = null;
         multi = new MultipartRequest(request, 
                                 realFolder,
                                 fileSize,
                                 "utf-8",
                                 new DefaultFileRenamePolicy());
         
         String filename = multi.getFilesystemName("choicePhoto");
         
         u.setUSER_PROFILEPHOTO(filename);
         int userKey = Integer.parseInt(session.getAttribute("userKey").toString());
         u.setUSER_KEY(userKey);
         
         //DAO���� ���� �޼��� ȣ���Ͽ� �����մϴ�.
         result = dao.photoUpdate(userKey, filename);
         
         //������ ������ ���
         if (result == false) {
            System.out.println("������ ���� ���� ����");
            forward = new ActionForward();
            request.setAttribute("message", "������ ���� ��� �����Դϴ�.");
            forward.setPath("error/error.jsp");
            return forward;
         }
         
         //���� ������ ���
         System.out.println("������ ���� ���� �Ϸ�");         
         
         forward.setRedirect(true);
         //������ �� ������ �����ֱ� ���� �� ���� ���� �������� �̵��ϱ� ���� ��θ� ����
         forward.setPath("update.net");
         return forward;
      } catch (Exception e) {
         e.printStackTrace();
      }   
      return null;
   }
}