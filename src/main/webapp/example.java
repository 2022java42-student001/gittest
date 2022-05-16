package la.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import la.bean.ItemBean;

public class ItemDAO2 {
// URL、ユーザ名、パスワードの準備
	private String url = "jdbc:postgresql:sample";
	private String user = "student";
	private String pass = "himitu";

	public ItemDAO2() throws DAOException {
		try {
// JDBC ドライバの登録
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DAOException("ドライバの登録に失敗しました。");
		}
	}

	public List<ItemBean> findAll() throws DAOException {
// SQL 文の作成
		String sql = "SELECT * FROM item";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
// PreparedStatement オブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);
// SQL の実行
				ResultSet rs = st.executeQuery();) {
// 結果の取得
			List<ItemBean> list = new ArrayList<ItemBean>();
			while (rs.next()) {
				int code = rs.getInt("code");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				ItemBean bean = new ItemBean(code, name, price);
				list.add(bean);
			}
// 商品一覧を List として返す
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		}
	}

	public List<ItemBean> sortPrice(boolean isAscending) throws DAOException {
// SQL 文の作成
		String sql;
// ソートキーの指定
		if (isAscending)
			sql = "SELECT * FROM item ORDER BY price";
		else
			sql = "SELECT * FROM item ORDER BY price desc";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
// PreparedStatement オブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);
// SQL の実行
				ResultSet rs = st.executeQuery();) {
// 結果の取得
			List<ItemBean> list = new ArrayList<ItemBean>();
			while (rs.next()) {
				int code = rs.getInt("code");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				ItemBean bean = new ItemBean(code, name, price);
				list.add(bean);
			}
// 商品一覧を List として返す
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}

	public int addItem(String name, int price) throws DAOException {
// SQL 文の作成
		String sql = "INSERT INTO item(name, price) VALUES(?, ?)";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
// PreparedStatement オブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
// 商品名と値段の指定
			st.setString(1, name);
			st.setInt(2, price);
// SQL の実行
			int rows = st.executeUpdate();
			return rows;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}

	public List<ItemBean> findByPrice(int lePrice) throws DAOException {
		// SQL 文の作成
		String sql = "SELECT * FROM item WHERE price <= ?";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatement オブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			// 値段のセット
			st.setInt(1, lePrice);

			try (// SQL の実行
					ResultSet rs = st.executeQuery();) {
				// 結果の取得
				List<ItemBean> list = new ArrayList<ItemBean>();
				while (rs.next()) {
					int code = rs.getInt("code");
					String name = rs.getString("name");
					int price = rs.getInt("price");
					ItemBean bean = new ItemBean(code, name, price);
					list.add(bean);
				}
				// 商品一覧を List として返す
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("レコードの操作に失敗しました。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}

	public int deleteByPrimaryKey(int key) throws DAOException {
		// SQL 文の作成
		String sql = "DELETE FROM item WHERE code = ?";

		try (// データベースへの接続
				Connection con = DriverManager.getConnection(url, user, pass);
				// PreparedStatement オブジェクトの取得
				PreparedStatement st = con.prepareStatement(sql);) {
			// 主キーの指定
			st.setInt(1, key);
			// SQL の実行
			int rows = st.executeUpdate();
			return rows;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		}
	}
}