package mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import mysite.repository.UserRepository;
import mysite.vo.UserVo;

@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void join(UserVo userVo) {
		userRepository.insert(userVo);
	}

	public UserVo getUser(String email, String password) {
		UserVo vo = userRepository.findByEmailAndPassword(email,password);
		return vo;
		
	}
	
	public UserVo getUser(Long id) {
		UserVo vo = userRepository.findById(id);
		return vo;
		
	}
	public void update(UserVo userVo) {
		userRepository.updateUser(userVo);
	}
}
