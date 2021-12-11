package process.builder;

import process.builder.event.raw.OutputErrorEvent;
import process.builder.event.raw.OutputFinishedEvent;
import process.builder.event.raw.OutputMessageEvent;
import process.builder.event.handler.ProcessHandler;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 */
public class TrackedProcessExecutor {

    /**
     * The current input stream of the currently active process.
     */
    private BufferedReader inputStream;

    /**
     * The current error stream of the currently active process.
     */
    private BufferedReader errorStream;

    /**
     * The current output stream of the currently active process.
     */
    private BufferedOutputStream outputStream;

    /**
     * The current process, or the previous process, or null; depending on if a
     * process has not finished, finished, or no process has ever been started.
     */
    private Process currentProcess;

    /**
     * The factory object that will supply live processes.
     */
    private ProcessFactory processFactory = ProcessFactory.DEFAULT_FACTORY;

    /**
     * The handler which will receive all updates of a given process.
     */
    private ProcessHandler processHandler = new ProcessHandler.ConsoleHandler();

    /**
     * Starts a process and initialises the standard IO traps sending all data
     * whenever a request is received or asked for.
     * <p>
     * By Default this does not do any Threading all calls are done on the
     * current thread and the return is when the task has concluded and all
     * events are complete.
     *
     * @param args The command line arguments to execute.
     * @return The process that was started. It may have concluded, or not by
     * the time it's received.
     * @throws IOException           If one occurs whilst building the process.
     * @throws IllegalStateException If a task is currently running and not yet
     *                               halted or been killed.
     */
    public synchronized Process start(final String... args)
            throws IOException, IllegalStateException {

        if ((this.currentProcess != null) && this.currentProcess.isAlive()) {
            throw new IllegalStateException();
        }

        this.currentProcess = processFactory.create(args);
        initTask();

        this.handleProcess();

        return this.currentProcess;
    }

    /**
     * Reads both channels of output and for any new messages informs the
     * handler of said changes.
     */
    private synchronized void handleProcess() throws IOException {

        String curMessage;
        while ((curMessage = nextMessage()) != null) {

            // Ping of message
            this.processHandler.onConsoleMessage(
                    new OutputMessageEvent(this, curMessage)
            );

            // Ping of error if any
            final String curError = nextError();
            if (curError != null) {
                this.processHandler.onErrorMessage(
                        new OutputErrorEvent(this, curMessage)
                );
            }
        }

        // Determine exit value
        int exitValue = -1;
        if (!this.currentProcess.isAlive()) {
            exitValue = this.currentProcess.exitValue();
        }

        // Close streams
        this.inputStream.close();
        this.errorStream.close();
        this.outputStream.close();

        // Ping of end
        this.processHandler.onProcessFinished(new OutputFinishedEvent(
                this,
                exitValue == 0
        ));
    }

    /**
     * @return Next line in the stream.
     * @throws IOException If one occurs whilst attempting to read the stream.
     */
    private synchronized String nextMessage() throws IOException {
        return this.inputStream.readLine();
    }

    /**
     * @return Next error line in the stream.
     * @throws IOException If one occurs whilst reading the stream.
     */
    private synchronized String nextError() throws IOException {
        return this.errorStream.readLine();
    }

    /**
     * Initialise readers ready to accept process input and output.
     */
    private synchronized void initTask() {
        this.inputStream = new BufferedReader(
                new InputStreamReader(this.currentProcess.getInputStream())
        );
        this.errorStream = new BufferedReader(
                new InputStreamReader(this.currentProcess.getErrorStream())
        );
        this.outputStream = new BufferedOutputStream(
                this.currentProcess.getOutputStream()
        );
    }

    /**
     * Set the process factory object used to construct new processes.
     * <p>
     * If not set then {@link ProcessFactory#DEFAULT_FACTORY} is used.
     *
     * @param factory The new factory to use for building processes.
     */
    public synchronized void setProcessFactory(final ProcessFactory factory) {
        this.processFactory = factory;
    }

    /**
     * @return If a process is currently running and has not yet stopped.
     */
    public synchronized boolean isAlive() {
        return (this.currentProcess != null) && this.currentProcess.isAlive();
    }

    /**
     * Forcibly stops the current process. This call is equivalent to {@link
     * Process#destroyForcibly()} and as such depending on the implementation of
     * the {@link ProcessFactory} this may or may not be the same as {@link
     * Process#destroy()}.
     */
    public synchronized void haltTask() {
        if (this.currentProcess != null) {
            this.currentProcess.destroyForcibly();
        }
    }

    /**
     * Gets the current output stream of the target process. If no process has
     * been started then this will be the previous output stream. But if no
     * process has ever been started then this will be {@code null}.
     *
     * @return The current output stream being used.
     */
    public synchronized BufferedOutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * Set the current process handler to the provided one. This is the handler
     * that will receive all updates about the currently alive process.
     * <p>
     * If this is not set then a Default handler is used that being {@link
     * ProcessHandler.ConsoleHandler}. Which will send all
     * messages to the console.
     *
     * @param handler The new handler to use.
     */
    public synchronized void setProcessHandler(final ProcessHandler handler) {
        this.processHandler = handler;
    }
}
