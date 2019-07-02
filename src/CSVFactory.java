import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.nio.file.FileSystemAlreadyExistsException;

public class CSVFactory {
	private String headers, path;
	private PrintWriter writer;
	private File file;
	private int counter, lines;
	private boolean printTrace;

	private CSVFactory(String directory) {
		counter = 0;
		lines = 0;
		path = directory;
		file = new File(buildCSVName());

		try {
			if (file.createNewFile()) {
				writer = new PrintWriter(file);
			} else
				throw new FileSystemAlreadyExistsException();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CSVFactory(String directory, String... headers) {
		this(directory);
		
		StringBuffer builder = new StringBuffer();

		for(int i = 0; i < headers.length; i++)
			builder.append(headers[i] + (i == headers.length - 1 ? "" : ","));
		
		this.headers = builder.toString();
		writeInFile(builder.toString(), false);
	}
	
	public void addRecord(Object... values) {
		Object value;
		StringBuffer builder = new StringBuffer();
		
		for(int i = 0; i < getHeaderLength(); i++) {
			value = (i >= values.length ? "" : values[i]);
			builder.append(value + (i == getHeaderLength() - 1 ? "" : ","));
		}
		
		writeInFile("\n", false);
		writeInFile(builder.toString(), printTrace);
		lines++;
	}
	
	public void newFile() {
		close();
		
		file = new File(buildCSVName());
		
		try {
			if (file.createNewFile()) {
				writer = new PrintWriter(file);
				lines = 0;
				writeInFile(headers, false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public long getFileSize() {
		return file.length();
	}

	public int getNumberOfLines() {
		return lines;
	}

	public String getFileName() {
		return file.getName();
	}

	public void printTrace(boolean printTrace) {
		this.printTrace = printTrace;
	}
	
	public void close() {
		writer.flush();
		writer.close();
	}
	
	private void writeInFile(String record, boolean printTrace) {
		if(printTrace)
			System.out.println("Writing line number " + lines+ "... ");

		writer.print(record);
	}
	
	private int getHeaderLength() {
		return headers.split(",").length;
	}

	private String buildCSVName() {
		return path + "/csv" + Calendar.getInstance().getTimeInMillis() + "_" + (counter++) + ".csv";
	}
}