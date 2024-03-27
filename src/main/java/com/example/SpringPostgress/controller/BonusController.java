package com.example.SpringPostgress.controller;

import com.example.SpringPostgress.DTO.BonusCriteria;
import com.example.SpringPostgress.model.Bonus;
import com.example.SpringPostgress.service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.example.SpringPostgress.DTO.BonusDTO;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/bonuses")
public class BonusController {

    @Autowired
    private BonusService bonusService;

    @GetMapping
    public List<BonusDTO> getBonuses(){
        return bonusService.getAllBonuses();
    }


    @PostMapping
    public BonusDTO saveBonus(@RequestBody BonusDTO bonusDTO) {
        return bonusService.saveBonus(bonusDTO);
    }

    @PutMapping
    public BonusDTO updateBonus(@RequestBody BonusDTO bonusDTO){
        return bonusService.updateBonus(bonusDTO);
    }

    @GetMapping("/{id}")
    public BonusDTO getBonusById(@PathVariable Long id){
        return bonusService.getBonusById(id);
    }

    @DeleteMapping
    public boolean deleteBonus(@RequestBody BonusDTO bonusDTO){
        return bonusService.deleteBonus(bonusDTO);
    }

    @PostMapping("/calculation")
    public double calculateBonus(BonusCriteria bonusCriteria )
    {
        return bonusService.calculateBonus(bonusCriteria.getSalary(), bonusCriteria.getSeason());
    }

    @PostMapping("/creation")
    public List<Bonus> createBonuses(BonusCriteria bonusCriteria){
        return bonusService.createCompanyBonuses(bonusCriteria.getCompanyId(), bonusCriteria.getSeason());
    }

}
