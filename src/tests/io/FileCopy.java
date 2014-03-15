package tests.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {

	private static final int BUF_SIZE = 65536;
	
	private static void copy(String source, String dest) throws IOException {
		try (FileInputStream input = new FileInputStream(source)) {
			try (FileOutputStream output = new FileOutputStream(dest)) {
				byte[] buffer = new byte[BUF_SIZE];
				
				int cnt;
				while ((cnt = input.read(buffer)) != -1) {
					output.write(buffer, 0, cnt);
				}
			}
		}
	}
	
	private static boolean compare(String file1, String file2) throws IOException {
		try (FileInputStream input1 = new FileInputStream(file1)) {
			try (FileInputStream input2 = new FileInputStream(file2)) {
				
				int b1 = 0, b2 = 0;
				do {
					b1 = input1.read();
					b2 = input2.read();
					if (b2 != b1) {
						return false;
					}
				} while (b1 != -1 && b2 != -1);
				return true;
			}
		}
	}
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.out.println("Usage: FileCopy <source> <destination>");
		}

		copy(args[0], args[1]);
		System.out.println("Result: " + (compare(args[0], args[1]) ? "true" : "false"));
	}

}
