package com.jjf.collection;

//import com.jjf.resource.HashMap;
import java.util.SortedMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 2017��2��17��18:30:22
 * @author jjf_lenovo
 *
 */
public class HashMapTest {
	public static void main(String args[]){
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
//		Map<String, String> hashMap = Collections.synchronizedMap(new HashMap<String, String>());//new HashMap<String, String>();
		int i=1;
		while(i<10000){
//			Map<String, String> map = new HashMap<String, String>();
//			hashMap.put("hash", "����");
			new putOne(hashMap).start();;
			i++;
		}
	}
}

class putOne extends Thread{
	Map<String, String> map = null;
	public putOne(Map<String, String> obj){
		map = obj ;
	}
	@Override
	public void run(){
		Long time = System.currentTimeMillis();
		map.put(String.valueOf(time), "1");
		map.put("hash", "����");
		String ss = map.get("hash");
		if(!ss.equals("����")){
			System.out.println("����ȫ��HashMap:"+time);
		}
	}
}

