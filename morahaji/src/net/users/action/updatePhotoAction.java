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
      
      //WebContent 아래에 꼭 폴더 생성하기
      String saveFolder = "boardupload";
      
      //업로드할 파일의 최대 사이즈(5MB)
      int fileSize = 5*1024*1024;
      
      //실제 저장 경로를 지정
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
         
         //DAO에서 수정 메서드 호출하여 수정합니다.
         result = dao.photoUpdate(userKey, filename);
         
         //수정에 실패한 경우
         if (result == false) {
            System.out.println("프로필 사진 수정 실패");
            forward = new ActionForward();
            request.setAttribute("message", "프로필 사진 등록 실패입니다.");
            forward.setPath("error/error.jsp");
            return forward;
         }
         
         //수정 성공의 경우
         System.out.println("프로필 사진 수정 완료");         
         
         forward.setRedirect(true);
         //수정한 글 내용을 보여주기 위해 글 내용 보기 페이지로 이동하기 위해 경로를 설정
         forward.setPath("update.net");
         return forward;
      } catch (Exception e) {
         e.printStackTrace();
      }   
      return null;
   }
}