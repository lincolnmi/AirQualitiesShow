package jason.tongji.model;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.tx.Tx;
import jason.tongji.tool.DataHanlder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("serial")
public class Publications extends Model<Publications> {
	public static Publications dao = new Publications();

	public boolean addViewCount(int mid) {
		Publications publications = dao.findById(mid);
		if (publications == null) {
			return false;
		} else {
			return publications
					.set("view_count", publications.getInt("view_count") + 1)
					.update();
		}
	}

	public int getUserId(int mid) {
		Publications publications = dao
				.findFirst("select user_id from publications where id=" + mid);
		if (publications != null) {
			return publications.getInt("user_id");
		} else {
			return -1;
		}
	}

	public ArrayList<Publications> getPublications() {
		return (ArrayList<Publications>) dao
				.find("select * from publications order by id desc");
	}

	public ArrayList<Publications> getPublications(int uid) {
		return (ArrayList<Publications>) dao
				.find("select * from materials where user_id =" + uid
						+ " order by id desc");
	}

	@Before(Tx.class)
	public int updatePublication(int mid, String title, String content,int year) {
        if (dao.findById(mid).set("title", title).set("content", content)
                .set("update_time", new Date()).set("year",year)
                .update()) {
            return mid;
        } else {
            return -1;
        }
	}

	public ArrayList<Publications> getAllPublications() {
		ArrayList<Publications> list = (ArrayList<Publications>) dao
				.find("select * from publications");
		return list;
	}

	@Before(Tx.class)
	// Transaction support
	public int addPublication(String title, String content, String author,
			int user_id, int year) {
		Publications publications = new Publications().set("title", title)
				.set("content", content).set("user_id", user_id)
				.set("year", year).set("update_time", new Date())
				.set("create_time", new Date()).set("author", author);
		boolean msave_reuslt = publications.save();
		int mid = publications.get("id");
		if (!msave_reuslt) {
			return -1;
		}
		return mid;
	}

}
