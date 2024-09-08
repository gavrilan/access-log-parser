public class UserAgent {

    private final String osName;
    private final String browserName;

    public String getOsName() {
        return osName;
    }

    public String getBrowserName() {
        return browserName;
    }

    public UserAgent(String userAgentStr) {
        if (userAgentStr.length() > 5) {
            String osNameTail = userAgentStr.substring(userAgentStr.indexOf("(") + 1);
            if (osNameTail.indexOf(")") >= 0) {
                // find osname
                String osNames = osNameTail.substring(0, osNameTail.indexOf(")"));
                String[] parts = osNames.split(";");
                if (parts.length >= 1) {
                    osName = parts[0];
                } else {
                    osName = null;
                }
                // find browser
                String browserTail = osNameTail.substring(osNameTail.indexOf(")") + 1);
                if (browserTail.indexOf(")") >= 0) {
                    browserTail = browserTail.substring(browserTail.indexOf(")") + 1);
                    if (browserTail.length() > 0) {
                        int ver = browserTail.indexOf("/");
                        if (ver > 0) {
                            browserName = browserTail.substring(1, ver);
                        } else {
                            browserName = browserTail.substring(1, browserTail.indexOf(" "));
                        }
                    } else {
                        browserName = null;
                    }
                } else {
                    browserName = null;
                }
            } else {
                osName = null;
                browserName = null;
            }
        } else {
            osName = null;
            browserName = null;
        }
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "osName='" + osName + '\'' +
                ", browserName='" + browserName + '\'' +
                '}';
    }
}
