package relampagorojo93.caketwitch.relautils.shared.webqueries;

import relampagorojo93.caketwitch.jsonlib.JSONElement;
import relampagorojo93.caketwitch.jsonlib.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class WebQuery {
    public static JSONElement queryToJSON(String url, WebMethod method) throws Exception {
        return queryToJSON(url, method, (JSONElement) null);
    }

    public static JSONElement queryToJSON(String url, WebMethod method, Map<String, String> headers) throws Exception {
        return queryToJSON(url, method, headers, null);
    }

    public static JSONElement queryToJSON(String url, WebMethod method, JSONElement default_element) throws Exception {
        return queryToJSON(url, method, new HashMap<>(), default_element);
    }

    public static JSONElement queryToJSON(String url, WebMethod method, Map<String, String> headers,
                                          JSONElement default_element) throws Exception {
        URL urlobject = new URL(url);
        HttpURLConnection http = (HttpURLConnection) urlobject.openConnection();
		for (Entry<String, String> entry : headers.entrySet()) {
			http.setRequestProperty(entry.getKey(), entry.getValue());
		}
        http.setReadTimeout(2000);
        http.setRequestMethod(method.name());
        http.setDoInput(true);
        BufferedReader inreader = new BufferedReader(new InputStreamReader(http.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String input = null;
        try {
			while ((input = inreader.readLine()) != null) {
				sb.append(input);
			}
        }
        catch (SocketTimeoutException e) {
        }
        http.disconnect();
        return JSONParser.parseJson(sb.toString());
    }

    public static ClientResponse queryToClientResponse(String url, WebMethod method) throws Exception {
        return queryToClientResponse(url, method, new HashMap<>());
    }

    public static ClientResponse queryToClientResponse(String url, WebMethod method, Map<String, String> headers) throws Exception {
        URL urlobject = new URL(url);
        HttpURLConnection http = (HttpURLConnection) urlobject.openConnection();
		for (Entry<String, String> entry : headers.entrySet()) {
			http.setRequestProperty(entry.getKey(), entry.getValue());
		}
        http.setReadTimeout(2000);
        http.setRequestMethod(method.name());
        http.setDoInput(true);
        BufferedReader inreader = new BufferedReader(new InputStreamReader(http.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String input = null;
        try {
			while ((input = inreader.readLine()) != null) {
				sb.append(input);
			}
        }
        catch (SocketTimeoutException e) {
        }
        http.disconnect();
        HashMap<String, String> parameters = new HashMap<>();
		if (http.getURL().getQuery() != null) {
			for (String query : http.getURL().getQuery().split("&")) {
				if (query.isEmpty() || !query.contains("=")) {
					continue;
				}
				String[] split = query.split("=", 2);
				parameters.put(split[0], split[1]);
			}
		}
        return new ClientResponse(sb.toString(), http.getURL().getPath(), parameters);
    }

    public static int queryToResponse(String url, WebMethod method, Map<String, String> headers) {
        try {
            URL urlobject = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlobject.openConnection();
			for (Entry<String, String> entry : headers.entrySet()) {
				http.setRequestProperty(entry.getKey(), entry.getValue());
			}
            http.setReadTimeout(2000);
            http.setRequestMethod(method.name());
            http.setDoInput(true);
            return http.getResponseCode();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static ClientResponse inputToResponse(InputStream stream) {
        String content = null, path = null;
        ClientResponse.ResponseVerb verb = null;
        HashMap<String, String> headers = new HashMap<>(), parameters = new HashMap<>();
        int line = 0;
        BufferedReader inreader = new BufferedReader(new InputStreamReader(stream));
        String input = null;
        try {
            while ((input = inreader.readLine()) != null) {
                if (line == 0) {
                    String[] split = input.split(" ", 3);
                    try {
                        verb = ClientResponse.ResponseVerb.valueOf(split[0]);
                    }
                    catch (Exception e) {
                        return null;
                    }
                    String[] spath = split[1].split("\\?");
                    path = spath[0];
					if (spath.length > 1) {
						for (String parameter : spath[1].split("&")) {
							String[] sparameter = parameter.split("=");
							parameters.put(sparameter[0], sparameter[1]);
						}
					}
                }
                else {
                    if (input.isEmpty()) {
                        int length = 0;
						for (Entry<String, String> entry : headers.entrySet()) {
							if (entry.getKey().equalsIgnoreCase("content-length")) {
								length = Integer.parseInt(entry.getValue());
								break;
							}
						}
                        content = "";
						while (length-- != 0) {
							content += (char) inreader.read();
						}
                        return new ClientResponse(content, path, parameters, verb, headers);
                    }
                    else {
                        if (input.contains(":")) {
                            String[] split = input.split(":", 2);
                            headers.put(split[0].toLowerCase(), split[1].trim());
                        }
                    }
                }
                line++;
            }
            inreader.close();
        }
        catch (Exception e) {
        }
        return null;
    }

    public static class ClientResponse {
        private String content, path;
        private ResponseVerb responseverb;
        private HashMap<String, String> headers, parameters;

        public ClientResponse(String content, String path, HashMap<String, String> parameters) {
            this(content, path, parameters, ResponseVerb.GET);
        }

        public ClientResponse(String content, String path, HashMap<String, String> parameters,
                              ResponseVerb responseverb) {
            this(content, path, parameters, responseverb, new HashMap<>());
        }

        public ClientResponse(String content, String path, HashMap<String, String> parameters,
                              ResponseVerb responseverb, HashMap<String, String> headers) {
            this.content = content;
            this.path = path;
            this.parameters = parameters;
            this.responseverb = responseverb;
            this.headers = headers;
        }

        public String getContent() {
            return content;
        }

        public String getPath() {
            return path;
        }

        public String getParameter(String key) {
            return parameters.get(key);
        }

        public Set<String> getParameterKeys() {
            return parameters.keySet();
        }

        public ResponseVerb getResponseVerb() {
            return responseverb;
        }

        public String getHeader(String key) {
            return headers.get(key);
        }

        public Set<String> getHeaderKeys() {
            return headers.keySet();
        }

        public static enum ResponseVerb {
            GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE, PATCH;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder().append("Path: " + path + "\r\n")
                .append("Content: " + content + "\r\n").append("Response Verb: " + responseverb.name() + "\r\n");
            sb.append("Parameters:\r\n");
			for (Entry<String, String> parameter : parameters.entrySet()) {
				sb.append("  " + parameter.getKey() + " -> " + parameter.getValue() + "\r\n");
			}
            sb.append("Headers:\r\n");
			for (Entry<String, String> header : headers.entrySet()) {
				sb.append("  " + header.getKey() + " -> " + header.getValue() + "\r\n");
			}
            return sb.toString();
        }
    }
}
