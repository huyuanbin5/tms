package com.wh.tms.util;

import com.wh.tms.constans.SystemCons;
import com.wh.tms.result.Result;

/**
 * 通用返回数据工具类
* @ClassName: ResultUtils 
* @Description: TODO 
* @author Huqk
* @date 2018年2月27日 下午10:10:32
 */
public class ResultUtils {
	
	/**
	 * 成功
	* @Title: success 
	* @Description: TODO
	* @param @param object
	* @param @return 
	* @return Result
	* @throws
	 */
	public static Result success(Object object) {
		return success(1,object);
	}
	
	/**
	 * 成功
	* @Title: success 
	* @Description: TODO
	* @param @param totalCount
	* @param @param object
	* @param @return 
	* @return Result
	* @throws
	 */
	public static Result success(long totalCount,Object object) {
        return success(totalCount,object,"成功");
    }
	/**
	 * 
	* @Title: success 
	* @Description: TODO
	* @param @param totalCount
	* @param @param object
	* @param @param msg
	* @param @return 
	* @return Result
	* @throws
	 */
	public static Result success(long totalCount,Object object,String msg) {
        Result result = new Result();
        result.setCode(SystemCons.SUCCESS);
        result.setMsg(msg);
        result.setCount(totalCount);
        result.setData(object);
        return result;
    }

    /**
     * 失败
    * @Title: error 
    * @Description: TODO
    * @param @param code
    * @param @param msg
    * @param @return 
    * @return Result
    * @throws
     */
    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    /**
     * 返回结果失败
    * @Title: failure 
    * @Description: TODO
    * @param @param msg
    * @param @return 
    * @return Result
    * @throws
     */
    public static Result failure(String msg) {
    		return error(SystemCons.FAILURE,msg);
    }
    /**
     * 参数为空
    * @Title: paramsEmpty 
    * @Description: TODO
    * @param @param msg
    * @param @return 
    * @return Result
    * @throws
     */
    public static Result paramsEmpty(String msg) {
    		return error(SystemCons.ERROR_EMPTY_PARAMS,msg);
    }

}
