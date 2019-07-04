package br.com.matheus;

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

	public CSVFactory(String directory) {
		this.path = directory;
		this.file = new File(buildCSVName());
		this.counter = 0;
		this.currentLine = 0;

		try {
			if (this.file.createNewFile()) {
				this.writer = new PrintWriter(this.file);
			} else
				throw new FileSystemAlreadyExistsException();
		} catch (IOException e) {
			System.err.println(e.getStackTrace().toString());
		}
	}
	
	public CSVFactory(String directory, String... headers) {
		this(directory);
		setHeaders(headers);
	}

	public void setHeaders(String... headers) {
		if(this.headers != null)
			return;

		StringBuffer builder = new StringBuffer();

		for(int i = 0; i < headers.length; i++)
			builder.append(addSlashes(headers[i]) + (i == headers.length - 1 ? "" : ","));

		this.headers = builder.toString();
		writeInFile(this.headers, false);
	}
	
	public void addRecord(Object... values) {
		Object value;
		StringBuffer builder = new StringBuffer();
		
		for(int i = 0; i < getHeaderLength(); i++) {
			value = (i >= values.length ? "" : values[i]);
			builder.append(addSlashes(value.toString())+ (i == getHeaderLength() - 1 ? "" : ","));
		}
		
		writeInFile("\n", false);
		writeInFile(builder.toString(), this.printTrace);
		this.currentLine++;
	}

	public void addRecords(Object... values) {
		if(values.length <= getHeaderLength()){
			addRecord(values);
			return;
		}

		//int recordsQtd = values.length % getHeaderLength();

		addRecord(subList(values, 0, getHeaderLength()));
		values = subList(values, getHeaderLength());

		addRecords(values);
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

	public int getNumberOfRecords() {
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
		return this.path + "\\csv" + Calendar.getInstance().getTimeInMillis() + "_" + (this.counter++) + ".csv";
	}

	private String addSlashes(String str) {
		return str.replace("\n", "\\\\n").replace("\t", "\\\\t").replace("\r", "\\\\r");
	}

	private Object[] subList(Object[] list, int start, int end) {
		Object[] arr = new Object[end - start];

		if(start > list.length || end > list.length) {
			return arr;
		}

		if(start > end){
			int aux = start;
			start = end;
			end = aux;
		}

		for(int i = start, j = 0; i < end; i++, j++) {
			arr[j] = list[i];
		}

		return arr;
	}

	private Object[] subList(Object[] list, int start) {
		Object[] arr = new Object[list.length - start];

		if(start > list.length)
			return arr;

		for(int i = start, j = 0; j < arr.length; i++, j++) {
			arr[j] = list[i];
		}

		return arr;
	}

//	private class CSVException extends Exception {}
//
//	private class CSVTooLargeException extends CSVException {}
}