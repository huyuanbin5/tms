package com.wh.tms.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class Test {
	
	private static final String filePath = "/Users/jesson/ph";
	private static final String templatePath = "/Users/jesson/temp.txt";
	private static int index = 1;
	
	private static void list(List<File> fileList, File dir) {
		File[] files = dir.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				list(fileList, f);
			} else {
				if (!f.getName().endsWith(".jpg")) { continue; }
				fileList.add(f);
			}
		}
	}

	private static File macdir() {
		File file = new File("/Users/jesson/oper/"+index);
		if (!file.exists()){
			boolean s = file.mkdir();
			if (s) {
				index ++;
			}
		}
		return file;
	}
	public static void main(String[] args) {
		List<File> files = new ArrayList<File>();
		list(files,new File(filePath));
		File templateFile = new File(templatePath);
		
		try {
			File dir = null;
			List<String> fileNums = FileUtils.readLines(templateFile);
			int len = files.size();
			for (int i = 0; i < len; i++) {
				String fileName = fileNums.get(i);
				File saveFile = files.get(i);
				if (saveFile == null) {
					continue;
				}
				if(i % 500 == 0) {
					dir = macdir();
				}
				
				if (null != dir) {
					String savePath = dir.getPath();
					if (StringUtils.isNotBlank(savePath)) {
						String newFilePath = savePath+File.separator+fileName+".jpg";
						FileUtils.copyFile(saveFile, new File(newFilePath));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
