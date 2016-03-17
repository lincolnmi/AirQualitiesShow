package jason.tongji.controller;

import jason.tongji.config.GlobalConfig;
import jason.tongji.interceptor.LoginInterceptor;
import jason.tongji.interceptor.OwnerRequiredInterceptor;
import jason.tongji.model.Files;
import jason.tongji.model.FirstTag;
import jason.tongji.model.Materials;
import jason.tongji.model.Users;
import jason.tongji.tool.DataHanlder;
import jason.tongji.tool.UITools;
import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.upload.UploadFile;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Before(LoginInterceptor.class)
public class MaterialManageController extends Controller {
	public void index() {
		Users user = getSessionAttr("loginUser");
		if (user != null) {
			if (user.isAdmin()) {
				setAttr("materials", Materials.dao.getMaterials());
			} else {
				setAttr("materials",
						Materials.dao.getMaterials(user.getInt("id")));
			}
			String flag = getPara(0);
			if (flag != null) {
				if (flag.equals(GlobalConfig.SUCCESS)) {
					setAttr("msg", "Operation Success!");
				}
			}
			render("/backpage/material/list_material.html");
		} else {
			redirect("/private/login/");
		}
	}

	public void tags() {
		setAttr("tags", FirstTag.dao.getFirstTags());
		render("/backpage/material/tags.html");
	}

	public void delete() {
		String id = getPara(0);
		if (Materials.dao.deleteMaterial(Integer.parseInt(id))) {
			setAttr("result", "success");
			redirect("/private/material");
		} else {
			setAttr("result", "fail");
			render("/backpage/feedback/error.html");
		}
	}

	public void addMaterialView() {
		if ("GET".equals(getRequest().getMethod())) {
			setAttr("tags", FirstTag.dao.getFirstTags());
			render("/backpage/material/add_material.html");
		}
	}

	@Before(OwnerRequiredInterceptor.class)
	public void editView() {
		if ("GET".equals(getRequest().getMethod())) {
			int mid = getParaToInt(0);
			String flag = getPara(1);
			if (flag != null) {
				if (flag.equals(GlobalConfig.SUCCESS)) {
					setAttr("msg", "Operation Success!");
				}
			}
			setAttr("material", Materials.dao.findById(mid));
			setAttr("tags", FirstTag.dao.getFirstTags(mid));
			setAttr("files", Files.dao.getFilesByMaterialId(mid));
			render("/backpage/material/edit.html");
		} else {
			int mid = getParaToInt("id");
			String title = getPara("title");
			String content = getPara("content");
			int draft = UITools.convertCheckboxValue(getPara("draft"));
			String[] file_ids = UITools.convertIdsValue(getParaValues("file"));
			String[] tag_ids = UITools
					.convertIdsValue(getParaValues("secondTag"));
			try {
				int rc = Materials.dao.updateMaterial(mid, title, content,
						draft, file_ids, tag_ids);
				if (rc != -1) {
					redirect("/private/material/editView/" + rc + "-success");
				} else {
					render("/backpage/feedback/error.html");
				}

			} catch (Exception e) {
				// TODO: handle exception
				render("/backpage/feedback/error.html");
			}
		}

	}

	public void addMaterial() {
		if ("POST".equals(getRequest().getMethod())) {
			String title = getPara("title");
			String content = getPara("content");
			int draft = UITools.convertCheckboxValue(getPara("draft"));
			Users user = (Users) getSessionAttr("loginUser");
			String[] file_ids = UITools.convertIdsValue(getParaValues("file"));
			String[] tag_ids = UITools
					.convertIdsValue(getParaValues("secondTag"));

			try {
				int rc = Materials.dao.addMaterial(title, content,
						user.getStr("name"), user.getInt("id"), draft,
						file_ids, tag_ids);
				if (rc != -1) {
					//redirect("/private/material/editView/" + rc + "-success");
					redirect("/private/material/success");
				} else {
					setAttr("msg", "Save material fail!");
					render("/backpage/feedback/error.html");
				}

			} catch (Exception e) {
				// TODO: handle exception
				render("/backpage/feedback/error.html");
			}
		}
	}

	
	@ClearInterceptor
	public void fileUpload() {
		String contextPath = JFinal.me().getServletContext().getRealPath("/");
		String savePath = contextPath + "/resource/dynamic/upload/";
		Date today = new Date();
		// Create a folder
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(today).toString();
		savePath = savePath + dateString + "/";
		File file = new File(savePath);
		if (file.isFile() || !file.exists()) {
			file.mkdir();
		}
		String timeMillis = System.currentTimeMillis() + "";
		savePath = savePath + timeMillis + "/";
		file = new File(savePath + timeMillis);
		if (file.isFile() || !file.exists()) {
			file.mkdir();
		}

		UploadFile uploadFile = getFile("up-file", savePath);
		File savedFile = new File(uploadFile.getSaveDirectory()
				+ uploadFile.getFileName());
		try {
			@SuppressWarnings("resource")
			FileInputStream fis = new FileInputStream(savedFile);
			
			String size = DataHanlder.getSizeString(fis.available());
			Files file1 = new Files().addFile(uploadFile.getFileName(), dateString
					+ "/" + timeMillis + "/" + uploadFile.getFileName(), "txt",size);
			String result = file1.toJson();
			renderJson(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			renderJson("{\"result\":\"fail\"");
		}  
		
	}

}
