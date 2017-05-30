package com.jjf.collection;

//import com.jjf.resource.HashMap;
import java.util.Map;

/**
 * 2017��2��17��18:30:22
 * @author jjf_lenovo
 *
 */
public class HashMapTest {
	
	public static void main(String args[]){
		HashMap hashMap = new HashMap<String, String>();
//		Map<String, String> hashMap = Collections.synchronizedMap(new HashMap<String, String>());//new HashMap<String, String>();
		while(true){
//			Map<String, String> map = new HashMap<String, String>();
			hashMap.put("hash", "����");
			new putOne(hashMap).start();;
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
//		map.put("hash", "����");
		String ss = map.get("hash");
		if(ss==null){
			System.out.println("����ȫ��HashMap:"+time);
		}
	}
}

