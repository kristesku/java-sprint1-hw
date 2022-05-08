import java.text.SimpleDateFormat;
import java.util.*;

public class StepTracker {
    private final GregorianCalendar calendar;
    private final int[][][] walkedStepsData;
    private int targetSteps;

    public StepTracker() {
        walkedStepsData = new int[3000][12][0];
        targetSteps = 10000;
        calendar = new GregorianCalendar();
    }

    public String getStepStatistics(int yearIndex, int monthIndex) {
        int[] monthData = walkedStepsData[yearIndex][monthIndex];
        MonthStepStatistics statistics = new MonthStepStatistics(monthData, targetSteps);
        String statisticsString;

        if (statistics.monthDataIsEmpty()) {
            statisticsString = "За указанный период нет данных о пройденных шагах";
        } else {
            statisticsString = "Количество пройденных шагов по дням:\n"
                    + statistics.getDailyStepsString(monthData)
                    + "\nОбщее количество пройденных шагов: " + statistics.totalSteps
                    + "\nМаксимальное пройденное количество шагов в месяце: " + statistics.maxSteps
                    + "\nСреднее количество шагов: " + Math.round(statistics.averageStepsCount)
                    + "\nПройденная дистанция (км): " + Math.round(statistics.walkedDistance)
                    + "\nКоличество сожжённых килокалорий: " + statistics.totalKilocalories
                    + "\nЛучшая серия (дн.): " + statistics.bestStreakSize;
        }
        return statisticsString;
    }

    public String getFormattedDate(int yearIndex, int monthIndex, int dayIndex) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        this.calendar.set(yearIndex + 1, monthIndex, dayIndex + 1);
        Date date = this.calendar.getTime();
        return dateFormatter.format(date);
    }

    public void addWalkedStepsData(int yearIndex, int monthIndex, int dayIndex, int daysInMonth, int stepsWalked) {
        if (walkedStepsData[yearIndex][monthIndex].length == 0) {
            walkedStepsData[yearIndex][monthIndex] = new int[daysInMonth];
        }
        walkedStepsData[yearIndex][monthIndex][dayIndex] = stepsWalked;
    }

    public int getNumberOfDaysInMonth(int yearIndex, int monthIndex) {
        calendar.set(yearIndex + 1, monthIndex, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public void setTargetSteps(int targetSteps) {
        this.targetSteps = targetSteps;
    }

    private static class MonthStepStatistics {
        int totalSteps, maxSteps, totalKilocalories;
        double averageStepsCount, walkedDistance;
        int bestStreakSize;

        public MonthStepStatistics(int[] monthData, int targetSteps) {
            Converter converter = new Converter();

            totalSteps = Arrays.stream(monthData).sum();
            walkedDistance = converter.stepsToKilometers(totalSteps);
            totalKilocalories = converter.stepsToKilocalories(totalSteps);

            OptionalDouble averageStepsOptional = Arrays.stream(monthData).average();
            averageStepsCount = averageStepsOptional.isPresent() ? averageStepsOptional.getAsDouble() : 0.0;

            OptionalInt maxStepsOptional = Arrays.stream(monthData).max();
            maxSteps = maxStepsOptional.isPresent() ? maxStepsOptional.getAsInt() : 0;

            bestStreakSize = 0;
            int currentStreakSize = 0;
            for (int monthDatum : monthData) {
                if (monthDatum > targetSteps) {
                    currentStreakSize = currentStreakSize + 1;
                } else {
                    bestStreakSize = Math.max(currentStreakSize, bestStreakSize);
                }
            }
        }

        public Boolean monthDataIsEmpty() {
            return totalSteps == 0;
        }

        public String getDailyStepsString(int[] monthData) {
            List<String> dailyStepsList = new ArrayList<>();
            for (int i = 0; i < monthData.length; i++) {
                if (monthData[i] == 0) {
                    continue;
                }
                String dailyStepsString = (i + 1) + " день: " + monthData[i];
                dailyStepsList.add(dailyStepsString);
            }
            return String.join(", ", dailyStepsList);
        }
    }
}