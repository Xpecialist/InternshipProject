package com.example.SpringPostgress.service;

import com.example.SpringPostgress.DTO.BonusDTO;
import com.example.SpringPostgress.Enum.BonusRate;
import com.example.SpringPostgress.exception.ResourceNotFoundException;
import com.example.SpringPostgress.model.Bonus;
import com.example.SpringPostgress.model.Employee;
import com.example.SpringPostgress.repository.BonusRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class for managing bonuses.
 */
@Service
@Log4j2
@Transactional
public class BonusService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BonusRepository bonusRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Saves a bonus.
     * @param bonusDTO The bonus DTO to save.
     * @return The saved bonus DTO.
     */
    public BonusDTO saveBonus(BonusDTO bonusDTO){
        bonusRepository.save(modelMapper.map(bonusDTO, Bonus.class));
        return bonusDTO;
    }

    /**
     * Updates a bonus.
     * @param bonusDTO The bonus DTO to update.
     * @return The updated bonus DTO.
     */
    public BonusDTO updateBonus(BonusDTO bonusDTO){
        bonusRepository.save(modelMapper.map(bonusDTO, Bonus.class));
        return bonusDTO;
    }

    /**
     * Retrieves all bonuses.
     * @return List of all bonus DTOs.
     * @throws ResourceNotFoundException if no bonuses are found.
     */
    public List<BonusDTO> getAllBonuses(){
        List<Bonus> bonusList = bonusRepository.findAll();
        if (bonusList.isEmpty()) {
            throw new ResourceNotFoundException("No bonus entries found.");
        }
        return modelMapper.map(bonusList, new TypeToken<List<BonusDTO>>(){}.getType());
    }

    /**
     * Deletes a bonus.
     * @param bonusDTO The bonus DTO to delete.
     * @return True if the bonus was deleted successfully, false otherwise.
     */
    public boolean deleteBonus(BonusDTO bonusDTO){
        bonusRepository.delete(modelMapper.map(bonusDTO, Bonus.class));
        return true;
    }

    /**
     * Retrieves a bonus by ID.
     * @param id The ID of the bonus.
     * @return The bonus DTO.
     */
    public BonusDTO getBonusById(Long id) {
        Optional<Bonus> bonus = bonusRepository.findById(id);
        return modelMapper.map(bonus, BonusDTO.class);
    }

    /**
     * Calculates the bonus amount for an employee based on salary and season.
     * @param salary The salary of the employee.
     * @param season The season for which bonus is calculated.
     * @return The calculated bonus amount.
     */
    public double calculateBonus(float salary,String season) {
        BonusRate bonusSeason = BonusRate.checkBonusSeason(season);
        return bonusSeason.getRate() * salary;
    }

    /**
     * Creates bonuses for all employees of a company for a specific season.
     * @param companyId The ID of the company.
     * @param season The season for which bonuses are created.
     * @return List of created bonuses.
     */
    public List<Bonus> createCompanyBonuses(long companyId, String season) {
        List<Employee> employees = employeeService.getEmployeesByCompanyId(companyId);
        List<Bonus> bonuses = new ArrayList<>();

        for (Employee employee : employees) {
            log.debug("In loop for creating company bonuses");
            bonuses.add(createEmployeeBonus(employee, season));
        }
        bonusRepository.saveAll(bonuses);
        return bonuses;
    }

    /**
     * Creates a bonus object for an employee based on the employee's salary and the given season.
     *
     * @param employee the employee for whom the bonus is being created
     * @param season   the season for which the bonus is being calculated
     * @return a Bonus object representing the bonus for the employee
     */
    private Bonus createEmployeeBonus(Employee employee, String season){

        float bonusAmount = (float) calculateBonus(employee.getSalary(), season);
        Bonus bonus = new Bonus();
        bonus.setEmployee(employee);
        bonus.setCompany(employee.getCompany());
        bonus.setAmount(bonusAmount);
        return bonus;
    }
}

