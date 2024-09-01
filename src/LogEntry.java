public class LogEntry {
    private String ipAddress;
    private String dateQuery;
    private String methodName;
    private String path;
    private String codeAnswer;
    private String sizeAnswer;
    private String referer;
    private String userAgent;
    private String botName;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDateQuery() {
        return dateQuery;
    }

    public void setDateQuery(String dateQuery) {
        this.dateQuery = dateQuery;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCodeAnswer() {
        return codeAnswer;
    }

    public void setCodeAnswer(String codeAnswer) {
        this.codeAnswer = codeAnswer;
    }

    public String getSizeAnswer() {
        return sizeAnswer;
    }

    public void setSizeAnswer(String sizeAnswer) {
        this.sizeAnswer = sizeAnswer;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "ipAddress='" + ipAddress + '\'' +
                ", dateQuery='" + dateQuery + '\'' +
                ", methodName='" + methodName + '\'' +
                ", path='" + path + '\'' +
                ", codeAnswer='" + codeAnswer + '\'' +
                ", sizeAnswer='" + sizeAnswer + '\'' +
                ", referer='" + referer + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", botName='" + botName + '\'' +
                '}';
    }

    public void parseStr(String logStr) {
        int prev = 0;
        int cur;
        int len = logStr.length();
        String tail = logStr;
        // Выделение ip
        cur = tail.indexOf(" ");
        if (cur >= 0) {
            String ip = tail.substring(0, cur);
            setIpAddress(ip);
            tail = tail.substring(cur + 1);
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
            setDateQuery(dateQuery);
            tail = tail.substring(cur + 2);
        }
        // Выделение метода
        tail = tail.substring(1);
        cur = tail.indexOf("\"");
        if (cur >= 0) {
            String fullName = tail.substring(0, cur);
            String methodName = fullName.substring(0, fullName.indexOf(" "));
            setMethodName(methodName);
            String path = fullName.substring(fullName.indexOf(" ") + 1);
            setPath(path);
            tail = tail.substring(cur + 2);
        }
        // Выделение codeAnswer
        cur = tail.indexOf(" ");
        if (cur >= 0) {
            String codeAnswer = tail.substring(0, cur);
            setCodeAnswer(codeAnswer);
            tail = tail.substring(cur + 1);
        }
        // Выделение sizeAnswer
        cur = tail.indexOf(" ");
        if (cur >= 0) {
            String sizeAnswer = tail.substring(0, cur);
            setSizeAnswer(sizeAnswer);
            tail = tail.substring(cur + 1);
        }
        // Выделение referer
        tail = tail.substring(1);
        cur = tail.indexOf("\"");
        if (cur >= 0) {
            String referer = tail.substring(0, cur);
            setReferer(referer);
            tail = tail.substring(cur + 2);
        }
        // Выделение userAgent
        tail = tail.substring(1);
        cur = tail.indexOf("\"");
        if (cur >= 0) {
            String userAgent = tail.substring(0, cur);
            setUserAgent(userAgent);
            if (userAgent.length() > 5) {
                String botNameTail = userAgent.substring(userAgent.indexOf("(") + 1);
                if (botNameTail.indexOf(")") >= 0) {
                    botNameTail = botNameTail.substring(0, botNameTail.indexOf(")"));
                    String[] parts = botNameTail.split(";");
                    if (parts.length >= 2) {
                        String botName = parts[1];
                        int ver = botName.indexOf("/");
                        if (ver > 0) {
                            setBotName(botName.substring(1, ver));
                        } else {
                            setBotName(botName.substring(1));
                        }
                    }
                }
            }
        }

    }


}
