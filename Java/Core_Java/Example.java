import java.util.Scanner;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.DayOfWeek;

public class Example {
    public static void main(String[] args) {
        example4();
    }

    record Point(double x, double y) {
        public double size() {
            return Math.hypot(x, y);
        }
    }

    private static final void example4() {
        Point point = new Point(3.0, 4.0);
        System.out.printf("%s %s\n", point, point.size());
        // it doesn't generate setters for it, only getters.
        // System.out.printf("%s %s\n", point.x(3), point.x(3).size());
    }


    private static final void example3() {
        // Final stringbuilder doesn't act as kind of static stringbuilder clearly.
        MutFinal mt = new MutFinal();
        MutFinal mt2 = new MutFinal();
        mt.append("mt1");
        mt2.append("mt2");
        System.out.println(mt2.eval());
    }
    // https://stackoverflow.com/questions/16731240/what-is-a-reasonable-order-of-java-modifiers-abstract-final-public-static-e
    // This post is about what is the best way to order the java modifiers.
    private static final void example1() {
        // This prints relative to where the shell session started
        System.out.printf("Hello from: %s\n", System.getProperty("user.dir"));
        // Concatenation is inefficient, use string builder
        StringBuilder builder = new StringBuilder();
        builder.append("0123");
        builder.append(456);
        builder.setCharAt(6, 'A');
        builder.insert(2, "hi");
        System.out.printf("String: %s\n", builder.toString());

        Scanner scanner = new Scanner(System.in);

        System.out.printf("Read until newline: ");
        String s = scanner.nextLine();
        System.out.printf("String in: %s\n", s);

        System.out.printf("Read until space: ");
        s = scanner.next();
        System.out.printf("String in: %s\n", s);

        // Rounding off error bigdecimal
        Float f = 0.1f;
        BigDecimal dec = new BigDecimal("0.1");
        System.out.printf("0.1 in float: %.32f\n", f);
        System.out.printf("0.1 bigdecimal: %.32f\n", dec);
    }

    private static final void example2() {
            LocalDate ld = LocalDate.now();
            String[] daysOfTheWeek = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
            System.out.printf("%s\n", String.join(" ", daysOfTheWeek));
            var today = ld.getDayOfMonth();

            var month = ld.getMonthValue();
            ld = ld.minusDays(today - 1);
            var weekday = ld.getDayOfWeek();

            for (int i = 1; i < weekday.getValue(); i++) {
                System.out.print("    ");
            }
            while (ld.getMonthValue() == month) {
            System.out.printf("%3d", ld.getDayOfMonth());

            if (ld.getDayOfMonth() == today) {
                    System.out.print("*");
            } else {
                    System.out.print(" ");
            }

            if (ld.getDayOfWeek() == DayOfWeek.SUNDAY)
                System.out.print("\n");

            ld = ld.plusDays(1);
            }
            System.out.print("\n");
    }
}

class MutFinal {
    private final StringBuilder eval = new StringBuilder();

    public void append(String s) {
        eval.append(s);
    }

    public String eval() {
        return (String) eval.toString();
    }
}
