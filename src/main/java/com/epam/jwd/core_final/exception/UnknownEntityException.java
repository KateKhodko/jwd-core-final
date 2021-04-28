package com.epam.jwd.core_final.exception;

public class UnknownEntityException extends RuntimeException {

    private final String entityName;
    private final Object[] args;

    public UnknownEntityException(String entityName) {
        super();
        this.entityName = entityName;
        this.args = null;
    }

    public UnknownEntityException(String entityName, Object[] args) {
        super();
        this.entityName = entityName;
        this.args = args;
    }

    @Override
    public String getMessage() {
        // todo
        // you should use entityName, args (if necessary)
        String message = "Unknown entity: " + entityName;
        if (args != null && args.length != 0) {
            message += " args:\n";
            StringBuilder argsStr = new StringBuilder();
            for (Object arg : args) {
                argsStr.append(arg.toString()).append("\n");
            }
            message += argsStr;
        }
        return message;
    }
}
