package jason.tongji.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class SecondTag extends Model<SecondTag> {
	public static SecondTag dao = new SecondTag();
	private boolean isChecked = false;
	
	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public ArrayList<SecondTag> getTagList(){
		return (ArrayList<SecondTag>) dao.find("select * from secondtag");
	}
}
