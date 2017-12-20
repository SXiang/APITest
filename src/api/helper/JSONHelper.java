package api.helper;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Steve Xiang
 * @date 10/31/2017
 */

public class JSONHelper {

	public static List<Integer> getListIntegerFromArray(String jsonArray, String key){
		JSONArray objArray = new JSONArray(jsonArray);
		List<Integer> list = new ArrayList<>();
		for(int i=0; i<objArray.length(); i++){
			int value = -1;
			JSONObject object = objArray.getJSONObject(i);
			try{
				value = object.getInt(key);
			}catch(Exception e){
				System.err.println(e);
				System.err.println("Property '"+key+"' not found in "+object.toString());
			}
			list.add(value);
		}
		return list;
	}

	public static List<String> getListStringFromArray(String jsonArray, String key){
		JSONArray objArray = new JSONArray(jsonArray);
		List<String> list = new ArrayList<>();
		for(int i=0; i<objArray.length(); i++){
			String value = "NotFound";
			JSONObject object = objArray.getJSONObject(i);
			try{
				value = object.getString(key);
			}catch(Exception e){
				System.err.println(e);
				System.err.println("Property '"+key+"' not found in "+object.toString());
			}
			list.add(value);
		}
		return list;
	}

	public static String getString(String jsonString, String key){
		JSONObject object = new JSONObject(jsonString);
		String value = null;
		try{
			value = object.getString(key);
		}catch(Exception e){
			System.err.println(e);
			System.err.println("Property '"+key+"' not found in "+object.toString());
		}
		return value;
	}

	public static List<String> getStringList(String jsonString, String key){
		JSONObject object = new JSONObject(jsonString);
		JSONArray objArray = null;
		try{
			objArray = object.getJSONArray(key);
		}catch(Exception e){
			System.err.println(e);
			System.err.println("Property '"+key+"' not found in "+object.toString());
			return null;
		}
		List<String> list = new ArrayList<>();
		for(int i=0; i<objArray.length(); i++){
			list.add(objArray.getString(i));
		}
		return list;
	}
}
