package com.page5of4.codon.examples.application;

import java.util.Date;
import java.util.UUID;

public interface ProjectManagementService {
   void defineProject(ProjectDto project);

   void startProject(UUID id, Date date);

   void abandonProject(UUID id, Date date, String reason);

   void completeProject(UUID id, Date date);
}
