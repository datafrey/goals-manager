package com.datafrey.goalsmanager.util.userinputvalidation;

public abstract class InputValidatorMiddleware {

    private InputValidatorMiddleware next = null;

    public InputValidatorMiddleware linkWith(InputValidatorMiddleware next) {
        this.next = next;
        return next;
    }

    public abstract InputValidationResult check(String input);

    protected InputValidationResult checkNext(String input) {
        if (next == null) {
            return InputValidationResult.OK;
        }

        return next.check(input);
    }
}
