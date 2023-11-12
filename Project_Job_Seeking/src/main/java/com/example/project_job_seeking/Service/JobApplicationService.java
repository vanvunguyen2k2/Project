package com.example.project_job_seeking.Service;

import com.example.project_job_seeking.Config.Exception.AppException;
import com.example.project_job_seeking.Config.Exception.ErrorResponseEnum;
import com.example.project_job_seeking.Repository.JobRepository;
import com.example.project_job_seeking.Repository.JobApplicationRepository;
import com.example.project_job_seeking.Repository.UserRepository;
import com.example.project_job_seeking.modal.Entity.Job;
import com.example.project_job_seeking.modal.Entity.JobStatus;
import com.example.project_job_seeking.modal.Entity.jobApplication;
import com.example.project_job_seeking.modal.Entity.User;
import com.example.project_job_seeking.modal.dto.JobApplicationRequestDto;
import com.example.project_job_seeking.modal.dto.UserDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class JobApplicationService implements IJobApplicationService {


    @Autowired
    private  JobRepository jobRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private JobApplicationRepository job_management_repository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public jobApplication create(JobApplicationRequestDto dto) {

        Set<Job> jobList = null;
        Set<User> userList = null;


        Optional<Job> optionalJob=jobRepository.findById(dto.getJob_id());

        Optional<User> optionalUser=userRepository.findById(dto.getUser_id());


        if (optionalJob.isEmpty() || optionalUser.isEmpty() ) {
            throw new AppException(ErrorResponseEnum.Not_FOUND);
        }



            Job job = optionalJob.get();
            User user = optionalUser.get();
            if (user.isWorking() == false) {

                throw new AppException(ErrorResponseEnum.FORBIDDEN_USER);

            }
            jobApplication job_management = new jobApplication();
            job_management.setJob(job);
            job_management.setUser(user);
            job_management.setJobStatus(JobStatus.PENDING);
//            APPLIEDJOB: những job mà user đã applied


            jobList = user.getAppliedJob();
            jobList.add(job_management.getJob());
            user.setAppliedJob(jobList);
//            System.out.println(user.getAppliedJob());

//            isAplliedByUser : những user đã applied job này
//            userList = job.getIsAppliedBy();
//            userList.add(job_management.getUser());
//            job.setIsAppliedBy(userList);


            return job_management_repository.save(job_management);


    }

    @Override
    public List<jobApplication> getAll() {
        return job_management_repository.findAll();
    }

    @Override
    public jobApplication cancelJobManagament(int id) {
        Optional<jobApplication> optionalJobManagement = job_management_repository.findById(id);
        System.out.println(optionalJobManagement);
        if (optionalJobManagement.isPresent()){
            jobApplication jobManagement = new jobApplication();
            System.out.println(jobManagement);
            jobManagement.setJobStatus(JobStatus.CANCEL);
            System.out.println(jobManagement);
            return job_management_repository.save(jobManagement);
        }
        return null;
    }

    public void deleteAll() {
        job_management_repository.deleteAll();
    }


    @Override
    public List<jobApplication> findByJobStatus(JobStatus jobStatus, int userID) {
        Optional<User> userOptional = userRepository.findById(userID);
        if (userOptional.isEmpty()){
            throw new AppException(ErrorResponseEnum.NOT_FOUND_USER);
//            System.out.println("Không tìm thấy người dùng");

        }
        if (jobStatus != null){
           return job_management_repository.findAllByJobStatusAndUser_Id(jobStatus, userID);
        }
        else {
            return job_management_repository.findByUser_Id(userID);
        }

    }

    @Override
    public jobApplication approvedJob(int jobManaId) {
        Optional<jobApplication> optionalJobApplication = job_management_repository.findById(jobManaId);
        if (optionalJobApplication.isEmpty()) {
            throw new AppException(ErrorResponseEnum.NOT_FOUND_JOB_APPLY);
        }
        jobApplication jobApplication = optionalJobApplication.get();
        jobApplication.setJobStatus(JobStatus.APPROVED);
        return job_management_repository.save(jobApplication);
    }


}
