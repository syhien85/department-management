package root.controller;

import jakarta.persistence.NoResultException;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
@Slf4j
public class ExceptionController {

//    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({NoResultException.class})
    public String noResultException(NoResultException e, Model model) {
        // e.printStackTrace();
//        logger.info("INFO", e); //org.slf4j.Logger

        log.warn("INFO", e); // @Slf4j
        model.addAttribute("err", e);
        return "404.html";
    }

    @ExceptionHandler({UnexpectedTypeException.class})
    public String unexpectedTypeException(Exception e, Model model) {
        log.warn("WARN", e);
        model.addAttribute("err", e);
        if (e.getMessage().contains("UniqueElements")) {
            model.addAttribute("err_detail", "*Truong da nhap bi trung");
        }
        return "error.html";
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public String sQLIntegrityConstraintViolationException(Exception e, Model model) {
        log.warn("WARN", e);
        model.addAttribute("err", e.getMessage());
//        model.addAttribute("err_detail", "*Cannot delete or update a parent row: a foreign key constraint fails...");
        return "error.html";
    }
}
