package net.word.action;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;

public class PapagoAPIAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String clientId = "m6uh1XduC62Qir0h7ODS";// 애플리케이션 클라이언트 아이디값";
		String clientSecret = "w0q4ZrskoH";// 애플리케이션 클라이언트 시크릿값";
		try {
			HashMap<String, String> hashMapStr = new HashMap<String, String>();
			String gettext = request.getParameter("text").toString();
			String text = URLEncoder.encode(gettext, "UTF-8");
			String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			// post request
			String postParams = "source=ko&target=en&text=" + text;
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer rowResponse = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				rowResponse.append(inputLine+"\n");

				inputLine=inputLine.replaceAll("\\{", "");
				inputLine=inputLine.replaceAll("\\}", "");
				inputLine=inputLine.replaceAll("\"", "");
				
				System.out.println("row : "+inputLine);
				
				String[] splited = inputLine.split(",");
				String[] tempStr = null;
		        for (int i = 0; i < 6 ; i++) {
		        	tempStr = splited[i].split(":");
		        	System.out.println(tempStr[1]);
		            hashMapStr.put(tempStr[0], tempStr[1]);
		        }
			}
			System.out.println("----translatedText : "+hashMapStr.get("translatedText"));
			br.close();
			response.getWriter().append(hashMapStr.get("translatedText"));
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

}
