package com.soclosetoheaven.common.io;


import com.soclosetoheaven.common.util.TerminalColors;

import java.io.*;
import java.util.LinkedList;

/**
 * Class is provided for processing user's input and output stream
 */
public class BasicIO {
    /**
     * stack of input streams
     */

    private final LinkedList<BufferedReader> stack = new LinkedList<>();

    /**
     * output stream writer
     */
    private final BufferedWriter writer;

    private final BufferedWriter errWriter;


    /**
     * default BasicClientIO constructor
     */
    public BasicIO() {
        this(System.in, System.out);
    }

    /**
     * adds new input stream to streams {@link #stack} and output stream to {@link #writer}
     * @param in input stream
     * @param out output stream
     */

    public BasicIO(InputStream in, OutputStream out) {
        this.stack.add(new BufferedReader(new InputStreamReader(in)));
        this.writer = new BufferedWriter(new OutputStreamWriter(out));
        this.errWriter = new BufferedWriter(new OutputStreamWriter(System.err));
    }


    /**
     * writes object to output stream
     * @param obj any java object
     */
    public void write(Object obj) {
        try {
            writer.write(obj.toString());
            writer.flush();
        } catch (IOException e) {
            writeErr("%s: %s%n".formatted("Something went wrong with userIO", e.getMessage()));
            System.exit(-1);
        } catch (NullPointerException e) {
            this.write("null");
        }
    }

    /**
     * the same method as {@link #write} but next output starts with new line
     * @param obj any java object
     */
    public void writeln(Object obj) {
        try {
            writer.write(obj.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            writeErr("%s: %s%n".formatted("Something went wrong with userIO", e.getMessage()));
            System.exit(-1);
        } catch (NullPointerException e) {
            this.writeln("null");
        }
    }


    /**
     *
     * @return read one line from input stream, if it ends stream will be closed via method {@link #removeAndClose()}
     */
    public String read() {
        try {
            String input;
            BufferedReader reader = stack.getLast();
            do {
                input = reader.readLine();
                if (input == null) {
                    //stream ended
                    //move to next stream
                    removeAndClose();
                    continue;
                }
                break;
            } while (stack.size() > 0);
            if (stack.size() == 0) {
                writeErr("All streams ended, stopping program");
                System.exit(-1);
            }
            return input;
        } catch (IOException e) {
            return read();
        }
    }


    /**
     *
     * @param message that will be written in output stream before reading data from input stream
     * @return String read from input stream
     */
    public String read(String message) {
        this.write(message);
        return this.read();
    }



    /**
     * @param reader will be added to {@link #stack}
     */
    public void add(BufferedReader reader) {
        stack.add(reader);
    }


    /**
     * removes last stream from {@link #stack}
     */
    public void removeAndClose() {
        try {
            stack.getLast().close();
            remove();
        } catch (IOException e) {
            writeErr("%s: %s%n".formatted("Something went wrong with userIO when closing stream", e.getMessage()));
            System.exit(-1);
        }
    }

    public BufferedReader getFirstReader() {
        return this.stack.getFirst();
    }

    public void remove() {
        stack.removeLast();
    }

    // костыльный момент, надо переделать
    public String stdRead() {
        String input;
        try {
            BufferedReader stdReader = getFirstReader();
            input = stdReader.readLine();
            if (input == null) {
                stdReader.close();
                throw new IOException("Default stream ended, no option to continue program");
            }
            return input;
        } catch (IOException e) {
            writeErr(e.getMessage());
            System.exit(-1);
        }
        return "Something went wrong with userIO";
    }
    public String stdRead(String message) {
        this.write(message);
        return this.stdRead();
    }

    public String stdReadLineWithNull() {
        String line = stdRead();
        return (line.isEmpty()) ? null : line;
    }


    public String stdReadLineWithNull(String message) {
        this.write(message);
        return this.stdReadLineWithNull();
    }

    public void writeErr(Object obj) {
        try {
            errWriter.write(TerminalColors.setColor(
                    obj.toString(),
                    TerminalColors.RED)
            );
            errWriter.newLine();
            errWriter.flush();
        } catch (IOException e) {
            System.exit(-33);
        } catch (NullPointerException e) {
            writeErr("EXCEPTION MESSAGE IS NULL");
        }
    }
}
