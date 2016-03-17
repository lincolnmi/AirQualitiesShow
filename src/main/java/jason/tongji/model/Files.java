package jason.tongji.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Files extends Model<Files> {
	public static Files dao = new Files();

	public Files addFile(String name, String path, String type, String size) {
		Files file = new Files().set("file_name", name).set("file_path", path)
				.set("file_type", type).set("file_size", size);
		file.save();
		return file;
	}

	public ArrayList<Files> getFilesByMaterialId(int id) {
		return (ArrayList<Files>) dao
				.find("select * from files where id in (select file_id from material_file where material_id="
						+ id + ")");
	}

	public ArrayList<Files> getFilesByPostId(int id) {
		return (ArrayList<Files>) dao
				.find("select * from files where id in(select file_id from post_file where post_id="
						+ id + ")");
	}
}
