package com.wh.tms.doc;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wh.tms.util.HTMLParserUtils;

/**
 * 文档插件工具类
* @ClassName: DocPlugin 
* @Description: TODO 
* @author Huqk
* @date 2018年3月5日 下午10:43:19
 */
public class DocPlugin {
	
	private static final Log log = LogFactory.getLog(DocPlugin.class);
	
	

	/**
	 * 文档页面存储路径
	 */
	private String targetFile;
	
	/**
	 * 项目域名
	 */
	private String host;
	
	/**
	 * 项目包名根路径
	 */
	private String basePackage;
	
	/**
	 * 扫描的class文件路径
	 */
	private String classPath;
	/**
	 * 项目jar包路径
	 */
	private String libDir;
	
	
	public DocPlugin() {}
	public DocPlugin(String targetFile,String host,String basePackage,String classPath,String libDir) {
		this.targetFile = targetFile;
		this.host = host;
		this.basePackage = basePackage;
		this.classPath = classPath;
		this.libDir = libDir;
	}
	
	
	
	//全局类加载器
	private URLClassLoader loader;

	public void execute() throws Exception {
		log.info("获取到参数列表");
		log.info("classPath=" + classPath);
		log.info("libDir=" + libDir);
		log.info("basePackage=" + basePackage);
		log.info("targetFile=" + targetFile);
		log.info("host=" + host);
		
		try{
			
			//初始化类加载器
			String basePath = (new URL("file",null,new File(classPath).getCanonicalPath() + File.separator)).toString();
			String libPath = (new URL("file",null,new File(libDir).getCanonicalPath() + File.separator)).toString();
			
			URLStreamHandler sh = null;
			List<URL> libs = new ArrayList<URL>();
			log.info("开始读取jar包...");
			File libDir = new File(libPath.replaceAll("file:", ""));
			for (File jar : libDir.listFiles()) {
				libs.add(new URL(null,libPath + jar.getName(),sh));
			}
			libs.add(new URL(null,basePath,sh));
			log.info("jar包读取完毕!");
			loader = new URLClassLoader(libs.toArray(new URL[libs.size()]),Thread.currentThread().getContextClassLoader());
			
			log.info("开始读取class文件...");
			//缓存所有相关的类文件
			List<Class<?>> classes = new ArrayList<Class<?>>();
			File classDir = new File(classPath);
			list(classes,classDir);
			
			if(classes.size() == 0){
				log.info("未加载任何类文件");
				return;
			}
			log.info("读取class文件完毕!");
			log.info("开始生成文档...");
			HTMLParserUtils.generate(targetFile, classes, host);
			log.info("已成功生成文档");
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	/**
	 * 递归扫描所有的class文件
	 * 
	 * @param clazz
	 * @param dir
	 */
	private void list(List<Class<?>> clazz, File dir) {
		File[] files = dir.listFiles();
		for (File f : files) {

			if (f.isDirectory()) {
				list(clazz, f);
			} else {

				if (!f.getName().endsWith(".class")) { continue; }
				String className = f.getPath().replaceAll("\\\\", "/").replaceAll(classPath.replaceAll("\\\\", "/"), "").replaceAll("/", ".").replaceAll("\\.class", "");
				if (className.startsWith(basePackage)) {
					try {
						clazz.add(Class.forName(className, true, loader));
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}
			}
		}
	}

}
