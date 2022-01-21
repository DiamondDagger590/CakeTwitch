package relampagorojo93.caketwitch.ircbot;

import relampagorojo93.caketwitch.spigotthreads.objects.Thread;

import javax.net.SocketFactory;
import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.List;

public class IRCBot extends Thread implements Thread.Runnable, Thread.CallBack {

    private String host;
    private int port, autoreconnect = 0;
    private IRCBotListener listener;
    private IRCSocket socket = null;
    private SocketFactory socketfactory;

    public IRCBot(String host, int port, SocketFactory socketfactory, IRCBotListener listener) {
        this.socketfactory = socketfactory;
        this.host = host;
        this.port = port;
        this.listener = listener;
        setRunnable(this);
        setCallBack(this);
    }

    public Status getStatus() {
		if (isRunning()) {
			return Status.RUNNING;
		}
		else {
			return Status.STOPPED;
		}
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public IRCBotListener getListener() {
        return listener;
    }

    public boolean sendCommands(String... commands) {
		if (socket == null || socket.getWriter() == null) {
			return false;
		}
		if (commands.length == 0) {
			return true;
		}
        try {
			for (int i = 0; i < commands.length; i++) {
				socket.getWriter().write(commands[i] + "\r\n");
			}
            socket.getWriter().flush();
            return true;
        }
        catch (Exception e) {
        }
        return false;
    }

    public void setAutoReconnect(int attempts) {
        this.autoreconnect = attempts;
    }

    // ---------------------------------------------------------------------//
    // Startup commands methods
    // ---------------------------------------------------------------------//

    private List<String> startupcmds = new ArrayList<>();

    public void setStartupCommands(List<String> startupcmds) {
        this.startupcmds = startupcmds;
    }

    public List<String> getStartupCommands() {
        return startupcmds;
    }

    // ---------------------------------------------------------------------//
    // Callback methods
    // ---------------------------------------------------------------------//

    @Override
    public void onError(Exception ex) {
		if (this.listener != null) {
			this.listener.onError(this, ex);
		}
    }

    @Override
    public void onInput(Object input) {
		if (this.listener != null) {
			this.listener.onInputMessage(this, (String) input);
		}
    }

    @Override
    public void onInterrupt() {
		if (socket != null) {
			socket.close();
		}
    }

    @Override
    public void onFinish() {
		if (this.listener != null) {
			this.listener.onFinish(this);
		}
    }

    @Override
    public void onStart() {
		if (this.listener != null) {
			this.listener.onStart(this);
		}
    }

    // ---------------------------------------------------------------------//
    // Runnable methods
    // ---------------------------------------------------------------------//

    @Override
    public void run() {

        try {
            int tries = 0;
            boolean interrupted;
            while (!(interrupted = java.lang.Thread.interrupted())) {

                //Connect and reconnect
                socket = new IRCSocket(this.socketfactory, this.host, this.port);
                tries++;
                if (!socket.isConnected()) {
					if (autoreconnect != -1 && tries >= autoreconnect) {
						throw new Exception("Attempted to join successfully " + autoreconnect + " times without succeed!");
					}
					else {
						try {
							java.lang.Thread.sleep(3000);
						}
						catch (InterruptedException e) {
							break;
						}
						continue;
					}
                }

                //Read task
                try {
					if (!sendCommands(startupcmds.toArray(new String[startupcmds.size()]))) {
						throw new Exception("Not able to send commands!");
					}
					if (this.listener != null) {
						this.listener.onConnect(this);
					}
                    while (!(interrupted = java.lang.Thread.interrupted()) && socket.isConnected() && socket.getReader() != null) {
                        try {
                            String line = socket.getReader().readLine();
							if (line == null) {
								throw new Exception("Connection is broken!");
							}
                            this.listener.onInputMessage(this, line);
                        }
                        catch (Exception e) {
							if ((interrupted = java.lang.Thread.interrupted())) {
								throw new InterruptedException();
							}
							else {
								throw e;
							}
                        }
                    }
					if (interrupted) {
						throw new InterruptedException();
					}
                }
                catch (InterruptedException e) {
                    throw e;
                }
                catch (Exception e) {
					if (autoreconnect != -1 && tries >= autoreconnect) {
						throw e;
					}
                }

                //Completely close
                try {
					if (socket != null) {
						socket.close();
					}
                    socket = null;
                }
                catch (Exception e) {
                }

                //Close if it doesn't have to reconnect
				if (autoreconnect == 0) {
					throw new Exception("Connection closed unexpectedly!");
				}

                java.lang.Thread.sleep(3000);
            }
        }
        catch (ClosedByInterruptException | InterruptedException e) {
            this.listener.onDisconnect(this);
        }
        catch (Exception e) {
			if (this.listener != null) {
				this.onError(e);
			}
        }
        try {
			if (socket != null) {
				socket.close();
			}
            socket = null;
        }
        catch (Exception e) {
        }

    }

    @Override
    public void output(Object output) {
        this.onInput(output);
    }
}
