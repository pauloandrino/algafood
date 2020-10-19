package com.algaworks.algafoodapi.core.validation;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private DataSize maxsize;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        this.maxsize = DataSize.parse(constraintAnnotation.max());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || value.getSize() <= this.maxsize.toBytes();
    }
}
