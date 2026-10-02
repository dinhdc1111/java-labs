package task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        selfCheck();

        Path file = Path.of(args.length > 0 ? args[0] : "data.txt");
        List<String> lines;
        try {
            lines = Files.readAllLines(file);
        } catch (IOException e) {
            System.err.println("Không đọc được file " + file.toAbsolutePath() + ": " + e);
            return;
        }

        List<Task> tasks = parse(lines);
        System.out.println("Danh sách Task hợp lệ:");
        tasks.forEach(t -> System.out.println("  " + t));

        LocalTime[] gap = maxGap(tasks);
        if (gap == null) {
            System.out.println("Không có khoảng thời gian trễ.");
        } else {
            System.out.printf("Thời gian trễ lớn nhất: %d phút (%s -> %s)%n",
                    Duration.between(gap[0], gap[1]).toMinutes(), gap[0], gap[1]);
        }
    }

    static List<Task> parse(List<String> lines) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
            String[] p = line.split("\\s+");
            try {
                if (p.length != 4) throw new IllegalArgumentException("cần 4 cột, có " + p.length);
                LocalTime start = LocalTime.parse(p[2]);
                LocalTime end = LocalTime.parse(p[3]);
                if (!end.isAfter(start)) throw new IllegalArgumentException("giờ kết thúc phải sau giờ bắt đầu");
                tasks.add(new Task(Integer.parseInt(p[0]), p[1], start, end));
            } catch (DateTimeParseException e) {
                warn(i, line, "sai định dạng giờ HH:mm");
            } catch (IllegalArgumentException e) { // gồm cả NumberFormatException
                warn(i, line, e instanceof NumberFormatException ? "mã công việc không phải số" : e.getMessage());
            }
        }
        return tasks;
    }

    private static void warn(int i, String line, String reason) {
        System.err.printf("Bỏ qua dòng %d \"%s\": %s%n", i + 1, line, reason);
    }

    /** Trả về [bắt đầu, kết thúc] của khoảng trễ dài nhất, hoặc null nếu không có. */
    static LocalTime[] maxGap(List<Task> tasks) {
        List<Task> sorted = new ArrayList<>(tasks);
        sorted.sort(Comparator.comparing(Task::getStart));
        LocalTime[] best = null;
        LocalTime maxEnd = null;
        for (Task t : sorted) {
            if (maxEnd != null && t.getStart().isAfter(maxEnd)
                    && (best == null || Duration.between(maxEnd, t.getStart()).compareTo(Duration.between(best[0], best[1])) > 0)) {
                best = new LocalTime[]{maxEnd, t.getStart()};
            }
            if (maxEnd == null || t.getEnd().isAfter(maxEnd)) maxEnd = t.getEnd();
        }
        return best;
    }

    private static void selfCheck() {
        List<Task> sample = parse(List.of("1 A 10:30 12:00", "2 B 12:30 14:15", "3 C 15:10 18:00", "4 D 15:35 16:00"));
        LocalTime[] g = maxGap(sample);
        assert g != null && Duration.between(g[0], g[1]).toMinutes() == 55;
        assert maxGap(parse(List.of("1 A 10:00 12:00", "2 B 11:00 13:00"))) == null;
    }
}
