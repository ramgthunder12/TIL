import java.time.*;

public class WeekendCounter2 {
    private int year;
    public WeekendCounter2(int year) {
        this.year = year;
    }

    public static void main(String[] args) {
        int year = 2025;
        WeekendCounter2 counter = new WeekendCounter2(year);
        int weekendCount = counter.countWeekends();
        System.out.println(year + "년의 주말(토/일) 개수: "  + weekendCount + "일");
        System.out.println("주말은 총 " + (weekendCount / 2) + "번 있습니다.");
    }

    public int countWeekends() {
        LocalDate start = LocalDate.of(this.year, 1, 1);
        LocalDate end = LocalDate.of(this.year, 12, 31);

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