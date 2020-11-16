package com.datafrey.goalsmanager.util.userinputvalidation;

public class InputIsTooShortMiddleware extends InputValidatorMiddleware {

    private int minimumLength;

    public InputIsTooShortMiddleware(int minimumLength) {
        this.minimumLength = minimumLength;
    }

    @Override
    public InputValidationResult check(String input) {
        if (input.length() < minimumLength) {
            return InputValidationResult.INPUT_IS_TOO_SHORT;
        }

        return checkNext(input);
    }
}
