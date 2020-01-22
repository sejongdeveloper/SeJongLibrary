package sejong.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PageCalc {
	public static void main(String[] args) {
		// 인자값: 현재페이지, 총페이지, 월~금 페이지, 토요일 페이지
		String datePage = getDate(558, 980, 15, 25);
		System.out.println(datePage);
		
		// 인자값: 현재페이지, 총페이지, 월~금 페이지, 토요일 페이지, Calendar.class
		Calendar cal = Calendar.getInstance();
		cal.set(2020, 0, 21);
		String datePage2 = getDate(558, 980, 15, 25, cal);
		System.out.println(datePage2);
	}
	
	public static String getDate(int page, int maxPage, int dayPage, int weekPage) {
		Calendar cal = Calendar.getInstance();
		
		while(page < 980) {
			int week = cal.get(Calendar.DAY_OF_WEEK);
			
			if(2 <= week && week <= 6) {
				page+=dayPage;
			} else if(week == 7) {
				page+=weekPage;
			}
			
			cal.add(Calendar.DATE, 1);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
		return "페이지:"+page+" "+sdf.format(cal.getTime());
	}
	
	public static String getDate(int page, int maxPage, int dayPage, int weekPage, Calendar cal) {
		
		while(page < 980) {
			int week = cal.get(Calendar.DAY_OF_WEEK);
			
			if(2 <= week && week <= 6) {
				page+=dayPage;
			} else if(week == 7) {
				page+=weekPage;
			}
			
			cal.add(Calendar.DATE, 1);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
		return "페이지:"+page+" "+sdf.format(cal.getTime());
	}
}
