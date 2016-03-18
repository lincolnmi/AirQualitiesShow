package jason.tongji.model;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class PublicationFile extends Model<PublicationFile> {
	public static PublicationFile dao = new PublicationFile();
	
	public int count(int id){
		return dao.find("select * from publication_file where publication_id="+id).size();
	}
	
}
