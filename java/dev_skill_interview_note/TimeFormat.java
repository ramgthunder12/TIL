public class TimeFormat {
	public static String format12To24Time(String format12Time) {
		String[] timeAndMeridiem = format12Time.split(" ");
		String timeData = timeAndMeridiem[0];

		if(timeAndMeridiem[1].charAt(0) == 'P') {
			String[] hourMinuteSecond = timeData.split(":");
			int hour = Integer.parseInt(hourMinuteSecond[0]);

			hour = hour + 12;
			hourMinuteSecond[0] = hour;
			
			return String.join(":", hourMinuteSecond);
		} else {
			return timeData;
		}
	}

	public static void main(String[] args) {
		String inputData = "07:05:45 PM";
		
		System.out.println(format12To24Time(inputData));
	}
}
