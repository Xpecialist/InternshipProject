package com.example.SpringPostgress.service;

import com.example.SpringPostgress.DTO.BonusDTO;
import com.example.SpringPostgress.Enum.BonusRate;
import com.example.SpringPostgress.exception.ResourceNotFoundException;
import com.example.SpringPostgress.model.Bonus;
import com.example.SpringPostgress.model.Employee;
import com.example.SpringPostgress.repository.BonusRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class BonusService {


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BonusRepository bonusRepository;

    @Autowired
    private ModelMapper modelMapper;

    public BonusDTO saveBonus(BonusDTO bonusDTO){
        bonusRepository.save(modelMapper.map(bonusDTO, Bonus.class));
        return bonusDTO;
    }

    public BonusDTO updateBonus(BonusDTO bonusDTO){
        bonusRepository.save(modelMapper.map(bonusDTO, Bonus.class));
        return bonusDTO;
    }

    public List<BonusDTO> getAllBonuses(){
        List<Bonus> bonusList = bonusRepository.findAll();
        if (bonusList.isEmpty()) {
            throw new ResourceNotFoundException("No bonus entries found.");
        }
        return modelMapper.map(bonusList, new TypeToken<List<BonusDTO>>(){}.getType());
    }

    public boolean deleteBonus(BonusDTO bonusDTO){
        bonusRepository.delete(modelMapper.map(bonusDTO, Bonus.class));
        return true;
    }

    public BonusDTO getBonusById(Long id) {
        Optional<Bonus> bonus = bonusRepository.findById(id);
        return modelMapper.map(bonus, BonusDTO.class);
    }

    public double calculateBonus(float salary,String season) {

        BonusRate bonusSeason = BonusRate.checkBonusSeason(season);
        return bonusSeason.getRate() * salary;

    }

    public List<Bonus> createCompanyBonuses(long companyId, String season) {

        List<Employee> employees = employeeService.getEmployeesByCompanyId(companyId);
        List<Bonus> bonuses = new ArrayList<>();

        for (Employee employee : employees) {

            float bonusAmount = (float) calculateBonus(employee.getSalary(), season);
            Bonus bonus = new Bonus();

            bonus.setEmployee(employee);
            bonus.setCompany(employee.getCompany());
            bonus.setAmount(bonusAmount);

            bonuses.add(bonus);

        }
        bonusRepository.saveAll(bonuses);
        return bonuses;

    }


}
