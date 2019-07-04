import java.nio.file.FileSystemAlreadyExistsException;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Calendar;
import java.io.File;


public class CSVFactory {
	private String headers, path;
	private PrintWriter writer;
	private File file;
	private int counter, currentLine;
	private boolean printTrace;

	private CSVFactory(String directory) {
		this.file = new File(buildCSVName());
		this.path = directory;

		this.counter = 0;
		this.currentLine = 0;

		try {
			if (this.file.createNewFile()) {
				this.writer = new PrintWriter(this.file);
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
		writeInFile(builder.toString(), this.printTrace);
		this.currentLine++;
	}
	
	public void newFile() {
		close();
		
		this.file = new File(buildCSVName());
		
		try {
			if (this.file.createNewFile()) {
				this.writer = new PrintWriter(this.file);
				this.currentLine = 0;
				writeInFile(this.headers, false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public long getFileSize() {
		return this.file.length();
	}

	public int getNumberOfLines() {
		return this.currentLine;
	}

	public String getFileName() {
		return this.file.getName();
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
			System.out.println("Writing line number " + this.currentLine + "... ");

		this.writer.print(record);
	}
	
	private int getHeaderLength() {
		return this.headers.split(",").length;
	}

	private String buildCSVName() {
		return this.path + "/csv" + Calendar.getInstance().getTimeInMillis() + "_" + (this.counter++) + ".csv";
	}
}