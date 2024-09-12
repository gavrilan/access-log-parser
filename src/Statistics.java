import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {

    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private HashSet<String> pageAddresses;
    private HashMap<String, Integer> countOS;

    public Statistics() {
        totalTraffic = 0;
        minTime = null;
        maxTime = null;
        pageAddresses = new HashSet<>();
        countOS = new HashMap<>();
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
        if (logEntry.getResponseCode() == 200) {
            pageAddresses.add(logEntry.getPath());
        }
        if (logEntry.getUserAgent() != null) {
            String osName = logEntry.getUserAgent().getOsName();
            if (osName != null && !osName.isEmpty()) {
                Integer cnt = countOS.get(osName);
                if (cnt != null) {
                    cnt++;
                    countOS.put(osName, cnt);
                } else {
                    countOS.put(osName, 1);
                }
            }
        }

    }

    public HashMap<String, Double> getOSPercent() {
        HashMap<String, Double> res = new HashMap<>();
        if (countOS.size() > 0) {
            Double allCntOsName = 0.0;
            for (HashMap.Entry<String, Integer> entry : countOS.entrySet()) {
                allCntOsName += entry.getValue();
            }
            for (HashMap.Entry<String, Integer> entry : countOS.entrySet()) {
                String osName = entry.getKey();
                Integer osNameCnt = entry.getValue();
                Double percent = osNameCnt / allCntOsName;
                res.put(osName, percent);
            }
        }
        return res;
    }

    public HashMap<String, Integer> getCountOS() {
        return countOS;
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

    public HashSet<String> getPageAddresses() {
        return pageAddresses;
    }
}
