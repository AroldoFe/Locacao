package br.ufrn.imd.locacao.Locacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class BusinessRuleException extends Exception {
    public BusinessRuleException() {
        super();
    }

    public BusinessRuleException(String message) {
        super(message);
    }

    public BusinessRuleException(List<String> messages) {
        super(String.join("|", messages));
    }
}

