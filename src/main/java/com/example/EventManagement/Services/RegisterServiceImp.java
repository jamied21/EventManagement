package com.example.EventManagement.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EventManagement.Models.Event;
import com.example.EventManagement.Models.Register;
import com.example.EventManagement.Models.User;
import com.example.EventManagement.Repository.RegisterRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class RegisterServiceImp implements IRegisterService {

	@Autowired
	private RegisterRepository registerRepository;
	@Autowired
	private EventServiceImp eventService;
	@Autowired
	private UserServiceImp userService;

	public RegisterServiceImp(RegisterRepository registerRepository, EventServiceImp eventService,
			UserServiceImp userService) {
		this.registerRepository = registerRepository;
		this.eventService = eventService;
		this.userService = userService;
	}

	public Register saveRegister(Register register) {
		return this.registerRepository.save(register);

	}

	public List<Register> getAllRegisters() {
		return this.registerRepository.findAll();
	}

	public Register findRegisterById(Integer id) {
		return this.registerRepository.findById(id).orElse(null);
	}

	public boolean updateRegisterById(Integer id, Register register) {
		boolean result = this.registerRepository.existsById(id);

		if (result)

		{
			this.registerRepository.save(register);
			return true;
		}

		return false;

	}

	public boolean deleteRegisterById(Integer id) {

		boolean result = this.registerRepository.existsById(id);

		if (result)

		{
			this.registerRepository.deleteById(id);
			return true;
		}

		return false;

	}

	@Override
	public List<Register> findRegistrationsByUserId(Integer userId) {
		return registerRepository.findByUsersId(userId);
	}

	public List<User> getAttendingUsersForEvent(Integer eventId) {
		return registerRepository.findAttendingUsersByEventId(eventId);
	}

	@Override
	public List<Register> registerUsersToEvent(Integer eventId, Set<Integer> userIds) {
		Event event = eventService.findEventById(eventId);

		return userIds.stream()
				.map((id) -> this.registerRepository.save(
						new Register(false, LocalDateTime.now(), null, event, this.userService.findOwnerById(id))))
				.toList();
	}

	@Override
	public List<User> getNotAttendingUserForEvent(Integer eventId) {
		return this.registerRepository.findNonAttendingUsersByEventId(eventId);
	}
}
