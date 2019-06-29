package shaoziruiMIS02;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StuClass
 */
@WebServlet("/StuClass")
public class StuClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StuClass() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String aclss=(String) request.getParameter("aclss");
		StuDao stuDao = new StuDao();
		if(aclss==null) {
			ArrayList<String> aList=stuDao.getAllClass();
			ArrayList<CourseBean> cList=stuDao.getClass(null);
			request.setAttribute("alist", aList);
			request.setAttribute("clist", cList);
			RequestDispatcher rDispatcher = request.getRequestDispatcher("/WEB-INF/stuclass.jsp");
			rDispatcher.forward(request, response);
		}
		else {
			ArrayList<String> aList=stuDao.getAllClass();
			String num=aclss.substring(aclss.indexOf("（")+1, aclss.length()-1);
			ArrayList<CourseBean> cList=stuDao.getClass(num);
			request.setAttribute("alist", aList);
			request.setAttribute("clist", cList);
			RequestDispatcher rDispatcher = request.getRequestDispatcher("/WEB-INF/stuclass.jsp");
			rDispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
