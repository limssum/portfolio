package com.DAO;

// JDBC 컨트롤러 
import java.util.Random;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.VO.PlayerVO;
import com.VO.PikachuVO;
import com.DAO.PlayerDAO;

public class PikaDAO {

	Random rand = new Random();
	Scanner sc = new Scanner(System.in);

	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "pikachu";
	private String pass = "1234"; // 본인이 설정한 비밀번호 넣으세요

	private Connection con = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;

	private String Pname = null;
	private int Plevel = 0;
	private int Peng = 100; // 처음에는 업데이트, 이후부터는 sql문으로 불러와야함
	private int Pexp = 0;
	private String id = null;
	private String pw = null;

	private PlayerVO Playvo = null;
	private PikachuVO Pikavo = null;

	private boolean result = false;

	public void connect() { // 데이터베이스 연동

		try {// 1. jdbc 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 클래스 안의 파일 경로를 적어주는 것

			// 2. 데이터베이스 연결
			con = DriverManager.getConnection(url, user, pass);

			// 데이터베이스 연결 커넥트
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void close(PreparedStatement pst) {
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 1. 회원가입 참, 거짓 판별(성공, 실패)
	public boolean Join(String id, String pw) {
		try {

			connect();
			// 3. sql 작성
			String sql = "insert into player values(?, ?)";

			// 4. 바인드변수 채우기
			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			pst.setString(2, pw);

			// 5. sql 실행처리
			// excuteUpdate => insert, update, delete
			int cnt = pst.executeUpdate();
			if (cnt > 0) {
				result = true;
			} else {
				result = false; System.out.println("회원가입 실패! 메인 화면으로 돌아갑니다.\n");
			}

		} catch (Exception e) {
			System.out.println("회원가입 실패! 메인 화면으로 돌아갑니다.\n");
		} finally {

			close(pst);
			close(con);
		}
		return result;
	}

	// 2. 로그인 참, 거짓 판별(정보 있음-성공, 정보 없음-실패)
	public boolean Login(String id, String pw) {

		try {
			connect();
			String sql = "select * from player where id=? and pw =?";

			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			pst.setString(2, pw);

			rs = pst.executeQuery();
			if (rs.next()) {
				result = true;
			}

		} catch (SQLException e) {
			System.out.println("login sql문 오류");
		} finally {
			close(pst);
			close(con);
		}

		return result;
	}

	// 4. 피카츄 등록
	public boolean PikaRg(String id, String Pname) {
		try {

			connect();
			// 3. sql 작성
			String sql = "insert into pika values(?, ?, 1, 100)";

			// 4. 바인드변수 채우기
			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			pst.setString(2, Pname);

			// 5. sql 실행처리
			// excuteUpdate => insert, update, delete
			int cnt = pst.executeUpdate();
			if (cnt > 0) {
				System.out.println("피카츄가 등록되었습니다.");
			} else {
			}

		} catch (Exception e) {
			System.out.println("피카츄가 이미 있습니다.");
		} finally {

			close(pst);
			close(con);
		}
		return result;
	}

	// 로그인 하면서 입력받은 아이디로 피카츄 등록 유무 확인
	public PikachuVO PlevelFind(String id) {
		try {
			connect();
			// 3. sql 작성
			String sql = "select * from pika where id = ?";
			pst = con.prepareStatement(sql);
			pst.setString(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				Pname = rs.getString("Pname");
				Plevel = rs.getInt("Plevel");
				Peng = rs.getInt("Peng");
			}

			if (Pname == null) {
				System.out.println("피카츄를 등록해주세요");
				System.out.println("아이디 : " + id);
				System.out.print("피카츄 별명 : ");
				Pname = sc.next();
				PikaRg(id, Pname);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pst);
			close(con);
		}
		return Pikavo;
	}

	// 피카츄 레벨업한 것을 DB에 업데이트
	public void PlevelUp(String id) {
		try {
			connect();
			// 3. sql 작성
			String sql = " update pika set Plevel =" + Plevel + ", Peng = " + Peng + "where id = ?";
			pst = con.prepareStatement(sql);
			pst.setString(1, id);

			pst.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pst);
			close(con);
		}
	}

	// 피카츄 사망 시 데이터베이스에서 정보 삭제
	public void PDelete(String id) {

		PlevelFind(id);
		if (Peng <= 0) {

			try {
				connect();
				// 3. sql 작성
				String sql = " delete from pika where id = ?";
				pst = con.prepareStatement(sql);
				pst.setString(1, id);

				pst.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close(pst);
				close(con);
			}
		}

	}
	
	// 피카츄 요구사항 (배고파 심심해 뻐근해 등) 출력, 피카츄 육성(밥먹기 놀기 등) 문장 선택 후 비교, 그 후 경험치 증가와 에너지 증감
	public void PikaPt() {
		int num1 = 0, num2 = 0;

		while (true) {
			String[] Askicode = {
					"\r\n" + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⣦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠂⠀⠀⠀⠀⠀⠀⠀⠀⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⠷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠠⠁⠀⠀⠑⢄⠀⡀⠠⣴⠀⠘⢿⣷⣦⠄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠹⠁⠀⠰⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⡐⠁⠀⠀⠀⡀⠀⠁⡠⣼⣿⠀⠀⠈⢻⡇⠀⠀⠑⢄⠀⠀⠀⠀⠀⠀⠀⠀⠸⠀⠀⠀⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⡐⠀⠀⠀⠀⢀⠈⠐⠈⠀⢻⣿⢾⡟⣀⣠⡥⡀⠀⠀⠀⠁⣄⡀⠀⠀⠀⠀⠀⠀⠂⠀⠀⢰⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⡌⠀⠀⠀⠀⠀⠘⠀⠀⠀⠀⠀⢀⢜⣿⠟⢩⣾⣿⣦⡀⠀⠀⠈⠔⠃⠈⠉⠉⠉⠁⠘⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠐⠀⠀⠀⠀⠀⠀⡄⠀⠀⠀⠀⠐⠁⠸⠁⣰⣿⠿⠋⠁⠀⠑⣢⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠁⠂⠄⢀⡀⠀⠃⠀⠀⠀⡼⠀⠀⠀⠀⠟⠁⠀⠀⠀⠀⠰⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣄⣀⣰⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠻⣿⣧⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⣏⡆⠀⠀⠀⠀⢸⣽⡄⡆⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠛⠿⠷⣶⣤⣤⣄⠀⠀⠀⠀⠀⠶⣶⡄⠙⠛⠁⠀⠀⠀⢀⠀⠉⢡⡇⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠛⢷⣄⠀⠀⠀⠀⠈⠹⡀⠀⠀⠀⠀⠠⠄⠀⠀⡻⠓⠄⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠳⢦⣄⡀⠀⠀⠘⠠⠀⠀⠀⠠⠄⠐⠚⠀⠐⠘⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠀⠀⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n",
					"\r\n" + "⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣷⡂⠀⠀⠀⠢⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠄⠒⠈⠀⣸⣿⣿⣿⡟⠁⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡿⡂⠀⠀⠀⠀⠈⠢⡀⠀⠀⠀⠀⢀⣀⣀⣀⣀⠀⠀⠀⠀⠀⠀⡠⠐⠋⠀⠀⠀⠀⠐⣸⣿⡿⠋⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡇⠙⢀⠀⠀⠀⠀⠀⣨⠦⠲⠉⠀⠀⠀⠀⠀⠀⠀⠈⠁⣲⡔⠈⠀⠀⠀⠀⠀⠀⠀⣠⡿⠋⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀ ⣿⡇⠀⠀⠹⣦⣀⣤⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠔⠁⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡇⠀⠀⠀⠈⠻⠃⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⠴⠊⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣴⣾⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⢀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡇⠀⠀⠀⠀⡂⣲⣄⢀⡀⠀⠀⠀⠀⠀⠀⠀⣠⣄⣠⡀⠀⠀⠉⠙⠉⠚⠀⠀⠀⠀⢀⡠⠠⢒⡒⠓⠂⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡇⠀⠀⠀⢀⠂⣿⣧⣤⣠⠀⠀⠀⠀⠀⠀⠰⣅⣼⣿⣿⡦⠀⠀⠀⠀⠀⢃⠀⠀⠀⡁⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡇⠀⠀⠀⠌⠀⠈⠉⠉⠀⣤⡀⠀⠀⠀⠀⠀⠙⠛⠛⠏⠁⠀⠀⠀⠀⠀⠘⠀⠀⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡇⠀⠀⠀⣿⣿⣦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣤⣤⡀⠀⠀⠀⠀⠀⠇⠀⠀⢀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⡀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡇⠀⠀⠀⠹⣿⣿⣼⣾⣶⣤⣤⡤⠐⢄⠀⠀⠀⣿⣿⣿⣿⣧⠀⠀⠀⠀⠀⢀⠀⠀⠸⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⣿⣿⣶⣦⣄⣀⠀⠀⣸⣿⡇⠀⠀⠀⡀⣸⣿⣿⣿⣿⣿⣿⣿⣿⣄⢂⠀⠀⢿⣿⣿⣿⠏⠀⠀⠀⠀⠀⠸⣀⡀⠀⠀⠀⠀⠀⠀⡍⠉⠁\r\n"
							+ "⠈⠉⠛⠻⠿⣿⣿⣿⣿⣿⣧⣀⣀⣊⡄⢺⣿⣿⣿⣿⣿⣿⣿⣿⣿⣏⣀⠀⠀⠀⡀⠀⠀⠀⠀⠀⠀⠀⣀⣿⠊⠀⠀⠀⠀⠀⠀⡇⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠈⠉⠛⠻⢿⣿⣿⡿⢲⣾⣿⣿⣿⣿⣿⣿⣿⣿⠑⠉⠉⠓⠚⠉⠁⠀⠀⠀⠀⠀⠀⠺⣿⣿⠆⠀⠀⠀⠀⠀⠀⠃⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢄⠀⠘⣿⣿⣿⣿⣿⣿⣿⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⣄⣀⣀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢉⠿⠿⠿⠿⠛⠙⢦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⡍⠉⠁⠀⠐⠚⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣶⢄⢀⠄⠀⠀⠀⠀⠀⠀⠀⠀⠙⢦⣀⣀⣀⣠⣤⡾⠃⠀⠀⠀⠀⠀⢸⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠋⠀⢻⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢈⣽⣿⣄⠀⠀⠀⠀⠀⢀⢠⣾⣿⣿⣷⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⠀⢀⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠛⣿⣿⢿⡀⠀⠀⠀⢀⣾⣿⣿⣿⣿⣿⣿⣶⣦⣤⣀⡀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠁⣸⣿⣷⣶⣦⣶⣶⣶⣶⣶⣤⣄⠀⠀⣿⠀⢸⡇⢀⣠⣶⣿⣿⣿⣿⣿⠉⠙⠛⠿⢿⣿⣿⣿⣿\r\n" + "",
					"\r\n" + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⠓⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⠀⠀⠰⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⡀⠀⠀⠘⢋⡀⠀⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠀⣴⣶⣿⡿⠁⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠐⠂⠀⠈⠷⠀⠀⠀⠈⠗⢘⣃⠀⠀⠀⣀⠀⠀⠀⠀⠀⠀⠀⠀⢀⢠⠀⠊⠁⢀⣿⡿⠋⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠹⣤⣀⠀⠀⠀⠁⠀⠀⠀⠈⠀⠁⠒⠀⡀⠀⡀⠒⠈⠀⠀⠀⢀⡼⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⠀⠀⠀⠀⡀⢨⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠀⠀⠀⢀⡠⠄⠈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡄⠂⠁⠸⠁⢀⣤⡖⢤⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣶⠞⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠂⠰⢠⢀⠀⠀⠀⠀⠸⣿⣿⡾⠀⠀⠀⠀⠀⠀⠀⣠⠤⣤⡀⠀⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠠⠄⠀⠀⠀⠈⠀⣴⣶⣤⡀⠈⠉⠀⠀⠀⢠⡄⠀⠀⠀⣿⣶⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠘⢀⠀⠀⠀⠀⣿⣿⣿⠇⠀⠀⠀⣤⣤⣤⡀⠀⠀⠀⠈⠉⠁⠀⠀⣠⡀⠀⠦⠤⠄⢠⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠱⣄⠀⠀⠘⢿⡉⠀⠀⠀⠀⢸⣿⣿⣿⠛⠁⠀⠀⠀⣰⣾⣷⡆⠀⠀⠀⠀⢀⡈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢘⣶⠀⠀⠙⠆⠀⠀⠀⠘⣿⡿⠁⠀⠀⠀⠀⠀⢿⣿⠏⠀⠀⠀⡠⠆⠈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢘⠁⠀⠀⠀⠀⠀⠀⢠⣄⣈⣀⠀⠀⠀⢀⠤⠐⠂⢁⣠⠄⠂⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⡄⠀⠀⠀⠀⠀⠀⠀⠈⠉⠉⠀⠀⠀⠀⠀⠀⠀⠈⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⠘⠂⠁⠀⠃⠆⠀⣀⠀⠀⠀⠀⠀⠀⠀⢀⣠⡼⡁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠁⠉⠉⠀⠀⠉⠫⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n" + "",
					"\r\n" + "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠀⢰⣶⣿⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠰⠊⠀⠀⢸⣿⡿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⠒⠁⠀⠀⠀⠀⠾⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⠊⠀⠀⠀⢀⡠⠒⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠊⠀⠀⠉⠉⠉⠉⠐⠂⠤⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⢊⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠂⠘⠓⠃⠀⠀⠐⠂⠠⠄⢀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢁⣮⠆⠀⠀⠀⠀⠀⢰⡊⣳⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⣴⣦⣄⡀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⡌⠈⠁⠀⠃⠀⠀⠀⠀⠸⣿⣿⠇⠀⠀⠀⠀⠀⢲⠂⠠⠤⠀⣀⣀⣀⣀⣤⣾⣿⣿⣿⠿⠟⠠⡀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⣿⡗⠀⠱⣾⣷⣦⣄⡀⠀⠀⠀⣠⣴⣤⡄⠀⠀⠀⠈⠀⠀⠀⠀⠀⠀⠀⢀⠠⠊⠁⠀⠀⠀⠀⠀⠱⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⡍⠀⠀⠀⠏⠀⠈⠙⠁⠀⠀⠈⣿⣿⣿⣿⠀⢀⠀⠀⠇⠐⣄⠀⢀⠀⠂⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠡⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠡⠀⠀⠀⠘⠀⠀⡘⠀⠀⠀⠈⠛⠿⢿⠟⠊⠀⠀⠀⠀⠀⠸⠊⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⡤⠔⠒⠀⠉⠉⠀⠐⠃⠀⠀⠈⠡⠔⠀⠀⠀⠀⠀⡠⠂⠁⠀⠀⠀⠀⠀⠀⠀⠌⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⡠⠀⠀\r\n"
							+ "⠀⠀⢨⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠄⠂⠁⠁⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠒⢄⠀⠀⠀⠀⠀⠔⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠔⠢⠀⠀⠀⠀⠀⠀⢀⡠⠔⠊⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⡷⡄⠀⠈⢰⢢⠔⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡰⠋⠀⠀⠠⡀⠀⠀⠀⠀⠀⢣⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠗⠐⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡄⠀⠀⣀⡀⠱⠀⠀⠀⠀⠀⠀⢃⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⢃⠀⠡⠀⠀⠀⠀⠀⢸⢓⠀⠀⠀⡀⠀⠄⠀⠀⠀⠀⠀⠀⠀⠀⣠⡴⠶⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⡄⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⢃⠀⠀⠀⠀⠀⠀⠸⠞⠄⡔⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⡏⠀⢣⠀⠀⢀⡄⠀⠒⠒⠂⠀⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠈⢢⣄⠀⠀⠀⠀⠀⠆⠘⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣼⣤⣤⣤⣿⣿⣿⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠉⠢⢀⠀⠀⢚⡀⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣾⣿⡿⠛⠛⠛⠛⠛⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠈⠑⢄⡈⠅⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠘⠦⣁⠀⠀⠀⠀⠀⣀⠀⠚⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n"
							+ "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠉⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\r\n" };
			
			String[] desire = { "자고 싶어~", "배고파!", "심심해, 놀아줘!", "뻐근한데 나갈래!", "" }; // 피카츄 요구사항 + 게임종료 배열
			String[] train = { "잠자기", "밥먹기", "놀기", "운동하기", "메인 화면" }; // 피카츄 키우기 + 게임종료 배열
			String[] move = { "쿨쿨(_ _).zZ", "냠냠", "노는게 제일 좋아~!", "저기까지 뛰다 올게!", " " }; // 선택 후 출력문

			while (true) {// 게임 종료 들어올 때까지 육성 계속 실행
				num1 = rand.nextInt(4);
				System.out.println("현재 상태");
				System.out.println("레벨 : " + Plevel + "\t에너지 : "+Peng);
				System.out.println("\n\t\t" + desire[num1] + "\n"); //욕구 출력
				System.out.println(Askicode[num1]); //그림 출력

				for (int i = 0; i < train.length; i++) {
					System.out.print("[" + (i + 1) + "]" + train[i] + " ");
				}
				System.out.print("\n메뉴선택 >> ");

				num2 = (sc.nextInt() - 1); // 인덱스는 0부터 시작하기 때문에 입력 받은 값에서 1을 빼준 후 넣어준다.
				try {
					System.out.println("\n" + move[num2] + "\n");
				} catch (Exception e) {
					System.out.println("잘못된 입력입니다.");
				}

				// [5] 게임종료 선택시 종료
				if (num2 == 4) {
					break;
				} else if (num1 == num2) {
					// 3. 피카츄 요구사항 피카츄 육성문장 비교 (1번 2번 인덱스 위치 비교)

					int Upexp = 1 + rand.nextInt(20); // 경험치 랜덤 증가값
					Pexp += Upexp; // 경험치 증가
					System.out.println("경험치가 " + Upexp + " 증가하여 " + Pexp + "이 되었습니다.\n");

				} else if (num1 != num2) { // 피카츄 욕구 무시
					System.out.println("피카츄가 화를 냅니다.\n");
				}

				if (Pexp >= 100) { // 경험치 100 이상이면 레벨업, 0으로 초기화

					Plevel += 1;
					Pexp = 0;
					System.out.println("경험치가 100이 넘어 레벨이 올랐습니다.\n");
					System.out.println("현재 레벨 : " + Plevel + "\n");
					// sql(레벨값 업데이트)
				}

				if (num2 == 0 || num2 == 1) {
					Peng += 10;
					System.out.println("에너지가 10 증가하여 " + Peng + "이 되었습니다.\n");
					System.out.println("_________________________________________________");
					// sql(에너지 업데이트)

				} else if (num2 == 2 || num2 == 3) {
					Peng -= 10;
					System.out.println("에너지가 10 감소하여 " + Peng + "이 되었습니다.\n");
					System.out.println("_________________________________________________");
					// sql(에너지 업데이트)

					if (Peng <= 0) {
						break;
					}
				} else {

					System.out.println("잘못된 입력입니다.\n");
				}

			}
			if (num2 == 4) {
				Pexp = 0;
				System.out.println("메인 화면으로 돌아갑니다.\n");
				break;
			}
			if (Peng <= 0) {
				System.out.println("에너지가 0 이하가 되어 피카츄가 사망했습니다.");
				System.out.println("메인 화면으로 돌아갑니다.\n");
				break;
			}
		}
	}
}