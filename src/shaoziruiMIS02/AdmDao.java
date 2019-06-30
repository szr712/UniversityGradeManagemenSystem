package shaoziruiMIS02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.accessibility.internal.resources.accessibility;
import com.sun.xml.internal.bind.v2.model.runtime.RuntimeReferencePropertyInfo;

public class AdmDao extends BaseDao {

	public AdmDao() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<TeacherBean> getTeacher() {
		String sql = "SELECT * FROM shaozr_教师02";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);	
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<TeacherBean> tList = new ArrayList<>();
			while (resultSet.next()) {
				TeacherBean teacherBean = new TeacherBean();
				teacherBean.setNum(resultSet.getString("szr_教师编号02").trim());
				teacherBean.setName(resultSet.getString("szr_姓名02").trim());
				teacherBean.setSex(resultSet.getString("szr_性别02").trim());
				teacherBean.setAge(resultSet.getInt("szr_年龄02"));
				teacherBean.setRank(resultSet.getString("szr_职称02").trim());
				teacherBean.setPhone(resultSet.getString("szr_电话02"));
				tList.add(teacherBean);
			}
			connection.close();
			return tList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public boolean addTeacher(TeacherBean teacherBean) {
		String sql="insert into shaozr_教师02 values(?,?,?,?,?,?,'123456')";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, teacherBean.getNum());
			preparedStatement.setString(2, teacherBean.getName());
			preparedStatement.setString(3, teacherBean.getSex());
			preparedStatement.setInt(4, teacherBean.getAge());
			preparedStatement.setString(5, teacherBean.getRank());
			preparedStatement.setString(6, teacherBean.getPhone());
			preparedStatement.executeUpdate();
			connection.close();
			return true;
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
		
	} 
	
	public boolean deleteTeacher(ArrayList<TeacherBean>tList,int i) {
		String sql="delete from shaozr_教师02 where szr_教师编号02=?";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,tList.get(i).getNum());
			preparedStatement.executeUpdate();
			connection.close();
			return true;	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
		
	} 
	
	public boolean changTeacher(TeacherBean teacherBean) {
		String sql="update shaozr_教师02\r\n" + 
				"set szr_姓名02=?,szr_性别02=?,szr_年龄02=?,szr_职称02=?,szr_电话02=?\r\n" + 
				"where szr_教师编号02=?";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, teacherBean.getName());
			preparedStatement.setString(2, teacherBean.getSex());
			preparedStatement.setInt(3, teacherBean.getAge());
			preparedStatement.setString(4, teacherBean.getRank());
			preparedStatement.setString(5, teacherBean.getPhone());
			preparedStatement.setString(6, teacherBean.getNum());
			preparedStatement.executeUpdate();
			connection.close();
			return true;	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
		
	}

	
	public ArrayList<StudentBean> getStudent() {
		String sql = "SELECT * FROM shaozr_学生02,shaozr_班级02 WHERE shaozr_班级02.szr_班级编号02=shaozr_学生02.szr_班级编号02";
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ArrayList<StudentBean> sList=new ArrayList<>();
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				StudentBean studentBean = new StudentBean();
				studentBean.setNum(resultSet.getString("szr_学号02").trim());
				studentBean.setName(resultSet.getString("szr_姓名02").trim());
				studentBean.setSex(resultSet.getString("szr_性别02").trim());
				studentBean.setAge(resultSet.getInt("szr_年龄02"));
				studentBean.setPlace(resultSet.getString("szr_生源所在地02").trim());
				studentBean.setCredit(resultSet.getFloat("szr_已修学分总数02"));
				studentBean.setClassname(resultSet.getString("szr_班级名称02").trim());
				studentBean.setClassnum(resultSet.getString("szr_班级编号02").trim());
				sList.add(studentBean);
			}
			connection.close();
			return sList;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<String> getAllClass() {
		String sql = "select szr_班级编号02,szr_班级名称02 from shaozr_班级02";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<String> aList = new ArrayList<>();
			while (resultSet.next()) {
				String aclass = resultSet.getString("szr_班级名称02").trim() + "（"
						+ resultSet.getString("szr_班级编号02").trim() + "）";
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

	
	public boolean addStudent(StudentBean studentBean) {
		String sql="insert into shaozr_学生02 values(?,?,?,?,?,?,'123456',?)\r\n"
				+ "EXEC dbo.shaozr_转专业并更改选课02 @szr_学号02 = ?, @szr_班级编号02 = ?";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, studentBean.getNum());
			preparedStatement.setString(2, studentBean.getName());
			preparedStatement.setString(3, studentBean.getSex());
			preparedStatement.setInt(4, studentBean.getAge());
			preparedStatement.setString(5, studentBean.getPlace());
			preparedStatement.setFloat(6, studentBean.getCredit());
			preparedStatement.setString(7, studentBean.getClassnum());
			preparedStatement.setString(8, studentBean.getNum());
			preparedStatement.setString(9, studentBean.getClassnum());
			preparedStatement.executeUpdate();
			connection.close();
			return true;	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
		
	} 
	
	public boolean deleteStudent(ArrayList<StudentBean>sList,int i) {
		String sql="delete from shaozr_学生02 where szr_学号02=?";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,sList.get(i).getNum());
			preparedStatement.executeUpdate();
			connection.close();
			return true;	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
		
	} 
	
	public boolean changeStudent(StudentBean studentBean) {
		String sql="update shaozr_学生02\r\n" + 
				"set szr_姓名02=?,szr_性别02=?,szr_年龄02=?,szr_生源所在地02=?,szr_班级编号02=?\r\n" + "where szr_学号02=?\r\n"+
				 "EXEC dbo.shaozr_转专业并更改选课02 @szr_学号02 = ?, @szr_班级编号02 = ?";
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, studentBean.getName());
			preparedStatement.setString(2, studentBean.getSex());
			preparedStatement.setInt(3, studentBean.getAge());
			preparedStatement.setString(4, studentBean.getPlace());
			preparedStatement.setString(5, studentBean.getClassnum());
			preparedStatement.setString(6, studentBean.getNum());
			preparedStatement.setString(7, studentBean.getNum());
			preparedStatement.setString(8, studentBean.getClassnum());
			preparedStatement.executeUpdate();
			connection.close();
			return true;	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
		
	} 
	
	public ArrayList<PlaceBean> getPlace() {
		String sql = "select * from shaozr_地区学生数统计02";
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ArrayList<PlaceBean> pList=new ArrayList<>();
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				PlaceBean placeBean = new PlaceBean();
				placeBean.setPlace(resultSet.getString("生源所在地"));
				placeBean.setSum(resultSet.getInt("地区总人数"));
				
				pList.add(placeBean);
			}
			connection.close();
			return pList;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean changePasscode(String passcode,String num) {
		String sql="UPDATE shaozr_管理员02 SET szr_登录密码02=? WHERE szr_管理员编号02=?";
		Connection connection;
		try {
			connection=dataSource.getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1,passcode);
			preparedStatement.setString(2,num);
			preparedStatement.executeUpdate();
			connection.close();
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	
}
