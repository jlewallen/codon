package com.page5of4.codon.examples.application.impl;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;
import com.page5of4.codon.Bus;
import com.page5of4.codon.examples.application.ProjectDto;
import com.page5of4.codon.examples.application.model.Project;
import com.page5of4.codon.examples.application.model.ProjectEvents;
import com.page5of4.codon.examples.application.model.repositories.ProjectRepository;
import com.page5of4.codon.examples.messages.ProjectAbandonedMessage;
import com.page5of4.codon.examples.messages.ProjectCompletedMessage;
import com.page5of4.codon.examples.messages.ProjectDefinedMessage;
import com.page5of4.codon.examples.messages.ProjectStartedMessage;
import com.page5of4.codon.useful.DomainEvents;

public class ProjectManagementServiceSpecs {
   private ProjectRepository projectRepository;
   private ProjectManagementServiceImpl service;
   private Bus bus;

   @Before
   public void before() {
      projectRepository = mock(ProjectRepository.class);
      bus = mock(Bus.class);

      EventBus eventBus = new EventBus();
      DomainEvents domainEvents = new DomainEvents(eventBus);
      eventBus.register(new ProjectEvents(bus));

      service = new ProjectManagementServiceImpl(projectRepository, bus);
   }

   @Test
   public void when_defining_a_new_project_should_publish_project_defined_message() {
      ProjectDto projectDto = new ProjectDto(UUID.randomUUID(), "Codon", "Jacob Lewallen", 1000L, new Date());
      service.defineProject(projectDto);

      verify(bus).publish(isA(ProjectDefinedMessage.class));
   }

   @Test
   public void when_starting_a_new_project_should_publish_project_started_message() {
      UUID id = UUID.randomUUID();
      Project project = new Project(id, "Codon", "Jacob Lewallen", 1000L, new Date());
      when(projectRepository.get(id)).thenReturn(project);

      service.startProject(id, new Date());

      verify(bus).publish(isA(ProjectStartedMessage.class));
   }

   @Test
   public void when_abandoning_a_new_project_should_publish_project_abandoned_message() {
      UUID id = UUID.randomUUID();
      Project project = new Project(id, "Codon", "Jacob Lewallen", 1000L, new Date());
      when(projectRepository.get(id)).thenReturn(project);

      service.abandonProject(id, new Date(), "Tired");

      verify(bus).publish(isA(ProjectAbandonedMessage.class));
   }

   @Test
   public void when_completing_a_started_project_should_publish_project_completed() {
      UUID id = UUID.randomUUID();
      Project project = new Project(id, "Codon", "Jacob Lewallen", 1000L, new Date());
      project.start();
      when(projectRepository.get(id)).thenReturn(project);

      service.completeProject(id, new Date());

      verify(bus).publish(isA(ProjectCompletedMessage.class));
   }

   @Test(expected = RuntimeException.class)
   public void when_completing_a_new_project_should_publish_project_completed() {
      UUID id = UUID.randomUUID();
      Project project = new Project(id, "Codon", "Jacob Lewallen", 1000L, new Date());
      when(projectRepository.get(id)).thenReturn(project);

      service.completeProject(id, new Date());
   }
}
