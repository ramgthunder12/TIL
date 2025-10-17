import java.time.DayOfWeek;
import java.time.LocalDate;
public class WeekendCounter {
    public static void main(String[] args) {
        int year = 2025;
        int weekendCount = countWeekends(year);
        System.out.println(year + "년의 주말(토/일) 개수"  + weekendCount + "일");
        System.out.println("주말은 총 " + (weekendCount / 2) + "번 있습니다.");
    }

    public static int countWeekends(int year) {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);

        int count = 0;
        while(!start.isAfter(end)) {
            DayOfWeek day = start.getDayOfWeek();
            if(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                count++;
            }
            start = start.plusDays(1);
        }
        return count;
    }
}