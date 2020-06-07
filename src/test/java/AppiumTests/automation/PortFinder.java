package AppiumTests.automation;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Random;

/**
 * Finds an available port on localhost.
 */
public class PortFinder {

	/**
	 * Finds a free port between {@link #MIN_PORT_NUMBER} and
	 * {@link #MAX_PORT_NUMBER}.
	 *
	 * @return a free port
	 * @throw RuntimeException if a port could not be found
	 */
	public synchronized int findFreePort() {
		// the ports below 5554 are system ports
		int MIN_PORT_NUMBER = 5554;

		// the ports above 5585 are dynamic and/or private
		int MAX_PORT_NUMBER = 5585;
		return getRendomPortBetween(MIN_PORT_NUMBER, MAX_PORT_NUMBER);
	}

	/**
	 * Returns true if the specified port is available on this host.
	 *
	 * @param port
	 *            the port to check
	 * @return true if the port is available, false otherwise
	 */
	public boolean available(final int port) {
		ServerSocket serverSocket = null;
		DatagramSocket dataSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setReuseAddress(true);
			dataSocket = new DatagramSocket(port);
			dataSocket.setReuseAddress(true);
			return true;
		} catch (final IOException e) {
			return false;
		} finally {
			if (dataSocket != null) {
				dataSocket.close();
			}
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (final IOException e) {
					// can never happen
				}
			}
		}
	}

	/**
	 * Finds a free port between {@link #MIN_PORT_NUMBER} and
	 * {@link #MAX_PORT_NUMBER}.
	 *
	 * @return a free port
	 * @throw RuntimeException if a port could not be found
	 */
	public synchronized int findAppuimFreePort() {
		// the ports below 4500 are system ports
		int MIN_PORT_NUMBER = 4500;

		// the ports above 5500 are dynamic and/or private
		int MAX_PORT_NUMBER = 5500;
		System.out.print("");
		return getRendomPortBetween(MIN_PORT_NUMBER, MAX_PORT_NUMBER);
	}

	/**
	 * Finds a free port between {@link #MIN_PORT_NUMBER} and
	 * {@link #MAX_PORT_NUMBER}.
	 *
	 * @return a free port
	 * @throw RuntimeException if a port could not be found
	 */
	public synchronized int findwdaLocalPortFreePort() {
		int Min_PORT_NUMBER = 8100;
		int MAX_PORT_NUMBER = 9100;
		return getRendomPortBetween(Min_PORT_NUMBER, MAX_PORT_NUMBER);
	}

	/**
	 * Finds a free port between {@link #MIN_PORT_NUMBER} and
	 * {@link #MAX_PORT_NUMBER}.
	 *
	 * @return a free port
	 * @throw RuntimeException if a port could not be found
	 */
	public synchronized int findSystemPortFreePort() {
		int Min_Prot_NUMBER = 8200;
		int MAX_PORT_NUMBER = 8299;
		return getRendomPortBetween(Min_Prot_NUMBER, MAX_PORT_NUMBER);
	}

	/**
	 * Finds a free port between {@link #MIN_PORT_NUMBER} and
	 * {@link #MAX_PORT_NUMBER}.
	 *
	 * @return a free port
	 * @throw RuntimeException if a port could not be found
	 */
	public synchronized int findmjpegServerPortFreePort() {
		int Min_PORT_NUMBER = 9200;
		int MAX_PORT_NUMBER = 9299;
		return getRendomPortBetween(Min_PORT_NUMBER, MAX_PORT_NUMBER);
	}

	public synchronized int getRendomPortBetween(int Min_Port, int Max_Port) {
		int i = 0;
		while (i <= 2000) {
			i++;
			Random r = new Random();
			int result = r.nextInt(Max_Port - Min_Port) + Min_Port;
			if (available(result)) {
				System.out.println("Appium PORT::" + result);
				return result;
			}
		}
		throw new RuntimeException("Could not find an available port between " + Min_Port + " and " + Max_Port);
	}

}
