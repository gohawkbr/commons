package br.com.gohawk.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandUtils {

	private static final int SUCESSO = 0;
	private static final Logger LOG = LoggerFactory.getLogger(CommandUtils.class);

	public static String executar(String command) {
		String result = "";
		try {
			Process processo = Runtime.getRuntime().exec(command(command));
			result = getOutput(processo.getInputStream());
			processo.waitFor();
			if (processo.exitValue() != SUCESSO) {
				throw new IOException("Erro ao executar comando.");
			}
		} catch (IOException | InterruptedException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;

	}

	public static void executarBackground(String comando) {
		try {
			Process process = Runtime.getRuntime().exec(command(comando));
			Background background = new Background(process);
			background.start();
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}

	private static String[] command(String command) {
		return new String[] { "/bin/bash", "-c", command };
	}

	private static String getOutput(InputStream stream) {
		StringBuilder output = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.defaultCharset()));
			String outputLine;
			while ((outputLine = reader.readLine()) != null) {
				output.append(outputLine + System.lineSeparator());
			}
			if (StringUtils.isNotEmpty(output.toString())) {
				LOG.info(output.toString());
			}
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		return output.toString();
	}

	static class Background extends Thread {
		private Process process;
		private BufferedReader reader;
		private String output;

		public Background(Process process) {
			this.process = process;
		}

		@Override
		public void run() {
			try {
				InputStreamReader isReader = new InputStreamReader(process.getInputStream(), Charset.defaultCharset());
				this.reader = new BufferedReader(isReader);
				while ((output = reader.readLine()) != null) {
					LOG.info(output);
				}
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
	}

}
