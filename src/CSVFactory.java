import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystemAlreadyExistsException;
import java.util.Calendar;

public class CSVFactory {
	private String headers, path;
	private PrintWriter writer;
	private File file;
	private int counter, lines;

	private CSVFactory(String directory) {
		counter = 0;
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
	
	public CSVFactory(String path, String... headers) {
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
	
	public void addRecord(Object... values) {
		Object value;
		String csv = "";
		
		for(int i = 0; i < getHeaderLength(); i++) {
			value = (i >= values.length ? "" : values[i]);
			csv += value + (i == getHeaderLength() - 1 ? "" : ",");
		}
		
		writeInFile(csv);
	}
	
	public void newFile() {
		close();
		
		file = new File(buildCSVName());
		
		try {
			if (file.createNewFile()) {
				writer = new PrintWriter(file);
				lines = 0;
				writeInFile(headers);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public long getFileSize() {
		return file.length();
	}

	public int getLinesNumber() {
		return lines;
	}
	
	public void close() {
		writer.flush();
		writer.close();
	}
	
	private void writeInFile(String line) {
		writer.println(line);
		lines++;
	}
	
	private int getHeaderLength() {
		return headers.split(",").length;
	}

	private String buildCSVName() {
		return path + "/csv" + Calendar.getInstance().getTimeInMillis() + "_" + (counter++) + ".csv";
	}
}
