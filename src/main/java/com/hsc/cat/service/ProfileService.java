package com.hsc.cat.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsc.cat.TO.CreateProfileTO;
import com.hsc.cat.VO.CreateProfileVO;
import com.hsc.cat.entity.ProfileEntity;
import com.hsc.cat.repository.ProfileRepository;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
private SkillProfileService skillProfileService;
	
	public CreateProfileTO createProfile(CreateProfileVO profileVO) {
		
		CreateProfileTO createProfileTO=new CreateProfileTO();
		
		ProfileEntity profileEntity=new ProfileEntity();
		profileEntity.setEmpId(profileVO.getUsername());
		profileEntity.setProjectRole(profileVO.getProjectRole());
		Date date=new Date();
		profileEntity.setCreationDate(date);
		profileRepository.save(profileEntity);
		
		ProfileEntity saved=profileRepository.findByEmpId(profileVO.getUsername());
		
		createProfileTO.setFirstName(profileVO.getFirstName());
		createProfileTO.setLastName(profileVO.getLastName());
		createProfileTO.setDepartment(profileVO.getDepartment());
		createProfileTO.setEmail(profileVO.getEmail());
		createProfileTO.setProjectRole(profileEntity.getProjectRole());
		createProfileTO.setUsername(profileVO.getUsername());
		createProfileTO.setSelectedSkills(profileVO.getSelectedSkills());
		
		System.out.println("\n\n\n#######################"+saved.getId());
		
		for(String skill:profileVO.getSelectedSkills()) {
			skillProfileService.saveSkillProfile(skill, saved.getId());
		}
		return createProfileTO;
	}
}
