import java.time.Duration;
import java.time.LocalDateTime;

public class Statistics {

    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    public Statistics() {
        totalTraffic = 0;
        minTime = null;
        maxTime = null;
    }

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();
        if (minTime == null) {
            minTime = logEntry.getTime();
        } else {
            if (minTime.isAfter(logEntry.getTime())) {
                minTime = logEntry.getTime();
            }
        }
        if (maxTime == null) {
            maxTime = logEntry.getTime();
        } else {
            if (maxTime.isBefore(logEntry.getTime())) {
                maxTime = logEntry.getTime();
            }
        }
    }

    public double getTrafficRate() {
        double result = 0;
        Duration duration = Duration.between(minTime, maxTime);
        long hours = duration.toHours();
        if (hours > 0) {
            result = totalTraffic / hours;
        }
        return result;
    }

    public long getTotalTraffic() {
        return totalTraffic;
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }
}
