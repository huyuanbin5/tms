package com.wh.tms.web.controller.doc;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wh.tms.doc.DocPlugin;
import com.wh.tms.util.PropertiesUtils;

@Controller
@RequestMapping("/doc")
public class DocController {
	
	@RequestMapping("/init")
	public @ResponseBody String init(HttpServletRequest request,HttpServletResponse response) {
		String controllerClassPath = this.getClass().getClassLoader().getResource("").getPath();
		String libDir = request.getServletContext().getRealPath("/") +"WEB-INF"+File.separator+"lib";
		String basePackage = "com.wh";
		String targetFile = request.getServletContext().getRealPath("/")+"doc"+File.separator+"open-api.html";
		String host = PropertiesUtils.getString("sysHost"); 
		DocPlugin plugin = new DocPlugin(targetFile,host,basePackage,controllerClassPath,libDir);
		try {
			plugin.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Sucess";
	}

}
