package com.gy.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gy.model.Game;

public class SplitString {
	
	public static Game getGame(Map map) {
		// TODO Auto-generated method stub
		Game gamenew = null;
		List list = (List) map.get("games");
		System.err.println("数组"+list);
		
		if(list==null)
		{
			String status = "0405";
			String message = "传入的游戏为空";
			int userid = 0;
			map.put("status", status);
			map.put("message", message);
			map.put("useid", userid);
		}
		else
		{
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Object game = it.next();
				/*Game game = (Game) o;*/
				String games = game.toString();
				String[] info = games.split(",");
				for(int i=0;i<info.length;i++){
		            System.out.println(info[i]);//循环输出结果
		        }
				gamenew = new Game();
	            gamenew.setGamename(info[0].substring(10, info[0].length()));
	            gamenew.setGamepackage(info[1].substring(13, info[1].length()));
	            gamenew.setRemark(info[2].substring(8, info[2].length()-1));
	            System.err.println(gamenew.getGamename()+"\t"+gamenew.getGamepackage()+"\t"+gamenew.getRemark());
			}
			
		}
		return gamenew;
	}
	
	public void rearch() {
		// TODO Auto-generated method stub
		/*Map <String, ArrayList> map = new HashMap <String, ArrayList>();
		Set<String> keys = map.keySet();
		Iterator<String> iterator = keys.iterator();
		while(iterator.hasNext()) {
		    String key = iterator.next();
		    ArrayList arrayList = (ArrayList) map.get(key);
		    for(Object o : arrayList) {
		        o.toString();
		    }
		}*/
	}
}
