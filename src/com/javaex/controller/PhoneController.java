package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.utill.WebUtill;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc")
public class PhoneController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("컨트롤러");

		// 파라미터 action 값을 읽어온다
		String action = request.getParameter("action");
		//System.out.println(action);

		if ("list".equals(action)) {

			// 리스트
			PhoneDao phoneDao = new PhoneDao();
			List<PersonVo> personList = phoneDao.getPersonList();

			System.out.println("controller===================");
			System.out.println(personList);

			// 어트리뷰트
			request.setAttribute("pList", personList);

			//포워드
			WebUtill.forword(request, response, "/WEB-INF/updateForm.jsp");
			
			// 포워드
			//RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/list.jsp");
			//rd.forward(request, response);

		} else if ("wform".equals(action)) {
			
			//결과확인
			System.out.println("[글쓰기폼]");
			
			//포워드
			WebUtill.forword(request, response, "WEB-INF/writeForm.jsp");

			// 포워드
			//RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/writeForm.jsp");
			//rd.forward(request, response);
			
		} else if("insert".equals(action)) {
			
			//결과확인
			System.out.println("[저장]");
			
			//dao --> 저장
			//파라미터를 꺼낸다 name, hp, company
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			//vo로 묶어준다
			PersonVo personVo = new PersonVo(name, hp, company);
			//PersonVo - toString 결과확인
			System.out.println(personVo);
			
			//dao personInsert(vo)
			PhoneDao phoneDao = new PhoneDao();
			int count = phoneDao.personInsert(personVo);
			
			WebUtill.redirect(request, response, "/phonebook2/pbc?action=list");
			
			/*
			리다이렉트
			
			저장완료후 빈화면 response 한 뒤 다시 request 
			주소에 맞게 response.sendRedirect("주소")
			 
			response.sendRedirect("/phonebook2/pbc?action=list");
			*/
			
		}else if("delete".equals(action)) {
			
			//결과확인
			System.out.println("[삭제]");
			
			PhoneDao phoneDao = new PhoneDao();	
			//파라미터 꺼내기
			int personId = Integer.parseInt( request.getParameter("id") );
			
		    //삭제하기
			int count = phoneDao.personDelete(personId);
			
			//리다이렉트
			WebUtill.redirect(request, response, "/phonebook2/pbc?action=list");
			
		}else if("uform".equals(action)) {
			
			//결과확인
			System.out.println("[수정폼]");
			
			//파라미터값 가져오기
			int personId = Integer.parseInt(request.getParameter("id"));
			
			//dao 번호의 데이터 가져오기
			PhoneDao phoneDao = new PhoneDao();
			PersonVo personVo = phoneDao.getPerson(personId);
			
			//어트리뷰트
			request.setAttribute("personVo", personVo);
			
			//포워드
			WebUtill.forword(request, response, "/WEB-INF/updateForm.jsp");
			
			//포워드
			//RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/updateForm.jsp");
			//rd.forward(request, response);
			
		}else if("update".equals(action)) {
			
			//결과확인
			System.out.println("[수정]");
			
			//파라미터값 가져오기
			int personId = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");

			//파라미터 vo로 만들기
			PersonVo personVo = new PersonVo(personId, name, hp, company);
			
			//DB에 업데이트 하기
			PhoneDao phoneDao = new PhoneDao();
			phoneDao.personUpdate(personVo);
			
			//리다이렉트
			WebUtill.redirect(request, response, "/phonebook2/pbc?action=list");
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	


}
