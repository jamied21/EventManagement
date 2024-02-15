package com.example.EventManagement.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EventManagement.Exceptions.RegisterNotFoundException;
import com.example.EventManagement.Models.Feedback;
import com.example.EventManagement.Models.Register;
import com.example.EventManagement.Repository.FeedbackRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class FeedbackServiceImp implements IFeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	public FeedbackServiceImp(FeedbackRepository feedbackRepository) {

		this.feedbackRepository = feedbackRepository;
	}

	public Feedback saveFeedback(Feedback feedback) {
		return this.feedbackRepository.save(feedback);

	}

	public List<Feedback> getAllFeedbacks() {
		return this.feedbackRepository.findAll();
	}

	public Feedback findFeedbackById(Integer id) {
		return this.feedbackRepository.findById(id).orElse(null);
	}

	public boolean updateFeedbackById(Integer id, Feedback feedback) {
		boolean result = this.feedbackRepository.existsById(id);

		if (result)

		{
			this.feedbackRepository.save(feedback);
			return true;
		}

		return false;

	}

	public boolean deleteFeedbackById(Integer id) {

		boolean result = this.feedbackRepository.existsById(id);

		if (result)

		{
			this.feedbackRepository.deleteById(id);
			return true;
		}

		return false;

	}

	public Register findRegisterAndEventById(Integer registerId) {
		Register register = feedbackRepository.findRegisterById(registerId);

		if (register != null) {

			register.getEvent();

			return register;
		} else {
			throw new RegisterNotFoundException("Register with ID " + registerId + " not found");
		}

	}

}
