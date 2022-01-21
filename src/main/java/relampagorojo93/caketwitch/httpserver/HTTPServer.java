package relampagorojo93.caketwitch.httpserver;

import relampagorojo93.caketwitch.relautils.shared.webqueries.WebQuery;
import relampagorojo93.caketwitch.spigotthreads.ThreadManager;
import relampagorojo93.caketwitch.spigotthreads.objects.Thread;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Set;

public class HTTPServer extends Thread implements Thread.Runnable, Thread.CallBack {

    private int port = 1;
    private ServerSocketFactory factory = null;
    private ServerSocket server = null;
    private final ThreadManager tmanager = new ThreadManager();

    public HTTPServer() {
        setRunnable(this);
        setCallBack(this);

        this.port = 80;
        factory = ServerSocketFactory.getDefault();
    }

    public HTTPServer(File privkey, File fullchain) throws Exception {
        this(privkey, fullchain, "");
    }

    public HTTPServer(File privkey, File fullchain, String password) throws Exception {
        setRunnable(this);
        setCallBack(this);

        this.port = 443;
        SSLContext context = createSSLContext(privkey, fullchain, password);
        if (context != null) {
            this.factory = context.getServerSocketFactory();
        }
        else {
            throw new Exception("Not able to create a SSLContext!");
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    private SSLContext createSSLContext(File keyFile, File certFile, String password) {
        try {
            PrivateKey privateKey = generateKey(keyFile);
            if (privateKey == null) {
                return null;
            }
            Certificate cert = generateCertificate(certFile);
            if (cert == null) {
                return null;
            }

            KeyStore store = KeyStore.getInstance("JKS");
            store.load(null);
            store.setCertificateEntry("certificate", cert);
            store.setKeyEntry("private-key", privateKey, password.toCharArray(), new Certificate[]{cert});

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(store, password.toCharArray());

            SSLContext context = SSLContext.getInstance("SSL");
            context.init(kmf.getKeyManagers(), null, SecureRandom.getInstanceStrong());

            return context;
        }
        catch (Exception e) {
            return null;
        }
    }

    private PrivateKey generateKey(File privkey) {
        try {
            InputStream ikey = new FileInputStream(privkey);
            byte[] bytes = new byte[ikey.available()];
            ikey.read(bytes);
            String skey = new String(bytes, Charset.defaultCharset());
            ikey.close();
            skey = skey.replaceAll("-----.*-----", "").replaceAll(System.lineSeparator(), "");

            KeyFactory kf = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(skey));
            return kf.generatePrivate(keySpec);
        }
        catch (Exception e) {
            return null;
        }
    }

    private Certificate generateCertificate(File fullchain) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return cf.generateCertificate(new FileInputStream(fullchain));
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public void run() {
        try {
            server = factory.createServerSocket(port);
            while (!java.lang.Thread.interrupted()) {
                Socket socket = server.accept();
                tmanager.registerThread(new Thread(new Thread.Runnable() {

                    @Override
                    public void run() {
                        if (socket instanceof SSLSocket) {
                            SSLSocket sslsocket = (SSLSocket) socket;
                            sslsocket.addHandshakeCompletedListener(event -> processSocket(event.getSocket()));
                            try {
                                sslsocket.startHandshake();
                            }
                            catch (Exception e) {
                                onError(e);
                            }
                        }
                        else {
                            processSocket(socket);
                        }
                    }

                    @Override
                    public void output(Object output) {
                    }
                })).startSecure();
            }
        }
        catch (Exception e) {
            onError(e);
        }
    }

    private void processSocket(Socket socket) {
        try {
            socket.setSoTimeout(5000);
            WebQuery.ClientResponse cresponse = WebQuery.inputToResponse(socket.getInputStream());
            ServerResponse sresponse = new ServerResponse("", ServerResponse.ResponseCode.BAD_REQUEST);
            if (cresponse != null) {
                Page page = notfoundpage;
                if (pages.containsKey(cresponse.getPath())) {
                    page = pages.get(cresponse.getPath());
                }
                if (page != null) {
                    sresponse = page.getResponse(cresponse);
                }
                if (sresponse == null) {
                    sresponse = NotFoundResponse.RESPONSE;
                }
            }
            else {
                socket.close();
                return;
            }
            BufferedWriter writern = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));
            writern.write(sresponse.toString());
            writern.flush();
            socket.close();
        }
        catch (Exception e) {
            if (!(e instanceof InterruptedException)) {
                onError(e);
            }
        }
    }

    @Override
    public void onFinish() {
        try {
            if (server != null) {
                server.close();
            }
            server = null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        tmanager.unregisterThreads();
    }

    @Override
    public void onInterrupt() {
        try {
            if (server != null) {
                server.close();
            }
        }
        catch (Exception e) {
        }
    }

    @Override
    public void onError(Exception ex) {
        if (ex instanceof BindException) {
            System.out.println("<<HTTPServer>> Can't bind to port " + port + "!");
        }
        //else if (ex instanceof SocketException || ex instanceof SSLHandshakeException || ex instanceof EOFException)
        //System.out.println("<<HTTPServer>> Socket closed/issue! <If you see this frequently, it's not a good signal!>");
    }

    @Override
    public void onInput(Object input) {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void output(Object output) {
    }

    private final HashMap<String, Page> pages = new HashMap<>();
    private Page notfoundpage = NotFoundPage.PAGE;

    public static class ServerResponse {
        private final String page;
        private final ResponseCode responsecode;
        private final HashMap<String, String> headers;

        public ServerResponse() {
            this("");
        }

        public ServerResponse(String page) {
            this(page, ResponseCode.OK);
        }

        public ServerResponse(String page, ResponseCode responsecode) {
            this(page, responsecode, new HashMap<>());
        }

        public ServerResponse(String page, ResponseCode responsecode, HashMap<String, String> headers) {
            this.page = page;
            this.responsecode = responsecode;
            this.headers = headers;
        }

        public String getPage() {
            return page;
        }

        public ResponseCode getResponseCode() {
            return responsecode;
        }

        public String getHeader(String key) {
            return headers.get(key);
        }

        public Set<String> getHeaderKeys() {
            return headers.keySet();
        }

        public enum ResponseCode {
            OK(200, "HTTP/1.0 200 OK"),
            MOVED_PERMANENTLY(301, "HTTP/1.0 301 Moved Permanently"),
            TEMPORARY_REDIRECT(301, "HTTP/1.0 307 Temporary Redirect"),
            BAD_REQUEST(400, "HTTP/1.0 400 Bad Request"),
            FORBIDDEN(403, "HTTP/1.0 403 Forbidden"),
            NOT_FOUND(404, "HTTP/1.0 404 Not Found"),
            SERVICE_UNAVAILABLE(503, "HTTP/1.0 503 Service Unavailable");
            private int code = -1;
            private String header = "";

            ResponseCode(int code, String header) {
                this.code = code;
                this.header = header;
            }

            public int getCode() {
                return code;
            }

            public String getHeader() {
                return header;
            }

            public static ResponseCode getByCode(int code) {
                for (ResponseCode rcode : values()) {
                    if (rcode.code == code) {
                        return rcode;
                    }
                }
                return null;
            }

        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(responsecode.getHeader() + "\r\n");
            for (String key : getHeaderKeys()) {
                if (!key.equalsIgnoreCase("Content-Length")) {
                    sb.append(key + ": " + getHeader(key) + "\r\n");
                }
            }
            return sb.append("Content-Length: " + getPage().length() + "\r\n\r\n" + getPage()).toString();
        }
    }

    public static class NotFoundResponse extends ServerResponse {
        public NotFoundResponse() {
            super("", ResponseCode.NOT_FOUND);
        }

        public static NotFoundResponse RESPONSE = new NotFoundResponse();
    }

    public interface Page {
        ServerResponse getResponse(WebQuery.ClientResponse response);
    }

    public static class NotFoundPage implements Page {
        @Override
        public ServerResponse getResponse(WebQuery.ClientResponse response) {
            return NotFoundResponse.RESPONSE;
        }

        public static NotFoundPage PAGE = new NotFoundPage();
    }

    public void removePage(String path) {
        this.pages.remove(path);
    }

    public void setPage(String path, Page page) {
        this.pages.put(path, page);
    }

    public void setNotFoundPage(Page notfoundpage) {
        this.notfoundpage = notfoundpage;
    }
}