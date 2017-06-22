package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job job = jobData.findById(id);
        model.addAttribute("job", job);


        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if(errors.hasErrors()){
            model.addAttribute("jobForm", jobForm);
            return "new-job";
        }

        String name1 = jobForm.getName();
        Employer employer1 = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location location1 = jobData.getLocations().findById(jobForm.getLocationId());
        CoreCompetency coreCompetency1 = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
        PositionType positionType1 = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());


        Job newJob = new Job(name1, employer1, location1, positionType1, coreCompetency1);

        jobData.add(newJob);

        int newId = newJob.getId();



        return "redirect:/job?id=" + newId;

    }
}
