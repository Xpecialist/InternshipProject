package com.example.SpringPostgress.service;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.example.SpringPostgress.Enum.VacationStatus;

@Converter(autoApply = true)
public class EnumConverter implements AttributeConverter<VacationStatus, String> {

    @Override
    public String convertToDatabaseColumn(VacationStatus status) {
        return status.toDbValue();
    }
    @Override
    public VacationStatus convertToEntityAttribute(String dbData) {
        return VacationStatus.from(dbData);
    }
}
