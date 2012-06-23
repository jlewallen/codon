package com.page5of4.codon.examples.webapp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Lists;
import com.page5of4.codon.examples.application.ProjectDto;
import com.page5of4.codon.examples.application.ProjectManagementService;
import com.page5of4.mustache.SingleModelAndView;

@Controller
@RequestMapping(value = "/projects")
public class ProjectsController {
   private static final Logger logger = LoggerFactory.getLogger(ProjectsController.class);

   private final ProjectManagementService projectManagementService;

   @Autowired
   public ProjectsController(ProjectManagementService projectManagementService) {
      super();
      this.projectManagementService = projectManagementService;
   }

   @RequestMapping(method = RequestMethod.GET)
   public SingleModelAndView index() {
      List<Object> projects = Lists.newArrayList();
      return new SingleModelAndView("projects/index", projects);
   }

   @RequestMapping(method = RequestMethod.POST)
   public String define(ProjectDto projectDto) {
      projectManagementService.definedProject(projectDto);
      return "redirect:/projects";
   }

   @RequestMapping(value = "{id}/start", method = RequestMethod.POST)
   public String start(@PathVariable UUID id) {
      projectManagementService.startProject(id, new Date());
      return "redirect:/projects";
   }

   @RequestMapping(value = "{id}/abandon", method = RequestMethod.POST)
   public String abandon(@PathVariable UUID id) {
      projectManagementService.abandonProject(id, new Date());
      return "redirect:/projects";
   }

   @RequestMapping(value = "{id}/complete", method = RequestMethod.POST)
   public String complete(@PathVariable UUID id) {
      projectManagementService.completeProject(id, new Date());
      return "redirect:/projects";
   }
}
