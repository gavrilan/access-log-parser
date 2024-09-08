import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {
    private final String ipAddr;
    private final String dateQuery;
    private final LocalDateTime time;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int responseSize;
    private final String referer;
    private final String userAgentStr;
    private final UserAgent userAgent;
    private final String botName;

    public String getIpAddr() {
        return ipAddr;
    }

    public String getDateQuery() {
        return dateQuery;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public String getUserAgentStr() {
        return userAgentStr;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    public String getBotName() {
        return botName;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "ipAddr='" + ipAddr + '\'' +
                ", dateQuery='" + dateQuery + '\'' +
                ", time=" + time +
                ", method=" + method +
                ", path='" + path + '\'' +
                ", responseCode=" + responseCode +
                ", responseSize=" + responseSize +
                ", referer='" + referer + '\'' +
                ", userAgentStr='" + userAgentStr + '\'' +
                ", userAgent=" + userAgent +
                ", botName='" + botName + '\'' +
                '}';
    }

    public LogEntry(String logStr) {
        int prev = 0;
        int cur;
        int len = logStr.length();
        String tail = logStr;
        // Выделение ip
        cur = tail.indexOf(" ");
        if (cur >= 0) {
            String ip = tail.substring(0, cur);
            ipAddr = ip;
            tail = tail.substring(cur + 1);
        } else {
            ipAddr = null;
        }
        // Пропуск 2 -
        cur = tail.indexOf(" ");
        if (cur >= 0) {
            tail = tail.substring(cur + 1);
        }
        cur = tail.indexOf(" ");
        if (cur >= 0) {
            tail = tail.substring(cur + 1);
        }
        // Выделение дат
        cur = tail.indexOf("]");
        if (cur >= 0) {
            String dateQuery = tail.substring(1, cur);
            this.dateQuery = dateQuery;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss xxxx");
            this.time = LocalDateTime.parse(dateQuery,formatter);
            tail = tail.substring(cur + 2);
        } else {
            this.dateQuery = null;
            this.time = null;
        }
        // Выделение метода
        tail = tail.substring(1);
        cur = tail.indexOf("\"");
        if (cur >= 0) {
            String fullName = tail.substring(0, cur);
            String methodName = fullName.substring(0, fullName.indexOf(" "));
            this.method = HttpMethod.valueOf(methodName);
            String path = fullName.substring(fullName.indexOf(" ") + 1);
            this.path = path;
            tail = tail.substring(cur + 2);
        } else {
            this.method = HttpMethod.NONE;
            this.path = null;
        }
        // Выделение codeAnswer
        cur = tail.indexOf(" ");
        if (cur >= 0) {
            String codeAnswer = tail.substring(0, cur);
            this.responseCode = Integer.parseInt(codeAnswer);
            tail = tail.substring(cur + 1);
        } else {
            this.responseCode = 0;
        }

        // Выделение sizeAnswer
        cur = tail.indexOf(" ");
        if (cur >= 0) {
            String sizeAnswer = tail.substring(0, cur);
            this.responseSize = Integer.parseInt(sizeAnswer);
            tail = tail.substring(cur + 1);
        } else {
            this.responseSize = 0;
        }
        // Выделение referer
        tail = tail.substring(1);
        cur = tail.indexOf("\"");
        if (cur >= 0) {
            String referer = tail.substring(0, cur);
            this.referer = referer;
            tail = tail.substring(cur + 2);
        } else {
            this.referer = null;
        }
        // Выделение userAgent
        tail = tail.substring(1);
        cur = tail.indexOf("\"");
        if (cur >= 0) {
            String userAgent = tail.substring(0, cur);
            this.userAgentStr = userAgent;
            this.userAgent = new UserAgent(userAgent);
            if (userAgent.length() > 5) {
                String botNameTail = userAgent.substring(userAgent.indexOf("(") + 1);
                if (botNameTail.indexOf(")") >= 0) {
                    botNameTail = botNameTail.substring(0, botNameTail.indexOf(")"));
                    String[] parts = botNameTail.split(";");
                    if (parts.length >= 2) {
                        String botName = parts[1];
                        int ver = botName.indexOf("/");
                        if (ver > 0) {
                            this.botName = botName.substring(1, ver);
                        } else {
                            this.botName = botName.substring(1);
                        }
                    } else {
                        this.botName = null;
                    }
                } else {
                    this.botName = null;
                }
            } else {
                this.botName = null;
            }
        } else {
            this.userAgentStr = null;
            this.userAgent = null;
            this.botName = null;
        }

    }


}
