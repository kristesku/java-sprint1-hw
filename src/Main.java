import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int minCommand = 0;
        int maxCommand = 3;
        int command;

        StepTracker stepTracker = new StepTracker();

        System.out.println("\n# StepTracker v0.1\n");
        while (true) {
            printMenu();
            String message = "Введите номер команды (" + minCommand + "-" + maxCommand + "): ";
            command = inputInt(message, minCommand, maxCommand);
            if (command == 0) {
                System.out.println("\nЗавершение программы. Всего доброго!");
                return;
            } else if (command == 1) {
                addWalkedStepsData(stepTracker);
            } else if (command == 2) {
                showMonthStatistics(stepTracker);
            } else if (command == 3) {
                setTargetSteps(stepTracker);
            }
        }
    }

    private static void printMenu() {
        System.out.println("-----------------------------------------------");
        System.out.println("1 - Ввести количество пройденных шагов за день.");
        System.out.println("2 - Вывести статистику за месяц.");
        System.out.println("3 - Изменить целевое количество шагов в день.");
        System.out.println("0 - Выход.");
        System.out.println("-----------------------------------------------");
    }

    private static int inputInt(String message, int minValue, int maxValue) {
        int inputValue;
        while (true) {
            System.out.print(message);
            Scanner scanner = new Scanner(System.in);
            try {
                inputValue = scanner.nextInt();
                if ((inputValue >= minValue) & (inputValue <= maxValue)) {
                    return inputValue;
                } else {
                    System.out.println("Введено некорректное значение. Пожалуйста, повторите ввод.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Введено некорректное значение. Пожалуйста, повторите ввод.");
            }
        }
    }

    private static void addWalkedStepsData(StepTracker stepTracker) {
        System.out.println("\n## Ввод количества пройденных шагов за день.");

        int yearIndex = getYearIndex();
        int monthIndex = getMonthIndex();

        int minDay = 1;
        int daysInMonth = stepTracker.getNumberOfDaysInMonth(yearIndex, monthIndex);
        String message = "Введите день (" + minDay + "-" + daysInMonth + "): ";
        int dayIndex = inputInt(message, minDay, daysInMonth) - 1;

        int minSteps = 1;
        int maxSteps = Integer.MAX_VALUE;
        int stepsWalked = inputInt("Введите количество пройденных шагов: ", minSteps, maxSteps);

        stepTracker.addWalkedStepsData(yearIndex, monthIndex, dayIndex, daysInMonth, stepsWalked);

        String formattedDate = stepTracker.getFormattedDate(yearIndex, monthIndex, dayIndex);
        System.out.println("\nЗа " + formattedDate + " пройдено " + stepsWalked + " шагов.\n");
    }

    private static int getMonthIndex() {
        int minMonth = 1;
        int maxMonth = 12;
        String message = "Введите месяц (" + minMonth + "-" + maxMonth + "): ";
        return inputInt(message, minMonth, maxMonth) - 1;
    }

    private static int getYearIndex() {
        int minYear = 0;
        int maxYear = 3000;
        String message = "Введите год (" + minYear + "-" + maxYear + "): ";
        return inputInt(message, minYear, maxYear) - 1;
    }

    private static void showMonthStatistics(StepTracker stepTracker) {
        System.out.println("\n## Получение статистики за месяц по пройденным шагам.");

        int yearIndex = getYearIndex();
        int monthIndex = getMonthIndex();

        String stepStatistics = stepTracker.getStepStatistics(yearIndex, monthIndex);
        System.out.println("\n" + stepStatistics + "\n");
    }

    private static void setTargetSteps(StepTracker stepTracker) {
        int minTarget = 1;
        int maxTarget = Integer.MAX_VALUE;

        System.out.println("\n## Получение статистики за месяц по пройденным шагам.");
        String message = "Введите новое целевое количество шагов в день (" + minTarget + "-" + maxTarget + "): ";
        int newTarget = inputInt(message, minTarget, maxTarget);

        stepTracker.setTargetSteps(newTarget);
        System.out.println("\nНовая цель — " + newTarget + " шагов в день\n");
    }
}
