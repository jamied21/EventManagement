package com.example.EventManagement.Services;

import java.util.List;

import com.example.EventManagement.Models.Feedback;

public interface IFeedbackService {

	Feedback saveFeedback(Feedback feedback);

	List<Feedback> getAllFeedbacks();

	Feedback findFeedbackById(Integer id);

	boolean updateFeedbackById(Integer id, Feedback feedback);

	boolean deleteFeedbackById(Integer id);

}
