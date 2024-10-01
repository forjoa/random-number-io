package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Adivina {
	
	static String file = "record.txt";
	
	public static int knowPreviousRecord() {
		int att = 0;

		if (!new File(file).exists()) {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
				bw.write(String.valueOf(att));
			} catch (IOException e) {
				System.out.println("Error al crear el archivo: " + e.getMessage());
			}
			return att;
		}

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = br.readLine();
			att = Integer.parseInt(line);
		} catch (IOException e) {
			System.out.println("Error leyendo el archivo: " + e.getMessage());
		}

		return att;
	}

	public static void writeNewRecord(int att) {
		String file = "record.txt";

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			bw.write(String.valueOf(att));
		} catch (IOException e) {
			System.out.println("Error writing to file: " + e.getMessage());
		}
	}

	public static void main(String[] args) {

		boolean flag = true;

		int tries = 0;

		int randomNumber = (int) (Math.random() * 100);

		int att = knowPreviousRecord();

		while (flag) {
			try {
				BufferedReader entry = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("Introduce un número: ");

				String userNumberString = entry.readLine();
				int userNumber;
				try {
					userNumber = Integer.parseInt(userNumberString);
					tries++;

					if (userNumber == randomNumber) {
						if ((tries < att && att != 0) || att == 0) {
							System.out.println("¡Batiste un nuevo record!");
							writeNewRecord(tries);
						}
						System.out.println("Felicidades, ¡acertaste!");
						System.out.print("¿Deseas intentarlo otra vez? (s/n) ");
						String res = entry.readLine();

						if (!res.equals("s")) {
							flag = false;
							return;
						} else {
							tries = 0;
							att = knowPreviousRecord();
							randomNumber = (int) (Math.random() * 100);
						}

					} else {
						System.out.println(
								(userNumber > randomNumber) ? "Número demasiado grande" : "Número demasiado pequeño");
					}
				} catch (NumberFormatException e) {
					tries++;
					System.out.println("Escribe solo números");
				}
			} catch (IOException e) {
				System.out.println("Error de entrada del texto: " + e.getMessage());
			}
		}

	}

}
