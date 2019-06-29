package shaoziruiMIS02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StuDao extends BaseDao {

	public StuDao() {
		// TODO Auto-generated constructor stub
	}

	public StudentBean getStudent(String num) {
		String sql = "SELECT * FROM shaozr_学生02,shaozr_班级02,shaozr_专业02 WHERE szr_学号02=? and shaozr_班级02.szr_班级编号02=shaozr_学生02.szr_班级编号02 and shaozr_班级02.szr_专业编号02=shaozr_专业02.szr_专业编号02";
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			ResultSet resultSet = preparedStatement.executeQuery();
			StudentBean studentBean = new StudentBean();
			while (resultSet.next()) {

				studentBean.setNum(resultSet.getString("szr_学号02").trim());
				studentBean.setName(resultSet.getString("szr_姓名02").trim());
				studentBean.setSex(resultSet.getString("szr_性别02").trim());
				studentBean.setAge(resultSet.getInt("szr_年龄02"));
				studentBean.setPlace(resultSet.getString("szr_生源所在地02").trim());
				studentBean.setCredit(resultSet.getFloat("szr_已修学分总数02"));
				studentBean.setClassname(resultSet.getString("szr_班级名称02").trim());
				studentBean.setMajor(resultSet.getString("szr_专业名称02").trim());
				studentBean.setClassnum(resultSet.getString("szr_班级编号02").trim());
			}
			connection.close();
			return studentBean;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<CourseBean> getCourse(String num, String classnum) {
		String sql = "select  * from shaozr_学生所学课程及学分统计02,shaozr_教师任课查询02 where 学号=? and szr_班级编号02=?\r\n"
				+ "and shaozr_学生所学课程及学分统计02.课程编号=shaozr_教师任课查询02.szr_课程编号02";
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			preparedStatement.setString(2, classnum);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<CourseBean> cList = new ArrayList<>();
			while (resultSet.next()) {
				CourseBean courseBean = new CourseBean();
				courseBean.setCoursenum(resultSet.getString("课程编号").trim());
				boolean flag = true;
				for (int i = 0; i < cList.size(); i++) {
					if (courseBean.getCoursenum().equals(cList.get(i).getCoursenum())) {
						String teacher = cList.get(i).getTeacher();
						cList.get(i).setTeacher(teacher + "、" + resultSet.getString("szr_姓名02").trim());
						flag = false;
					}
				}
				if (flag) {
					courseBean.setCoursenum(resultSet.getString("课程编号").trim());
					courseBean.setCoursename(resultSet.getString("课程名称").trim());
					courseBean.setTeacher(resultSet.getString("szr_姓名02").trim());
					courseBean.setYear(resultSet.getString("开课学年").trim());
					if (resultSet.getBoolean("开课学期")) {
						courseBean.setTerm("上学期");
					} else {
						courseBean.setTerm("下学期");
					}

					courseBean.setTime(resultSet.getInt("学时"));
					if (resultSet.getBoolean("考试或考察")) {
						courseBean.setTestway("考察");
					} else {
						courseBean.setTestway("考试");
					}
					courseBean.setCredit(resultSet.getFloat("学分"));
					cList.add(courseBean);
				}

			}
			connection.close();
			return cList;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	public ArrayList<String> getAllClass(){
		String sql = "select szr_班级编号02,szr_班级名称02 from shaozr_班级02" ;
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<String> aList = new ArrayList<>();
			while (resultSet.next()) {
				String aclass=resultSet.getString("szr_班级名称02").trim()+"（"+resultSet.getString("szr_班级编号02").trim()+"）";
				aList.add(aclass);
			}
			connection.close();
			return aList;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<CourseBean> getClass(String num) {
		String sql = "select * from shaozr_班级开课查询02 where szr_班级编号02=?";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<CourseBean> cList = new ArrayList<>();
			while (resultSet.next()) {
				CourseBean courseBean = new CourseBean();
				courseBean.setCoursenum(resultSet.getString("szr_课程编号02").trim());
				courseBean.setCoursename(resultSet.getString("szr_课程名称02").trim());
				courseBean.setTime(resultSet.getInt("szr_学时02"));
				courseBean.setCredit(resultSet.getFloat("szr_学分02"));
				courseBean.setClassname(resultSet.getString("szr_班级名称02").trim());
				cList.add(courseBean);
			}
			connection.close();
			return cList;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public ArrayList<CourseBean> getGrade(String num, String classnum,String year) {
		String sql="select distinct 学号,姓名,学年,学期,课程名称,成绩,szr_姓名02,课程编号 from shaozr_学生成绩统计02,shaozr_教师任课查询02\r\n" + 
				"where shaozr_学生成绩统计02.课程编号=shaozr_教师任课查询02.szr_课程编号02 and 学号=? and szr_班级编号02=? and 学年=?";
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, num);
			preparedStatement.setString(2, classnum);
			preparedStatement.setString(3, year);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<CourseBean> cList = new ArrayList<>();
			while (resultSet.next()) {
				CourseBean courseBean = new CourseBean();
				courseBean.setCoursenum(resultSet.getString("课程编号").trim());
				boolean flag = true;
				for (int i = 0; i < cList.size(); i++) {
					if (courseBean.getCoursenum().equals(cList.get(i).getCoursenum())) {
						String teacher = cList.get(i).getTeacher();
						cList.get(i).setTeacher(teacher + "、" + resultSet.getString("szr_姓名02").trim());
						flag = false;
					}
				}
				if (flag) {
					courseBean.setCoursenum(resultSet.getString("课程编号").trim());
					courseBean.setCoursename(resultSet.getString("课程名称").trim());
					courseBean.setTeacher(resultSet.getString("szr_姓名02").trim());
					courseBean.setYear(resultSet.getString("学年").trim());
					if (resultSet.getBoolean("学期")) {
						courseBean.setTerm("上学期");
					} else {
						courseBean.setTerm("下学期");
					}
					courseBean.setGrade(resultSet.getFloat("成绩"));
					cList.add(courseBean);
				}

			}
			connection.close();
			return cList;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
