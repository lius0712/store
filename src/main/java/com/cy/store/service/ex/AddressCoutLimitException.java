package com.cy.store.service.ex;

/**收货地址总数超出限制的异常（20条）*/
public class AddressCoutLimitException extends ServiceException {
    public AddressCoutLimitException() {
        super();
    }

    public AddressCoutLimitException(String message) {
        super(message);
    }

    public AddressCoutLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressCoutLimitException(Throwable cause) {
        super(cause);
    }

    protected AddressCoutLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
