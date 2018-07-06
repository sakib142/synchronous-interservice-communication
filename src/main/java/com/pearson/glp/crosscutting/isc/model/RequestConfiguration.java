package com.pearson.glp.crosscutting.isc.model;

/**
 * The RequestConfiguration class.
 * 
 * <p>Configuration of request.
 * 
 *
 */
public class RequestConfiguration {
    /**
     * The instance variable timoutInMiliseconds.
     */
    private int timoutInMiliseconds;
    /**
     * The instance variable threadPoolCoreSize.
     */
    private int threadPoolCoreSize;
    /**
     * The instance variable threadPoolQueueSize.
     */
    private int threadPoolQueueSize;

    /**
     * The parameter constructor of RequestConfiguration class.
     * 
     * @param timoutInMiliseconds
     *            timeout value
     * @param threadPoolCoreSize
     *            size of pool
     * @param threadPoolQueueSize
     *            size of queue
     */
    public RequestConfiguration(int timoutInMiliseconds, int threadPoolCoreSize,
            int threadPoolQueueSize) {
        super();
        this.timoutInMiliseconds = timoutInMiliseconds;
        this.threadPoolCoreSize = threadPoolCoreSize;
        this.threadPoolQueueSize = threadPoolQueueSize;
    }

    /**
     * The parameter constructor of RequestConfiguration class.
     * 
     * @param threadPoolCoreSize
     *            size of pool
     * @param threadPoolQueueSize
     *            size of queue
     */
    public RequestConfiguration(int threadPoolCoreSize,
            int threadPoolQueueSize) {
        super();
        this.threadPoolCoreSize = threadPoolCoreSize;
        this.threadPoolQueueSize = threadPoolQueueSize;
    }

    /**
     * The parameter constructor of RequestConfiguration class.
     * 
     * @param timoutInMiliseconds
     *            timeout value
     */
    public RequestConfiguration(int timoutInMiliseconds) {
        super();
        this.timoutInMiliseconds = timoutInMiliseconds;
    }

    /**
     * Get timoutInMiliseconds.
     * 
     * @return integer timeout
     */
    public int getTimoutInMiliseconds() {
        return this.timoutInMiliseconds;
    }

    /**
     * Get threadPoolCoreSize.
     * 
     * @return integer core pool size
     */
    public int getThreadPoolCoreSize() {
        return this.threadPoolCoreSize;
    }

    /**
     * Get threadPoolQueueSize.
     * 
     * @return integer queue size
     */
    public int getThreadPoolQueueSize() {
        return this.threadPoolQueueSize;
    }
}
