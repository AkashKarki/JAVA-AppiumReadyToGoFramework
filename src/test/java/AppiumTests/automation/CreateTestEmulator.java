package AppiumTests.automation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AppiumTests.automation.GlobalPropertyReader;
import AppiumTests.automation.PortFinder;
import static AppiumTests.automation.CustomFeatures.hardWait;

public class CreateTestEmulator {
	Boolean CreationFlag = false;
	String Port;
	String EmulatorName;
	String AndroidVersion;

	public CreateTestEmulator(String EmulatorName) {

		if (System.getProperty("AndroidVersion") != null) {
			this.AndroidVersion = System.getProperty("AndroidVersion");
		} else {
			this.AndroidVersion = GlobalPropertyReader.getGlobalProperty("AndroidVersion");
		}

		this.EmulatorName = EmulatorName;

		String DeviceType;
		if (System.getProperty("Device") != null) {
			DeviceType = System.getProperty("Device");
		} else {
			DeviceType = GlobalPropertyReader.getGlobalProperty("Device");
		}

		System.out.println("DeviceType:" + DeviceType);
		if (DeviceType.equalsIgnoreCase("Emulator")) {
			CreationFlag = true;
			System.out.println("Cretaetion Flag is ture");
		}

		if (CreationFlag) {
			InstallTheRequiredSystemImage(AndroidVersion);
			CreateNewEmulator(AndroidVersion);
			StartTheEmulator(EmulatorName);
		}
	}

	private void InstallTheRequiredSystemImage(String AndroidVersion) {
		System.out.println("Installing System Image.....");
		String AndroidSystemImage = GetSystemImageForTheAndroidVersion(AndroidVersion);
		String Command = "sdkmanager --install \"" + AndroidSystemImage + "\"";
		System.out.println("Command::" + Command);
		ExecuteCmdCommand(Command, false, 0);
		System.out.println("Android System Image " + AndroidSystemImage + " is Installed");
	}

	private void CreateNewEmulator(String AndroidVersion) {
		String AndroidSystemImage = GetSystemImageForTheAndroidVersion(AndroidVersion);
		String Command = "echo \"no\" | avdmanager --verbose create avd --force --name \"" + EmulatorName
				+ "\" --device \"pixel\" --package \"" + AndroidSystemImage + "\" --abi \"x86\"";
		System.out.println("########Cretating New Emulator#########");
		System.out.println("Emulator Name::" + EmulatorName);
		System.out.println("System Iamge::" + AndroidSystemImage);
		System.out.println(Command);
		ExecuteCmdCommand(Command, false, 0);
		System.out.println("Android Emulator " + EmulatorName + " is Created..");
	}

	private synchronized void StartTheEmulator(String EmulatorName) {
		PortFinder PortFind = new PortFinder();
		Port = Integer.toString(PortFind.findFreePort()).trim();
		// this.EmulatorNameAndPort="emulator"+Port;
		String Command = "cd %Android_Home%/Emulator && emulator -port " + Port + " -avd " + EmulatorName
				+ " & adb wait-for-device";
		System.out.println(Command);
		System.out.println("Startig Emulator::" + EmulatorName);
		ExecuteCmdCommand(Command, true, 0);
		System.out.println(EmulatorName + " is started..");
		waitforEmulatorToBootUp(Port.trim());

	}

	private void waitforEmulatorToBootUp(String portNumber) {
		System.out.println("#####Waiting for the Emulator to Boot Up######");
		String Command = "adb devices";
		int NoTry = 0;
		while (NoTry <= 60) {
			NoTry++;
			String ListOfDevices = ExecuteAndReturnCommandStatus(Command).trim().replaceAll("[\\t\\n\\r\\s+]", " ");

			/*
			 * System.out.println(ListOfDevices); System.out.println("Searching for:: " +
			 * "emulator-" + portNumber +" device");
			 */

			if (ListOfDevices.contains("emulator-" + portNumber + " device")) {
				hardWait(60);
				System.out.println("emulator-" + portNumber + " is Ready to Use");
				break;
			} else {
				hardWait(5);
			}

		}

	}

	public void ExecuteCmdCommand(String command, Boolean flag, int SecWaitTIme) {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("cmd.exe", "/c", command);

		try {

			Process process = processBuilder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = null;

			if (flag) {
				process.waitFor(60, TimeUnit.SECONDS);
				System.out.println(line = reader.readLine());
				hardWait(SecWaitTIme);
				return;
			}

			else {
				while ((line = reader.readLine()) != null) {
					// System.out.println(line);
				}
				hardWait(SecWaitTIme);
				System.out.println("Process End Code::" + process.waitFor());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String ExecuteAndReturnCommandStatus(String command) {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("cmd.exe", "/c", command);

		String RetunDevices = "";
		try {
			String line = "";
			Process process = processBuilder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			while ((line = reader.readLine()) != null) {
				RetunDevices = RetunDevices + " " + line;
			}
			// System.out.println("Process End Code::" + process.waitFor());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return RetunDevices;
	}

	private String GetSystemImageForTheAndroidVersion(String AndroidVersion) {

		switch (AndroidVersion) {
		case "7":
			return "system-images;android-24;google_apis;x86";
		case "8":
			return "system-images;android-26;google_apis;x86";
		case "9":
			return "system-images;android-29;google_apis;x86";
		default:
			return null;

		}
	}

	public void stopEmulator(String Emulator) {
		String Pid = getProcessIdFromPort(Port);
		String command = "taskkill /pid " + Pid + "";
		System.out.println("Executing Command" + command);
		ExecuteCmdCommand(command, true, 10);
		System.out.println("Emulator '" + Emulator + "' Stopped.");

		String Command = "avdmanager delete avd -n " + Emulator;
		System.out.println("Stopping  Emulator::" + Emulator);
		System.out.println("Executing Commaind::" + Command);
		ExecuteCmdCommand(Command, false, 0);
		System.out.println(Emulator + " is Closed and wiped out data comleted.");
	}

	public String getProcessIdFromPort(String PortNo) {
		PortNo = PortNo.trim();
		String line = "";
		String command = "netstat -ano | findstr " + PortNo + "";
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("cmd.exe", "/c", command);
		List<String> ProcessIds = new ArrayList<>();
		try {

			Process process = processBuilder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			while ((line = reader.readLine()) != null) {
				ProcessIds.add(line);
				// System.out.println(line);
			}

			System.out.println("Process End Code::" + process.waitFor());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String Ports = ":" + PortNo;
		String pattern = ".*\\" + Ports + "\\b.*";
		Pattern p = Pattern.compile(pattern);
		for (Iterator<String> iterator = ProcessIds.iterator(); iterator.hasNext();) {
			String PorcessOpen = (String) iterator.next();
			Matcher m = p.matcher(PorcessOpen);
			if (m.find()) {
				PorcessOpen = PorcessOpen.replaceAll(" +", " ").trim();
				String Pid[] = PorcessOpen.split(" ");
				return Pid[4];
			}

		}
		return null;
	}

}
