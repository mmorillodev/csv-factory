import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystemAlreadyExistsException;
import java.util.Calendar;

public class CSVFactory {
	private String headers;
	private String path;
	private PrintWriter writer;
	private File file;
	private int counter;
	
	private CSVFactory(String directory) throws IOException {
		counter = 0;
		path = directory;
		file = new File(directory + "/csv" + Calendar.getInstance().getTimeInMillis() + "_" + (counter++) + ".csv");
		
		if(file.createNewFile()) {
			writer = new PrintWriter(file);
		}
		else
			throw new FileSystemAlreadyExistsException();
	}
	
	public CSVFactory(String path, String... headers) throws IOException {
		this(path);
		
		String csv = "";
		int i = 0;
		for(String header : headers) {
			csv += header + (i == headers.length - 1 ? "" : ",");
			i++;
		}
		
		this.headers = csv;
		writeInFile(csv);
	}
	
	public void addRecord(String... values) {
		String value;
		String csv = "";
		
		for(int i = 0; i < getHeaderLength(); i++) {
			value = (i >= values.length ? "" : values[i]);
			csv += value + (i == getHeaderLength() - 1 ? "" : ",");
		}
		
		writeInFile(csv);
	}
	
	public void newFile() throws IOException {
		close();
		
		file = new File(path + "/csv" + Calendar.getInstance().getTimeInMillis() + "_" + (counter++) + ".csv");
		
		if(file.createNewFile()) {
			writer = new PrintWriter(file);
		}
		
		writer = new PrintWriter(file);
		writeInFile(headers);
	}
	
	public void flush() {
		writer.flush();
	}
	
	public long getFileSize() {
		return file.length();
	}
	
	public void close() {
		writer.flush();
		writer.close();
	}
	
	private void writeInFile(String line) {
		writer.println(line);
	}
	
	private int getHeaderLength() {
		return headers.split(",").length;
	}
}
