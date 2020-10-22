package shaoziruiMIS02;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=shaoziruiMIS02";
			con = (Connection) DriverManager.getConnection(url, "sa", "123456");
			//System.out.println("good!");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String status = request.getParameter("status");
		if (status.equals("学生")) {
			String num = request.getParameter("num");
			String password = request.getParameter("password");
			ArrayList<StudentBean> sList = new ArrayList<>();
			String sql = "SELECT * FROM shaozr_学生02 WHERE szr_学号02=?";
			PreparedStatement pStatement;
			try {
				pStatement = con.prepareStatement(sql);
				pStatement.setString(1, num);
				ResultSet resultSet = pStatement.executeQuery();
				StudentBean studentBean = new StudentBean();
				while (resultSet.next()) {
					studentBean.setNum(resultSet.getString("szr_学号02").trim());
					studentBean.setPasscode(resultSet.getString("szr_登录密码02").trim());
					studentBean.setName(resultSet.getString("szr_姓名02").trim());
					studentBean.setClassnum(resultSet.getString("szr_班级编号02").trim());
					sList.add(studentBean);
				}
				if (!sList.isEmpty() && studentBean.getPasscode().equals(password)) {
					Cookie nameCookie = new Cookie("name", studentBean.getName().trim());
					nameCookie.setMaxAge(60 * 60 * 12);
					response.addCookie(nameCookie);
					Cookie numCookie = new Cookie("num", studentBean.getNum());
					Cookie classnumCookie = new Cookie("classnum", studentBean.getClassnum());
					numCookie.setMaxAge(60 * 60 * 12);
					classnumCookie.setMaxAge(60 * 60 * 12);
					response.addCookie(numCookie);
					response.addCookie(classnumCookie);
					request.setAttribute("num", num);
					RequestDispatcher rDispatcher = request.getRequestDispatcher("StuProfile");
					rDispatcher.forward(request, response);
				} else {
					request.setAttribute("fail", "登录失败请重试！");
					RequestDispatcher rDispatcher = request.getRequestDispatcher("index.jsp");
					rDispatcher.forward(request, response);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (status.equals("教师")) {
			String num = request.getParameter("num");
			String password = request.getParameter("password");
			String sql = "SELECT * FROM shaozr_教师02 WHERE szr_教师编号02=?";
			PreparedStatement pStatement;
			try {
				pStatement = con.prepareStatement(sql);
				pStatement.setString(1, num);
				ArrayList<TeacherBean> tList = new ArrayList<>();
				TeacherBean teacherBean = new TeacherBean();
				ResultSet resultSet = pStatement.executeQuery();
				while (resultSet.next()) {
					teacherBean.setNum(resultSet.getString("szr_教师编号02"));
					teacherBean.setName(resultSet.getString("szr_姓名02"));
					teacherBean.setPasscode(resultSet.getString("szr_登录密码02"));
					tList.add(teacherBean);
				}
				if (!tList.isEmpty() && teacherBean.getPasscode().equals(password)) {
					Cookie nameCookie = new Cookie("name", teacherBean.getName().trim());
					nameCookie.setMaxAge(60 * 60 * 12);
					response.addCookie(nameCookie);
					Cookie numCookie = new Cookie("num", teacherBean.getNum());
					numCookie.setMaxAge(60 * 60 * 12);				
					response.addCookie(numCookie);				
					request.setAttribute("num", num);
					RequestDispatcher rDispatcher = request.getRequestDispatcher("TeaProfile");
					rDispatcher.forward(request, response);
				}else {
					request.setAttribute("fail", "登录失败请重试！");
					RequestDispatcher rDispatcher = request.getRequestDispatcher("index.jsp");
					rDispatcher.forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if (status.equals("管理员")) {
			String num = request.getParameter("num");
			String password = request.getParameter("password");
			String sql = "SELECT * FROM shaozr_管理员02 WHERE szr_管理员编号02=?";
			PreparedStatement pStatement;
			try {
				pStatement = con.prepareStatement(sql);
				pStatement.setString(1, num);
				ArrayList<String> adList = new ArrayList<>();
				ResultSet resultSet = pStatement.executeQuery();
				while (resultSet.next()) {
					adList.add(resultSet.getString("szr_管理员编号02"));
					adList.add(resultSet.getString("szr_登录密码02"));
					
				}
				if (!adList .isEmpty() && adList .get(1).equals(password)) {
					Cookie nameCookie = new Cookie("name", adList.get(0));
					nameCookie.setMaxAge(60 * 60 * 12);
					response.addCookie(nameCookie);
					Cookie numCookie = new Cookie("num", adList.get(0));
					numCookie.setMaxAge(60 * 60 * 12);				
					response.addCookie(numCookie);				
					request.setAttribute("num", num);
					RequestDispatcher rDispatcher = request.getRequestDispatcher("AdmTeacher");
					rDispatcher.forward(request, response);
				}else {
					request.setAttribute("fail", "登录失败请重试！");
					RequestDispatcher rDispatcher = request.getRequestDispatcher("index.jsp");
					rDispatcher.forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
