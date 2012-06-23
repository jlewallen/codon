package com.page5of4.codon.examples.application.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.page5of4.codon.Bus;
import com.page5of4.codon.examples.application.ProjectDto;
import com.page5of4.codon.examples.application.ProjectManagementService;
import com.page5of4.codon.examples.application.model.Project;
import com.page5of4.codon.examples.application.model.repositories.ProjectRepository;

@Transactional
public class ProjectManagementServiceImpl implements ProjectManagementService {
   private final ProjectRepository projectRepository;
   private final Bus bus;

   @Autowired
   public ProjectManagementServiceImpl(ProjectRepository projectRepository, Bus bus) {
      super();
      this.projectRepository = projectRepository;
      this.bus = bus;
   }

   @Override
   public void defineProject(ProjectDto projectDto) {
      Project project = new Project(projectDto.getId(), projectDto.getTitle(), projectDto.getOwner(), projectDto.getHoursRequired(), projectDto.getDefinedAt());
      projectRepository.add(project);
      project.defined();
   }

   @Override
   public void startProject(UUID id, Date date) {
      Project project = projectRepository.findById(id);
      project.start();
   }

   @Override
   public void abandonProject(UUID id, Date date, String reason) {
      Project project = projectRepository.findById(id);
      project.abandon(reason);
   }

   @Override
   public void completeProject(UUID id, Date date) {
      Project project = projectRepository.findById(id);
      project.complete();
   }
}
